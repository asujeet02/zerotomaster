package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.ContactEntity;
import com.cg.springsecurity.zero_to_master.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactEntityController {

    private final ContactRepository contactRepository;

    @PostMapping("/myContact")
    public ContactEntity saveContactInquiryDetails(@RequestBody ContactEntity contactEntity)
    {
        contactEntity.setContactId(getServiceReqNumber());
        contactEntity.setCreateDt(new Date(System.currentTimeMillis()));
        return contactRepository.save(contactEntity);
    }

    public String getServiceReqNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999999-9999)+9999;
        return "SR"+randomNumber;
    }

}
