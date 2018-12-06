package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Question;

import java.util.List;

public interface QuestionDAO {
    //returns all questions
    List<Question> getAllQuestions();

    //given the String q returns all questions which have part of that text
    List<Question> find(String t);
}
