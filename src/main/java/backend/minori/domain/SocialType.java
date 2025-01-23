package backend.minori.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao"),
    TWITTER("twitter");

    private final String socialTypeName;

    public static SocialType fromString(String socialTypeName) {
        for (SocialType socialType : SocialType.values()) {
            if (socialType.getSocialTypeName().equalsIgnoreCase(socialTypeName)) {
                return socialType;
            }
        }
        throw new IllegalArgumentException("Unknown registration id: " + socialTypeName);
    }
}