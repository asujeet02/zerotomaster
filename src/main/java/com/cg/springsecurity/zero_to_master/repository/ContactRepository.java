package com.cg.springsecurity.zero_to_master.repository;

import com.cg.springsecurity.zero_to_master.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}
