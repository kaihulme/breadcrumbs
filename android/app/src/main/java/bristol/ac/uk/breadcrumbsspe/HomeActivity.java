package bristol.ac.uk.breadcrumbsspe;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bristol.ac.uk.breadcrumbsspe.api.MeetingHandler;
import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.entity.User;


public class HomeActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FloatingActionButton cameraButton = findViewById(R.id.camera_button);
//        startMeeting(cameraButton);

        Intent i = getIntent();
        int qIndex = i.getIntExtra("CURRENT_QUESTION", -1);
        System.out.print("qIndex" + qIndex);
        if(qIndex != -1 && qIndex != 8) {
            updateMap(qIndex);
        }

        if(((MapState)this.getApplication()).getCurrentQuestion() == 8)
            startMeeting(cameraButton);

        Animation scale_fab_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_in);
        cameraButton.startAnimation(scale_fab_in);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation scale_fab_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_out);
                cameraButton.startAnimation(scale_fab_out);
                startActivity(new Intent(HomeActivity.this, QRCodeScannerActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
        helpButton(cameraButton);

        drawMap(cameraButton);
        updateScore();
        makeDrawer();
        navigationView.setCheckedItem(R.id.nav_map);
    }

    private void drawMap(FloatingActionButton cameraButton) {
        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting Map...");
        progressDialog.show();
        ImageView map = findViewById(R.id.map);
        String url = ((MapState) this.getApplication()).getCurrentMap();
        Picasso.get().load(url).into(map, new com.squareup.picasso.Callback(){
            @Override
            public void onSuccess() {
                progressDialog.cancel();
            }

            @Override
            public void onError(Exception e) {
                progressDialog.cancel();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("No connection.")
                        .setMessage("Please reload or try again later.")
                        .setPositiveButton("Reload", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Animation scale_fab_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_out);
                                cameraButton.startAnimation(scale_fab_out);
                                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
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

    private void startMeeting(FloatingActionButton cameraButton){
        MeetingHandler meetingHandler = new MeetingHandler();
        meetingHandler.getMeeting(this, cameraButton, UserInSession.getUser().getId());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HomeActivity.this, WelcomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
