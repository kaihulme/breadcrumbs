package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Hint;
import com.spe.breadcrumbs.entity.Question;

import java.util.List;

public interface QuestionDAO {

    //returns all questions
    List<Question> getAllQuestions();

    //given the String q returns all questions which have part of that text
    List<Question> find(String t);

    //finds a question by its id. return null if not found
    Question getQuestion(Long id);

    boolean deleteQuestion(Long id);

    //get questions that user with id userId has answered
    List<Question> getQuestionsAnswered(Long userId);

    //finds a question by its code
    Question findByCode(String code);

    List<Choice> getChoices(Long questionId);

    List<Hint> getHints(Long questionId);


    boolean addHint(Hint h, Long question_id);

    boolean deleteHint(Long hint_id);

    boolean updateHint(Hint h);

    boolean updateHintLocation(Hint h);

    boolean updateChoices(Long questionId, List<Choice> choices);

    boolean update(Long id, Question q);

    boolean deleteChoice(Long choiceId);
    boolean updateLocation(Long id, Question q);
}
