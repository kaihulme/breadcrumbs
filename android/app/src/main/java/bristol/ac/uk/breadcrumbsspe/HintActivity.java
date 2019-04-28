package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class HintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        getHintData();
        goHome();
    }

    // Back to Home Screen
    private void goHome(){
        Button homeButton = findViewById(R.id.button_back_hint);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HintActivity.this,HomeActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    private void getHintData() {
        drawHint();
        updateText();
    }

    private void updateText(){
        TextView hintText = findViewById(R.id.hint_textview);
        // TODO set to hint text
        String text = "";
        hintText.setText(text);

    }

    private void drawHint() {
        ImageView hintImage = findViewById(R.id.hint_image);
        // TODO set to hint image
        String url = "http://breadcrumbs.bioscientifica.com/image/venueMap_empty";
        Picasso.get().load(url).into(hintImage);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
