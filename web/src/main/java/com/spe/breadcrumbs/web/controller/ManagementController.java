package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin
@Controller
@RequestMapping("/management")

public class ManagementController {

    private UserDAO userDAO = new UserDbDAO();
    private ExpertDAO expertDAO = new ExpertDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

    @RequestMapping(method = RequestMethod.GET)
    public String tableContent(Model m){
        m.addAttribute("users",userDAO.getAllUsers());
        m.addAttribute("experts", expertDAO.getAllExperts());
        m.addAttribute("questions", questionDAO.getAllQuestions());
        return "views/management";
    }

    @RequestMapping(method = RequestMethod.GET,value= "/user")
    public String addUser(Model m) {
        return "views/management_user";
    }

    @RequestMapping(method = RequestMethod.GET,value= "/expert")
    public String addExpert(Model m) {
        return "views/management_expert";
    }

    @RequestMapping(method = RequestMethod.GET,value= "/breadcrumb")
    public String tempBreadcrumb(Model m) {
        return "views/management_breadcrumb";
    }

}
