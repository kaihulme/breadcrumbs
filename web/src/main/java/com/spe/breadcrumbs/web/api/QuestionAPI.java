package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.dao.QuestionDbDAO;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.web.DBConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/questions")

public class QuestionAPI {
    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());

    //Questions
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getQuestions(@RequestParam(value = "code",required = false,defaultValue = "") String code){
        if(code.equals("")){
            return new ResponseEntity<>(questionDAO.getAllQuestions(), HttpStatus.OK);
        }else{
            Question q = questionDAO.findByCode(code);
            if(q == null){
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(q,HttpStatus.OK);
            }
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "{id}")
    public ResponseEntity getQuestion(@PathVariable Long id) {
        Question q = questionDAO.getQuestion(id);
        if(q != null){
            return new ResponseEntity<>(q,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
