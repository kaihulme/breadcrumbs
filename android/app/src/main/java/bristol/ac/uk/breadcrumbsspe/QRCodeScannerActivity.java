package bristol.ac.uk.breadcrumbsspe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;

public class QRCodeScannerActivity extends AppCompatActivity {
    private TextView mResultTextView;
    private static int QRCodeReaderRequestCode = 1;
    private static String LogTag = LoginActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_qrscanner);
        mResultTextView = findViewById(R.id.result_textview);

        Button scanQrCode = findViewById(R.id.scan_qrcode_button);
        scanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QRCodeCaptureActivity.class);
                startActivityForResult(intent, QRCodeReaderRequestCode);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        mDrawerLayout = findViewById(R.id.camera_drawer_layout);

        NavigationView navigationView = findViewById(R.id.camera_nav_view);
        navigationView.setCheckedItem(R.id.nav_camera);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_account:
                                startActivity(new Intent(QRCodeScannerActivity.this, WelcomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_map:
                                startActivity(new Intent(QRCodeScannerActivity.this, HomeActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_camera:
                                startActivity(new Intent(QRCodeScannerActivity.this, QRCodeScannerActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                            case R.id.nav_sponsors:
                                startActivity(new Intent(QRCodeScannerActivity.this, SponsorActivity.class));
//                                overridePendingTransition(R.anim.slide_right, R.anim.slide_right1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                return true;
                        }
                        return true;
                    }
                });


        Toolbar toolbar = findViewById(R.id.camera_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QRCodeReaderRequestCode) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    Barcode QRCode = intent.getParcelableExtra(QRCodeCaptureActivity.QRcodeObject);
                    //mResultTextView.setText(QRCode.displayValue);
                    String url = QRCode.displayValue;

                    int currentQuestion = ((MapState) this.getApplication()).getCurrentQuestion() + 1;

                    if (url.endsWith(Integer.toString(currentQuestion))) {
                        Intent i = new Intent(QRCodeScannerActivity.this, QuestionActivity.class);
                        i.putExtra("QUESTION_URL", url);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    } else {
                        wrongQuestionDialog();
                    }
                } else
                    mResultTextView.setText(R.string.no_qrcode_captured);
            } else
                Log.e(LogTag, String.format(getString(R.string.qrcode_error_format), CommonStatusCodes.getStatusCodeString(resultCode)));
        } else
            super.onActivityResult(requestCode, resultCode, intent);
    }

    private void wrongQuestionDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Wrong question")
                .setMessage("Sorry that's the wrong question. Keep looking!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
