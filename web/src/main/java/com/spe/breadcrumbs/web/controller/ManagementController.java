package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

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


//    @GetMapping("/user")
//    public String userForm(Model m, User user) {
//        return "views/management_user";
//    }


    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute User user) {
        userDAO.addUser(user);
        return new RedirectView("http://localhost:8080/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/user")
    public String addUser(Model m) {
        m.addAttribute("user", new User());
        return "views/management_user";
    }

    @RequestMapping(method = RequestMethod.GET, value= "/expert")
    public String addExpert(Model m) {
        return "views/management_expert";
    }

    @RequestMapping(method = RequestMethod.GET, value= "/breadcrumb")
    public String tempBreadcrumb(Model m) {
        return "views/management_breadcrumb";
    }

}
