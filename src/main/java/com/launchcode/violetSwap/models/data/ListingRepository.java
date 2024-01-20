package com.launchcode.violetSwap.models.data;

import com.launchcode.violetSwap.models.Listing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Integer> {
}

