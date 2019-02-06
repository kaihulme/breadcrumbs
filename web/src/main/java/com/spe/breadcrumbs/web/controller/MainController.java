package com.spe.breadcrumbs.web.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class MainController{

    @RequestMapping("/index")
    public String index() { return "index"; }

    @RequestMapping("/questions")
    public String allQuestions(){
        return "views/questions";
    }

    @RequestMapping("/management")
    public String management(){
        return "views/management";
    }

    @RequestMapping("/paths")
    public String paths(){
        return "views/paths";
    }

}