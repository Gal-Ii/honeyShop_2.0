package app.web.dto.user;

import app.model.entity.user.Country;
import app.model.entity.user.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest{
    @NotBlank(message = "Please write your first and last name.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 symbols.")
    private String name;

    @NotBlank
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 symbols.")
    private String password;

    @NotNull
    private Country country;

    @NotNull
    private Gender gender;
}