package com.example.myapp5;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView firstUserProfile,secondUserProfile;
    TextView firstUserText, secondUserText;

    public ChatMyViewHolder(View view) {
        super(view);

        firstUserProfile=view.findViewById(R.id.firstUserProfile);
        secondUserProfile=view.findViewById(R.id.secondUserProfile);
        firstUserText=view.findViewById(R.id.firstUserText);
        secondUserText=view.findViewById(R.id.secondUserText);

    }
}
