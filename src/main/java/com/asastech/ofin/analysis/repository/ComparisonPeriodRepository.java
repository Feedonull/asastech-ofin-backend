package com.asastech.ofin.analysis.repository;

import com.asastech.ofin.analysis.model.ComparisonPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComparisonPeriodRepository extends JpaRepository<ComparisonPeriod, Long> {
}
