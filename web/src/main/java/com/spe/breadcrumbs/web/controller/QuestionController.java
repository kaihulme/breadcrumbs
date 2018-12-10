package com.spe.breadcrumbs.web.controller;

import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.entity.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class QuestionController {
    private QuestionDAO questionDAO;
}
