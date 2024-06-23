package com.asastech.ofin.account.service.impl;

import com.asastech.ofin.account.enums.IncomeType;
import com.asastech.ofin.account.model.Account;
import com.asastech.ofin.account.model.AccountBalance;
import com.asastech.ofin.account.model.CustomerEntity;
import com.asastech.ofin.account.service.LeanAPIService;
import com.asastech.ofin.account.service.RawDataService;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RawDataServiceImpl implements RawDataService {

    private final LeanAPIService leanAPIService;
    @Override
    public List<CustomerEntity> getEntities(String customerId) {
        return leanAPIService.getEntities(customerId);
    }

    @Override
    public List<Account> getAccounts(String entityId) {
        return leanAPIService.getAccounts(entityId);
    }

    @Override
    public List<AccountBalance> getBalance(String accountId, String entityId) {
        return leanAPIService.getBalance(accountId, entityId);
    }

    @Override
    public Map<String,Object> getExpenses(String startDate, String entityId) {
        return leanAPIService.getExpenses(startDate, entityId);
    }

    @Override
    public Map<String,Object> getIncome(String startDate, String entityId, IncomeType incomeType) {
        return leanAPIService.getIncome(startDate, entityId, incomeType);
    }
}
