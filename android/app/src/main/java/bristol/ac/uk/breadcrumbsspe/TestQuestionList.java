package bristol.ac.uk.breadcrumbsspe;

import com.google.android.gms.vision.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bristol.ac.uk.breadcrumbsspe.entity.Question;

public class TestQuestionList {

    private static List<Question> questions = new ArrayList<>();
    private static String jsonString = "[{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]}]";
    private static String choices1 = "[{\"question\":{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"May have psammoma bodies\",\"answer\":true},{\"question\":{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"The least common kind of thyroid carcinoma\",\"answer\":false},{\"question\":{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"The type of thyroid carcinoma with the worst prognosis\",\"answer\":false},{\"question\":{\"id\":1,\"question\":\"Which of the following is true of papillary thyroid carcinoma\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Occurs in patients with MEN II\",\"answer\":false}]";
    private static String choices2 = "[{\"question\":{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Pituitary dysfunction\",\"answer\":false},{\"question\":{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"DeQuervain’s thyroiditis\",\"answer\":false},{\"question\":{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Graves’ disease\",\"answer\":false},{\"question\":{\"id\":2,\"question\":\"The most common cause of hypothyroidism in underprivileged countries is:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Iodine deficiency\",\"answer\":true}]" ;
    private static String choices3 = "[{\"question\":{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Cortisol\",\"answer\":false},{\"question\":{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Thyroid hormone\",\"answer\":false},{\"question\":{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Parathormone\",\"answer\":false},{\"question\":{\"id\":3,\"question\":\"Which of the following hormones, in addition to participating in the initiation of labor, may play a role in trust, monogamy, and the desire to cuddle?\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Oxytocin\",\"answer\":true}]";
    private static String choices4 = "[{\"question\":{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Obesity\",\"answer\":false},{\"question\":{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"A buffalo hump\",\"answer\":false},{\"question\":{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Moon facies\",\"answer\":false},{\"question\":{\"id\":4,\"question\":\" A patient with Cushing syndrome might present with any of the following EXCEPT:\",\"noOfAttempts\":0,\"score\":0,\"quiz\":null,\"attempts\":[],\"choices\":[]},\"choiceText\":\"Bronze or hyperpigmented skin\",\"answer\":true}]";
    private static List<String> allChoices = Arrays.asList(choices1,choices2,choices3,choices4);
    static{
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0;i< jsonArray.length();i++){
                JSONObject obj = jsonArray.getJSONObject(i);
                Long id = obj.getLong("id");
                String question = obj.getString("question");
                Question q = new Question(id,question,0);
                questions.add(q);
            }
        }catch(JSONException e){
            System.out.println("failed to parse");
            e.printStackTrace();
        }
    }

    private TestQuestionList(){}
}
