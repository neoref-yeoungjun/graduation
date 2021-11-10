package com.example.graduation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button mLoginBtn, mRegisterBtn, mPwdBtn;
    EditText mEmailText, mPasswordText;
    private FirebaseAuth firebaseAuth;
    private BottomNavigationView bottomNavigationView;

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
        mPwdBtn = findViewById(R.id.id_pwd_search);
        Intent intent = getIntent();

        //가입 버튼 눌리면
        mRegisterBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        // 비밀번호 변경 버튼 눌리면
        mPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,PwdActivity.class));
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
                                    intent.putExtra("uid",firebaseAuth.getCurrentUser().getUid());
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this,"로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavi_login);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_fragment1 :
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("uid",firebaseAuth.getCurrentUser().getUid());
                        startActivity(intent);
                        break;

                    case R.id.item_fragment2://이전 버튼
                        onBackPressed();
                        break;

                    case R.id.item_fragment3://새로 고침
                        Intent intent2 = getIntent();
                        finish();
                        startActivity(intent2);

                        break;

                    case R.id.item_fragment4:
                        if (user != null) {
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case R.id.item_fragment5:
                        if (user != null) {
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                }
                return true;
            }
        });

    }

    



}
