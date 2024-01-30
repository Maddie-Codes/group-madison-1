package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //Use of path variable will change to getting userId from request or from session when auth workflow is implemented
    @GetMapping("/{userId}")
    public String displayUserPage(@PathVariable int userId, Model model) {
        Optional optUser = userRepository.findById(userId);
        if (optUser.isEmpty()) {
            return "redirect:/";
        }
        User currentUser = (User) optUser.get();
        model.addAttribute("user", currentUser);

        return "user/details";
    }

    @GetMapping("/newUser")
    public String displayCreateNewUserForm(Model model) {
        model.addAttribute("title", "Create an Account");
        model.addAttribute(new User());

        return "user/newUser";
    }

    @PostMapping("/newUser")
    public String processCreateNewUserForm(@ModelAttribute @Valid User newUser, Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Create an Account");
            return "user/newUser";
        }
        userRepository.save(newUser);
        long newUserId = newUser.getId();
        return "redirect:/user/"+newUserId;
    }
}
