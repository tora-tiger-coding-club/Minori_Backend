package backend.minori.common.auth.service;

import backend.minori.api.user.repository.UserRepository;
import backend.minori.common.auth.CustomOAuth2User;
import backend.minori.common.auth.OAuthAttributes;
import backend.minori.domain.SocialType;
import backend.minori.domain.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private  final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = loadOAuth2User(userRequest);
        OAuthAttributes authAttributes = getOAuthAttributes(userRequest, oAuth2User);

        SocialType socialType = getSocialType(userRequest.getClientRegistration().getRegistrationId());
        User createdUser = getUserOrSaveIfNotPresent(authAttributes, socialType);

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(createdUser.getRole().getKey());

        return new CustomOAuth2User(
                createdUser.getUserId(),
                createdUser.getEmail(),
                createdUser.getEmail(),
                createdUser.getRole(),
                List.of(simpleGrantedAuthority),
                oAuth2User.getAttributes()
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

    private SocialType getSocialType(String registrationId){
        return SocialType.fromString(registrationId);
    }

    private User getUserOrSaveIfNotPresent(OAuthAttributes authAttributes, SocialType socialType) {
        User findUser = userRepository.findBySocialTypeAndSocialId(socialType,
                authAttributes.getOAuth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(authAttributes, socialType);
        } else {
            log.info(findUser.getEmail());
        }
        return findUser;
    }

    private User saveUser(OAuthAttributes attributes, SocialType socialType) {
        User createdUser = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return userRepository.save(createdUser);
    }
}