package com.example.graduation;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MypageFragment extends Fragment {
    private static final String TAG = "MypageFragment";
    private Button userinfo,mylogout,userout;;
    private String userid, useremail;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference1;
    TextView usermyid;
    private FirebaseUser user;

    private HomeFragment home1;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.frag_mypage,container,false);
        usermyid = (TextView)view.findViewById(R.id.usermyid);
        userinfo = (Button)view.findViewById(R.id.userinfo);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userid =user.getUid();
            useremail= user.getEmail();
        }
        usermyid.setText(useremail);
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
        home1= new HomeFragment();
        mylogout =(Button)view.findViewById(R.id.mylogout);
        mylogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(new Intent(getActivity(), MainActivity.class));
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, home1);
                transaction.commit(); //저장

            }
        });

        userout=(Button) view.findViewById(R.id.userout);
        userout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                databaseReference1 = database.getReference("User");
                databaseReference1.child("UserAccount").child(userid).removeValue();
                revokeAccess();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, home1);
                transaction.commit(); //저장
            }
        });

        return view;

    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void revokeAccess() {
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }
                });

    }
}
