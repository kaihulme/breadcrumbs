package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class SponsorActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        ImageButton sponsorButton1 = findViewById(R.id.sponsor_1);
        sponsorButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        ImageButton sponsorButton2 = findViewById(R.id.sponsor_2);
        sponsorButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        ImageButton sponsorButton3 = findViewById(R.id.sponsor_3);
        sponsorButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });

        ImageButton sponsorButton4 = findViewById(R.id.sponsor_4);
        sponsorButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });
        makeDrawer();
        navigationView.setCheckedItem(R.id.nav_sponsors);

        FloatingActionButton continueButton = findViewById(R.id.sponsor_continue);

        Animation scale_fab_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_in);
        continueButton.startAnimation(scale_fab_in);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation scale_fab_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_fab_out);
                continueButton.startAnimation(scale_fab_out);
                Intent i = new Intent(SponsorActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SponsorActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
