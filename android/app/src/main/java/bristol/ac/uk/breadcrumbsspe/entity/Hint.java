package bristol.ac.uk.breadcrumbsspe.entity;

import java.io.Serializable;

public class Hint implements Serializable {
    private Long questionId;
    private String hintText;
    private String hintImageUrl;

    public Hint(Long questionId,String hintText, String hintImageUrl){
        this.questionId = questionId;
        this.hintText = hintText;
        this.hintImageUrl = hintImageUrl;
    }

    public String getHintText(){
        return hintText;
    }

    public Long getQuestionId(){
        return questionId;
    }

    public String getHintImageUrl(){
        return hintImageUrl;
    }
}
