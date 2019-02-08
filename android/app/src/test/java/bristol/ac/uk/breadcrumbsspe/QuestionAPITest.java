package bristol.ac.uk.breadcrumbsspe;

import org.junit.Test;

import bristol.ac.uk.breadcrumbsspe.api.FetchQuestions;
import bristol.ac.uk.breadcrumbsspe.entity.Question;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class QuestionAPITest {

    @Test
    public void questionsAreFetched(){
        FetchQuestions process = new FetchQuestions();
        process.execute();
        assertNotEquals(QuestionActivity.questionsJSON,"");
    }
}