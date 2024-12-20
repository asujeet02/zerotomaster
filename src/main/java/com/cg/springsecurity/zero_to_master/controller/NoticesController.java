package com.cg.springsecurity.zero_to_master.controller;

import com.cg.springsecurity.zero_to_master.model.Notice;
import com.cg.springsecurity.zero_to_master.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class NoticesController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices()
    {
        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if(notices!=null)
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                    .body(notices);
        else
            return null;
    }
}
