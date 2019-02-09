package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.rgb;

public class FetchQuestions implements Callback<List<Question>> {

    private List<Question> questions = new ArrayList<>();
    private QuestionActivity questionActivity;

    public void start(QuestionActivity questionActivity){
        this.questionActivity = questionActivity;
        QuestionService questionService = QuestionService.retrofit.create(QuestionService.class);
        Call<List<Question>> questionCall = questionService.getAllQuestions();
        questionCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
        if(response.isSuccessful() && response.body() != null){
            questions = response.body();
            Question q = questions.get(questionActivity.qIndex);
            questionActivity.question_textview.setText(q.getQuestion());
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
                            b.setBackgroundColor(rgb(0, 191, 0));
                            //wait
                            Intent nextQ = new Intent(questionActivity, HomeActivity.class);
                            nextQ.putExtra("PREV_QUESTION", questionActivity.qIndex);
                            questionActivity.startActivity(nextQ);
                        } else
                            b.setBackgroundColor(rgb(191, 0, 0));
                    }
                });
            }
        }else{
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Question>> call, Throwable t) {
        t.printStackTrace();
    }
}
