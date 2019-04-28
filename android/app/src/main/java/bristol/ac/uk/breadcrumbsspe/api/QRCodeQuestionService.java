package bristol.ac.uk.breadcrumbsspe.api;

import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QRCodeQuestionService {
    @GET(".")
    Call<Question> getQuestion();
}
