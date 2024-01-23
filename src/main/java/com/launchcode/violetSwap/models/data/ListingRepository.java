package com.launchcode.violetSwap.models.data;

import com.launchcode.violetSwap.models.Listing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ListingRepository extends CrudRepository<Listing, Integer> {

    List<Listing> findByVariety(String variety);

}

