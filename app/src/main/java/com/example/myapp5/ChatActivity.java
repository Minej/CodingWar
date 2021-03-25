package com.example.myapp5;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText inputSMS;
    ImageView btnSend;
    CircleImageView userProfileImageAppbar;
    TextView status,usernameAppbar;
    String OtherUserID;
    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String OtherUsername, OtherUserProfileImageLink,OtherUserStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        OtherUserID=getIntent().getStringExtra("OtherUserID");

        toolbar=findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerview);
        inputSMS=findViewById(R.id.inputSMS);
        btnSend=findViewById(R.id.btnSend);
        userProfileImageAppbar=findViewById(R.id.userProfileImageAppbar);
        status=findViewById(R.id.status);
        usernameAppbar=findViewById(R.id.usernameAppbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUserRef= FirebaseDatabase.getInstance().getReference().child("User");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        LoadOtherUser();
    }

    private void LoadOtherUser() {

        mUserRef.child(OtherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    OtherUsername=snapshot.child("username").getValue().toString();
                    OtherUserProfileImageLink=snapshot.child("profileImage").getValue().toString();
                    OtherUserStatus=snapshot.child("status").getValue().toString();
                    Picasso.get().load(OtherUserProfileImageLink).into(userProfileImageAppbar);
                    usernameAppbar.setText(OtherUsername);
                    status.setText(OtherUserStatus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}