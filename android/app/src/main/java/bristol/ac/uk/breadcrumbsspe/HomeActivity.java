package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonClick();
    }
    private void buttonClick(){
        Button scanButton = findViewById(R.id.scan_btn);
        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             //   new FetchQuestions().execute();
                Intent prevQuestion = getIntent();
                int index = (prevQuestion.getIntExtra("PREV_QUESTION",-1) + 1) % 4;
                Intent answerQuestion = new Intent(HomeActivity.this, QuestionActivity.class);
                answerQuestion.putExtra("CURRENT_QUESTION",index);
                startActivity(answerQuestion);
//                Intent test = new Intent(HomeActivity.this,TestActivity.class);
//                startActivity(test);
            }
        });
    }
}
