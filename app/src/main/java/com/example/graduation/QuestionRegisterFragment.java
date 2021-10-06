package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class QuestionRegisterFragment extends Fragment {
    private static final String TAG = "QuestionRegisterFragment";

    private View view;
    private Button register_enroll;
    private String result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_register, container, false);

        register_enroll = (Button)view.findViewById(R.id.register_enroll);
        register_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                bundle.putString("fromfragquestionRegister","questionRegister 프래그먼트");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                QuestionFragment frag_question = new QuestionFragment();
                frag_question.setArguments(bundle);
                transaction.replace(R.id.frame_container, frag_question);
                transaction.commit(); //저장

            }
        });
        return view;


    }
}