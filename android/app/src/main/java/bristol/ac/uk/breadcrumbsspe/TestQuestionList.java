package bristol.ac.uk.breadcrumbsspe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Choice;
import bristol.ac.uk.breadcrumbsspe.entity.Question;

public class TestQuestionList {

    private static List<Question> questions = new ArrayList<>();
    //private static String jsonString = "";
    private static String jsonString = "[{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]}]";
    private static String choices1 = "[{\"questionId\":1,\"choiceText\":\"May have psammoma bodies\",\"answer\":true},{\"questionId\":1,\"choiceText\":\"The least common kind of thyroid carcinoma\",\"answer\":false},{\"questionId\":1,\"choiceText\":\"The type of thyroid carcinoma with the worst prognosis\",\"answer\":false},{\"questionId\":1,\"choiceText\":\"Occurs in patients with MEN II\",\"answer\":false}]";
    private static String choices2 = "[{\"questionId\":2,\"choiceText\":\"Pituitary dysfunction\",\"answer\":false},{\"questionId\":2,\"choiceText\":\"DeQuervain’s thyroiditis\",\"answer\":false},{\"questionId\":2,\"choiceText\":\"Graves’ disease\",\"answer\":false},{\"questionId\":2,\"choiceText\":\"Iodine deficiency\",\"answer\":true}]";
    private static String choices3 = "[{\"questionId\":3,\"choiceText\":\"Cortisol\",\"answer\":false},{\"questionId\":3,\"choiceText\":\"Thyroid hormone\",\"answer\":false},{\"questionId\":3,\"choiceText\":\"Parathormone\",\"answer\":false},{\"questionId\":3,\"choiceText\":\"Oxytocin\",\"answer\":true}]";
    private static String choices4 =  "[{\"questionId\":4,\"choiceText\":\"Obesity\",\"answer\":false},{\"questionId\":4,\"choiceText\":\"A buffalo hump\",\"answer\":false},{\"questionId\":4,\"choiceText\":\"Moon facies\",\"answer\":false},{\"questionId\":4,\"choiceText\":\"Bronze or hyperpigmented skin\",\"answer\":true}]";
    private static List<String> allChoices = Arrays.asList(choices1,choices2,choices3,choices4);
//    static{
//        try{
//            JSONArray jsonArray = new JSONArray(jsonString);
//            for(int i = 0;i< jsonArray.length();i++){
//                JSONObject obj = jsonArray.getJSONObject(i);
//                Long id = obj.getLong("id");
//                String question = obj.getString("question");
//                Question q = new Question(id,question,0);
//
//                //add all the choices
//                List<Choice> choices = new ArrayList<>();
//                String choiceJson = allChoices.get(i);
//                JSONArray choiceArr = new JSONArray(choiceJson);
//                for(int j = 0; j < choiceArr.length();j++){
//                    JSONObject obj2 = choiceArr.getJSONObject(j);
//                    String choiceText = obj2.getString("choiceText");
//                    boolean answer = obj2.getBoolean("answer");
//                    Choice c = new Choice(q.getId(),choiceText,answer);
//                    choices.add(c);
//                }
//                q.setChoices(choices);
//                questions.add(q);
//            }
//        }catch(JSONException e){
//            System.out.println("failed to parse");
//            e.printStackTrace();
//        }
//    }
    private static void parseJson(){
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0;i< jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Long id = obj.getLong("id");
                String question = obj.getString("question");
                Question q = new Question(id,question,0);

                //add all the choices
                List<Choice> choices = new ArrayList<>();
                String choiceJson = allChoices.get(i);
                JSONArray choiceArr = new JSONArray(choiceJson);
                for(int j = 0; j < choiceArr.length();j++){
                    JSONObject obj2 = choiceArr.getJSONObject(j);
                    String choiceText = obj2.getString("choiceText");
                    boolean answer = obj2.getBoolean("answer");
                    Choice c = new Choice(q.getId(),choiceText,answer);
                    choices.add(c);
                }
                q.setChoices(choices);
                questions.add(q);
            }
        }catch(JSONException e){
            System.out.println("failed to parse");
            e.printStackTrace();
        }
    }

    private TestQuestionList(){}

    public static List<Question> getQuestions() {
        parseJson();
        return questions;
    }

    public static void setJsonString(String jsonString) {
        TestQuestionList.jsonString = jsonString;
    }
}
