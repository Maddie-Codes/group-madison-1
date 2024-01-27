package com.launchcode.violetSwap.models;

import com.launchcode.violetSwap.models.data.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ListedData {

    @Autowired
    ListingRepository listingRepository;

    public List<Listing> findByVariety(String variety){
        return listingRepository.findByVariety(variety);
    }
}
