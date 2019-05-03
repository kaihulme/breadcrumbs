package bristol.ac.uk.breadcrumbsspe.entity;

import android.app.Application;

public class MapState extends Application {

    private String currentMap = "http://breadcrumbs.bioscientifica.com/image/venueMap_empty";

    private int currentQuestion = 0;

    public String getCurrentMap() {
        int questionNo = currentQuestion + 1;
        if(questionNo == 9)
            return "http://breadcrumbs.bioscientifica.com/image/venueMap_empty";
        return "http://breadcrumbs.bioscientifica.com/image/venueMap_q" + questionNo;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestion() { return this.currentQuestion; }
}
