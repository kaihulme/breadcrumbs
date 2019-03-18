package bristol.ac.uk.breadcrumbsspe.api;


import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextCodeQuestionHandler{
    public void start(String code,QRCodeQuestionHandler qrCodeQuestionHandler){
        QuestionService questionService = RetrofitClient.retrofit.create(QuestionService.class);
        Call<Question> questionCall = questionService.getQuestion(code);
        questionCall.enqueue(qrCodeQuestionHandler);
    }
}
