package com.asastech.ofin.account.service;

import com.asastech.ofin.account.enums.IncomeType;
import com.asastech.ofin.account.model.Account;
import com.asastech.ofin.account.model.AccountBalance;
import com.asastech.ofin.account.model.CustomerEntity;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface RawDataService {

    List<CustomerEntity> getEntities(String customerId);
    List<Account> getAccounts(String entityId);
    List<AccountBalance> getBalance(String accountId, String entityId);
    Map<String,Object> getExpenses(String startDate, String entityId);
    Map<String,Object> getIncome(String startDate, String entityId, IncomeType incomeType);

}
