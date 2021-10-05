package com.example.graduation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button mLoginBtn, mRegisterBtn;
    EditText mEmailText, mPasswordText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        //버튼 등록하기
        mEmailText = findViewById(R.id.id);
        mPasswordText = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);
        mRegisterBtn = findViewById(R.id.sign_up);
        Intent intent = getIntent();

        //가입 버튼 눌리면
        mRegisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        //로그인 버튼이 눌리면
        mLoginBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish(); // 현재 액티비티 파괴
                                }else{
                                    Toast.makeText(LoginActivity.this,"로그인 실패...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }



}
