package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Accounts;
import com.cg.springsecurity.zero_to_master.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    public  final AccountsRepository accountsRepository;
    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {
        Accounts accounts = accountsRepository.findByCustomerId(id);

        if (accounts != null)
        {
            return accounts;
        }
        else
            return null;
    }

}
