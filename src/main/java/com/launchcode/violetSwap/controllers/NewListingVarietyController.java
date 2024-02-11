package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;
import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//connects to user/new-listing and user/new-variety
@Controller
@RequestMapping("user")
public class NewListingVarietyController {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private UserRepository userRepository;

    //________________________________________________________________________________________________user/new-listing.html - make a new listing
    @GetMapping("new-listing")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        //model.addAttribute("varieties", varietyRepository.findAll()); //note for future to pass in the available AV varieties, does not have to follow this naming convention/structure
        model.addAttribute("maturityLevels", Maturity.values());//enum Maturity
        return "user/new-listing";
    }

    @PostMapping("new-listing")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing, Errors errors, Model model, HttpServletRequest request){
        if (errors.hasErrors()){
            return "user/new-listing";
        } else{
            HttpSession session = request.getSession(); //get session
            String username = session.getAttribute("username").toString(); //get username from session
            User theUser = userRepository.findByUsername(username); //get user from userRepository
            newListing.setUser(theUser); //set the user of the new listing


            listingRepository.save(newListing);//if no errors, save listing to repository
        }
        return "redirect:user/details";
    }
    //________________________________________________________________________________________________
    //________________________________________________________________________________________________ user/new-variety.html - add a new variety

    //in progress

    //________________________________________________________________________________________________
}

