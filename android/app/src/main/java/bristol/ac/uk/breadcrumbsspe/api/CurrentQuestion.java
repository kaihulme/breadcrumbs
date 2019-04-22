package bristol.ac.uk.breadcrumbsspe.api;

import java.util.List;

import bristol.ac.uk.breadcrumbsspe.UserInSession;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentQuestion implements Callback<List<Question>> {

    private Long currentQuestion;
    public void start(Long userId){
        AttemptService attemptService = RetrofitClient.retrofit.create(AttemptService.class);
        User u = UserInSession.getUser();
        Call<List<Question>> questionsCall = attemptService.getQuestionsAnswered(u.getId());
        questionsCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
        if(response.isSuccessful() && response.body()!= null){
            List<Question> questions = response.body();
            Question lastQuestion = questions.get(questions.size() - 1);
            currentQuestion = lastQuestion.getId() + 1;
        }

    }

    public Long getCurrentQuestion(Long userId){
        start(userId);
        return currentQuestion;
    }

    @Override
    public void onFailure(Call<List<Question>> call, Throwable t) {
        t.printStackTrace();
    }
}
