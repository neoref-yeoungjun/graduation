package com.example.graduation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<edu> arrayList;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private eduAdapter.OnItemClickListener listener;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));// 구분선 추가
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)


        eduAdapter.OnItemClickListener listener= new eduAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
//                eduAdapter.eduViewHolder viewHolder = (eduAdapter.eduViewHolder) recyclerView.findViewHolderForAdapterPosition(pos);
                Bundle bundle = new Bundle();
                bundle.putString("table_1",arrayList.get(pos).getName());

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                eduTableFragment eduTableFragment = new eduTableFragment();
                eduTableFragment.setArguments(bundle);
                transaction.replace(R.id.frame_container, eduTableFragment);
                transaction.commit();

            }
        };
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("edu"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    edu edu = snapshot.getValue(edu.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(edu); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });




        adapter = new eduAdapter(arrayList, getContext(), listener);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결


        return view;
    }


}

