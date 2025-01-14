package backend.minori.api.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO for {@link backend.minori.domain.User}
 */
@Getter
@Builder
public class UserDto {
    Long userId;

    String username;

    @Email
    String email;

    boolean isPublic;

    String bio;

    Boolean activated;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}