package bristol.ac.uk.breadcrumbsspe.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import bristol.ac.uk.breadcrumbsspe.entity.User;

public class JSONHandler {
    //given the string parses it and returns a list of questions
    public static List<Question> parseQuestions(String s){
        List<Question> questions = new ArrayList<>();
        try {
            JSONArray questionArray = new JSONArray(s);
            for(int i = 0; i < questionArray.length(); i++){
                JSONObject obj = questionArray.getJSONObject(i);
                Long id = obj.getLong("id");
                String question = obj.getString("question");
                Question q = new Question(id,question,0);
                //add choices
                List<Choice> choices = new ArrayList<>();
                JSONArray choicesArray = obj.getJSONArray("choices");
                for(int j = 0;  j < choicesArray.length(); j++){
                    JSONObject obj2 = choicesArray.getJSONObject(j);
                    String choiceText = obj2.getString("choiceText");
                    boolean answer = obj2.getBoolean("answer");
                    Choice c = new Choice(q.getId(),choiceText,answer);
                    choices.add(c);
                }
                q.setChoices(choices);
                questions.add(q);
            }
            return questions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static Question parseQuestion(String json) {
        return null;
    }

    public static User parseUser(String json) {
        return null;
    }
}
