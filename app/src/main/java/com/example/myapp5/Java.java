package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Java extends AppCompatActivity {

    ImageView java_2;
    TextView textView2;
    Animation text_anim;
    Animation java_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        text_anim = AnimationUtils.loadAnimation(this,R.anim.anim_java);
        java_anim = AnimationUtils.loadAnimation(this,R.anim.anim_text_java);

        java_2 = findViewById(R.id.java_2);
        textView2 = findViewById(R.id.textView2);


        java_2.setAnimation(java_anim);
        textView2.setAnimation(text_anim);

    }
}