package bristol.ac.uk.breadcrumbsspe.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice implements Serializable {
   private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAnswer() {
        return answer;
    }
}
