package bristol.ac.uk.breadcrumbsspe.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bristol.ac.uk.breadcrumbsspe.TestQuestionList;

public class FetchQuestions extends AsyncTask<Void,Void,Void> {

    String data = "";
    @Override
    protected Void doInBackground(Void... voids) {
        try{
            URL url = new URL("http://129.213.113.83/api/questions");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TestQuestionList.setJsonString(data);
    }
}
