package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.api.AttemptHandler;
import bristol.ac.uk.breadcrumbsspe.api.AttemptService;
import bristol.ac.uk.breadcrumbsspe.api.RetrofitClient;
import bristol.ac.uk.breadcrumbsspe.entity.Attempt;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class QuestionActivity extends AppCompatActivity {

    private List<Button> buttons;
    private TextView questionTextView;
    private int answer;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        question = (Question) i.getSerializableExtra("QUESTION");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        buttons = new ArrayList<>();
        questionTextView = findViewById(R.id.question_textview);
        answer();
    }

    //answer question with this index in question list
    private void answer(){
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
        if(question != null){
            questionTextView.setText(question.getQuestion());
            for (int i = 0; i < 4; i++) {
                Choice choice = question.getChoices().get(i);
                buttons.get(i).setText(choice.getChoiceText());
                if(choice.isAnswer()) answer = i;
            }
            for (Button button : buttons) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttemptService attemptService = RetrofitClient.retrofit.create(AttemptService.class);
                        User user = UserInSession.getUser();
                        int current = buttons.indexOf(button);
                        Choice choice = question.getChoices().get(current);
                        Attempt attempt = new Attempt(user,choice);
                        Call<ResponseBody> responseBodyCall  = attemptService.addAttempt(attempt);
                        responseBodyCall.enqueue(new AttemptHandler(QuestionActivity.this, button, question));
                    }
                });
            }
        } else {
            startActivity(new Intent(getApplicationContext(), QRCodeCaptureActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public int getAnswer(){
        return answer;
    }

    public List<Button> getButtons(){
        return buttons;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
