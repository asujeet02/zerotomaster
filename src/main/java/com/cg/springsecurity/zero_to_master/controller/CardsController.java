package com.cg.springsecurity.zero_to_master.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {
    @GetMapping("/cards")
    public String getCardsDetails() {
        return "Here are the Cards details from the DB!";
    }
}
