package com.asastech.ofin.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceAmount {

    private Double amount;
    private String currency;

}
