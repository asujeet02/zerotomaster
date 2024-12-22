package com.cg.springsecurity.zero_to_master.repository;

import com.cg.springsecurity.zero_to_master.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long> {
	
	@Query(value = "SELECT * from notice_details n where GETDATE() BETWEEN n.notic_beg_dt AND n.notic_end_dt",nativeQuery=true)
	List<Notice> findAllActiveNotices();

}
