package bristol.ac.uk.breadcrumbsspe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import bristol.ac.uk.breadcrumbsspe.api.RetrofitClient;
import bristol.ac.uk.breadcrumbsspe.api.UserService;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import bristol.ac.uk.breadcrumbsspe.qrcode.QRCodeCaptureActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login();
    }

    // Logging in, sending user to Home Screen
    private void login(){
        FloatingActionButton loginButton = findViewById(R.id.signIn_btn);
        EditText codeText = findViewById(R.id.login_code);
        codeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                String code = codeText.getText().toString();
                code = "RAcJG"; //TODO REMOVE THIS LINE BEFORE FINAL RELEASE
                UserService userService = RetrofitClient.retrofit.create(UserService.class);
                Call<User> userCall = userService.getUserByCode(code);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismiss();

                        if(response.isSuccessful()){
                            User u = response.body();
                            UserInSession userInSession = UserInSession.getInstance(u);
                            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                            overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                        }else{
                            wrongCodeDialog();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Cannot connect to server. Please try again later.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void wrongCodeDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }
        else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Invalid Code")
        .setMessage("That is an invalid code. Please try again.")
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
                        // Return to welcome page
        }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
