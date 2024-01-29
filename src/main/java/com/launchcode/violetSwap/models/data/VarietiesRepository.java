package com.launchcode.violetSwap.models.data;

import com.launchcode.violetSwap.models.Varieties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarietiesRepository extends JpaRepository<Varieties, Long> {
}
