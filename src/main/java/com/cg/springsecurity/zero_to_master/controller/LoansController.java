package com.cg.springsecurity.zero_to_master.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/loan")
    public String getLoanDetails() {
        return "Here are the Loan details from the DB!";
    }
}
