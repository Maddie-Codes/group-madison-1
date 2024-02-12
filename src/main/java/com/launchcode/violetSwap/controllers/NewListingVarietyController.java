package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;
import com.launchcode.violetSwap.models.data.ListingRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//connects to user/new-listing and user/new-variety
@Controller
@RequestMapping("user")
public class NewListingVarietyController {

    @Autowired
    private ListingRepository listingRepository;

    //________________________________________________________________________________________________user/new-listing.html - make a new listing
    @GetMapping("new-listing")
    public String displayNewListingForm(Model model) {
        model.addAttribute(new Listing());
        //model.addAttribute("varieties", varietyRepository.findAll()); //note for future to pass in the available AV varieties, does not have to follow this naming convention/structure
        model.addAttribute("maturityLevels", Maturity.values());//enum Maturity
        return "user/new-listing";
    }

    @PostMapping("new-listing")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing, Errors errors, Model model){
        if (errors.hasErrors()){
            return "user/new-listing";
        } else{
            listingRepository.save(newListing);//if no errors, save listing to repository
        }
        return "redirect:user/details";
    }
    //________________________________________________________________________________________________
    //________________________________________________________________________________________________ user/new-variety.html - add a new variety

    //in progress

    //________________________________________________________________________________________________
    @GetMapping("/listings")
    public String displayListings(Model model) {
        List<Listing> listings = listingRepository.findAll();
        model.addAttribute("maturityLevels", Maturity.values());
        model.addAttribute("listings", listings);
        return "search/listings";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        Listing listing = listingRepository.findById(id).orElse(null);
        if (listing != null) {
            model.addAttribute("listing", listing);
            model.addAttribute("maturityLevels", Maturity.values());
            return "/search/updateListing";
        } else {
            return "redirect:/listings";
        }
    }

    @PostMapping("/update/{id}")
    public String updateListing(@PathVariable Integer id,@ModelAttribute @Valid Listing updateListing, Model model) {
        Listing existingListing = listingRepository.findById(id).orElse(null);
        if (existingListing != null) {
            existingListing.setMaturity(updateListing.getMaturity());
            existingListing.setDescription(updateListing.getDescription());
            listingRepository.save(existingListing);
            // Log a message to verify that the update was successful
            System.out.println("Listing updated successfully with ID: " + id);
        } else {
            // Log a message if the listing with the specified ID was not found
            System.out.println("Listing with ID " + id + " not found.");
        }
        // Log a message to verify that the redirection is being attempted
        System.out.println("Redirecting to /user/listings...");
        return "redirect:/user/listings";
    }
}
