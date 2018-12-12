package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;*/

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;


//@SpringBootApplication
public class QuestionActivity extends AppCompatActivity {

    private int numberOfAnswers = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int a = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        answer(a);
    }

    //@RequestMapping("/answerQuestion")
    private void answer(/*@RequestParam("answer")*/ int rightAnswer){
        List<Button> buttons = new ArrayList<>();
        buttons.add((Button)findViewById(R.id.button_answer1));
        buttons.add((Button)findViewById(R.id.button_answer2));
        buttons.add((Button)findViewById(R.id.button_answer3));
        buttons.add((Button)findViewById(R.id.button_answer4));
        for(Button b : buttons){
            b.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(buttons.indexOf(b) == rightAnswer) {
                        b.setBackgroundColor(rgb(0, 191, 0));
                        //wait
                        startActivity(new Intent(QuestionActivity.this, HomeActivity.class));
                    }
                    else
                        b.setBackgroundColor(rgb(191, 0, 0));
                }
            });
        }
    }

}
