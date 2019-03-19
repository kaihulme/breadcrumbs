package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.QRCodeScannerActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import okhttp3.OkHttpClient;
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
                .addInterceptor(new BasicAuthInterceptor("jackSmith@hotmail.co.uk", "aurora44"))
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
                        if (questionActivity.buttons.indexOf(b) == questionActivity.answer) {
                            q.correctAttemptMade(true);
                            b.setBackgroundColor(rgb(0, 191, 0));
                            //wait
                            Intent nextQ = new Intent(questionActivity, HomeActivity.class);
                            nextQ.putExtra("CURRENT_QUESTION", q.getId().intValue());
                            //System.out.println(q.getId() + "getId");
                            questionActivity.startActivity(nextQ);
                        } else {
                            q.correctAttemptMade(false);
                            b.setBackgroundColor(rgb(191, 0, 0));
                            b.setEnabled(false);
                        }
                    }
                });
            }
        }else{
            System.out.println(response.errorBody());
            Intent nextQ = new Intent(questionActivity, QRCodeCaptureActivity.class);
            questionActivity.startActivity(nextQ);
        }
    }

    @Override
    public void onFailure(Call<Question> call, Throwable t) {
        t.printStackTrace();
        Intent nextQ = new Intent(questionActivity, QRCodeCaptureActivity.class);
        questionActivity.startActivity(nextQ);
    }
}
