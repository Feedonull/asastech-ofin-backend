package com.asastech.ofin.analysis.repository;

import com.asastech.ofin.analysis.model.BasePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePeriodRepository extends JpaRepository<BasePeriod, Long> {
}
