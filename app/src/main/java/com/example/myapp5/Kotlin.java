package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Kotlin extends AppCompatActivity {

    ImageView kotlin_2;
    TextView textView3;
    Animation text_anim;
    Animation kotlin_anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kotlin);

        text_anim = AnimationUtils.loadAnimation(this,R.anim.anim_text_java);
        kotlin_anim = AnimationUtils.loadAnimation(this,R.anim.anim_kotlin);

        kotlin_2 = findViewById(R.id.kotlin_2);
        textView3 = findViewById(R.id.textView3);


        kotlin_2.setAnimation(kotlin_anim);
        textView3.setAnimation(text_anim);

    }
}