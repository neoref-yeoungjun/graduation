package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class QuestionRegisterFragment extends Fragment {
    private static final String TAG = "QuestionRegisterFragment";

    private View view;
    private Button register_enroll;
    private String result;
    private String userid,useremail;
    EditText title,con;
    private ArrayList<question> quearrayList;
    private String titlest,contenst;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference dataRef;
    private static final String EMAIL_PATTERN = "([\\w.])(?:[\\w.]*)(@.*)";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_register, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            useremail =user.getEmail();
            userid=user.getUid();
        }
        String eemail=useremail.replaceAll(EMAIL_PATTERN, "$1****$2");
        title = view.findViewById(R.id.register_title);
        con=view.findViewById(R.id.register_content);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference(); // DB 테이블 연결
        register_enroll = (Button)view.findViewById(R.id.register_enroll);
        register_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titlest=title.getText().toString();
                contenst=con.getText().toString();
                long now = System.currentTimeMillis();
                java.text.SimpleDateFormat dayTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(now);
                String str = dayTime.format(date);


                dataRef =databaseReference.child("question").push();
                String key= dataRef.getKey();
                question question = new question(titlest,contenst,userid,str,eemail,key);
                dataRef.setValue(question);
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