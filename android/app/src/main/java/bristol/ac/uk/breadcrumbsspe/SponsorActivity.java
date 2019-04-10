package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class SponsorActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    // TODO static sponsor page, set logos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        mDrawerLayout = findViewById(R.id.help_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_map:
                                startActivity(new Intent(SponsorActivity.this, HomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_camera:
                                startActivity(new Intent(SponsorActivity.this, QRCodeScannerActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_help:
                                startActivity(new Intent(SponsorActivity.this, SponsorActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                return true;
                        }
                        return true;
                    }
                });

        FloatingActionButton continueButton = findViewById(R.id.help_continue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SponsorActivity.this, HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        Toolbar toolbar = findViewById(R.id.help_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
