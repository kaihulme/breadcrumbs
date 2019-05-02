package bristol.ac.uk.breadcrumbsspe.api;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.R;
import bristol.ac.uk.breadcrumbsspe.entity.Meeting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingHandler implements Callback<Meeting> {
    private HomeActivity homeActivity;
    private FloatingActionButton cameraButton;

    public void getMeeting(HomeActivity homeActivity, FloatingActionButton cameraButton, Long userId){
        this.homeActivity = homeActivity;
        this.cameraButton = cameraButton;
        MeetingService meetingService = RetrofitClient.retrofit.create(MeetingService.class);
        Call<Meeting> meetingCall = meetingService.getMeeting(userId);
        meetingCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<Meeting> call, Response<Meeting> response) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(homeActivity)
                .setTitle("You have a Meeting with an Expert!")
                .setMessage("Press Continue to see the details of the meeting.")
                .setNeutralButton("Continue", null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        cameraButton.setImageResource(R.drawable.ic_assignment);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });

        Button b_neutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        b_neutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b_neutral.setText("Close");
                b_neutral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
                TextView tv_message = (TextView) alertDialog.findViewById(android.R.id.message);
                if(response.isSuccessful()){
                    Meeting meeting = response.body();
                    tv_message.setText("You will be meeting with " + meeting.getExpert().getFirstName() + " " + meeting.getExpert().getLastName() + " at " + meeting.getMeetingTime().toString() + " at location: " + meeting.getLocation());
                } else {
                    tv_message.setText("You will be meeting with EXPERT at TIME at location: LOCATION");
                    Toast.makeText(homeActivity, "No connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onFailure(Call<Meeting> call, Throwable t) {
        t.printStackTrace();
    }
}
