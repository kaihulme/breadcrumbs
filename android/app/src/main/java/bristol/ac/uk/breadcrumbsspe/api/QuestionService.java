package bristol.ac.uk.breadcrumbsspe.api;


import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionService {
    @GET("questions")
    Call<Question> getQuestion(@Query("code") String code);
}
