package com.spe.breadcrumbs.web.controller;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.spe.breadcrumbs.dao.MapDAO;
import com.spe.breadcrumbs.dao.MapDbDAO;

import java.sql.Blob;
import java.sql.SQLException;

@SpringBootApplication
@Controller
public class MainController{

    private MapDAO mapDAO = new MapDbDAO();

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() { return "index"; }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(){
        return "redirect:/participants";
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

    @RequestMapping("/map")
    public String map() { return "views/map"; }

    @RequestMapping(value = "/picture/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getPicture(@PathVariable("id") Long id) throws SQLException {
        Blob blob = mapDAO.getMap(id).getPicture();
        byte[] picture = blob.getBytes(1, (int)blob.length());
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<byte[]>(picture, headers, HttpStatus.OK);
    }

}