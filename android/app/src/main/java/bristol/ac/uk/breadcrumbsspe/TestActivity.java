package bristol.ac.uk.breadcrumbsspe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestionsAsync;

public class TestActivity extends AppCompatActivity {
    public static TextView question_textView;
    private static String testString = "testing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        question_textView =   findViewById(R.id.questionTest_textView);
        showQuestions();
    }
    public void showQuestions(){
        Button getQuestion_btn = findViewById(R.id.getQuestion_btn);
        getQuestion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchQuestionsAsync process = new FetchQuestionsAsync();
                process.execute();
            }
        });
    }

    public static void setTestString(String testString) {
        TestActivity.testString = testString;
    }
}
