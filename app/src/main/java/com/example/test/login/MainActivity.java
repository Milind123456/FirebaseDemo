package com.example.test.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        signout = (Button) findViewById(R.id.button5);

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                updateUI(null);
            }
        });

    }


    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }


    }

    public void complain(View view) {
        Intent i = new Intent(this,Complain.class);
        startActivity(i);
    }
}
