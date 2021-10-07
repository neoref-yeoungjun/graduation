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

public class QuestionDetailFragment extends Fragment {
    private static final String TAG = "QuestionRegisterFragment";

    private View view;
    private Button question_modify;
    private String result;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_detail, container, false);

        question_modify = (Button)view.findViewById(R.id.question_modify);
        question_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                bundle.putString("fromquestiondatail","questiondatail 프래그먼트");
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                QuestionModifyFragment frag_question_modify = new QuestionModifyFragment();
                frag_question_modify.setArguments(bundle);
                transaction.replace(R.id.frame_container, frag_question_modify);
                transaction.commit(); //저장

            }
        });
        return view;


    }
}
