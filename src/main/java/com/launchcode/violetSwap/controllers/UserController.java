package com.launchcode.violetSwap.controllers;


import com.launchcode.violetSwap.models.Listing;

import com.launchcode.violetSwap.models.Email;

import com.launchcode.violetSwap.models.LoginType;
import com.launchcode.violetSwap.models.User;

import com.launchcode.violetSwap.models.data.ListingRepository;

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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private UserService userService;


    private static final String userSessionKey = "user";


    @GetMapping("/myDetails")
    public String displayUserPage(HttpServletRequest request, Model model) {

        User currentUser = userService.getUserFromSession(request.getSession());

        if (!currentUser.hasRequiredDetails()) {
            return "redirect:/user/update";
        }

        model.addAttribute("listings", currentUser.getListings());
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

        //TODO: set city, state, and lat/long here. If no data to do so, return.


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

        User userAccount = userRepository.findById(id).orElse(null); //get userAccount to delete
        if(userAccount==null){return"redirect:/user/myDetails";} //check account for null

        HttpSession session = request.getSession();//get session
        Integer userId = (Integer) session.getAttribute("user");//get user id from session

        if(userAccount.getId()==userId){ //if userAccount id and user id match,

            List<Listing> accountListings = userAccount.getListings(); //delete all listings in the account,
            for(Listing listing: accountListings){
                listingRepository.deleteById(listing.getId());
            }

            userRepository.deleteById(userId);//and delete the account
        }

        return "redirect:/login";
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
