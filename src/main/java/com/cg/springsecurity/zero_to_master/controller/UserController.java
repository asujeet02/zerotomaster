package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Customer;
import com.cg.springsecurity.zero_to_master.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try{
                String hashPwd=passwordEncoder.encode(customer.getPwd());
                customer.setPwd(hashPwd);
                Customer savedCustomer=customerRepository.save(customer);

                if(savedCustomer.getId()>0)
                {
                   return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Given user details are successfully registered");
                }
                else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Registration Failed");
                }
        }
        catch(Exception ex){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred:"+ex.getMessage());
        }
    }
}