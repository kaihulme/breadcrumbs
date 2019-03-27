package bristol.ac.uk.breadcrumbsspe.api;

import java.util.Map;

import bristol.ac.uk.breadcrumbsspe.entity.Attempt;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AttemptService {
    @POST("attempts")
    Call<ResponseBody> addAttempt(@Body Attempt attempt);
}
