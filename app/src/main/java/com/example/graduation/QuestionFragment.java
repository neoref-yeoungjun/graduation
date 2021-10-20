package com.example.graduation;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";

    private Button question_btn;
    private View view;
    private questionAdapter.OnItemClickListener listener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<question> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_question,container,false);



        arrayList = new ArrayList<>();
        question_btn = (Button)view.findViewById(R.id.question_btn);
        question_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                QuestionRegisterFragment frag_question_register = new QuestionRegisterFragment();
                transaction.replace(R.id.frame_container, frag_question_register);
                transaction.commit(); //저장

            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_question);
//        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));// 구분선 추가
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
         // User 객체를 담을 어레이 리스트 (어댑터쪽으로)


        questionAdapter.OnItemClickListener listener= new questionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Bundle bundle = new Bundle();
                bundle.putString("title", arrayList.get(pos).getTitle());
                bundle.putString("con", arrayList.get(pos).getContent());
                bundle.putString("date",arrayList.get(pos).getDate());
                bundle.putString("userid",arrayList.get(pos).getUser());
                bundle.putString("key",arrayList.get(pos).getKey());

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                QuestionDetailFragment questionDetailFragment = new QuestionDetailFragment();
                questionDetailFragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, questionDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        };


        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("question"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    question question = snapshot.getValue(question.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(question); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });




        adapter = new questionAdapter(arrayList, getContext(), listener);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결




        return view;

    }

}


