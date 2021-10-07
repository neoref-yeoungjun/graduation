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

public class MyinfoModifyFragment extends Fragment {
    private static final String TAG = "MyinfoModifyFragment";
    private Button info_pwd_modify;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_myinfo_modify, container, false);

        info_pwd_modify = (Button)view.findViewById(R.id.info_pwd_modify);

        info_pwd_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                bundle.putString("fromfragmyinfomodify","myinfomodify 프래그먼트");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                MypageFragment frag_mypage = new MypageFragment();
                frag_mypage.setArguments(bundle);
                transaction.replace(R.id.frame_container, frag_mypage);
                transaction.commit(); //저장

            }
        });

        return view;
    }
}
