package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemetal.auctionapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    private EditText editID, editEmail, editPass, editRePass;
    private TextView registerTx;
    private FirebaseAuth fbAuth;

    private ProgressBar progressBar;

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
        registerTx = findViewById(R.id.register);

        progressBar = findViewById(R.id.progress_bar_sign_up);
        progressBar.setVisibility(View.INVISIBLE);

        getSupportActionBar().hide();

        registerTx.setOnClickListener(new View.OnClickListener() {
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

        progressBar.setVisibility(View.VISIBLE);

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
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
