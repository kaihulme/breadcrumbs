package com.spe.breadcrumbs.web.api;

import com.spe.breadcrumbs.dao.AttemptDAO;
import com.spe.breadcrumbs.dao.AttemptDbDAO;
import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.dao.QuestionDbDAO;
import com.spe.breadcrumbs.entity.Attempt;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/api/attempts")

public class AttemptAPI {
    private AttemptDAO attemptDAO = new AttemptDbDAO();
    private QuestionDAO questionDAO = new QuestionDbDAO();

    //given a user and a choice, posts the attempt to the database
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addAttempt(@RequestBody Attempt attempt){
        if(attemptDAO.addAttempt(attempt)){
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    public ResponseEntity getQuestionsAnswered(@PathVariable Long userId){
        List<Question> questions = questionDAO.getQuestionsAnswered(userId);
        if(questions != null){
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }else{
            return new ResponseEntity(null,HttpStatus.NOT_FOUND);
        }
    }


}
