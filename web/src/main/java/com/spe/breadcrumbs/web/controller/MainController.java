package com.spe.breadcrumbs.web.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.spe.breadcrumbs.dao.MapDAO;
import com.spe.breadcrumbs.dao.MapDbDAO;

import java.sql.Blob;
import java.sql.SQLException;

@SpringBootApplication
@Controller
public class MainController{

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "index";
    }

//    @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
//    public String error() { return "error"; }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(){
        return "redirect:/participants";
    }

    @RequestMapping("/questions")
    public String allQuestions(){
        return "views/questions";
    }

    @RequestMapping("/management")
    public String management(){
        return "views/management";
    }

    @RequestMapping("/account")
    public String account(){
        return "views/account";
    }

    @RequestMapping("/paths")
    public String paths(){
        return "views/paths";
    }

    @RequestMapping("/meetings")
    public String meetings(){
        return "views/meetings";
    }

}