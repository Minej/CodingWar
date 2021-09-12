package com.example.myapp5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class java_les extends AppCompatActivity {

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_les);

        TextView mat_vichisleniya, classi, vvedeniye;

        mat_vichisleniya = findViewById(R.id.mat_vichisleniya);
        classi = findViewById(R.id.classi);
        vvedeniye = findViewById(R.id.vvedeniye);

        mat_vichisleniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(java_les.this, "Так как я хз че писать вы скажите что и как писать я напишу) Расул особенно от тебя так как ты знаешь опыт учителя", Toast.LENGTH_SHORT).show();
            }
        });

        classi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(java_les.this, "Так как я хз че писать вы скажите что и как писать я напишу) Расул особенно от тебя так как ты знаешь опыт учителя", Toast.LENGTH_SHORT).show();
            }
        });

        vvedeniye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(java_les.this, "Так как я хз че писать вы скажите что и как писать я напишу) Расул особенно от тебя так как ты знаешь опыт учителя", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
