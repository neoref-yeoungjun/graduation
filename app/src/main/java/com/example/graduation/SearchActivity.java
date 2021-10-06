package com.example.graduation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class SearchActivity extends AppCompatActivity{
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
    String text;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn = findViewById(R.id.searchBtn);
        spinner = findViewById(R.id.spinner);



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
                    databaseReference.orderByChild(searchOption).startAt(text).endAt(text).addListenerForSingleValueEvent(new ValueEventListener() {
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
            recyclerView = findViewById(R.id.recycler_view_search);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            editText = findViewById(R.id.searchWord);
            arrayList = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
            databaseReference = database.getReference("edu"); // DB 테이블 연결=

            adapter = new eduAdapter(arrayList, getApplicationContext(), listener);
            recyclerView.setAdapter(adapter);

            bottomNavigationView = findViewById(R.id.bottomNavi);

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override

                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item_fragment1:
                            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.item_fragment2://이전 버튼
                            onBackPressed();
                            break;

                        case R.id.item_fragment3://새로 고침


                            break;

                        case R.id.item_fragment4:
                            setFrag(1);
                            break;

                        case R.id.item_fragment5:
                            setFrag(2);
                            break;


                    }
                    return true;
                }
            });
            home1 = new HomeFragment();
            interest1 = new InterestFragment();
            setting1 = new SettingFragment();


        }



    private void setFrag(int n) {
        fm = getSupportFragmentManager();
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }




}
