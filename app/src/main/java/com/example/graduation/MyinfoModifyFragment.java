package com.example.graduation;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyinfoModifyFragment extends Fragment {
    private static final String TAG = "MyinfoModifyFragment";
    private Button info_pwd_modify;
    private View view;
    private TextView pswd, newpswd, newpswd2;
    private String newPassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_myinfo_modify, container, false);

        info_pwd_modify = (Button)view.findViewById(R.id.info_pwd_modify);
        pswd=view.findViewById(R.id.info_before_pwd);
        newpswd=view.findViewById(R.id.info_after_pwd);
        newpswd2=view.findViewById(R.id.info_after_pwd2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (newpswd.getText().toString().equals(newpswd2.getText().toString())) {
            info_pwd_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        newPassword=newpswd.getText().toString();
                        user.updatePassword(newPassword);
                        Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                        bundle.putString("fromfragmyinfomodify", "myinfomodify 프래그먼트");
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        MypageFragment frag_mypage = new MypageFragment();
                        frag_mypage.setArguments(bundle);
                        transaction.replace(R.id.frame_container, frag_mypage);
                        transaction.commit(); //저장
                    }
            });
        }
        return view;
    }
}
