package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.LoginType;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.UserRepository;
import com.launchcode.violetSwap.models.dto.RegisterFormDTO;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

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

        User currentUser = getUserFromSession(request.getSession());

        if (!currentUser.hasRequiredDetails()) {
            return "redirect:/user/update";
        }

        model.addAttribute("user", currentUser);

        return "user/details";
    }

    @GetMapping("/update")
    public String displayUpdateUserDetailsForm(HttpServletRequest request, Model model) {
        User currentUser = getUserFromSession(request.getSession());
        model.addAttribute(new UpdateFormDTO());
        model.addAttribute("title", "Please Update Your User Profile");
        model.addAttribute("subtitle", "We need a little more information in your profile for the features of this site.");
        model.addAttribute("user", currentUser);

        return "user/update";
    }

    @PostMapping("/update")
    public String processUpdateUserDetailsForm(@ModelAttribute @Valid UpdateFormDTO updateFormDTO, Errors errors,
                                               HttpServletRequest request, Model model) {
        User currentUser = getUserFromSession(request.getSession());

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

//______________________________________________________________________________________________delete user
    @GetMapping("/delete/{id}")
    public String showDeletePage(@PathVariable Integer id, Model model){
        model.addAttribute("id", id);
        return "user/deleteUser";
    }

    @PostMapping("/delete/{id}")
    public String processDeletePage(@PathVariable Integer id, HttpServletRequest request){

        if(id==null){return"redirect:/user/myDetails";} //check id for null

        User userAccount = userRepository.findById(id).orElse(null); //get account user

        HttpSession session = request.getSession();//get session
        Integer userId = (Integer) session.getAttribute("user");//get user id from session

        if(userAccount!=null && userAccount.getId()==userId){ //if userAccount id and user id match,
            List<Listing> accountListings = userAccount.getListings();
            for(Listing listing: accountListings){

                //code to delete listings, then make code to delete user

            }





        }







        return "redirect:/login";
    }
}
