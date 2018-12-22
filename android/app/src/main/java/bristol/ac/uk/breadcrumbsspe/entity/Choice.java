package bristol.ac.uk.breadcrumbsspe.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice {

   private Question question;
   private String choiceText;
   private boolean answer;

   public Choice(Question question,String choiceText,boolean answer){
      this.question = question;
      this.choiceText = choiceText;
      this.answer = answer;
   }

    public String getChoiceText() {
        return choiceText;
    }

    public boolean isAnswer() {
        return answer;
    }
}
