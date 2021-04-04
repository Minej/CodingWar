package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_Main extends AppCompatActivity {
    TextView textView;
    ImageView kotlin, java;
    Animation text_anim;
    Animation java_anim, kotlin_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);

        textView = findViewById(R.id.textView);
        kotlin = findViewById(R.id.kotlin);
        java = findViewById(R.id.java);


        text_anim = AnimationUtils.loadAnimation(this,R.anim.anim_java);
        java_anim = AnimationUtils.loadAnimation(this,R.anim.anim_text_java);
        kotlin_anim = AnimationUtils.loadAnimation(this,R.anim.anim_kotlin);

        java = findViewById(R.id.java);
        textView = findViewById(R.id.textView);
        kotlin= findViewById(R.id.kotlin);


        java.setAnimation(java_anim);
        textView.setAnimation(text_anim);
        kotlin.setAnimation(kotlin_anim);

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Main.this, Java.class));
            }
        });

        kotlin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Main.this, Kotlin.class));
            }
        });
    }
}