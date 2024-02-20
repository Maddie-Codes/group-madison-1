package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Email;
import com.launchcode.violetSwap.models.LoginType;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.UserService;
import com.launchcode.violetSwap.models.data.UserRepository;
import com.launchcode.violetSwap.models.dto.UpdateFormDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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


    @GetMapping("/myDetails")
    public String displayUserPage(HttpServletRequest request, Model model) {

        User currentUser = userService.getUserFromSession(request.getSession());

        if (!currentUser.hasRequiredDetails()) {
            return "redirect:/user/update";
        }

        model.addAttribute("isCurrentUser", true);
        model.addAttribute("displayedUser", currentUser);

        return "user/details";
    }

    @GetMapping("/update")
    public String displayUpdateUserDetailsForm(HttpServletRequest request, Model model) {
        User currentUser = userService.getUserFromSession(request.getSession());
        model.addAttribute(new UpdateFormDTO());
        model.addAttribute("title", "Please Update Your User Profile");
        model.addAttribute("subtitle", "We need a little more information in your profile for the features of this site.");
        model.addAttribute("user", currentUser);

        return "user/update";
    }

    @PostMapping("/update")
    public String processUpdateUserDetailsForm(@ModelAttribute @Valid UpdateFormDTO updateFormDTO, Errors errors,
                                               HttpServletRequest request, Model model) {
        User currentUser = userService.getUserFromSession(request.getSession());

        if(errors.hasErrors()) {
            model.addAttribute("title", "Please Update Your User Profile");
            model.addAttribute("subtitle", "We need a little more information in your profile for the features of this site.");
            model.addAttribute("user", currentUser);
            return "user/update";
        }


        currentUser.setEmail(updateFormDTO.getEmail());
        currentUser.setZipcode(updateFormDTO.getZipcode());

        userRepository.save(currentUser);

        return "redirect:/user/myDetails";
    }

    @GetMapping("/{username}")
    public String displayUserDetails(@PathVariable String username, HttpServletRequest request, Model model) {
        User userToDisplay = userRepository.findByUsername(username);
        User currentUser = userService.getUserFromSession(request.getSession());
        Boolean isCurrentUser = userToDisplay.equals(currentUser);

        if (userToDisplay == null) {
            return "user/details";
        }

        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("displayedUser", userToDisplay);
        model.addAttribute("emailToSend", new Email());

        return "user/details";
    }

}
