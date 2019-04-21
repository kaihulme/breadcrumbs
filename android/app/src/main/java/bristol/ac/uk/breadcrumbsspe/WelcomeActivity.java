package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bristol.ac.uk.breadcrumbsspe.entity.User;

public class WelcomeActivity extends DrawerActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        displayName();
        displayScore();
        displayDetails();
        goToHome();
        makeDrawer();
        navigationView.setCheckedItem(R.id.nav_account);
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

    private void displayDetails(){
        TextView detailsView = findViewById(R.id.details_textview);
        User u = UserInSession.getUser();
        String details = "";
        details += u.getFirstName();
        details += " ";
        details += u.getLastName();
        details += "\nEmail: ";
        details += u.getEmail();
        detailsView.setText(details);
    }

    private void goToHome(){
        Button begin_btn = findViewById(R.id.begin_btn);
        begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
