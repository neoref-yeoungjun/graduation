package com.example.graduation;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PwdActivity extends AppCompatActivity {
    private static final String TAG = "PwdActivity";
    Button pwdButton;
    private String emailAddress;
    EditText pwdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);

        pwdEditText= findViewById(R.id.pwdEditText);
        pwdButton=(Button) findViewById(R.id.pwdButton);
        pwdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAddress =pwdEditText.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.setLanguageCode("kr");
                auth.sendPasswordResetEmail(emailAddress);
                Intent intent = new Intent(PwdActivity.this,LoginActivity.class );
                startActivity(intent);
            }
        });
    }

}

