package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bristol.ac.uk.breadcrumbsspe.entity.User;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        displayName();
        displayScore();
        goToHome();
    }
    private void displayName(){
        TextView welcome_textview = findViewById(R.id.welcome_textview);
        String welcomeText = "Welcome ";
        User u = UserInSession.getUser();
        welcomeText += u.getFirstName();
        welcome_textview.setText(welcomeText);
    }

    private void displayScore() {
        TextView score_textview = findViewById(R.id.score_textview);
        String scoreText = "Score: ";
        User u = UserInSession.getUser();
        scoreText += u.getScore();
        score_textview.setText(scoreText);
    }
    private void goToHome(){
        FloatingActionButton begin_btn = findViewById(R.id.begin_btn);
        begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            }
        });


    }
}
