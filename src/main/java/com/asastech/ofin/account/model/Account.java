package com.asastech.ofin.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountId;
    private String status;
    private String currency;
    private String accountType;
    private String accountSubType;
    private String nickname;
    private String openingDate;
    private List<AccountScheme> accountSchemeList = new ArrayList<>();



}
