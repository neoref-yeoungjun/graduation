package com.example.graduation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class InterestFragment extends Fragment {
    String userid;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private interestAdapter.OnItemClickListener listener;
    private RecyclerView recyclerView;
    private ArrayList<edu> arrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.frag_interest,container,false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();// 로그인 상태확인
        if (user != null) {
            userid=user.getUid();
        }
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_interest);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("favorite").child(userid);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        interestAdapter.OnItemClickListener listener = new interestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
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

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot datasnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    edu edu = datasnapshot.getValue(edu.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(edu); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new interestAdapter(arrayList, getContext(), listener);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        return v;

    }
}
