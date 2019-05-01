package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.MeetingDAO;
import com.spe.breadcrumbs.dao.MeetingDbDAO;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/meetings")

public class MeetingAPI {
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    @RequestMapping(method = RequestMethod.GET,value = "{userId}")
    public ResponseEntity getMeeting(@PathVariable Long userId){
        Meeting m = meetingDAO.getMeeting(userId);
        if(m != null){
            return new ResponseEntity<>(m, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(m,HttpStatus.NOT_FOUND);
        }
    }
}
