package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.entity.Expert;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
public class MainController{

    @RequestMapping(value = {"/","/login"},method = RequestMethod.GET)
    public String  login(Model m){
        //m.addAttribute("expert",new Expert());
        return "index";
    }

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