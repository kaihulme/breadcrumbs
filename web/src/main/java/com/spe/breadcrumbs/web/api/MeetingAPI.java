package com.spe.breadcrumbs.web.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/meetings")

public class MeetingAPI {
    public ResponseEntity getMeeting(){
        return null;
    }
    public ResponseEntity updateMeeting(){
        return null;
    }
}
