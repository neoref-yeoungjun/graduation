package com.example.graduation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuestionDetailFragment extends Fragment {
    private static final String TAG = "QuestionRegisterFragment";

    private View view;
    private Button question_modify, question_remove;
    private String result;
    TextView question_title;
    TextView question_date;
    TextView question_content;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Query dataRef2;
    private String que_title,que_date,que_con;
    private String userid,user_id;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_detail, container, false);

        question_title=view.findViewById(R.id.question_title);
        question_date=view.findViewById(R.id.question_date);
        question_content=view.findViewById(R.id.question_content);

        if(getArguments() != null){
            que_title=getArguments().getString("title");
            question_title.setText(que_title);

            que_date=getArguments().getString("date");
            question_date.setText(que_date);

            que_con=getArguments().getString("con");
            question_content.setText(que_con);

            user_id=getArguments().getString("userid");

        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userid =user.getUid();
        }


        question_modify = (Button)view.findViewById(R.id.question_modify);
        question_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userid.equals(user_id)) {
                    Bundle bundle = new Bundle(); // 무언가를 담을 준비를 할 수 있는 꾸러미
                    bundle.putString("title",que_title);
                    bundle.putString("date",que_date);
                    bundle.putString("con",que_con);
                    bundle.putString("userid",user_id);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    QuestionModifyFragment frag_question_modify = new QuestionModifyFragment();
                    frag_question_modify.setArguments(bundle);
                    transaction.replace(R.id.frame_container, frag_question_modify);
                    transaction.commit(); //저장
                }
                else{
                    Toast.makeText(getContext(), "사용자가 다릅니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        question_remove = (Button)view.findViewById(R.id.question_remove);


        return view;


    }
}
