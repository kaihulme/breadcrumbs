package bristol.ac.uk.breadcrumbsspe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.squareup.picasso.Picasso;

import bristol.ac.uk.breadcrumbsspe.api.QRCodeHintService;
import bristol.ac.uk.breadcrumbsspe.entity.Hint;
import bristol.ac.uk.breadcrumbsspe.entity.MapState;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QRCodeScannerActivity extends DrawerActivity {
    private TextView mResultTextView;
    private static int QRCodeReaderRequestCode = 1;
    private static String LogTag = LoginActivity.class.getSimpleName();

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

        makeDrawer();
        navigationView.setCheckedItem(R.id.nav_camera);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == QRCodeReaderRequestCode) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (intent != null) {
                    Barcode QRCode = intent.getParcelableExtra(QRCodeCaptureActivity.QRcodeObject);
                    //mResultTextView.setText(QRCode.displayValue);
                    String url = QRCode.displayValue;

                    int currentQuestion = ((MapState) this.getApplication()).getCurrentQuestion() + 1;

                    if(url.contains("hints")){
                        OkHttpClient client = new OkHttpClient.Builder()
                                .build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(url)
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        QRCodeHintService qrCodeHintService = retrofit.create(QRCodeHintService.class);
                        Call<Hint> hintCall = qrCodeHintService.getHint();
                        hintCall.enqueue(new Callback<Hint>() {
                            @Override
                            public void onResponse(Call<Hint> call, Response<Hint> response) {
                                if(response.isSuccessful() && response.body() != null){
                                    Hint hint = response.body();
                                    if(hint.getQuestionId().intValue() == currentQuestion){
                                        Intent i = new Intent(QRCodeScannerActivity.this, HintActivity.class);
                                        i.putExtra("HINT", hint);
                                        startActivity(i);
                                    } else {
                                        wrongHintDialog();
                                    }
                                } else {
                                    System.out.println(response.errorBody());
                                    wrongHintDialog();
                                }
                            }

                            @Override
                            public void onFailure(Call<Hint> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }
                    else if (url.endsWith(Integer.toString(currentQuestion))) {
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

    private void wrongHintDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Wrong hint")
                .setMessage("Sorry that hint is part of a different question. Keep looking!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QRCodeScannerActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
