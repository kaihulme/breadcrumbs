package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.QRCodeQuestionHandler;
import bristol.ac.uk.breadcrumbsspe.api.TextCodeQuestionHandler;
import bristol.ac.uk.breadcrumbsspe.entity.MapState;

//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

    public List<Button> buttons;
    public TextView question_textview;
    public int qIndex;
    public int answer;
    private String url;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        qIndex = i.getIntExtra("CURRENT_QUESTION",-1);
        url = i.getStringExtra("QUESTION_URL");
        code = i.getStringExtra("CODE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        buttons = new ArrayList<>();
        question_textview = findViewById(R.id.question_textview);
        updateMap(qIndex);
        answer(qIndex);
    }

    //answer question with this index in question list
    private void answer(int q){
        QRCodeQuestionHandler qrCodeQuestionHandler = new QRCodeQuestionHandler();
        qrCodeQuestionHandler.setQuestionActivity(this);
        TextCodeQuestionHandler textCodeQuestionHandler = new TextCodeQuestionHandler();
        if(code == null){
            qrCodeQuestionHandler.start(this,url);
        }else{
            textCodeQuestionHandler.start(code,qrCodeQuestionHandler);
        }
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
    }

    private void updateMap(int questionNumber) {
        ((MapState) this.getApplication()).setCurrentQuestion(questionNumber);
    }

}
