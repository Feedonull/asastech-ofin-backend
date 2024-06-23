package com.asastech.ofin.analysis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HABalanceSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private ComparisonPeriod comparisonPeriod;
    @OneToOne
    private BasePeriod basePeriod;
    private Long userId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Double result;

}
