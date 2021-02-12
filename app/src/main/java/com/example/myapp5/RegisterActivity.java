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

public class RegisterActivity extends AppCompatActivity {

    private
    TextInputLayout inputEmail;
    TextInputLayout Password;
    TextInputLayout ConfirmPassword;
    Button btnRegister;
    TextView alreadyHaveAccount;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;
    ImageView sun, dayLand, nightLand;
    View daySky, nightSky;
    DayNightSwitch dayNightSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = findViewById(R.id.inputEmail);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        btnRegister = findViewById(R.id.btnLogin);
        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AtemptRegistartion();
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AtemptRegistartion() {
        String email = inputEmail.getEditText().getText().toString();
        String password = Password.getEditText().getText().toString();
        String confirmPassword = ConfirmPassword.getEditText().getText().toString();


        if (email.isEmpty()) {
            showError(this.inputEmail, "Email не найден");
        } else {
            if (password.isEmpty() || password.length() < 6) {
                showError(Password, "Пароль меньше шести. Взломать же могут");
            } else if (!confirmPassword.equals(password)) {
                showError(ConfirmPassword, "Воу! Что-то много, может поменьше?");

            } else {
                mLoadingBar.setTitle("Регистрация");
                mLoadingBar.setMessage("Пожалуйста подожди идет создание твоего аккаунта)");
                mLoadingBar.setCanceledOnTouchOutside(false);
                mLoadingBar.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Ура! Твой аккуант создан!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, SetupActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            mLoadingBar.dismiss();
                            Toast.makeText(RegisterActivity.this, "Упс! Что-то пошло не так(", Toast.LENGTH_SHORT).show();
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