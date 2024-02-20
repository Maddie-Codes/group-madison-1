package com.launchcode.violetSwap.security;

import com.launchcode.violetSwap.models.LoginType;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String authUsername;
        LoginType loginType = null;

        if (authentication instanceof OAuth2AuthenticationToken) {
            // github
            authUsername = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("login");
            loginType = LoginType.OAUTH_GITHUB;

            if (authUsername == null) {
                // gmail
                String tokenEmail = ((OAuth2AuthenticationToken) authentication).getPrincipal().getAttribute("email");
                authUsername = tokenEmail.split("@")[0];
                loginType = LoginType.OAUTH_GOOGLE;
            }
        } else {
            authUsername = authentication.getName();
        }

        User currentUser = userRepository.findByUsername(authUsername);

        if (currentUser == null) {
            User newUser = new User(authUsername, loginType);
            userRepository.save(newUser);
            currentUser = newUser;
        }

        setUserInSession(request.getSession(), currentUser);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
