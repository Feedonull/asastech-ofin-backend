package com.asastech.ofin.analysis.repository;

import com.asastech.ofin.analysis.model.HABalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HABalanceSheetRepository extends JpaRepository<HABalanceSheet, Long> {
}
