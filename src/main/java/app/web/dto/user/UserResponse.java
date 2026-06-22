package app.web.dto.user;

import app.model.entity.user.Country;
import app.model.entity.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private UUID id;

    private String name;

    private String email;

    private UserRole role;

    private Country country;

    private String profilePicture;
}
