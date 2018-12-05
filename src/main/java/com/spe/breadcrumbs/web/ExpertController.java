package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.entity.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.spe.breadcrumbs.web.ExpertRepository;

@SpringBootApplication
@RestController
public class ExpertController{

    private ExpertRepository expertRepo;

    public String login(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(String email, String password, Model m){ //once given the correct email and password,
        // logs in the expert
        Expert e = expertRepo.findByEmailAndPassword(email,password);
        return "participants";
    }
}