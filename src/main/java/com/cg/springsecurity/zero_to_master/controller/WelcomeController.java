package com.cg.springsecurity.zero_to_master.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/welcome")
    public String sayWelcome()
    {
        return "Welcome to spring application without security";
    };
}

