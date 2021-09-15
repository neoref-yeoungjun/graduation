package com.example.graduation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class eduTableFragment extends Fragment {

    TextView table_apply_start;
    TextView table_apply_url;
    TextView table_apply_end;
    TextView table_edu_person;
    TextView table_edu_start;
    TextView table_edu_end;
    TextView table_institution;
    TextView table_name;
    TextView table_time;
    TextView table_fee;
    TextView table_week;
    TextView table_outlook;
    TextView table_place;
    TextView table_target;
    TextView table_teacher;
    TextView table_teacher_info;

    private String apply_start;
    private String apply_url;
    private String apply_end;
//    private String edu_person;
    private String edu_start;
    private String edu_end;
    private String institution;
    private String name;
    private String time;
    private String fee;
    private String week;
    private String outlook;
    private String place;
    private String target;
    private String teacher;
    private String teacher_info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edu_table,container,false);

       table_name = v.findViewById(R.id.table_name);
       table_outlook = v.findViewById(R.id.table_outlook);
       table_institution = v.findViewById(R.id.table_institution);
       table_place = v.findViewById(R.id.table_place);
       table_week = v.findViewById(R.id.table_week);
       table_time = v.findViewById(R.id.table_time);
       table_teacher = v.findViewById(R.id.table_teacher);
       table_edu_person = v.findViewById(R.id.table_edu_person);
       table_apply_start = v.findViewById(R.id.table_apply_start);
       table_apply_end = v.findViewById(R.id.table_apply_end);
       table_edu_start = v.findViewById(R.id.table_edu_start);
       table_edu_end = v.findViewById(R.id.table_edu_end);
       table_target = v.findViewById(R.id.table_target);
       table_fee = v.findViewById(R.id.table_fee);
       table_teacher_info = v.findViewById(R.id.table_teacher_info);
       table_apply_url = v.findViewById(R.id.table_apply_url);

        if(getArguments() != null){
            name = getArguments().getString("table_name");
            table_name.setText(name);

            outlook = getArguments().getString("table_outlook");
            table_outlook.setText(outlook);

            institution = getArguments().getString("table_institution");
            table_institution.setText(institution);

            place = getArguments().getString("table_place");
            table_place.setText(place);

            week = getArguments().getString("table_week");
            table_week.setText(week);

            time = getArguments().getString("table_time");
            table_time.setText(time);

            teacher = getArguments().getString("table_teacher");
            table_teacher.setText(teacher);

//            edu_person = getArguments().getString("table_edu_person");
//            table_edu_person.setText(edu_person);

            apply_start = getArguments().getString("table_apply_start");
            table_apply_start.setText(apply_start);

            apply_end = getArguments().getString("table_apply_end");
            table_apply_end.setText(apply_end);

            edu_start = getArguments().getString("table_edu_start");
            table_edu_start.setText(edu_start);

            edu_end = getArguments().getString("table_edu_end");
            table_edu_end.setText(edu_end);

            target = getArguments().getString("table_target");
            table_target.setText(target);

            fee = getArguments().getString("table_fee");
            table_fee.setText(fee);

            teacher_info = getArguments().getString("table_teacher_info");
            table_teacher_info.setText(teacher_info);

            apply_url = getArguments().getString("table_apply_url");
            table_apply_url.setText(apply_url);
        }


        return v;

    }
}