package com.asastech.ofin.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalance {

    private String accountId;
    private String creditDebitIndicator;
    private AccountBalanceAmount accountBalanceAmount;
    private String type;

}
