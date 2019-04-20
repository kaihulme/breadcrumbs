package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.AttemptHandler;
import bristol.ac.uk.breadcrumbsspe.api.AttemptService;
import bristol.ac.uk.breadcrumbsspe.api.QRCodeQuestionHandler;
import bristol.ac.uk.breadcrumbsspe.api.RetrofitClient;
import bristol.ac.uk.breadcrumbsspe.api.TextCodeQuestionHandler;
import bristol.ac.uk.breadcrumbsspe.entity.Attempt;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.graphics.Color.rgb;

//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

    public List<Button> buttons;
    public TextView question_text_view;
    public int qIndex;
    public int answer;
    private String url;
    private String code;
    private Question q;

    // TODO Credits activity with us and sponsors


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        q = (Question) i.getSerializableExtra("QUESTION");
        url = i.getStringExtra("QUESTION_URL");
        code = i.getStringExtra("CODE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        buttons = new ArrayList<>();
        question_text_view = findViewById(R.id.question_textview);
        answer();
    }

    //answer question with this index in question list
    private void answer(){
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
        QRCodeQuestionHandler qrCodeQuestionHandler = new QRCodeQuestionHandler();
        qrCodeQuestionHandler.setQuestionActivity(this);
        TextCodeQuestionHandler textCodeQuestionHandler = new TextCodeQuestionHandler();
        if(q != null){
            //do everything that was being done in the QRCode Question handler
            question_text_view.setText(q.getQuestion());
            for (int i = 0; i < 4; i++) {
                Choice c = q.getChoices().get(i);
                buttons.get(i).setText(c.getChoiceText());
                if(c.isAnswer()) answer = i;
            }
            for (Button b : buttons) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttemptService attemptService = RetrofitClient.retrofit.create(AttemptService.class);
                        User u = UserInSession.getUser();
                        int a = buttons.indexOf(b);
                        Choice c = q.getChoices().get(a);
                        Attempt attempt = new Attempt(u,c);
                        Call<ResponseBody> responseBodyCall = attemptService.addAttempt(attempt);
                        responseBodyCall.enqueue(new AttemptHandler(QuestionActivity.this,b,q));
                    }
                });
            }
        }
        else if(code == null){
            qrCodeQuestionHandler.start(this,url);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
