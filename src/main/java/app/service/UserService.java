package app.service;

import app.exception.*;
import app.model.entity.user.Gender;
import app.model.entity.user.Country;
import app.web.dto.user.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import app.model.entity.user.User;
import app.model.entity.user.UserProperties;
import app.model.entity.user.UserRole;
import app.repository.user.UserRepository;
import app.web.dto.user.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, HttpSession session) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.session = session;
    }

    public User register(RegisterRequest registerRequest){

        String email = registerRequest.getEmail().trim().toLowerCase();
        Optional<User>optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User with [%s] email already exists.".formatted(registerRequest.getEmail()));
        }

        String profilePicture;

        if (registerRequest.getGender() == Gender.MALE) {
            profilePicture = "/images/default-avatar-man.png";
        } else {
            profilePicture = "/images/default-avatar-woman.png";
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(email)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .country(registerRequest.getCountry())
                .gender(registerRequest.getGender())
                .profilePicture(profilePicture)
                .role(UserRole.USER)
                .isActive(true)
                .createdOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();

        return userRepository.save(user);

    }

    public User ensureDefaultAdmin(UserProperties.DefaultUser defaultUser) {
        if (defaultUser == null) {
            throw new InvalidUserDataException("Default user properties are required.");
        }

        String email = defaultUser.getEmail().trim().toLowerCase();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRole() != UserRole.ADMIN || !Boolean.TRUE.equals(user.getIsActive())) {
                user.setRole(UserRole.ADMIN);
                user.setIsActive(true);
                user.setUpdatedOn(LocalDateTime.now());
                return userRepository.save(user);
            }

            return user;
        }

        LocalDateTime now = LocalDateTime.now();
        Country country = defaultUser.getCountry();

        User admin = User.builder()
                .name(defaultUser.getName())
                .email(email)
                .password(passwordEncoder.encode(defaultUser.getPassword()))
                .country(country)
                .gender(Gender.FEMALE)
                .profilePicture("/images/default-avatar-woman.png")
                .role(UserRole.ADMIN)
                .isActive(true)
                .createdOn(now)
                .updatedOn(now)
                .build();

        return userRepository.save(admin);
    }

    public User login(LoginRequest loginRequest){
        String email = loginRequest.getEmail().trim().toLowerCase();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new InvalidLoginException("Incorrect email or password."));

        String password = loginRequest.getPassword();
        String hashsetPassword = user.getPassword();

        if (!passwordEncoder.matches(password,hashsetPassword)){
            throw new InvalidLoginException("Incorrect email or password.");
        }

        if(!user.getIsActive()){
            throw new UnauthorizedActionException("User account is not active.");
        }

        return user;
    }

    public User getCurrentUser(){
        Object userId = session.getAttribute("user_id");

        if (userId == null){
            throw new UnauthorizedActionException("User must be logged in.");
        }
        return userRepository.findById((UUID) userId).orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void logout() {
        session.invalidate();
    }

    public boolean isAdmin(){
        try{
            return getCurrentUser().getRole() == UserRole.ADMIN;
        }catch (RuntimeException e){
            return false;
        }
    }
}
