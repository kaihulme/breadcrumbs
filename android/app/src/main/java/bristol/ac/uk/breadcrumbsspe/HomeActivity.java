package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Time;

import bristol.ac.uk.breadcrumbsspe.entity.Expert;
import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.entity.Meeting;
import bristol.ac.uk.breadcrumbsspe.entity.User;


public class HomeActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent i = getIntent();
        int qIndex = i.getIntExtra("CURRENT_QUESTION", -1);
        System.out.print("qIndex" + qIndex);
        if(qIndex != -1) {
            updateMap(qIndex);
        }

        if(((MapState)this.getApplication()).getCurrentQuestion() == 8)
            startMeeting();
        drawMap();
        updateScore();
        makeDrawer();
        navigationView.setCheckedItem(R.id.nav_map);

        FloatingActionButton cameraButton = findViewById(R.id.camera_button);

        Animation scale_fab_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_in);
        cameraButton.startAnimation(scale_fab_in);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Opening Camera", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Animation scale_fab_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_out);
                cameraButton.startAnimation(scale_fab_out);
                startActivity(new Intent(HomeActivity.this, QRCodeScannerActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
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
        helpButton(cameraButton);
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

    private void drawMap() {
        ImageView map = findViewById(R.id.map);
        String url = ((MapState) this.getApplication()).getCurrentMap();
        Picasso.get().load(url).into(map);
    }

//    private void drawMap() {
//        Bitmap bm = ((MapState) this.getApplication()).getCurrentMap();
//        ImageView map = findViewById(R.id.map);
//        map.setImageBitmap(bm);
//    }

//    private void drawMap () {
//        int mapID = ((MapState) this.getApplication()).getCurrentMap();
//        ImageView map = findViewById(R.id.map);
//        map.setImageResource(mapID);
//    }

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

    private void startMeeting(){
        Expert e = new Expert("Place", "Holder", "placeholder@gmail.com", "password");
        Meeting meeting = new Meeting(e, UserInSession.getUser(), new Time(0), "placeholder location");
        System.out.println("Placeholder " + ((MapState)getApplication()).getCurrentQuestion());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
