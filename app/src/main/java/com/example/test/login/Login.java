package com.example.test.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextView mail, password;
    Button login, signup;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = (TextView) findViewById(R.id.editText);
        password = (TextView) findViewById(R.id.editText2);
        login = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();

    }


    public void SignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void Login(View view) {
        String email = mail.getText().toString();
        String pwd = password.getText().toString();
        signIn(email, pwd);
    }

    private void signIn(String email, String pwd) {
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_LONG).show();

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Toast.makeText(Login.this, "UnSuccessfully logged in " + task.getException(), Toast.LENGTH_LONG).show();
                    updateUI(null);
                }
            }
        });


    }

    private void signOut(View v){
        mAuth.signOut();
        updateUI(null);
    }


    private boolean validateForm() {
        boolean valid = true;
        String email = mail.getText().toString();
        if(TextUtils.isEmpty(email)){

            mail.setError("Required.");
            valid = false;
        } else {
            mail.setError(null);

        }
        String pwd = password.getText().toString();
        if (TextUtils.isEmpty(pwd)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        else {
            Toast.makeText(Login.this,"Login Unsuccessfull",Toast.LENGTH_SHORT).show();

        }
    }
}

