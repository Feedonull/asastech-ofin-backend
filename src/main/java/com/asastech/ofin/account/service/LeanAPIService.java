package com.asastech.ofin.account.service;

import com.asastech.ofin.account.enums.IncomeType;
import com.asastech.ofin.account.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeanAPIService {

    @Value("${lean.app.token}")
    private String appToken;
    public String createCustomer(String appUserId) {

        final String CREATE_USER_URL = "https://sandbox.sa.leantech.me/customers/v1";

        HttpURLConnection con = null;
        String customer_id = "";
        try {
            con = (HttpURLConnection) new URL(CREATE_USER_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("lean-app-token", appToken);

        JSONObject urls = new JSONObject();

        try {
            urls.put("app_user_id", appUserId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        con.setDoOutput(true);
        try {
            con.getOutputStream().write(urls.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            customer_id = new JSONObject(output).getString("customer_id");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return customer_id;

    }

    public List<CustomerEntity> getEntities(String customerId){
        final String GET_ENTITIES_URL = "https://sandbox.sa.leantech.me/customers/v1/"+customerId+"/entities";

        HttpURLConnection con = null;
        List<CustomerEntity> entities = new ArrayList<>();
        try {
            con = (HttpURLConnection) new URL(GET_ENTITIES_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty("lean-app-token", appToken);

        con.setDoOutput(true);

        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONArray responseArray = new JSONArray(output);
            String entityId = "";
            String bankIdentifier = "";

            for(int i = 0; i < responseArray.length(); i++) {
                CustomerEntity customerEntity = new CustomerEntity();
                entityId = responseArray.getJSONObject(i).get("id").toString();
                bankIdentifier = responseArray.getJSONObject(i).get("bank_identifier").toString();
                customerEntity.setEntityId(entityId);
                customerEntity.setBankIdentifier(bankIdentifier);
                entities.add(customerEntity);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }

    public List<Account> getAccounts(String entityId){
        final String GET_ACCOUNTS_URL = "https://sandbox.sa.leantech.me/data/v2/accounts?entity_id="+entityId+"";

        HttpURLConnection con = null;
        List<Account> accounts = new ArrayList<>();
        try {
            con = (HttpURLConnection) new URL(GET_ACCOUNTS_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("lean-app-token", appToken);

        con.setDoOutput(true);


        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONObject data = new JSONObject(output);
            JSONArray accountsArray = data.getJSONObject("data").getJSONArray("accounts");

            String accountId;
            String status;
            String currency;
            String accountType;
            String accountSubType;
            String nickname;
            String openingDate;
            List<AccountScheme> accountSchemeList;

            for(int i = 0; i < accountsArray.length(); i++) {
                Account account = new Account();
                accountId = accountsArray.getJSONObject(i).get("account_id").toString();
                status = accountsArray.getJSONObject(i).get("status").toString();
                currency = accountsArray.getJSONObject(i).get("currency").toString();
                accountType = accountsArray.getJSONObject(i).get("account_type").toString();
                accountSubType = accountsArray.getJSONObject(i).get("account_sub_type").toString();
                nickname = accountsArray.getJSONObject(i).get("nickname").toString();
                openingDate = accountsArray.getJSONObject(i).get("opening_date").toString();
                JSONArray schemes = accountsArray.getJSONObject(i).getJSONArray("account");
                // preparing account scheme list
                accountSchemeList = new ArrayList<>();
                String schemeName;
                String identification;
                String name;
                //setting the account scheme list
                for(int j = 0; j < schemes.length(); j++){
                    schemeName = schemes.getJSONObject(j).getString("scheme_name");
                    identification = schemes.getJSONObject(j).getString("identification");
                    name = schemes.getJSONObject(j).getString("name");

                    AccountScheme accountScheme = new AccountScheme();
                    accountScheme.setSchemeName(schemeName);
                    accountScheme.setIdentification(identification);
                    accountScheme.setName(name);
                    account.getAccountSchemeList().add(accountScheme);
                }
                // setting the account object
                account.setAccountId(accountId);
                account.setStatus(status);
                account.setCurrency(currency);
                account.setAccountType(accountType);
                account.setAccountSubType(accountSubType);
                account.setNickname(nickname);
                account.setOpeningDate(openingDate);

                // add the account object to the accounts list
                accounts.add(account);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    public List<AccountBalance> getBalance(String accountId, String entityId){
        final String GET_BALANCE_URL = "https://sandbox.sa.leantech.me/data/v2/accounts/"+accountId+"/balances?entity_id="+entityId;

        HttpURLConnection con = null;
        List<AccountBalance> balances = new ArrayList<>();
        try {
            con = (HttpURLConnection) new URL(GET_BALANCE_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("lean-app-token", appToken);

        con.setDoOutput(true);


        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONObject data = new JSONObject(output);
            JSONArray balanceArray = data.getJSONObject("data").getJSONArray("balances");

            String balanceAccountId;
            String creditDebitIndicator;
            AccountBalanceAmount accountBalanceAmount;
            String type;

            for(int i = 0; i < balanceArray.length(); i++) {
                AccountBalance balance = new AccountBalance();

                balanceAccountId = balanceArray.getJSONObject(i).get("account_id").toString();
                creditDebitIndicator = balanceArray.getJSONObject(i).get("credit_debit_indicator").toString();
                Double amount = balanceArray.getJSONObject(i).getJSONObject("amount").getDouble("amount");
                String currency = balanceArray.getJSONObject(i).getJSONObject("amount").getString("currency");
                type = balanceArray.getJSONObject(i).get("type").toString();

                accountBalanceAmount = new AccountBalanceAmount();
                accountBalanceAmount.setAmount(amount);
                accountBalanceAmount.setCurrency(currency);


                balance.setAccountId(accountId);
                balance.setCreditDebitIndicator(creditDebitIndicator);
                balance.setAccountBalanceAmount(new AccountBalanceAmount(amount, currency));
                balance.setType(type);

                balances.add(balance);

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return balances;
    }

    public Map<String,Object> getExpenses(String startDate, String entityId){
        final String GET_BALANCE_URL = "https://sandbox.sa.leantech.me/insights/v2/expenses?async=false&start_date="+startDate+"&entity_id="+entityId;

        HttpURLConnection con = null;
        Map<String,Object> map;

        try {
            con = (HttpURLConnection) new URL(GET_BALANCE_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("lean-app-token", appToken);

        con.setDoOutput(true);


        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String json = new JSONObject(output.toString()).getJSONObject("insights").toString();
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(json, Map.class);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public Map<String,Object> getIncome(String startDate, String entityId, IncomeType incomeType){
        final String GET_INCOME_URL = "https://sandbox.sa.leantech.me/insights/v2/income?async=false";

        HttpURLConnection con = null;

        Map<String,Object> map;

        try {
            con = (HttpURLConnection) new URL(GET_INCOME_URL).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("lean-app-token", appToken);

        JSONObject urls = new JSONObject();

        try {
            urls.put("start_date", startDate);
            urls.put("entity_id", entityId);
            urls.put("income_type", incomeType.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        con.setDoOutput(true);
        try {
            con.getOutputStream().write(urls.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String output = null;
        try {
            output = new BufferedReader(new InputStreamReader(con.getInputStream())).lines()
                    .reduce((a, b) -> a + b).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            String json = new JSONObject(output.toString()).getJSONObject("insights").toString();
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(json, Map.class);

        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        return map;
    }
}