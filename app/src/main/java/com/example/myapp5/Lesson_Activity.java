package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Lesson_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Animation anim_top, anim_bottom;
        TextView lesson, java_txt, python_txt, js_text;
        ImageView java, python, JS;

        java = findViewById(R.id.java);
        python = findViewById(R.id.python);
        JS = findViewById(R.id.js);

        anim_top = AnimationUtils.loadAnimation(this, R.anim.anim_top);
        anim_bottom = AnimationUtils.loadAnimation(this, R.anim.anim_bottom);

        lesson = findViewById(R.id.lesson);
        java_txt = findViewById(R.id.java_txt);
        python_txt = findViewById(R.id.python_txt);
        js_text = findViewById(R.id.js_txt);

        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(Lesson_Activity.this, java_les.class);
                startActivity(intent);
            }
        });

        python.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Lesson_Activity.this, "Точно так же как и в java, но конечно что то будет тут)", Toast.LENGTH_SHORT).show();
            }
        });

        JS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Lesson_Activity.this, "Точно так же как и в java, но конечно что то будет тут)", Toast.LENGTH_SHORT).show();
            }
        });

        lesson.setAnimation(anim_bottom);
        java_txt.setAnimation(anim_bottom);
        python_txt.setAnimation(anim_bottom);
        js_text.setAnimation(anim_bottom);

        java.setAnimation(anim_top);
        python.setAnimation(anim_top);
        JS.setAnimation(anim_top);
    }
}