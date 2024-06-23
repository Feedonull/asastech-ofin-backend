package com.asastech.ofin.account.repository;

import com.asastech.ofin.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByAppUserId(String customerId);

}
