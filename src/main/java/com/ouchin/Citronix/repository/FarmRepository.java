package com.ouchin.Citronix.repository;

import com.ouchin.Citronix.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
