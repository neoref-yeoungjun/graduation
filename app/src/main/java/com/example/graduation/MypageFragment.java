package com.example.graduation;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MypageFragment extends Fragment {
    private static final String TAG = "MypageFragment";
    private Button userinfo;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_mypage,container,false);

        userinfo = (Button)view.findViewById(R.id.userinfo);

        userinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                bundle.putString("fromfragmypage","mypage 프래그먼트");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                MyinfoModifyFragment frag_myinfo_modify = new MyinfoModifyFragment();
                frag_myinfo_modify.setArguments(bundle);
                transaction.replace(R.id.frame_container, frag_myinfo_modify);
                transaction.commit(); //저장

            }
        });
        return view;

    }
}