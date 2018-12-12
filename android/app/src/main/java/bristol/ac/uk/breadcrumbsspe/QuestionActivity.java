package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Question;

import static bristol.ac.uk.breadcrumbsspe.TestQuestionList.getQuestions;

public class QuestionActivity extends AppCompatActivity {

    private int numberOfAnswers = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        answer();
    }

    private void answer(){
        List<Button> buttons = new ArrayList<>();
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
        for(Button b : buttons){
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    List<Question> questions = getQuestions();
                    System.out.println(questions);
                    startActivity(new Intent(QuestionActivity.this, HomeActivity.class));
                }
            });
        }
    }

}
