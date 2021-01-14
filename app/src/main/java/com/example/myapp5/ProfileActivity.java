package com.example.myapp5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView profileImageView;
    EditText inputUsername2, inputCity2, inputCountry2, inputProfession2;
    Button btnUpdate;

    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImageView = findViewById(R.id.circleImageView);
        inputCity2 = findViewById(R.id.inputCity2);
        inputCountry2 = findViewById(R.id.inputCountry2);
        inputUsername2 = findViewById(R.id.inputUsername2);
        inputProfession2 = findViewById(R.id.inputProfession2);
        btnUpdate = findViewById(R.id.btnUpdate);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("User");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String profileImageUrl = snapshot.child("ProfileImage").getValue().toString();
                    String username = snapshot.child("Username").getValue().toString();
                    String city = snapshot.child("City").getValue().toString();
                    String country = snapshot.child("Country").getValue().toString();
                    String profession = snapshot.child("Profession").getValue().toString();


                    Picasso.get().load(profileImageUrl).into(profileImageView);
                    inputUsername2.setText(username);
                    inputCity2.setText(city);
                    inputCountry2.setText(country);
                    inputProfession2.setText(profession);


                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Data not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}