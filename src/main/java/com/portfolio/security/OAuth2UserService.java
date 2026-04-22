package com.portfolio.security;

import com.portfolio.entity.User;
import com.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {
        Map<String, Object> attrs = oAuth2User.getAttributes();

        String githubId = String.valueOf(attrs.get("id"));
        String avatarUrl = (String) attrs.get("avatar_url");
        String login = (String) attrs.get("login");

        // GitHub may return email as null if user has hidden it; fall back to generated email
        String email = (String) attrs.get("email");
        if (email == null || email.isBlank()) {
            email = login + "@github.noemail";
        }

        final String finalEmail = email;
        final String finalLogin = login;

        User user = userRepository.findByGithubId(githubId)
                .map(existing -> updateExistingUser(existing, avatarUrl))
                .orElseGet(() -> createNewUser(githubId, finalLogin, finalEmail, avatarUrl));

        log.info("OAuth2 login successful for user: {}", user.getUsername());
        return new CustomOAuth2User(oAuth2User, user);
    }

    private User updateExistingUser(User user, String avatarUrl) {
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    private User createNewUser(String githubId, String login, String email, String avatarUrl) {
        // Generate a unique username if the GitHub login is already taken
        String username = generateUniqueUsername(login);

        User user = User.builder()
                .githubId(githubId)
                .username(username)
                .email(email)
                .avatarUrl(avatarUrl)
                .build();

        return userRepository.save(user);
    }

    private String generateUniqueUsername(String base) {
        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + suffix++;
        }
        return candidate;
    }
}
