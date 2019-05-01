package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.Meeting;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MeetingService {
    @GET("meetings/{userId}")
    Call<Meeting> getMeeting(@Path("userId") Long userId);
}
