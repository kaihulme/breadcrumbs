package bristol.ac.uk.breadcrumbsspe.api;

import java.util.List;
import java.util.Map;

import bristol.ac.uk.breadcrumbsspe.entity.Attempt;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AttemptService {
    @POST("attempts")
    Call<ResponseBody> addAttempt(@Body Attempt attempt);

    @GET("attempts/{userId}")
    Call<List<Question>> getQuestionsAnswered(@Path("userId") Long userId);
}
