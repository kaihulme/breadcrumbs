package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.entity.User;


public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;


    //TODO help button on the toolbar with help overlays for map, search area


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        int qIndex = i.getIntExtra("CURRENT_QUESTION", -1);
        System.out.print("qIndex" + qIndex);
        if(qIndex != -1)
            updateMap(qIndex);
        drawMap();
        updateScore();

        mDrawerLayout = findViewById(R.id.home_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_map:
                                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                return true;
                            case R.id.nav_camera:
                                startActivity(new Intent(HomeActivity.this, QRCodeScannerActivity.class));
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                return true;
                            case R.id.nav_help:
                                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                return true;
                        }
                        return true;
                    }
                });

        FloatingActionButton camera_button = findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Opening Camera", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(HomeActivity.this, QRCodeScannerActivity.class));
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
//                Intent currentQuestion = getIntent();
//                int index = currentQuestion.getIntExtra("CURRENT_QUESTION",-1);
//                Intent toQrCode = new Intent(HomeActivity.this,QRCodeScannerActivity.class);
//                toQrCode.putExtra("CURRENT_QUESTION",index);
//                startActivity(toQrCode);
//                //this is only for the Beta Version final release will work via QRCodeScanner
//                Intent prevQuestion = getIntent();
//                int index = (prevQuestion.getIntExtra("PREV_QUESTION",-1) + 1) % 4;
//                Intent answerQuestion = new Intent(HomeActivity.this, QuestionActivity.class);
//                answerQuestion.putExtra("CURRENT_QUESTION",index);
//                startActivity(answerQuestion);
            }
        });

        Toolbar toolbar = findViewById(R.id.home_toolbar);
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

    /*private void buttonClick(){
        Button scanButton = findViewById(R.id.scan_btn);
        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent prevQuestion = getIntent();
                int index = (prevQuestion.getIntExtra("PREV_QUESTION",-1) + 1) % 4;
                Intent answerQuestion = new Intent(HomeActivity.this, QuestionActivity.class);
                answerQuestion.putExtra("CURRENT_QUESTION",index);
                startActivity(answerQuestion);
            }
        });
    }*/

    private void drawMap () {
        int mapID = ((MapState) this.getApplication()).getCurrentMap();
        ImageView map = findViewById(R.id.map);
        map.setImageResource(mapID);
    }

    private void updateScore(){
        TextView score_textview = findViewById(R.id.home_score);
        String scoreText = "Your current score is: ";
        User u = UserInSession.getUser();
        scoreText += u.getScore();
        score_textview.setText(scoreText);
    }

    private void updateMap(int questionNumber) {
        ((MapState) this.getApplication()).setCurrentQuestion(questionNumber);
    }

}
