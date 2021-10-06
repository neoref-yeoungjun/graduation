package com.example.graduation;
import android.os.Bundle;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";

    private Button question_btn;
    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_question,container,false);

        question_btn = (Button)view.findViewById(R.id.question_btn);

        question_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                bundle.putString("fromfragquestion","question 프래그먼트");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                QuestionRegisterFragment frag_question_register = new QuestionRegisterFragment();
                frag_question_register.setArguments(bundle);
                transaction.replace(R.id.frame_container, frag_question_register);
                transaction.commit(); //저장

            }
        });
        return view;

    }

}


