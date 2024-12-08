package com.cg.springsecurity.zero_to_master.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

    @GetMapping("/notices")
    public String getNoticesDetails() {
        return "Here are the Notices details from the DB!";
    }
}
