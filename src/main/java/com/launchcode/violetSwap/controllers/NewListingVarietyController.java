package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;
import com.launchcode.violetSwap.models.Variety;
import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.VarietyRepository;
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
    private VarietyRepository varietyRepository;

    //________________________________________________________________________________________________user/new-listing.html - make a new listing
    @GetMapping("new-listing")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        model.addAttribute("availableVarieties", varietyRepository.findAll()); //pass in the available AV varieties
        model.addAttribute("maturityLevels", Maturity.values());//pass in enum Maturity
        return "user/new-listing";
    }

    @PostMapping("new-listing")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing, Errors errors, Model model){
        if (errors.hasErrors()){
            return "user/new-listing";
        } else{
            listingRepository.save(newListing);//if no errors, save listing to repository
        }
        return "redirect:/details";
    }
    //________________________________________________________________________________________________
    //________________________________________________________________________________________________ user/new-variety.html - add a new variety


    @GetMapping("new-variety")
    public String displayNewVarietyForm (Model model){
        model.addAttribute(new Variety());
        return"user/new-variety";
    }

    @PostMapping("new-variety")
    public String processNewVarietyForm(@ModelAttribute @Valid Variety newVariety, Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute(new Variety());
            return "user/new-variety";
        } else{
            varietyRepository.save(newVariety);

        }
        return "redirect:/user/new-listing";
    }
    //________________________________________________________________________________________________
}
