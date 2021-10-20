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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.questionViewHolder>{


    private ArrayList<question> arrayList;
    private Context context;
    private questionAdapter.OnItemClickListener mListener =null;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    public questionAdapter(ArrayList<question> arrayList, Context context, OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public questionAdapter.questionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_question, parent, false);
        questionAdapter.questionViewHolder holder = new questionAdapter.questionViewHolder(view);
        context =parent.getContext();



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull questionViewHolder holder, int position) {
        holder.list_question_title.setText(arrayList.get(position).getTitle());
        holder.comment_detail_userid.setText(arrayList.get(position).getEmail());
        holder.comment_datail_date.setText(arrayList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class questionViewHolder extends RecyclerView.ViewHolder {

        TextView list_question_title;
        TextView comment_detail_userid;
        TextView comment_datail_date;

        public questionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.list_question_title=itemView.findViewById(R.id.list_question_title);
            this.comment_detail_userid=itemView.findViewById(R.id.comment_detail_userid);
            this.comment_datail_date=itemView.findViewById(R.id.comment_datail_date);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Recyclerview", "position=" + getAdapterPosition());

                    int pos = getAdapterPosition();
                    question question = arrayList.get(pos);
                    mListener.onItemClick(v, pos);
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("table_2", question.getTitle());

                        }
                    }
                }
            });
        }
    }

}
