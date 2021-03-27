package com.example.myapp5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp5.Utills.Chat;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class ChatActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText inputSMS;
    ImageView btnSend;
    CircleImageView userProfileImageAppbar;
    TextView status,usernameAppbar;
    String OtherUserID;
    DatabaseReference mUserRef,smsRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String OtherUsername, OtherUserProfileImageLink,OtherUserStatus;
    FirebaseRecyclerOptions<Chat>options;
    FirebaseRecyclerAdapter<Chat, ChatMyViewHolder> adapter;

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
        smsRef= FirebaseDatabase.getInstance().getReference().child("Message");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        LoadOtherUser();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMS();
            }
        });
        
        LoadSMS();
    }

    private void LoadSMS() {
        options= new FirebaseRecyclerOptions.Builder<Chat>().setQuery(smsRef.child(mUser.getUid()).child(OtherUserID),Chat.class).build();
        adapter=new FirebaseRecyclerAdapter<Chat, ChatMyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ChatMyViewHolder holder, int position, @NonNull Chat model) {
                if (model.getUserID().equals(mUser.getUid()))
                {
                    holder.firstUserText.setVisibility(View.GONE);
                    holder.firstUserProfile.setVisibility(View.GONE);
                    holder.secondUserProfile.setVisibility(View.GONE);
                    holder.secondUserProfile.setVisibility(View.GONE);

                    holder.secondUserText.setText(model.getSms());
                }
                else{
                    holder.firstUserText.setVisibility(View.VISIBLE);
                    holder.firstUserProfile.setVisibility(View.VISIBLE);
                    holder.secondUserProfile.setVisibility(View.VISIBLE);
                    holder.secondUserProfile.setVisibility(View.VISIBLE);

                    holder.secondUserText.setText(model.getSms());
                    Picasso.get().load(OtherUserProfileImageLink).into(holder.firstUserProfile);

                }
            }

            @NonNull
            @Override
            public ChatMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singleview_sms,parent,false);
                return new ChatMyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void SendSMS() {
        String sms=inputSMS.getText().toString();
        if (sms.isEmpty()){
            Toast.makeText(this, "Лучше написать)", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap hashMap=new HashMap();
            hashMap.put("sms", sms);
            hashMap.put("status", "unseen");
            hashMap.put("userID",mUser.getUid());
            smsRef.child(OtherUserID).child(mUser.getUid()).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        smsRef.child(mUser.getUid()).child(OtherUserID).push().updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (task.isSuccessful()){
                                    inputSMS.setText(null);
                                    Toast.makeText(ChatActivity.this, "смс отправлен", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }

                }
            });
        }
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