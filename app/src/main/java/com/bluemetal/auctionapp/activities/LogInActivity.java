package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemetal.auctionapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogInActivity extends AppCompatActivity {

    private EditText editEmail, editPass;
    private FirebaseAuth fbAuth;
    private TextView loginTx, signupTx;

    @Override
    protected void onStart() {
        super.onStart();
        if(fbAuth.getCurrentUser() != null) {
            //Start HomeActivity
            finish();
            startActivity(new Intent(LogInActivity.this, HomeActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        fbAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.email);
        editPass = findViewById(R.id.passkey);
        loginTx = findViewById(R.id.logIn);
        signupTx = findViewById(R.id.signUp);

        getSupportActionBar().hide();

        signupTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(homeIntent);
            }
        });

        loginTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }

    void userLogin() {
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();

        if(email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter both Email and Password.", Toast.LENGTH_SHORT).show();
            return;
        }

        fbAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Start HomeActivity
                    finish();
                    startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Failed! Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
