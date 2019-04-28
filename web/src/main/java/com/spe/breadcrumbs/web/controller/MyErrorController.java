package com.spe.breadcrumbs.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model m) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value()) return "views/error-404";
        else if (Integer.valueOf(status.toString()) == HttpStatus.INTERNAL_SERVER_ERROR.value()) return "views/error-500";

        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
