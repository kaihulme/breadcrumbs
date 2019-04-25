package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import com.spe.breadcrumbs.web.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/account")

public class AccountController {

    @Autowired
    private SecurityService securityService;
    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private MeetingDAO meetingDAO = new MeetingDbDAO(new DBConnection());
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @RequestMapping(method = RequestMethod.GET)
    public String participants(Model m){
//        context.scan("com.spe.breadcrumbs.security");
//        context.refresh();
//        securityService = context.getBean(SecurityService.class);

        String username = securityService.findLoggedInUsername();
        Expert expert = expertDAO.findByEmail(username);
        List<Meeting> meetings = meetingDAO.getMeetingsWithExpert(expert.getId());

        m.addAttribute("expert", expert);
        m.addAttribute("meetings", meetings);

        return "views/account";
    }

}
