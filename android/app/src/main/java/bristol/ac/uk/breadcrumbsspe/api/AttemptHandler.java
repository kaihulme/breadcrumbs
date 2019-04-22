package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;
import android.widget.Button;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.LoginActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.R;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.rgb;

public class AttemptHandler implements Callback<ResponseBody> {
    private QuestionActivity questionActivity;
    private Button b;
    private Question q;
   public  AttemptHandler(QuestionActivity questionActivity,Button b,Question q){
        this.questionActivity = questionActivity;
        this.b =b;
        this.q = q;
    }
    public void setQuestionActivity(QuestionActivity questionActivity) {
        this.questionActivity = questionActivity;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
       if(response.isSuccessful()){
           if (questionActivity.buttons.indexOf(b) == questionActivity.answer) {
               LoginActivity.currentQuestion.start();
               q.correctAttemptMade(true);
               b.setBackgroundColor(rgb(0, 191, 0));
               //wait
               Intent nextQ = new Intent(questionActivity, HomeActivity.class);
               nextQ.putExtra("CURRENT_QUESTION", q.getId().intValue());
               //System.out.println(q.getId() + "getId");
               questionActivity.startActivity(nextQ);
               questionActivity.overridePendingTransition( R.anim.fade_in, R.anim.fade_out );
               questionActivity.finish();
           } else {
               q.correctAttemptMade(false);
               b.setBackgroundColor(rgb(191, 0, 0));
               b.setEnabled(false);
           }
       }else{
           System.out.println(response.code());
       }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }

}
