package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.UserDAO;
import com.spe.breadcrumbs.dao.UserListDAO;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.web.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spe.breadcrumbs.web.ExpertRepository;

@SpringBootApplication
@Controller
public class MainController{

    private UserDAO userDAO = new UserListDAO();

   @RequestMapping("index")
    public String index(){
        return "clients/embedded/index";
    }

    @RequestMapping("client.html")
    public String client(){
       return "clients/embedded/client";
    }

//    @RequestMapping("/participants_userProfile")
//    public String getUserDetail(){
//        return "views/participants_userProfile";
//    }


    //@RequestMapping("/participants/")
}