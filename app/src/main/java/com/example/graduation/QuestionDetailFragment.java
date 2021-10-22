package com.example.graduation;

import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class QuestionDetailFragment extends Fragment {
    private static final String TAG = "QuestionRegisterFragment";

    private View view;
    private Button question_modify, question_remove,question_comment_btn;
    private String result;
    TextView question_title;
    TextView question_date;
    TextView question_content;
    EditText question_comment_content;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1,databaseReference2,databaseReference3, dataRef;
    private Query dataRef2;
    private String que_title,que_date,que_con;
    private String userid,user_id,useremail;
    private String userkey, str2, str;
    private ArrayList<Comment> arrayList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String EMAIL_PATTERN = "([\\w.])(?:[\\w.]*)(@.*)";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_question_detail, container, false);
        QuestionDetailFragment questionDetailFragment = new QuestionDetailFragment();
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
            userkey=getArguments().getString("key");

        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userid =user.getUid();
            useremail= user.getEmail();
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
                    bundle.putString("key",userkey);
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
        question_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid.equals(user_id)) {
                    database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동

                    database.getReference("comment_question").orderByChild("key").equalTo(userkey).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            snapshot.getRef().removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    databaseReference1 = database.getReference("question");
                    databaseReference1.child(userkey).removeValue();

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    QuestionFragment questionFragment = new QuestionFragment();
                    transaction.replace(R.id.frame_container, questionFragment);
                    transaction.commit(); //저장
                } else{
                    Toast.makeText(getContext(), "사용자가 다릅니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        question_comment_content = (EditText) view.findViewById(R.id.question_comment_content);



        question_comment_btn = (Button)view.findViewById(R.id.question_comment_btn);
        question_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long now = System.currentTimeMillis();
                java.text.SimpleDateFormat dayTime = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(now);
                str = dayTime.format(date);
                str2 =question_comment_content.getText().toString();
                String eemail=useremail.replaceAll(EMAIL_PATTERN, "$1****$2");
                dataRef =FirebaseDatabase.getInstance().getReference("comment_question").push();
                String mykey= dataRef.getKey();
                Comment comment = new Comment(eemail,str,str2,userkey,mykey);
                dataRef.setValue(comment);
                adapter.notifyDataSetChanged();

                question_comment_content.setText(null);

            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_question_comment);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        CommentAdapter.OnItemClickListener listener= null;

        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference3 = database.getReference("comment_question"); // DB 테이블 연결
        databaseReference3.orderByChild("key").equalTo(userkey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    Comment comment = snapshot.getValue(Comment.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList.add(comment); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                Collections.reverse(arrayList);
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("HomeFragment", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });




        adapter = new CommentAdapter(arrayList, getContext(), listener);
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        return view;


    }
}
