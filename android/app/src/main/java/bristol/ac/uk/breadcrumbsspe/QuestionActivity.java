package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;*/

import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;

import static android.graphics.Color.rgb;
import static bristol.ac.uk.breadcrumbsspe.TestQuestionList.getQuestions;


//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

    private int numberOfAnswers = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        int a = i.getIntExtra("CURRENT_QUESTION",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        answer(a);
    }

    //@RequestMapping("/answerQuestion")
    //answer question with this index in question list
    private void answer(/*@RequestParam("answer")*/ int q){
        List<Button> buttons = new ArrayList<>();
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
//        FetchQuestions process = new FetchQuestions();
//        process.execute();
        List<Question> questions = getQuestions();
        Question testQuestion = questions.get(q);
        String questionText = testQuestion.getQuestion();
        TextView question_textview = (TextView) findViewById(R.id.question_textview);
        question_textview.setText(questionText);
        //set choices for question
        final int rightAnswer;
        int a = -1;
        for (int i = 0; i < 4; i++) {
            Choice c = testQuestion.getChoices().get(i);
            buttons.get(i).setText(c.getChoiceText());
            if (c.isAnswer()) a = i;
        }
        rightAnswer = a;
        for (Button b : buttons) {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttons.indexOf(b) == rightAnswer) {
                        b.setBackgroundColor(rgb(0, 191, 0));
                        //wait
                        Intent nextQ = new Intent(QuestionActivity.this, HomeActivity.class);
                        int a = q;
                        nextQ.putExtra("PREV_QUESTION",q);
                        startActivity(nextQ);
                    } else
                        b.setBackgroundColor(rgb(191, 0, 0));
                }
            });
        }
    }

}
