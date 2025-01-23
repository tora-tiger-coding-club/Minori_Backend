package backend.minori.api.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupRequestDto {
    private Long userId;
    private String username;
    private String introduce;
}
