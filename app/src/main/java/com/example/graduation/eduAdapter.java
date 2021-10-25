package com.example.graduation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.content.Intent;
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
import java.util.List;

public class eduAdapter extends RecyclerView.Adapter<eduAdapter.eduViewHolder> {

    private ArrayList<edu> arrayList;
    private ArrayList<Integer> check;

    private Context context;
    String start1;
    String end1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference, dataRef,dataremove,datainter,datacheck,datacheck2,checkremove;
    String userid;
    private int ch;

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    long now = System.currentTimeMillis();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    final String today = sdf.format(now);
    private OnItemClickListener mListener =null;
    Activity activity;



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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull eduViewHolder holder, int position) {

        holder.apply_start.setText(arrayList.get(position).getApply_start());
        holder.apply_end.setText(arrayList.get(position).getApply_end());
        holder.edu_start.setText(arrayList.get(position).getEdu_start());
        holder.edu_end.setText(arrayList.get(position).getEdu_end());
        holder.institution.setText(arrayList.get(position).getInstitution());
        holder.name.setText(arrayList.get(position).getName());
        holder.time.setText(arrayList.get(position).getTime());
        holder.fee.setText(arrayList.get(position).getFee());
        holder.week.setText(arrayList.get(position).getWeek());
        holder.catecori.setText(arrayList.get(position).getCategory());
        holder.onBind(arrayList.get(position));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        holder.onchec(arrayList.get(position));
        if (user != null) {

            holder.oninter(arrayList.get(position));
        }




    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }



    public class eduViewHolder extends RecyclerView.ViewHolder {
        TextView apply_start;
        TextView apply_end;
        TextView edu_start;
        TextView edu_end;
        TextView institution;
        TextView name;
        TextView time;
        TextView fee;
        TextView week;
        TextView apply_day;
        CheckBox checkBox;
        TextView catecori;


        public eduViewHolder(@NonNull View itemView)  {
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
            this.catecori = itemView.findViewById(R.id.text_cate);
            this.apply_day = itemView.findViewById(R.id.apply_day);
            this.checkBox=itemView.findViewById(R.id.favorite_Btn);



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
                apply_day.setBackgroundColor(Color.parseColor("#84FFFF"));

            }
            if(today.compareTo(end1)>0 & today.compareTo(end2)<=0){
                apply_day.setText("교육 중");
                apply_day.setBackgroundColor(Color.parseColor("#82B1FF"));

            }
            }
            public void oninter(edu edu){
                database = FirebaseDatabase.getInstance();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    userid = user.getUid();
                }
                datacheck= database.getReference("check").child(userid);
                datacheck.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                            check check =datasnapshot.getValue(check.class);
                            if(edu.getFIELD1()==check.getInter()){
                                checkBox.setChecked(true);
                                ch=check.getInter();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            public  void onchec(edu edu){
            database = FirebaseDatabase.getInstance();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    userid = user.getUid();
                }
            check = new ArrayList<>();
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBox.setSelected(isChecked);
                    if(checkBox.isChecked()){// 로그인 상태확인
                        if (user != null) {
                                datacheck2= database.getReference("favorite").child(userid);
                                datacheck2.orderByChild("field1").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                            edu edu3 = datasnapshot.getValue(edu.class);
                                            check.add(edu3.getFIELD1());
                                        }
                                        if(check.contains(edu.getFIELD1())){

                                        }
                                        else{
                                            Toast.makeText(context, "관심강좌가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                                            database = FirebaseDatabase.getInstance();
                                            databaseReference = database.getReference("favorite").child(userid);
                                            dataRef = databaseReference.push();
                                            String key = dataRef.getKey();
                                            edu.setEdukey(key);
                                            dataRef.setValue(edu);

                                            int i = edu.getFIELD1();
                                            datainter = database.getReference("check").child(userid).push();
                                            datainter.child("inter").setValue(i);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                        }
                        else{
                            Toast.makeText(context, "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        }
                    }
                    else{
                        Toast.makeText(context, "관심강좌를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                        database = FirebaseDatabase.getInstance();;
                        dataremove=database.getReference("favorite").child(userid);
                        dataremove.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                    edu edu2 = datasnapshot.getValue(edu.class);
                                    if(edu2.getFIELD1()==edu.getFIELD1()) {
                                        datasnapshot.getRef().removeValue();
                                        checkremove=database.getReference("check").child(userid);
                                        checkremove.orderByChild("inter").equalTo(edu.getFIELD1()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                                    check check2 = datasnapshot.getValue(check.class);
                                                    if(check2.getInter()==edu.getFIELD1()) {
                                                        datasnapshot.getRef().removeValue();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }
            });



            }

    }
}
