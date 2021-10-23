package com.example.graduation;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class LocationFragment extends Fragment {
    private Spinner spinner1, spinner2;
    ArrayAdapter<CharSequence> adspin1, adspin2;
    String choice_do="";
    String choice_se="";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<edu> arrayList,filteredList;
    private eduAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private eduAdapter.OnItemClickListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_location,container,false);
        spinner1 = v.findViewById(R.id.spinner_loca);
        spinner2 =v.findViewById(R.id.spinner_orgn);

        recyclerView = v.findViewById(R.id.recycler_location);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("edu"); // DB 테이블 연결=


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.chungju, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                switch(position){
                                    case 0:
                                        choice_se="충주";
                                        choice_do="충주"+"\uf8ff";
                                        basest();
                                        break;
                                    case 1:
                                        choice_se="충주교현안림동주민자치위원회";
                                        baseon();
                                        break;
                                    case 2:
                                        choice_se="충주노인복지관남부분관";
                                        baseon();
                                        break;
                                    case 3:
                                        choice_se="충주노인복지관본관";
                                        baseon();
                                        break;
                                    case 4:
                                        choice_se="충주문화동평생학습센터";
                                        baseon();
                                        break;
                                    case 5:
                                        choice_se="충주문화원";
                                        baseon();
                                        break;
                                    case 6:
                                        choice_se="충주시농업기술센터";
                                        baseon();
                                        break;
                                    case 7:
                                        choice_se="충주시립도서관";
                                        baseon();
                                        break;
                                    case 8:
                                        choice_se="충주시바이오산업과";
                                        baseon();
                                        break;
                                    case 9:
                                        choice_se="충주시정보통신과";
                                        baseon();
                                        break;
                                    case 10:
                                        choice_se="충주열린학교";
                                        baseon();
                                        break;
                                    case 11:
                                        choice_se="충주종합사회복지관";
                                        baseon();
                                        break;
                                    case 12:
                                        choice_se="충주평생학습관(본관)";
                                        baseon();
                                        break;
                                    case 13:
                                        choice_se="충주평생학습관(분관)";
                                        baseon();
                                        break;
                                    case 14:
                                        choice_se="충주한국교통대학교부설평생교육원";
                                        baseon();
                                        break;
                                    case 15:
                                        choice_se="충주호암.직동행정복지센터주민자치위원회";
                                        baseon();
                                        break;
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case 1:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.cheongju, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 2:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jecheon, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 3:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.boeun, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 4:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.okcheon, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 5:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.yeongdong, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 6:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jeungpyeong, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 7:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jincheon, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 8:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.goesan, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 9:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.eumseong, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;
                    case 10:
                        adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.danyang, android.R.layout.simple_spinner_dropdown_item);
                        adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner2.setAdapter(adspin2);
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        adapter = new eduAdapter(arrayList, getContext(), listener);
        recyclerView.setAdapter(adapter);
        return v;



    }

    public void baseon(){
        databaseReference.orderByChild("institution").equalTo(choice_se).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void basest(){
        databaseReference.orderByChild("institution").startAt(choice_se).endAt(choice_do).addListenerForSingleValueEvent(new ValueEventListener() {
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

}
