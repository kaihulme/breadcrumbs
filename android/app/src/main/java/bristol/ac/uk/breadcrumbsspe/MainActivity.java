package bristol.ac.uk.breadcrumbsspe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;

import bristol.ac.uk.breadcrumbsspe.api.UserService;
import bristol.ac.uk.breadcrumbsspe.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login();
    }

    // Logging in, sending user to Home Screen
    private void login(){
        Button loginButton = findViewById(R.id.signIn_btn);
        EditText codeText = findViewById(R.id.login_code);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String code = codeText.getText().toString();
                UserService userService = UserService.retrofit.create(UserService.class);
                Call<User> userCall = userService.getUserbyCode(code);
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            User u = response.body();
                            UserInSession userInSession = UserInSession.getInstance(u);
                            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                        }else{
                            wrongCodeDialog();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        t.printStackTrace();
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
}
