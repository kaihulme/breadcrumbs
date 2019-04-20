package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bristol.ac.uk.breadcrumbsspe.entity.User;

public class WelcomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        displayName();
        displayScore();
        displayDetails();
        goToHome();
        makeDrawer();
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

    private void makeDrawer(){
        mDrawerLayout = findViewById(R.id.welcome_drawer_layout);

        NavigationView navigationView = findViewById(R.id.welcome_nav_view);
        navigationView.setCheckedItem(R.id.nav_account);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_account:
                                startActivity(new Intent(WelcomeActivity.this, WelcomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_map:
                                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_camera:
                                startActivity(new Intent(WelcomeActivity.this, QRCodeScannerActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_sponsors:
                                startActivity(new Intent(WelcomeActivity.this, SponsorActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                        }
                        return true;
                    }
                });


        Toolbar toolbar = findViewById(R.id.welcome_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
