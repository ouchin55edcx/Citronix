package com.ouchin.Citronix.repository;

import com.ouchin.Citronix.entity.HarvestDetails;
import com.ouchin.Citronix.entity.Tree;
import com.ouchin.Citronix.entity.embedded.HarvestDetailsId;
import com.ouchin.Citronix.entity.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarvestDetailsRepository extends JpaRepository<HarvestDetails, HarvestDetailsId> {
    boolean existsByTreeAndHarvestSeason(Tree tree, Season season);

}
