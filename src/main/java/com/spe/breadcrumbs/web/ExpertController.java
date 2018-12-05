package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.entity.Expert;
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
public class ExpertController{

    private ExpertRepository expertRepo;

   @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("/participants")
    public String participants(){ //once given the correct email and password,
        return "views/participants";
    }
}