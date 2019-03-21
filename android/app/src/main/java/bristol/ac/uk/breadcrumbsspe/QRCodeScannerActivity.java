package bristol.ac.uk.breadcrumbsspe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == QRCodeReaderRequestCode){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(intent != null){
                    Barcode QRCode = intent.getParcelableExtra(QRCodeCaptureActivity.BarcodeObject);
                    //mResultTextView.setText(QRCode.displayValue);
                    String url = QRCode.displayValue;

                    int currentQuestion = ((MapState) this.getApplication()).getCurrentQuestion() + 1;

                    if (url.endsWith(Integer.toString(currentQuestion))) {
                        Intent i = new Intent(QRCodeScannerActivity.this, QuestionActivity.class);
                        i.putExtra("QUESTION_URL", url);
                        startActivity(i);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    else {
                        wrongQuestionDialog();
                    }
                }else
                    mResultTextView.setText(R.string.no_qrcode_captured);
            }else
                Log.e(LogTag, String.format(getString(R.string.qrcode_error_format), CommonStatusCodes.getStatusCodeString(resultCode)));
        }else
            super.onActivityResult(requestCode, resultCode, intent);
    }

    private void wrongQuestionDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Wrong question")
                .setMessage("Sorry that's the wrong question. Keep looking")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
