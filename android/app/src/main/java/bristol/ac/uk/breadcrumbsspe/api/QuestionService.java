package bristol.ac.uk.breadcrumbsspe.api;


import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface QuestionService {
    @GET("questions")
    Call<List<Question>> getAllQuestions();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://129.213.113.83/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
