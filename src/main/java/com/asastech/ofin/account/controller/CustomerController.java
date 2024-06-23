package com.asastech.ofin.account.controller;

import com.asastech.ofin.account.dto.ApiResponse;
import com.asastech.ofin.account.model.Customer;
import com.asastech.ofin.account.service.impl.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerService;
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody Customer customer){
        Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "customer created successfully", newCustomer));

    }

    @GetMapping("/get/{customerId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ApiResponse> getCustomerByCustomerId(@PathVariable("customerId") String customerId){
        Customer customer = customerService.getCustomerByAppUserId(customerId);
        if(customer != null){
            return ResponseEntity.ok()
                    .body(new ApiResponse(true, "customer retrieved successfully", customer));
        }else{
            return ResponseEntity.ok()
                    .body(new ApiResponse(false, "customer not found", null));
        }


    }

}
