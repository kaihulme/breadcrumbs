package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;

public class QRCodeScannerActivity extends AppCompatActivity {
    private TextView mResultTextView;
    private static int QRCodeReaderRequestCode = 1;
    private static String LogTag = MainActivity.class.getSimpleName();

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
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == QRCodeReaderRequestCode){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(intent != null){
                    Barcode QRCode = intent.getParcelableExtra(QRCodeCaptureActivity.BarcodeObject);
                    mResultTextView.setText(QRCode.displayValue);
                    String url = QRCode.displayValue;
//                    Intent fromHome = getIntent();
//                    int index = fromHome.getIntExtra("CURRENT_QUESTION",-1);

                    Intent i = new Intent(QRCodeScannerActivity.this,QuestionActivity.class);
                    i.putExtra("QUESTION_URL",url);
                    startActivity(i);
                }else
                    mResultTextView.setText(R.string.no_qrcode_captured);
            }else
                Log.e(LogTag, String.format(getString(R.string.qrcode_error_format), CommonStatusCodes.getStatusCodeString(resultCode)));
        }else
            super.onActivityResult(requestCode, resultCode, intent);
    }
}
