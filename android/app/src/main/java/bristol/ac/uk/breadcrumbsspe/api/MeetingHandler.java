package bristol.ac.uk.breadcrumbsspe.api;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bristol.ac.uk.breadcrumbsspe.entity.Meeting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingHandler implements Callback<Meeting> {
    private AppCompatActivity Activity;

    public void getMeeting(AppCompatActivity Activity, Long userId){
        this.Activity = Activity;
        MeetingService meetingService = RetrofitClient.retrofit.create(MeetingService.class);
        Call<Meeting> meetingCall = meetingService.getMeeting(userId);
        meetingCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<Meeting> call, Response<Meeting> response) {
        if(response.isSuccessful()){
            Meeting meeting = response.body();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Activity)
                    .setTitle("You have a Meeting with an Expert!")
                    .setMessage("Press Continue to see the details of the meeting.")
                    .setNeutralButton("Continue", null);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

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
                    try{
                        tv_message.setText("You will be meeting with " + meeting.getExpert().getFirstName() + " " + meeting.getExpert().getLastName() + " at " + meeting.getMeetingTime().toString() + " at location: " + meeting.getLocation());
                    } catch (Exception e){
                        tv_message.setText("You will be meeting with EXPERT at TIME at location: LOCATION");
                        Toast.makeText(Activity, "No connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(Call<Meeting> call, Throwable t) {
        t.printStackTrace();
    }
}
