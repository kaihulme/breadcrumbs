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

    private ExpertDAO expertDAO = new ExpertDbDAO(new DBConnection());
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    @Autowired
    private SecurityService securityService;


    @RequestMapping(method = RequestMethod.GET)
    public String participants(Model m){
//        context.scan("com.spe.breadcrumbs.security");
//        context.refresh();
//        securityService = context.getBean(SecurityService.class);

        String username = securityService.findLoggedInUsername();

        System.out.println("Expert username: " + username);

        Expert expert = expertDAO.findByEmail(username);

        System.out.println("Expert ID: " + expert.getId());

        m.addAttribute("expert", expert);

        return "views/account";
    }



}
