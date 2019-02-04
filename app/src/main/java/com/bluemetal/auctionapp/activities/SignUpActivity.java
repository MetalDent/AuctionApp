package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluemetal.auctionapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.security.auth.login.LoginException;


public class SignUpActivity extends AppCompatActivity {

    private EditText editID, editEmail, editPass, editRePass;
    private Button registerBn;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fbAuth = FirebaseAuth.getInstance();

        if(fbAuth.getCurrentUser() != null) {
            //Start HomeActivity
            finish();
            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        }

        editID = findViewById(R.id.editID);
        editEmail = findViewById(R.id.textemailID);
        editPass = findViewById(R.id.editPasskey);
        editRePass = findViewById(R.id.editPasskey2);
        registerBn = findViewById(R.id.register);

        registerBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister();
            }
        });
    }

    void userRegister() {
        String id = editID.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String pass = editPass.getText().toString().trim();
        String rePass = editRePass.getText().toString().trim();

        if(id.isEmpty() || email.isEmpty() || pass.isEmpty() || rePass.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pass.equals(rePass)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        fbAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Error... Please Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
