package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.Meeting;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingHandler implements Callback<Meeting> {

    public void getMeeting(Long userId){
        MeetingService meetingService = RetrofitClient.retrofit.create(MeetingService.class);
        Call<Meeting> meetingCall = meetingService.getMeeting(userId);
        meetingCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<Meeting> call, Response<Meeting> response) {
        if(response.isSuccessful()){
            Meeting m = response.body();
        }

    }

    @Override
    public void onFailure(Call<Meeting> call, Throwable t) {
        t.printStackTrace();
    }
}
