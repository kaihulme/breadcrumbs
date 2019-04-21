package bristol.ac.uk.breadcrumbsspe.qrcode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bristol.ac.uk.breadcrumbsspe.HomeActivity;
import bristol.ac.uk.breadcrumbsspe.QuestionActivity;
import bristol.ac.uk.breadcrumbsspe.R;
import bristol.ac.uk.breadcrumbsspe.api.QuestionService;
import bristol.ac.uk.breadcrumbsspe.api.RetrofitClient;
import bristol.ac.uk.breadcrumbsspe.camera.CameraSource;
import bristol.ac.uk.breadcrumbsspe.camera.CameraSourcePreview;
import bristol.ac.uk.breadcrumbsspe.entity.Question;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


import static android.view.View.TEXT_ALIGNMENT_CENTER;


public final class QRCodeCaptureActivity extends AppCompatActivity
        implements QRCodeTracker.QRcodeGraphicTrackerCallback,Callback<Question> {
    private Button InputCode;
    private AlertDialog dialog;
    @Override
    public void onResponse(Call<Question> call, Response<Question> response) {
        if(response.isSuccessful() && response.body() != null){
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            dialog.cancel();
            Intent i = new Intent(QRCodeCaptureActivity.this,QuestionActivity.class);
            Question q = response.body();
            i.putExtra("QUESTION",q);
            startActivity(i);
        }else {
            InputCode.performClick();
            Toast.makeText(QRCodeCaptureActivity.this, "Incorrect code. Please input the correct code.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Question> call, Throwable t) {

    }

    private CameraSource source;
    private CameraSourcePreview prev;

    private static final String TAG = "QRcode-reader";
    private static final int RC_HANDLE_GMS = 9001;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    public static final String QRcodeObject = "QRcode";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.qrcode_capture);
        prev = findViewById(R.id.preview);

        InputCode = findViewById(R.id.input_code);
        InputCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QRCodeCaptureActivity.this);
                builder.setTitle("Input Code Here").setIcon(R.drawable.ic_keyboard);

                final EditText input = new EditText(QRCodeCaptureActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    input.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                }
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

                builder.setView(input);

                builder.setPositiveButton("Submit", null);

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog = builder.create();

                dialog.show();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                try {
                    layoutParams.copyFrom(dialog.getWindow().getAttributes());
                }
                catch(Exception e){
                    Snackbar.make(v, "Bug. Please report.", Snackbar.LENGTH_LONG)
                            .setAction("Bug", null).show();
                }
                layoutParams.width = (int)(displayMetrics.widthPixels * 0.7f);

                dialog.getWindow().setAttributes(layoutParams);

                Button submitButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code = input.getText().toString();
                        if (code.length() != 4){
                            Toast.makeText(QRCodeCaptureActivity.this, "Invalid code. Please input the 4 character code.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            QuestionService questionService = RetrofitClient.retrofit.create(QuestionService.class);
                            Call<Question> questionCallback = questionService.getQuestion(code);
                            questionCallback.enqueue(QRCodeCaptureActivity.this);
                        }
                    }
                });
            }
        });

        Intent i = getIntent();
        boolean putDialog = i.getBooleanExtra("DIALOG", false);
        if(putDialog){
            InputCode.performClick();
            Toast.makeText(QRCodeCaptureActivity.this, "Incorrect code. Please input the correct code.", Toast.LENGTH_SHORT).show();
        }

        boolean autoFocus = true;
        boolean useFlash = false;

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onDetectedQrCode(Barcode qrcode) {
        if (qrcode != null) {
            Intent i = new Intent();
            i.putExtra(QRcodeObject, qrcode);
            setResult(CommonStatusCodes.SUCCESS, i);
            finish();
        }
    }

    private void requestCameraPermission() {
        Log.w(TAG, "Requesting Camera permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
        }
    }

    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        BarcodeDetector QRcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        QRCodeTrackerFactory QRcodeFactory = new QRCodeTrackerFactory(this);
        QRcodeDetector.setProcessor(new MultiProcessor.Builder<>(QRcodeFactory).build());

        if (!QRcodeDetector.isOperational()) {
            Log.w(TAG, "Detector dependencies not yet available.");

            IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowStorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error,
                        Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), QRcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(metrics.widthPixels, metrics.heightPixels)
                .setRequestedFps(24.0f);

        builder = builder.setFocusMode(autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);

        source = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (prev != null) {
            prev.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (prev != null) {
            prev.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int rc,
                                           @NonNull String[] perm,
                                           @NonNull int[] res) {
        if (rc != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + rc);
            super.onRequestPermissionsResult(rc, perm, res);
            return;
        }

        if (res.length != 0 && res[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            boolean autoFocus = true;
            boolean useFlash = false;
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + res.length +
                " Result code = " + (res.length > 0 ? res[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    private void startCameraSource() throws SecurityException {
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());

        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (source != null) {
            try {
                prev.start(source);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                source.release();
                source = null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QRCodeCaptureActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
