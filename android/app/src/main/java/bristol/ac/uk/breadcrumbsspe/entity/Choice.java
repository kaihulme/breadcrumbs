package bristol.ac.uk.breadcrumbsspe.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice {

   private Long questionId;
   private String choiceText;
   private boolean answer;

   public Choice(Long questionId,String choiceText,boolean answer){
      this.questionId = questionId;
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