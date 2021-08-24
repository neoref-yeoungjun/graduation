package com.example.graduation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eduAdapter extends RecyclerView.Adapter<eduAdapter.eduViewHolder> {

    private ArrayList<edu> arrayList;
    private Context context;

    public eduAdapter(ArrayList<edu> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public eduViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_edu , parent, false);
        eduViewHolder holder = new eduViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull eduViewHolder holder, int position) {

        holder.text_1.setText(arrayList.get(position).getName());
        holder.text_2.setText(arrayList.get(position).getInstitution());
        holder.text_3.setText(arrayList.get(position).getApply_start());
        holder.text_4.setText(arrayList.get(position).getEdu_start());
        holder.text_5.setText(arrayList.get(position).getWeek());

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class eduViewHolder extends RecyclerView.ViewHolder {
        TextView text_1;
        TextView text_2;
        TextView text_3;
        TextView text_4;
        TextView text_5;
        public eduViewHolder(@NonNull View itemView) {
            super(itemView);
            this.text_1=itemView.findViewById(R.id.text_1);
            this.text_2=itemView.findViewById(R.id.text_2);
            this.text_3=itemView.findViewById(R.id.text_3);
            this.text_4=itemView.findViewById(R.id.text_4);
            this.text_5=itemView.findViewById(R.id.text_5);


        }
    }
}
