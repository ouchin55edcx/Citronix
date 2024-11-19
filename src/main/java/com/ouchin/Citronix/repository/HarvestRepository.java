package com.ouchin.Citronix.repository;

import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.entity.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findBySeason(Season season);

}

