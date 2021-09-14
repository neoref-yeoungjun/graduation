package com.example.graduation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eduAdapter extends RecyclerView.Adapter<eduAdapter.eduViewHolder> {

    private ArrayList<edu> arrayList;
    private Context context;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    private OnItemClickListener mListener =null;

    public eduAdapter(ArrayList<edu> arrayList, Context context, OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener = listener;
    }






    @NonNull
    @Override
    public eduViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_edu, parent, false);
        eduViewHolder holder = new eduViewHolder(view);
        context =parent.getContext();

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull eduViewHolder holder, int position) {

        holder.name.setText(arrayList.get(position).getName());
        holder.institution.setText(arrayList.get(position).getInstitution());
        holder.apply_start.setText(arrayList.get(position).getApply_start());
        holder.apply_end.setText(arrayList.get(position).getApply_end());
        holder.edu_start.setText(arrayList.get(position).getEdu_start());
        holder.edu_end.setText(arrayList.get(position).getEdu_end());
        holder.time.setText(arrayList.get(position).getTime());
        holder.fee.setText(arrayList.get(position).getFee());
        holder.week.setText(arrayList.get(position).getWeek());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class eduViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView institution;
        TextView apply_start;
        TextView apply_end;
        TextView edu_start;
        TextView edu_end;
        TextView time;
        TextView fee;
        TextView week;

        public eduViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.text_1);
            this.institution = itemView.findViewById(R.id.text_2);
            this.apply_start = itemView.findViewById(R.id.text_3);
            this.apply_end = itemView.findViewById(R.id.text_4);
            this.edu_start = itemView.findViewById(R.id.text_5);
            this.edu_end = itemView.findViewById(R.id.text_6);
            this.time = itemView.findViewById(R.id.text_7);
            this.week = itemView.findViewById(R.id.text_8);
            this.fee = itemView.findViewById(R.id.text_9);

            //itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Recyclerview", "position="+getAdapterPosition());

                    int pos = getAdapterPosition();
                    edu edu = arrayList.get(pos);
                    mListener.onItemClick(v, getAdapterPosition());
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("table_1",edu.getName());





                        }
                    }

                    }
                });
        }


    }
}
