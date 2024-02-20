package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.Maturity;

import com.launchcode.violetSwap.models.User;
import com.launchcode.violetSwap.models.data.ListingRepository;
import com.launchcode.violetSwap.models.data.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.launchcode.violetSwap.models.Variety;

import com.launchcode.violetSwap.models.data.VarietyRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;



//connects to user/new-listing and user/new-variety
@Controller
@RequestMapping("user")
public class NewListingVarietyController {

    @Autowired
    private ListingRepository listingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VarietyRepository varietyRepository;
    @Value("${file.upload.directory}")
    private String uploadDirectory;


    //________________________________________________________________________________________________ make a new listing
    @GetMapping("new-listing")
    public String displayNewListingForm(Model model, HttpServletRequest request) {
        model.addAttribute(new Listing());
        model.addAttribute("availableVarieties", varietyRepository.findAll()); //pass in the available AV varieties
        model.addAttribute("maturityLevels", Maturity.values());//pass in enum Maturity
        return "user/new-listing";
    }

    @PostMapping("new-listing")
    public String processNewListingForm(@ModelAttribute @Valid Listing newListing,
                                        @RequestParam("image") MultipartFile imageFile,
                                        Errors errors, Model model, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "user/new-listing";
        } else {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("user");
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty() || userId == null) {
                return "user/new-listing";
            }
            User user = optionalUser.get();

            if (!imageFile.isEmpty()) {
                try {
                    // Save the uploaded file to the specified directory
                    byte[] bytes = imageFile.getBytes();
                    String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                    String filePath = uploadDirectory + File.separator + fileName;
                    Files.write(Paths.get(filePath), bytes);

                    // Set the image path in the Listing object
                    newListing.setImagePath(filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle error while processing the uploaded file
                    // You may want to return an error message to the user or handle it appropriately
                    return "user/new-listing";
                }
            }

            newListing.setUser(user);
            listingRepository.save(newListing);
        }
        return "user/details";
    }

    //________________________________________________________________________________________________
    //------------------------------------------------------------------------------------------------make a new variety

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
//____________________________________________________________________________________________________show listings

    @GetMapping("/listings")
    public String displayListings(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        List<Listing> listings = listingRepository.findAll();
        model.addAttribute("userId",userId);
        model.addAttribute("maturityLevels", Maturity.values());
        model.addAttribute("listings", listings);
        return "search/listings";
    }

    //_______________________________________________________________________________________________update listings
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model, HttpServletRequest request) {
        if (id == null) {
            return "redirect:/error";
        }
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        Listing listing = listingRepository.findById(id).orElse(null);
        if (listing != null && userId != null && userId.equals(listing.getUser().getId())) {
            model.addAttribute("listing", listing);
            model.addAttribute("maturityLevels", Maturity.values());
            return "/search/updateListing";
        } else {
            return "redirect:/listings";
        }
    }

    @PostMapping("/update/{id}")
    public String updateListing(@PathVariable Integer id, @ModelAttribute @Valid Listing updateListing, Model model, HttpServletRequest request) {
        if (id == null) {
            // Handle null id scenario, e.g., redirect to a different page or display an error message
            return "redirect:/error";
        }
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user");
        Listing existingListing = listingRepository.findById(id).orElse(null);
        if (existingListing != null && userId != null && userId.equals(existingListing.getUser().getId())) {
            existingListing.setMaturity(updateListing.getMaturity());
            existingListing.setDescription(updateListing.getDescription());
            listingRepository.save(existingListing);

        }
        List<Listing> listings = listingRepository.findAll();
        model.addAttribute("userId",userId);
        model.addAttribute("maturityLevels", Maturity.values());
        model.addAttribute("listings", listings);
        return "search/listings";
    }

//____________________________________________________________________________________________________delete listings
    @GetMapping("/delete-listing/{id}")
    public String deleteListing(@PathVariable Integer id, Model model){
        model.addAttribute("id", id);
        return "search/deleteListing";
    }

    @PostMapping("/delete-listing/{id}")
    public String handleDeleteListing(@PathVariable Integer id, HttpServletRequest request){

        if(id==null){return"redirect:/user/listings";} //check id

        Listing listingToDelete = listingRepository.findById(id).orElse(null);;//get listing or null from id

        HttpSession session = request.getSession();//get session
        Integer userId = (Integer) session.getAttribute("user");//get user id from session

        if(listingToDelete != null && userId.equals(listingToDelete.getUser().getId())){ //if the listing exists and the session user id matches the listing user_id
            listingRepository.deleteById(id);//delete the listing
            return "redirect:/user/listings";
        }

        return "redirect:/user/listings";
    }
//_______________________________________________________________________________________________________




}

