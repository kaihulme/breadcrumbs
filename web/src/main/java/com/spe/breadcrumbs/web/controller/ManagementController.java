package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/management")

public class ManagementController {

    private UserDAO userDAO = new UserDbDAO();
    private ExpertDAO expertDAO = new ExpertDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

    @RequestMapping(method = RequestMethod.GET)
    public String tableContent(Model m){
        List<Expert> experts = expertDAO.getExpertsWithQuizzes();
        List<User> users = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Expert e: experts){
            for(Quiz quiz: e.getQuizzes()){
                List<Question> q = quiz.getQuestions();
                List<User> u = quiz.getUsers();
                questions.addAll(q);
                users.addAll(u);
            }
        }
        m.addAttribute("users",users);
        m.addAttribute("experts", experts);
        m.addAttribute("questions", questions);
        return "views/management";
    }

    //////////////// USER UPDATE ADD ////////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/user/{id}")
    public String updateUser(@PathVariable Long id, Model m) {
        User match = userDAO.getUser(id);
        m.addAttribute("user", match);
        return "views/management_userEdit";
    }

    @PostMapping("/user/updateUser/{id}")
    public RedirectView updateUser(@ModelAttribute User user, @PathVariable Long id) {
        userDAO.update(id, user);
        return new RedirectView("http://localhost:8080/management");
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

    //////////////// EXPERT UPDATE ADD /////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/expert/{id}")
    public String updateExpert(@PathVariable Long id, Model m) {
        Expert match = expertDAO.getExpert(id);
        m.addAttribute("expert", match);
        return "views/management_expertEdit";
    }

    @PostMapping("/expert/updateExpert/{id}")
    public RedirectView updateExpert(@ModelAttribute Expert expert, @PathVariable Long id) {
        expertDAO.update(id, expert);
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

    //////////////// BREADCRUMB UPDATE ////////////////////////

    @RequestMapping(method = RequestMethod.GET, value= "/breadcrumb")
    public String addBreadcrumb(Model m) {
        return "views/management_breadcrumbAdd";
    }

}
