package backend.minori.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
// user 는 DB 에서의 예약어
public class User extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean isPublic;

    @Column(length = 500)
    private String introduce;

    @Column
    private String picture;

    @URL @Column
    private String socialLink;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SocialType socialType;

    @Column
    private String socialId;

    @Column
    private String refreshToken;

    @OneToMany(targetEntity = Record.class)
    private List<Record> records;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}