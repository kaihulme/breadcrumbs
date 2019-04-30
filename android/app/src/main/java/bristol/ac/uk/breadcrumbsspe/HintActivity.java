package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bristol.ac.uk.breadcrumbsspe.entity.Hint;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;


public class HintActivity extends AppCompatActivity {

    public TextView hintTextView;
    public ImageView hintImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        Intent intent = getIntent();
        goHome();
        Hint hint = (Hint) intent.getSerializableExtra("HINT");

        hintTextView = findViewById(R.id.hint_textview);
        hintImage = findViewById(R.id.hint_image);

        if(hint != null){
            hintTextView.setText(hint.getHintText());
            Picasso.get().load(hint.getHintImageUrl()).into(hintImage);
        } else {
            startActivity(new Intent(getApplicationContext(), QRCodeCaptureActivity.class));
        }
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
