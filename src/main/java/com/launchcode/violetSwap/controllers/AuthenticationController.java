package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import com.launchcode.violetSwap.models.dto.LoginFormDTO;
import com.launchcode.violetSwap.models.dto.RegisterFormDTO;
import com.launchcode.violetSwap.security.CustomAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.Token;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    private static final String userSessionKey = "user";
    private static final String userAuthTokenKey = "userAuthToken";

    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    private static void setUserAuthToken(HttpSession session, Authentication authentication) {
        session.setAttribute(userAuthTokenKey, authentication);
    }

    @GetMapping("/register")
    public String displayRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register with Violet Swap");
        return "register";
    }

    @PostMapping("/register")
    public String processCreateNewUserForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                           Errors errors, HttpServletRequest request, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Register with Violet Swap");
            return "register";
        }

        User existingUser = userRepository.findByUsername(registerFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register with Violet Swap");
            return "register";
        }

        String password = registerFormDTO.getPassword();
        String confirmPassword = registerFormDTO.getConfirmPassword();
        if(!password.equals(confirmPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            model.addAttribute("title", "Register with Violet Swap");
            return "register";

        }

        User newUser = new User(registerFormDTO.getUsername(), registerFormDTO.getPassword(), registerFormDTO.getEmail(), registerFormDTO.getZipcode());
        userRepository.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "/home";


    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {
        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

}
