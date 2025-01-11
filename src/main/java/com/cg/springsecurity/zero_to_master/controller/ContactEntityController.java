package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.ContactEntity;
import com.cg.springsecurity.zero_to_master.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactEntityController {

    private final ContactRepository contactRepository;

    @PostMapping("/myContact")
    //@PreFilter("filterObject.contactName!='Test'")
    @PostFilter("filterObject.contactName!= 'Test'")
    public List<ContactEntity> saveContactInquiryDetails(@RequestBody List<ContactEntity> contactEntities)
    {
        List<ContactEntity> returnContacts=new ArrayList<>();
        if(!contactEntities.isEmpty())
        {
            ContactEntity contactEntity=contactEntities.getFirst();
            contactEntity.setContactId(getServiceReqNumber());
            contactEntity.setCreateDt(new Date(System.currentTimeMillis()));
            ContactEntity saveContactEntity=contactRepository.save(contactEntity);
            returnContacts.add(saveContactEntity);
        }
        return  returnContacts;
    }

    public String getServiceReqNumber() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(999999999-9999)+9999;
        return "SR"+randomNumber;
    }

}
