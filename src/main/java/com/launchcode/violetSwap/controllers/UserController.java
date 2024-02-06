package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    //Use of path variable will change to getting userId from request or from session when auth workflow is implemented
    @GetMapping("/myDetails")
    public String displayUserPage(HttpServletRequest request, Model model) {
        
        Principal principal = request.getUserPrincipal();

        User currentUser = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", currentUser);

        return "user/details";
    }


}
