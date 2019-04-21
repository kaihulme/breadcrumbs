package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class DrawerActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;
    public NavigationView navigationView;

    public void makeDrawer(){
        mDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()){
                            case R.id.nav_account:
                                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_map:
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_camera:
                                startActivity(new Intent(getApplicationContext(), QRCodeScannerActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_sponsors:
                                startActivity(new Intent(getApplicationContext(), SponsorActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                        }
                        return true;
                    }
                });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    public void helpButton(FloatingActionButton presentButton){
        ImageButton helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View helpView = inflater.inflate(R.layout.help_layout, mDrawerLayout, false);
                mDrawerLayout.addView(helpView);
//                setContentView(helpView);
                helpButton.setClickable(false);
                presentButton.setClickable(false);


                FloatingActionButton backToHome = findViewById(R.id.help_view_submit);
                backToHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        setContentView(R.layout.activity_home);
                        mDrawerLayout.removeView(helpView);
                        helpButton.setClickable(true);
                        presentButton.setClickable(true);
                    }
                });

            }
        });
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
