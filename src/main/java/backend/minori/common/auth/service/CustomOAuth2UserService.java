package backend.minori.common.auth.service;

import backend.minori.api.user.repository.UserRepository;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.common.auth.OAuthAttributes;
import backend.minori.domain.SocialType;
import backend.minori.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private  final UserRepository userRepository;
    private static final String GOOGLE =  "google";
    private static final String NAVER =  "naver";
    private static final String KAKAO =  "kakao";
    private static final String TWITTER =  "twitter";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = loadOAuth2User(userRequest);
        OAuthAttributes authAttributes = getOAuthAttributes(userRequest, oAuth2User);
        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());
        User createdUser = getUserOrSaveIfNotPresent(authAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                oAuth2User.getAttributes(),
                authAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private OAuth2User loadOAuth2User(OAuth2UserRequest userRequest) {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        return delegate.loadUser(userRequest);
    }

    private OAuthAttributes getOAuthAttributes(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());

        return OAuthAttributes.of(socialType, userNameAttributeName, attributes);
    }

    private SocialType getSocialType (String registrationId){
        return switch (registrationId.toLowerCase()) {
            case GOOGLE -> SocialType.GOOGLE;
            case NAVER -> SocialType.NAVER;
            case KAKAO -> SocialType.KAKAO;
            case TWITTER -> SocialType.TWITTER;
            default -> throw new IllegalArgumentException("Unknown registration id: " + registrationId);
        };
    }

    private User getUserOrSaveIfNotPresent(OAuthAttributes authAttributes, SocialType socialType) {
        User findUser = userRepository.findBySocialTypeAndSocialId(socialType,
                authAttributes.getOAuth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(authAttributes, socialType);
        }

        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return userRepository.save(createdUser);
    }
}