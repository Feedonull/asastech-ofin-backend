package com.asastech.ofin.account.service;

import com.asastech.ofin.account.model.Customer;

public interface CustomerService {

    Customer getCustomerByAppUserId(String customerId);

    Customer createCustomer(Customer customer);

}
