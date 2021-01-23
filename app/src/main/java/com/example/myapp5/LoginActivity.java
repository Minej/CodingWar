package com.example.myapp5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout inputEmail, inputPassword;
    Button btnLogin;
    TextView forgotPassword, createNewAccount;
    ProgressDialog mLoadingBar;
    FirebaseAuth mAuth;
    ImageView sun, dayLand, nightLand;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        forgotPassword = findViewById(R.id.forgotPassword);
        createNewAccount = findViewById(R.id.createNewAccount);
        mLoadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        //Day_Night_Switch
        sun = findViewById(R.id.sun);
        dayLand = findViewById(R.id.day_landscape);
        nightLand = findViewById(R.id.night_landscape);
        daySky = findViewById(R.id.day_bg);
        nightSky= findViewById(R.id.night_bg);
        dayNightSwitch= findViewById(R.id.day_night_switch);

        sun.setTranslationY(-110);

        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night){
                    sun.animate().translationY(110).setDuration(1000);
                    dayLand.animate().alpha(0).setDuration(1300);
                    daySky.animate().alpha(0).setDuration(1300);
                }
                else
                {

                    sun.animate().translationY(-110).setDuration(1000);
                    dayLand.animate().alpha(1).setDuration(1300);
                    daySky.animate().alpha(1).setDuration(1300);

                }
            }
        });


        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtamptLogin();

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }


    private void AtamptLogin() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();

        if (email.isEmpty()) {
            showError(this.inputEmail, "Email не найден. Попробуй ещё раз)");
        } else {
            if (password.isEmpty() || password.length() < 6) {
                showError(inputPassword, "Что-то пошло не так( Попробуй заново");
            } else {
                mLoadingBar.setTitle("Логин");
                mLoadingBar.setMessage("Пожалуйста подожди, сейчас ищем твой аккаунт)");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Ура! Все прошло успешно!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            mLoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Извини, но что-то пошло не так( Попробуй заново.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    }

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();

    }

}
