package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

    @GetMapping("/myDetails")
    public String displayUserPage(HttpServletRequest request, Model model) {

        Principal principal = request.getUserPrincipal();
        String authUsername;

        if (principal instanceof OAuth2AuthenticationToken) {
            authUsername = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttribute("login");
        } else {
            authUsername = principal.getName();
        }

        User currentUser = userRepository.findByUsername(authUsername);

        if (currentUser == null) {
            User newUser = new User(authUsername);
            userRepository.save(newUser);
            currentUser = newUser;
        }

        model.addAttribute("user", currentUser);

        return "user/details";
    }
}
