package bristol.ac.uk.breadcrumbsspe.entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.UserInSession;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Serializable {
    private Long id;
    private String question;
    private int noOfAttempts;
    private int score;
    private List<Choice> choices = new ArrayList<>();
    public Question(Long id,String question,int score) {
        this.id = id;
        this.question = question;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void correctAttemptMade(boolean correctAnswer) {
        if (correctAnswer) {
            noOfAttempts += 1;
            updateScore();
        }
        else {
            noOfAttempts += 1;
        }
    }

    private void updateScore() {
        if (noOfAttempts == 1) score = 100;
        else if (noOfAttempts == 2) score = 50;
        else if (noOfAttempts == 3) score = 25;
        else score = 0;

        User u = UserInSession.getUser();
        u.addToScore(score);
    }
}
