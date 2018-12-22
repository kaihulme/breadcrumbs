package bristol.ac.uk.breadcrumbsspe.entity;



import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private String title;
    List<Question> questions = new ArrayList<>();

    List<User> users = new ArrayList<>();

    public Quiz(String title){
        this.title = title;
    }

    //returns a question in the quiz based on its id
    public Question findQuestion(Long id){
        for(Question q: questions){
            if(q.getId().equals(id)) return q;
        }
        return null;
    }

    public int getId() {
        return id;
    }
}
