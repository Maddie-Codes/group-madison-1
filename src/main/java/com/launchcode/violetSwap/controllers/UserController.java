package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.LoginType;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.UserService;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static final String userSessionKey = "user";


    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @GetMapping
    public String setUser(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();
        String authUsername;
        LoginType loginType = null;

        if (principal instanceof OAuth2AuthenticationToken) {
            // github
            authUsername = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("login");
            loginType = LoginType.OAUTH_GITHUB;

            if (authUsername == null) {
                // gmail
                String tokenEmail = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("email");
                authUsername = tokenEmail.split("@")[0];
                loginType = LoginType.OAUTH_GOOGLE;
            }
        } else {
            authUsername = principal.getName();
        }

        User currentUser = userRepository.findByUsername(authUsername);

        if (currentUser == null) {
            User newUser = new User(authUsername, loginType);
            userRepository.save(newUser);
            currentUser = newUser;
        }

        setUserInSession(request.getSession(), currentUser);

        return "redirect:/user/myDetails";
    }

    @GetMapping("/myDetails")
    public String displayUserPage(HttpServletRequest request, Model model) {

        User currentUser = userService.getUserFromSession(request.getSession());

        model.addAttribute("user", currentUser);

        return "user/details";
    }
}
