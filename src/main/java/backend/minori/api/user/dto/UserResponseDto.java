package backend.minori.api.user.dto;

import backend.minori.domain.User;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * DTO for {@link backend.minori.domain.User}
 */
@Getter
@Builder
public class UserResponseDto {
    private Long userId;

    private String username;

    @Email
    private String email;

    private boolean isPublic;

    private String introduce;

    private String picture;

    private String socialLink;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isPublic(user.isPublic())
                .introduce(user.getIntroduce())
                .picture(user.getPicture())
                .socialLink(user.getSocialLink())
                .build();
    }
}