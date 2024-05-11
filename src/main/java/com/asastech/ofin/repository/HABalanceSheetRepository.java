package com.asastech.ofin.repository;

import com.asastech.ofin.model.HABalanceSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HABalanceSheetRepository extends JpaRepository<HABalanceSheet, Long> {
}
