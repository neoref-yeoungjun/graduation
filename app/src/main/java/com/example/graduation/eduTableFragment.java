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

    TextView table_name;
    TextView table_apply_start;
    TextView table_apply_end;
    TextView table_institution;
    TextView table_place;
    private  String name;
    private  String apply_start;
    private  String apply_end;
    private  String institution;
    private  String place;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.edu_table,container,false);

       table_name = v.findViewById(R.id.table_name);
       table_apply_start = v.findViewById(R.id.table_apply_start);
       table_apply_end = v.findViewById(R.id.table_apply_end);
       table_institution = v.findViewById(R.id.table_institution);
       table_place = v.findViewById(R.id.table_place);


        if(getArguments() != null){
            name = getArguments().getString("table_name");
            table_name.setText(name);

            apply_start = getArguments().getString("table_apply_start");
            table_apply_start.setText(apply_start);

            apply_end = getArguments().getString("table_apply_end");
            table_apply_end.setText(apply_end);

            institution = getArguments().getString("table_institution");
            table_institution.setText(institution);

            place = getArguments().getString("table_place");
            table_place.setText(place);
        }


        return v;

    }
}