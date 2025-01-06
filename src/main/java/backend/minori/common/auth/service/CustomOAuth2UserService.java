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
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes authAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        User createdUser = getUser(authAttributes, socialType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getKey())),
                attributes,
                authAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getRole()
        );
    }

    private SocialType getSocialType(String registrationId) {
        return SocialType.GOOGLE;
    }

    private User getUser(OAuthAttributes authAttributes, SocialType socialType) {
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
