package com.asastech.ofin.account.service.impl;

import com.asastech.ofin.account.model.Customer;
import com.asastech.ofin.account.repository.CustomerRepository;
import com.asastech.ofin.account.service.CustomerService;
import com.asastech.ofin.account.service.LeanAPIService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final LeanAPIService leanAPIService;
    @Override
    public Customer getCustomerByAppUserId(String customerId) {
        return customerRepository.findByAppUserId(customerId);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        String customerId =  leanAPIService.createCustomer(customer.getAppUserId());
        customer.setCustomerId(customerId);
        return customerRepository.saveAndFlush(customer);
    }
}
