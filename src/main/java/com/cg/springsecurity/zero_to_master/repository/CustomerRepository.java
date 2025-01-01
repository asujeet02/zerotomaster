package com.cg.springsecurity.zero_to_master.repository;

import com.cg.springsecurity.zero_to_master.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}
