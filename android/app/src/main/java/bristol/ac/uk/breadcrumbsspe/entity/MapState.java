package bristol.ac.uk.breadcrumbsspe.entity;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import bristol.ac.uk.breadcrumbsspe.R;

public class MapState extends Application {

    private Bitmap currentMap;
//    private int currentMap;

    private int currentQuestion = 0;
    private int cluesFound = 11;
    private int firstClueFound = 10;

    public Bitmap getCurrentMap() {
        try {

            int questionNo = currentQuestion + 1;
            String mapURL = "http://breadcrumbs.bioscientifica.com/image/venueMap_q" + questionNo;
            URL url = new URL(mapURL);
            currentMap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentMap;
    }

//    public int getCurrentMap() {
//        if (currentQuestion == 0) {
//            if (cluesFound == 11) currentMap = R.drawable.venuemap_q1_c11_l0;
//            else if (cluesFound == 10) currentMap = R.drawable.venuemap_q1_c10_l0;
//            else if (cluesFound == 01) currentMap = R.drawable.venuemap_q1_c01_l0;
//            else {
//                if (firstClueFound == 10) currentMap = R.drawable.venuemap_q1_c00_l0;
//                else currentMap = R.drawable.venuemap_q1_c00_l1;
//            }
//        }
//
//        else if (currentQuestion == 1) {
//            if (cluesFound == 11) currentMap = R.drawable.venuemap_q2_c11_l0;
//            else if (cluesFound == 10) currentMap = R.drawable.venuemap_q2_c10_l0;
//            else if (cluesFound == 01) currentMap = R.drawable.venuemap_q2_c01_l0;
//            else {
//                if (firstClueFound == 10) currentMap = R.drawable.venuemap_q2_c00_l0;
//                else currentMap = R.drawable.venuemap_q2_c00_l1;
//            }
//        }
//
//        else if (currentQuestion == 2) {
//            if (cluesFound == 11) currentMap = R.drawable.venuemap_q3_c11_l0;
//            else if (cluesFound == 10) currentMap = R.drawable.venuemap_q3_c10_l0;
//            else if (cluesFound == 01) currentMap = R.drawable.venuemap_q3_c01_l0;
//            else {
//                if (firstClueFound == 10) currentMap = R.drawable.venuemap_q3_c00_l0;
//                else currentMap = R.drawable.venuemap_q3_c00_l1;
//            }
//        }
//
//        else if (currentQuestion >= 3) {
//            if (cluesFound == 11) currentMap = R.drawable.venuemap_q4_c11_l0;
//            else if (cluesFound == 10) currentMap = R.drawable.venuemap_q4_c10_l0;
//            else if (cluesFound == 01) currentMap = R.drawable.venuemap_q4_c01_l0;
//            else {
//                if (firstClueFound == 10) currentMap = R.drawable.venuemap_q4_c00_l0;
//                else currentMap = R.drawable.venuemap_q4_c00_l1;
//            }
//        }
//
//        return currentMap;
//    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getCurrentQuestion() { return this.currentQuestion; }

    public void setCluesFound(int cluesFound) {
        this.cluesFound = cluesFound;
    }

    public void setFirstClueFound(int firstClueFound) {
        this.firstClueFound = firstClueFound;
    }
}
