package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.Quiz;
import com.spe.breadcrumbs.entity.User;

import java.util.List;

public interface QuizDAO {

    Quiz getQuiz(int id);
    //get all questions with this quiz's id
    List<Question> getQuestions(Long id);
    List<User> getUsers(int id);
}
