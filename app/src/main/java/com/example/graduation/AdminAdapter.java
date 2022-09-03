package com.example.graduation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder>{
    private ArrayList<edu> arrayList;
    private Context context;
    String start1;
    String end1;
    private FirebaseDatabase database;
    private DatabaseReference dataremove, checkremove;
    String userid;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    long now = System.currentTimeMillis();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    final String today = sdf.format(now);
    private AdminAdapter.OnItemClickListener mListener =null;
    Activity activity;



    public AdminAdapter(ArrayList<edu> arrayList, Context context, AdminAdapter.OnItemClickListener listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AdminAdapter.AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_admin, parent, false);

        AdminAdapter.AdminViewHolder holder = new AdminAdapter.AdminViewHolder(view);
        context =parent.getContext();

        return holder;
    }



    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdminViewHolder holder, int position) {

        holder.apply_start.setText(arrayList.get(position).getApply_start());
        holder.apply_end.setText(arrayList.get(position).getApply_end());
        holder.edu_start.setText(arrayList.get(position).getEdu_start());
        holder.edu_end.setText(arrayList.get(position).getEdu_end());
        holder.institution.setText(arrayList.get(position).getInstitution());
        holder.name.setText(arrayList.get(position).getName());
        holder.time.setText(arrayList.get(position).getTime());
        holder.fee.setText(arrayList.get(position).getFee());
        holder.week.setText(arrayList.get(position).getWeek());
        holder.cate.setText(arrayList.get(position).getCategory());
        holder.onBind(arrayList.get(position));
        holder.onbtn(arrayList.get(position));



    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }



    public class AdminViewHolder extends RecyclerView.ViewHolder {
        TextView apply_start;
        TextView apply_end;
        TextView edu_start;
        TextView edu_end;
        TextView institution;
        TextView name;
        TextView time;
        TextView fee;
        TextView cate;
        TextView week;
        TextView apply_day;
        Button removeadmin;


        public AdminViewHolder(@NonNull View itemView)  {
            super(itemView);
            this.name = itemView.findViewById(R.id.admin_name2);
            this.institution = itemView.findViewById(R.id.admin_insti);
            this.cate = itemView.findViewById(R.id.admin_q);
            this.apply_start = itemView.findViewById(R.id.admin_aps);
            this.apply_end = itemView.findViewById(R.id.admin_ape);
            this.edu_start = itemView.findViewById(R.id.admin_interedus);
            this.edu_end = itemView.findViewById(R.id.admin_interedue);
            this.time = itemView.findViewById(R.id.admin_intertime);
            this.week = itemView.findViewById(R.id.admin_interweek);
            this.fee = itemView.findViewById(R.id.admin_interfee);
            this.apply_day = itemView.findViewById(R.id.admin_apply_day);
            this.removeadmin = itemView.findViewById(R.id.admin_Btn);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Recyclerview", "position="+getAdapterPosition());

                    int pos = getAdapterPosition();
                    edu edu = arrayList.get(pos);
                    mListener.onItemClick(v, pos);
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("table_1",edu.getName());





                        }
                    }

                }
            });
        }
        public void onBind(edu edu){
            String start1 = edu.getApply_start().toString();
            String end1 = edu.getApply_end().toString();
            String end2 = edu.getEdu_end().toString();
            if(today.compareTo(start1)<0 | today.compareTo(end2)> 0) {
                apply_day.setText("교육종료");
                apply_day.setBackgroundColor(Color.parseColor("#787878"));
            }
            if(today.compareTo(start1)>0 &today.compareTo(end1)<=0){
                apply_day.setText("접수 중");
                apply_day.setBackgroundColor(Color.parseColor("#00C853"));

            }
            if(today.compareTo(end1)>0 & today.compareTo(end2)<=0){
                apply_day.setText("교육 중");
                apply_day.setBackgroundColor(Color.parseColor("#82B1FF"));

            }
        }

        public void onbtn(edu edu){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                userid = user.getUid();
            }
            removeadmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "강좌를 삭제하였습니다.", Toast.LENGTH_SHORT).show();
                    database = FirebaseDatabase.getInstance();;
                    dataremove=database.getReference("edu");
                    dataremove.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                edu edu2 = datasnapshot.getValue(edu.class);
                                if(edu2.getFIELD1()==edu.getFIELD1()) {
                                    datasnapshot.getRef().removeValue();
                                    arrayList.remove(getAdapterPosition());
                                    notifyItemRemoved(getAdapterPosition());
                                    notifyDataSetChanged();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }



    }
}
