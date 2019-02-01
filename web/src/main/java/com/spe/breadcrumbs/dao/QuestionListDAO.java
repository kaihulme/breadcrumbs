package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class QuestionListDAO implements QuestionDAO {

    private CopyOnWriteArrayList<Question> questions = TestQuestionList.getTestQuestions();
    @Override
    public List<Question> getAllQuestions() {
        return questions;
    }

    @Override
    public List<Question> find(String t) {
        List<Question> matches = new ArrayList<>();
        for(Question q: questions){
            if(q.getQuestion().contains(t)) matches.add(q);
        }
        return matches;
    }

    @Override
    public Question findById(Long id) {
        for(Question q: questions){
            if(q.getId().equals(id)) return q;
        }
        return null;
    }

    @Override
    public List<Choice> getChoices(Question q) {
        return null;
    }


}
