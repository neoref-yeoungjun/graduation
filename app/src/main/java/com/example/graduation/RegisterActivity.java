package com.example.graduation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

        private static final String TAG = "SignUpActivity";
        private FirebaseAuth mAuth;
        private Button sign_up;
        private DatabaseReference mDatabaseRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            mAuth = FirebaseAuth.getInstance();
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("User");

            sign_up = findViewById(R.id.signUpButton);
            sign_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signUp();
                }
            });
        }

        // When initializing your Activity, check to see if the user is currently signed in.
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
        }


        private void signUp() {
            // 이메일
            EditText emailEditText = findViewById(R.id.emailEditText);
            String email = emailEditText.getText().toString();
            // 비밀번호
            EditText passwordEditText = findViewById(R.id.passwordEditText);
            String password = passwordEditText.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserAccount account = new UserAccount();
                                account.setIdToken(user.getUid());
                                account.setEmailId(user.getEmail());
                                // setValue : database에 정보 입력
                                mDatabaseRef.child("UserAccount").child(user.getUid()).setValue(account);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });
        }

        Intent intent = getIntent();

    }
