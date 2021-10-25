package com.example.graduation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{
    private ArrayList<Comment> arrayList;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public CommentAdapter(ArrayList<Comment> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @NonNull
    @Override
    public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_detail, parent, false);
        CommentAdapter.CommentViewHolder holder = new CommentAdapter.CommentViewHolder(view);
        context =parent.getContext();

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {

        holder.comment_detail_userid.setText(arrayList.get(position).getEmail());
        holder.comment_datail_date.setText(arrayList.get(position).getDate());
        holder.comment_detail_content.setText(arrayList.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_detail_userid;
        TextView comment_datail_date;
        TextView comment_detail_content;



        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            this.comment_datail_date = itemView.findViewById(R.id.comment_datail_date);
            this.comment_detail_userid = itemView.findViewById(R.id.comment_detail_userid);
            this.comment_detail_content = itemView.findViewById(R.id.comment_detail_content);




        }
    }
}
