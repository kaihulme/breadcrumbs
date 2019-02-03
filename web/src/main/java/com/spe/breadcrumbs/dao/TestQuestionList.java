package com.spe.breadcrumbs.dao;

import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestQuestionList {
    private static final CopyOnWriteArrayList<Question> testQuestions = new CopyOnWriteArrayList<>();
    static {
        Question q1 = new Question(new Long(1),"Which of the following would be symptom of McCune-Albright syndrome",1);
        Question q2 = new Question(new Long(2),"Which is FALSE about insulin treatment in type 1 diabetes?",4);
        Question q3 = new Question(new Long(3),"Which of these tests is most sensitive test to localize a pheochromocytoma?", 3);

        testQuestions.add(q1);
        testQuestions.add(q2);
        testQuestions.add(q3);
        for(Question q: testQuestions){
            Choice c1 = new Choice(q.getId(),"deer",false);
            Choice c2 = new Choice(q.getId(),"kidney",true);
            Choice c3 = new Choice(q.getId(),"hat",false);
            q.setAttempts(Arrays.asList(c1,c2,c3));
        }
    }

    private TestQuestionList(){}

    public static CopyOnWriteArrayList<Question> getTestQuestions() {
        return testQuestions;
    }
}

