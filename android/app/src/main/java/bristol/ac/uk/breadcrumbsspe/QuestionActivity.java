package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;

//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

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

    //answer question with this index in question list
    private void answer(int q){
        new FetchQuestions().start(this);
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
    }

}
