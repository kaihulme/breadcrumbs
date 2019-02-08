package bristol.ac.uk.breadcrumbsspe.api;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;

import static android.graphics.Color.rgb;
import static bristol.ac.uk.breadcrumbsspe.QuestionActivity.parseJson;

public class FetchQuestions extends AsyncTask<Void,Void,Void> {

    private QuestionActivity questionActivity;
    public FetchQuestions(QuestionActivity questionActivity){
        this.questionActivity = questionActivity;
    }
    String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("http://129.213.113.83/api/questions");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       questionActivity.questionsJSON = data;
        List<Question> questions = parseJson(data);
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
                        nextQ.putExtra("PREV_QUESTION",questionActivity.qIndex);
                        questionActivity.startActivity(nextQ);
                    } else
                        b.setBackgroundColor(rgb(191, 0, 0));
                }
            });
        }
    }
}

