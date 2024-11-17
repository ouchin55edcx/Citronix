package com.ouchin.Citronix.repository;

import com.ouchin.Citronix.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    long countByFarmId(Long farmId);

    @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm.id = ?1")
    double sumAreaByFarmId(Long farmId);
}
