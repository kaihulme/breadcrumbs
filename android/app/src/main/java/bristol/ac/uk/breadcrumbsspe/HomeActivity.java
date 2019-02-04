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

import java.util.Random;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;

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
                FetchQuestions process = new FetchQuestions();
                process.execute();
                //get a random question index
               // int index = new Random().nextInt(4);
                Intent prevQuestion = getIntent();
                int index = (prevQuestion.getIntExtra("PREV_QUESTION",-1) + 1) % 4;
                Intent answerQuestion = new Intent(HomeActivity.this, QuestionActivity.class);
                answerQuestion.putExtra("CURRENT_QUESTION",index);
                startActivity(answerQuestion);
            }
        });
    }
}
