package com.example.myapp5;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    ImageView postImage, likeImage, commentImage, commentSend;
    TextView username, timeAgo, postDesc, likeCounter, commentsCounter;
    EditText inputComments;
    public static RecyclerView recyclerView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage=itemView.findViewById(R.id.profileImagePost);
        postImage=itemView.findViewById(R.id.postImage);
        likeImage=itemView.findViewById(R.id.likeImage);
        commentImage=itemView.findViewById(R.id.commentImage);
        username=itemView.findViewById(R.id.profileUsernamePost);
        timeAgo=itemView.findViewById(R.id.timeAgo);
        postDesc=itemView.findViewById(R.id.postDesc);
        likeCounter=itemView.findViewById(R.id.likeCounter);
        commentsCounter=itemView.findViewById(R.id.commentsCounter);
        commentSend=itemView.findViewById(R.id.sendComment);
        recyclerView=itemView.findViewById(R.id.recyclerViewComments);
        inputComments=itemView.findViewById(R.id.inputComments);


    }

    public void countLikes(String postKey, String uid, DatabaseReference likeRef) {
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int TotalLikes= (int) snapshot.getChildrenCount();
                    likeCounter.setText(TotalLikes+"");
                }
                else
                {
                    likeCounter.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(uid).exists())
                {
                    likeImage.setColorFilter(Color.GREEN);
                }
                else
                {
                    likeImage.setColorFilter(Color.GRAY);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void countComments(String postKey, String uid, DatabaseReference commentRef) {
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    int TotalComment= (int) snapshot.getChildrenCount();
                    commentsCounter.setText(TotalComment+"");
                }
                else
                {
                    commentsCounter.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
    }
}
