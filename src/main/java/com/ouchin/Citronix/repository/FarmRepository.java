package com.ouchin.Citronix.repository;

import com.ouchin.Citronix.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {

    @Query("SELECT f FROM Farm f WHERE (:name IS NULL OR f.name LIKE CONCAT('%', :name, '%')) AND (:location IS NULL OR f.location LIKE CONCAT('%', :location, '%'))")
    List<Farm> findFarmsByCriteria(
            @Param("name") String name,
            @Param("location") String location
    );
}
