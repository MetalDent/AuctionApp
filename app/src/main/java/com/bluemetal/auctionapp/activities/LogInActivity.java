package com.bluemetal.auctionapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bluemetal.auctionapp.R;


public class LogInActivity extends AppCompatActivity {

    EditText email, password;
    Button login, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        login = (Button) findViewById(R.id.logIn);
        password = (EditText) findViewById(R.id.passkey);
        signUp = (Button) findViewById(R.id.signUp);

        getSupportActionBar().hide();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(homeIntent);
            }
        });

    }
}
