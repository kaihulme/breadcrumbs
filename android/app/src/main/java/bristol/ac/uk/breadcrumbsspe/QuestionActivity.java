package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;

import static android.graphics.Color.rgb;


//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

    private int numberOfAnswers = 4;
    public String questionsJSON;
    public List<Button> buttons;
    public TextView question_textview;
    public int qIndex;
    public int answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        qIndex = i.getIntExtra("CURRENT_QUESTION",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        buttons = new ArrayList<>();
        question_textview = findViewById(R.id.question_textview);
        answer(qIndex);
    }

    //@RequestMapping("/answerQuestion")
    //answer question with this index in question list
    private void answer(/*@RequestParam("answer")*/ int q){
        new FetchQuestions(this).execute();
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
//        List<Question> questions = parseJson(questionsJSON);
//        Question testQuestion = questions.get(q);
//        String questionText = testQuestion.getQuestion();
       // question_textview.setText(questionText);
        //set choices for question
        final int rightAnswer;
        rightAnswer = answer;
        //int a = -1;
//        for (int i = 0; i < 4; i++) {
//            Choice c = testQuestion.getChoices().get(i);
//            buttons.get(i).setText(c.getChoiceText());
//            if (c.isAnswer()) a = i;
//        }
       // rightAnswer = a;
    }

    //given the string parses it and returns qIndex list of questions
    public static List<Question> parseJson(String s){
        List<Question> questions = new ArrayList<>();
        try {
            JSONArray questionArray = new JSONArray(s);
            for(int i = 0; i < questionArray.length(); i++){
                JSONObject obj = questionArray.getJSONObject(i);
                Long id = obj.getLong("id");
                String question = obj.getString("question");
                Question q = new Question(id,question,0);
                //add choices
                List<Choice> choices = new ArrayList<>();
                JSONArray choicesArray = obj.getJSONArray("choices");
                for(int j = 0;  j < choicesArray.length(); j++){
                    JSONObject obj2 = choicesArray.getJSONObject(j);
                    String choiceText = obj2.getString("choiceText");
                    boolean answer = obj2.getBoolean("answer");
                    Choice c = new Choice(q.getId(),choiceText,answer);
                    choices.add(c);
                }
                q.setChoices(choices);
                questions.add(q);
            }
            return questions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
