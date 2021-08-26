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

        holder.name.setText(arrayList.get(position).getName());
        holder.institution.setText(arrayList.get(position).getInstitution());
        holder.apply_start.setText(arrayList.get(position).getApply_start());
        holder.edu_start.setText(arrayList.get(position).getEdu_start());
        holder.time.setText(arrayList.get(position).getTime());

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size() : 0);
    }

    public class eduViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView institution;
        TextView apply_start;
        TextView edu_start;
        TextView time;

        public eduViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name=itemView.findViewById(R.id.text_1);
            this.institution=itemView.findViewById(R.id.text_2);
            this.apply_start=itemView.findViewById(R.id.text_3);
            this.edu_start=itemView.findViewById(R.id.text_4);
            this.time=itemView.findViewById(R.id.text_5);


        }
    }
}
