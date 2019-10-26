package com.example.prepinsta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_activity extends AppCompatActivity {

    ProgressDialog mProgress;
    EditText signup_email;
    EditText signup_password;
    Button signup_submit;
    TextView login;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initialization();
        button_action();
    }

    public void initialization() {
        signup_email = (EditText) findViewById(R.id.signUp_email);
        signup_password = (EditText) findViewById(R.id.signUp_password);
        signup_submit = (Button) findViewById(R.id.signup_submit);
        login = (TextView) findViewById(R.id.signIn);
        making_progressbar();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void button_action() {
        signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.show();
                firebaseAuth.createUserWithEmailAndPassword(signup_email.getText().toString(), signup_password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgress.dismiss();
                                if (task.isSuccessful()) {
                                    firebaseAuth.getCurrentUser().sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(SignUp_activity.this, "Registered successfully. Please check your email for verification", Toast.LENGTH_LONG).show();
                                                        signup_email.setText("");
                                                        signup_password.setText("");
                                                    } else {
                                                        Toast.makeText(SignUp_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }

                                                }
                                            });
                                } else {
                                    Toast.makeText(SignUp_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp_activity.this, Login_activity.class));
            }
        });
    }


    public void making_progressbar() {
        mProgress = new ProgressDialog(SignUp_activity.this);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
