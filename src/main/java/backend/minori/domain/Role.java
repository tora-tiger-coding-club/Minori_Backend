package backend.minori.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자"),
    GUEST("ROLE_GUEST", "게스트");

    private static final Logger log = LoggerFactory.getLogger(Role.class);
    private final String key;
    private final String title;

    public static Role fromString(String roleTypeName) {
        for (Role roleType : Role.values()) {
            if (roleType.getKey().equals(roleTypeName)) {
                return roleType;
            }
        }
        throw new IllegalArgumentException("Unknown roleTypeName: " + roleTypeName);
    }
}
