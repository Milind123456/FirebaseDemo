package com.example.test.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUp extends AppCompatActivity {
    EditText mail,password;
    Button createAccount;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mail = (EditText)findViewById(R.id.editText3);
        password = (EditText)findViewById(R.id.editText5);
        createAccount = (Button)findViewById(R.id.button3);
        mAuth = FirebaseAuth.getInstance();
    }

    public void Signup(View view) {
        String email = mail.getText().toString();
        String pwd = password.getText().toString();

        CreateAccount(email,pwd);

    }

    private void CreateAccount(String email, String pwd) {
        if(!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this,"Created account successfully", LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }
                else{
                    Toast.makeText(SignUp.this," Unsuccessfully "+task.getException(), LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent i = new Intent(this,Login.class);
            startActivity(i);
        }
        else {
            Toast.makeText(SignUp.this,"Login Unsuccessfull",Toast.LENGTH_SHORT).show();

        }

    }
    private void signOut(){
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
}
