package com.launchcode.violetSwap.controllers;

import com.launchcode.violetSwap.models.ListedData;
import com.launchcode.violetSwap.models.Listing;
import com.launchcode.violetSwap.models.data.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ListedData listedData;

    @RequestMapping
    public String search(Model model) {

      return "search";
    }

    @PostMapping("/search")
    public String searchListings(@RequestParam(name = "variety") String variety, Model model) {
        List<Listing> searchResults = listedData.findByVariety(variety);
        model.addAttribute("listings", searchResults);
        model.addAttribute("searchVariety", variety);
        return "searchVariety";
    }

}
