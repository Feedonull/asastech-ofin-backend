package com.asastech.ofin.repository;

import com.asastech.ofin.model.BasePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePeriodRepository extends JpaRepository<BasePeriod, Long> {
}
