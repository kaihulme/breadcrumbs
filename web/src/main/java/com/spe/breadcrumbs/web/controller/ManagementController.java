package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.User;
import oracle.jdbc.proxy.annotation.Post;
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
        //TODO make this function faster (INNER JOINs)
        m.addAttribute("users",userDAO.getAllUsers());
        m.addAttribute("experts", expertDAO.getAllExperts());
        m.addAttribute("questions", questionDAO.getAllQuestions());
        return "views/management";
    }

    @RequestMapping(method = RequestMethod.GET, value= "/user")
    public String addUser(Model m) {
        m.addAttribute("user", new User());
        return "views/management_userAdd";
    }

    @PostMapping("/addUser")
    public RedirectView addUser(@ModelAttribute User user) {
        userDAO.addUser(user);
        return new RedirectView("http://localhost:8080/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/expert")
    public String addExpert(Model m) {
        m.addAttribute("expert", new Expert());
        return "views/management_expertAdd";
    }

    @PostMapping("/addExpert")
    public RedirectView addExpert(@ModelAttribute Expert expert) {
        expertDAO.addExpert(expert);
        return new RedirectView("http://localhost:8080/management");
    }

    @RequestMapping(method = RequestMethod.GET, value= "/breadcrumb")
    public String addBreadcrumb(Model m) {
        return "views/management_breadcrumbAdd";
    }

}
