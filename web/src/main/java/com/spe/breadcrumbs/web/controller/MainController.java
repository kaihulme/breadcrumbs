package com.spe.breadcrumbs.web.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
public class MainController{

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "index";
    }


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