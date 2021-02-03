package com.example.myapp5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef,requestRef, FriendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageUrl, username, city,country;

    CircleImageView profileImage;
    TextView Username,address;
    Button btnPerform, btnDecline;
    String CurrentState="nothing_happen";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);
        String userID=getIntent().getStringExtra("userKey");
        Toast.makeText(this,""+userID,Toast.LENGTH_SHORT).show();

        mUserRef= FirebaseDatabase.getInstance().getReference().child("User").child(userID);
        requestRef= FirebaseDatabase.getInstance().getReference().child("Requests");
        FriendRef= FirebaseDatabase.getInstance().getReference().child("Friends");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        btnPerform=findViewById(R.id.btnPerform);
        btnDecline=findViewById(R.id.btnDecline);

        profileImage=findViewById(R.id.profileImage);
        Username=findViewById(R.id.username4);
        address=findViewById(R.id.address);

        LoadUser();

        btnPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAction(userID);
                CheckUserExistance(userID);
            }
        });
    }

    private void CheckUserExistance(String userID) {
        FriendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                        CurrentState="Friends";
                        btnPerform.setText("Отправь СМС");
                        btnDecline.setText("Не дружить");
                        btnDecline.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FriendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    CurrentState="Friends";
                    btnPerform.setText("Отправь СМС");
                    btnDecline.setText("Не дружить");
                    btnDecline.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        requestRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState = "Отправлен запрос";
                        btnPerform.setText("Отмена на принятии дружбы");
                        btnDecline.setVisibility(View.GONE);
                    }
                    if (snapshot.child("status").getValue().toString().equals("decline")){
                        CurrentState = "Запрос отменён";
                        btnPerform.setText("Отмена на принятии дружбы");
                        btnDecline.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    if (snapshot.child("status").getValue().toString().equals("pending")){
                        CurrentState = "he_sent_pending";
                        btnPerform.setText("Accept Friend Request");
                        btnDecline.setText("Decline Friend");
                        btnDecline.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (CurrentState.equals("nothing_happen")){
            CurrentState = "nothing_happen";
            btnPerform.setText("Отправлен запрос на дружбу");
            btnDecline.setVisibility(View.GONE);
        }

    }

    private void PerformAction(String userID) {
        if (CurrentState.equals("nothing_happen")){
            HashMap hashMap = new HashMap();
            hashMap.put("status", "pending");
            requestRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "Запрос на добавление друга отправлено", Toast.LENGTH_SHORT).show();
                        btnDecline.setVisibility(View.GONE);
                        CurrentState = "Отправлено на рассмотрении";
                        btnPerform.setText("Отмена на добавление в друзья");
                    }
                    else {
                        Toast.makeText(ViewFriendActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        if (CurrentState.equals("Отправлено на рассмотрении") || CurrentState.equals("Кыш от сюда!")){
            requestRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ViewFriendActivity.this, "Отмена на добавление в друзья", Toast.LENGTH_SHORT).show();
                        CurrentState="nothing happen";
                        btnPerform.setText("Запрос на добавление друга");
                        btnDecline.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(ViewFriendActivity.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (CurrentState.equals("Он/она хочет подружится")){
            requestRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        HashMap hashMap=new HashMap();
                        hashMap.put("status","Friends");
                        hashMap.put("username",username);
                        hashMap.put("profileImageUrl",profileImageUrl);
                        FriendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    FriendRef.child(userID).child(mUser.getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            Toast.makeText(ViewFriendActivity.this, "Теперь вы друзья", Toast.LENGTH_SHORT).show();
                                            CurrentState="Friends";
                                            btnPerform.setText("Напиши СМС");
                                            btnDecline.setText("Не дружить");
                                            btnDecline.setVisibility(View.VISIBLE);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });
        }
        if (CurrentState.equals("Friends"))
        {

        }
    }

    private void LoadUser() {

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    profileImageUrl=snapshot.child("profileImage").getValue().toString();
                    username=snapshot.child("username").getValue().toString();
                    city=snapshot.child("city").getValue().toString();
                    country=snapshot.child("country").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImage);
                    Username.setText(username);
                    address.setText(city+","+country);
                }
                else
                {
                    Toast.makeText(ViewFriendActivity.this, "Не найдено", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ViewFriendActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}