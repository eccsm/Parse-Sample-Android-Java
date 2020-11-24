package com.eccsm.instacloneparse;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostHolder> {

    private ArrayList<Bitmap> image;
    private ArrayList<String> username;
    private ArrayList<String> comment;

    public RecyclerAdapter(ArrayList<Bitmap> image, ArrayList<String> username, ArrayList<String> comment) {
        this.image = image;
        this.username = username;
        this.comment = comment;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

        holder.textView.setText(username.get(position));
        holder.textView2.setText(comment.get(position));
        holder.imageView.setImageBitmap(image.get(position));


    }

    @Override
    public int getItemCount() {
        return username.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textView2;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}

