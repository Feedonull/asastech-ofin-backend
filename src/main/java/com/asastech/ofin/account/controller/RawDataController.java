package com.asastech.ofin.account.controller;

import com.asastech.ofin.account.dto.ApiResponse;
import com.asastech.ofin.account.enums.IncomeType;
import com.asastech.ofin.account.model.Account;
import com.asastech.ofin.account.model.AccountBalance;
import com.asastech.ofin.account.model.Customer;
import com.asastech.ofin.account.model.CustomerEntity;
import com.asastech.ofin.account.service.impl.RawDataServiceImpl;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/account")
@AllArgsConstructor
public class RawDataController {

    private final RawDataServiceImpl rawDataService;

    @GetMapping("/get/entity/{customerId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getEntities(@PathVariable("customerId") String customerId){
        List<CustomerEntity> entities = rawDataService.getEntities(customerId);
        if(entities != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "entities retrieved successfully", entities));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "entities not found", null));
        }

    }

    @GetMapping("/get/accounts/{entityId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getAccounts(@PathVariable("entityId") String entityId){
        List<Account> accounts = rawDataService.getAccounts(entityId);
        if(accounts != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Accounts retrieved successfully", accounts));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "Accounts not found", null));
        }

    }

    @GetMapping("/get/account/balance/{accountId}/{entityId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getBalance(@PathVariable("accountId") String accountId, @PathVariable("entityId") String entityId){
        List<AccountBalance> balances = rawDataService.getBalance(accountId, entityId);
        if(balances != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Account Balances retrieved successfully", balances));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "Account Balances not found", null));
        }

    }

    @GetMapping("/get/account/expense/{startDate}/{entityId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getExpenses(@PathVariable("startDate") String startDate, @PathVariable("entityId") String entityId){
        Map<String,Object> expenses = rawDataService.getExpenses(startDate, entityId);
        if(expenses != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Account expenses retrieved successfully", expenses));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "Account expenses not found", null));
        }

    }

    @PostMapping("/get/account/income/{startDate}/{entityId}/{incomeType}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getIncome(@PathVariable("startDate") String startDate, @PathVariable("entityId") String entityId, @PathVariable("incomeType") IncomeType incomeType){
        Map<String,Object> income = rawDataService.getIncome(startDate, entityId, incomeType);
        if(income != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "Account income retrieved successfully", income));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "Account income not found", null));
        }

    }

}
