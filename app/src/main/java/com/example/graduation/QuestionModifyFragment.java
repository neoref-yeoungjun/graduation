package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class QuestionModifyFragment extends Fragment {
    private static final String TAG = "QuestionModifyFragment";

    private View view;
    private Button register_modify_enroll;
    EditText register_modify_title;
    EditText register_modify_content;
    private String que_title,que_date,que_con;
    private String userid,user_id,user_key;
    private String title, con;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_modify, container, false);

        register_modify_title=view.findViewById(R.id.register_modify_title);
        register_modify_content=view.findViewById(R.id.register_modify_content);

        if(getArguments() != null){
            que_title=getArguments().getString("title");
            register_modify_title.setText(que_title);
            que_date=getArguments().getString("date");
            que_con=getArguments().getString("con");
            register_modify_content.setText(que_con);

            user_id=getArguments().getString("userid");
            user_key=getArguments().getString("key");
        }

        register_modify_enroll = (Button)view.findViewById(R.id.register_modify_enroll);
        register_modify_enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    title=register_modify_title.getText().toString();
                    con=register_modify_content.getText().toString();
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("question");
                    databaseReference.child(user_key).child("title").setValue(title);
                    databaseReference.child(user_key).child("content").setValue(con);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    QuestionFragment questionfragment = new QuestionFragment();
                    transaction.replace(R.id.frame_container, questionfragment);
                    transaction.commit(); //저장

            }
        });
        return view;


    }
}
