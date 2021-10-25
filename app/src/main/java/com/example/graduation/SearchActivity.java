package com.example.graduation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchActivity extends Fragment {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment home1;
    private InterestFragment interest1;
    private SettingFragment setting1;
    private Spinner spinner;
    EditText editText;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<edu> arrayList,filteredList;
    private eduAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private eduAdapter.OnItemClickListener listener;
    String searchOption="name";
    String text,text2;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.activity_search, container, false);
        btn = v.findViewById(R.id.searchBtn);
        spinner = v.findViewById(R.id.spinner);



        btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch(position){
                                case 0:
                                    searchOption="name";
                                    break;
                                case 1:
                                    searchOption="institution";
                                    break;
                                case 2:
                                    searchOption="week";
                                    break;
                                case 3:
                                    searchOption="fee";
                                    break;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    text = editText.getText().toString();
                    text2 = text+"\uf8ff";
                    databaseReference.orderByChild(searchOption).startAt(text).endAt(text2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                            arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                                edu edu = snapshot.getValue(edu.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                                arrayList.add(edu); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                            }
                            Collections.reverse(arrayList);// 리스트 저장 및 새로고침해야 반영이 됨
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // 디비를 가져오던중 에러 발생 시
                            Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
                        }
                    });

                }
            });


        eduAdapter.OnItemClickListener listener= new eduAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
//                eduAdapter.eduViewHolder viewHolder = (eduAdapter.eduViewHolder) recyclerView.findViewHolderForAdapterPosition(pos);
                Bundle bundle = new Bundle();
                bundle.putString("table_name",arrayList.get(pos).getName());
                bundle.putString("table_outlook",arrayList.get(pos).getOutlook());
                bundle.putString("table_institution",arrayList.get(pos).getInstitution());
                bundle.putString("table_place",arrayList.get(pos).getPlace());
                bundle.putString("table_week",arrayList.get(pos).getWeek());
                bundle.putString("table_time",arrayList.get(pos).getTime());
                bundle.putString("table_teacher",arrayList.get(pos).getTeacher());
                bundle.putString("table_edu_person", String.valueOf(arrayList.get(pos).getEdu_person()));
                bundle.putString("table_apply_start",arrayList.get(pos).getApply_start());
                bundle.putString("table_apply_end",arrayList.get(pos).getApply_end());
                bundle.putString("table_edu_start",arrayList.get(pos).getEdu_start());
                bundle.putString("table_edu_end",arrayList.get(pos).getEdu_end());
                bundle.putString("table_target",arrayList.get(pos).getTarget());
                bundle.putString("table_fee",arrayList.get(pos).getFee());
                bundle.putString("table_teacher_info",arrayList.get(pos).getTeacher_info());
                bundle.putString("table_apply_url",arrayList.get(pos).getApply_url());
                bundle.putString("table_cate",arrayList.get(pos).getCategory());


                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                eduTableFragment eduTableFragment = new eduTableFragment();
                eduTableFragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, eduTableFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        };

            recyclerView = v.findViewById(R.id.recycler_view_search);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            editText = v.findViewById(R.id.searchWord);
            arrayList = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
            databaseReference = database.getReference("edu"); // DB 테이블 연결=

            adapter = new eduAdapter(arrayList, getContext(), listener);
            recyclerView.setAdapter(adapter);


        return v;
        }



    private void setFrag(int n) {
        fm = getChildFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.frame_container, home1, "Frag1");
                ft.addToBackStack(null);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.frame_container, interest1, "Frag2");
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frame_container, setting1, "Frag3");
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }






}
