package com.asastech.ofin.repository;

import com.asastech.ofin.model.ComparisonPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonPeriodRepository extends JpaRepository<ComparisonPeriod, Long> {
}
