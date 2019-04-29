package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.QRCodeScannerActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.R;
import bristol.ac.uk.breadcrumbsspe.UserInSession;
import bristol.ac.uk.breadcrumbsspe.entity.Attempt;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.graphics.Color.rgb;

public class QRCodeQuestionHandler implements Callback<Question> {
    private String base_URL = "http://129.213.113.83/api/questions";
    private QuestionActivity questionActivity;
    private Question q;
    private void setURL(String url){
        base_URL = url;
        base_URL += "/";
    }

    public void start(QuestionActivity questionActivity,String url){
        setURL(url);
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.questionActivity = questionActivity;
        QRCodeQuestionService qrCodeQuestionService = retrofit.create(QRCodeQuestionService.class);
        Call<Question> questionCall = qrCodeQuestionService.getQuestion();
        questionCall.enqueue(this);
    }

    public void setQuestionActivity(QuestionActivity questionActivity) {
        this.questionActivity = questionActivity;
    }

    @Override
    public void onResponse(Call<Question> call, Response<Question> response) {
        if(response.isSuccessful() && response.body() != null){
            q = response.body();
            questionActivity.question_text_view.setText(q.getQuestion());
            for (int i = 0; i < 4; i++) {
                Choice c = q.getChoices().get(i);
                questionActivity.buttons.get(i).setText(c.getChoiceText());
                if(c.isAnswer()) questionActivity.answer = i;
            }

            for (Button b : questionActivity.buttons) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AttemptService attemptService = RetrofitClient.retrofit.create(AttemptService.class);
                        User u = UserInSession.getUser();
                        int a = questionActivity.buttons.indexOf(b);
                        Choice c = q.getChoices().get(a);
                        Attempt attempt = new Attempt(u,c);
                        Call<ResponseBody> responseBodyCall = attemptService.addAttempt(attempt);
                        responseBodyCall.enqueue(new AttemptHandler(questionActivity,b,q));
                    }
                });
            }
        }else{
            System.out.println(response.errorBody());
            Intent nextQ = new Intent(questionActivity, QRCodeCaptureActivity.class);
            nextQ.putExtra("DIALOG", true);
            questionActivity.startActivity(nextQ);
            questionActivity.overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
        }
    }

    @Override
    public void onFailure(Call<Question> call, Throwable t) {
        t.printStackTrace();
        Intent nextQ = new Intent(questionActivity, QRCodeCaptureActivity.class);
        nextQ.putExtra("DIALOG", true);
        questionActivity.startActivity(nextQ);
        questionActivity.overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
    }
}
