package com.example.prepinsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_activity extends AppCompatActivity {

    ProgressBar login_progress;
    EditText login_email, login_password, login_mobile;
    Button login_submit, mobile_submit;
    TextView signUp, login_forgot_password;

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    int RC_SIGN_IN = 1;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        login_progress.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initialization();
        button_actions();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(Login_activity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                Toast.makeText(Login_activity.this, "Verify Your Email .", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login_activity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void button_actions() {

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_progress.setVisibility(View.VISIBLE);
                startActivity(new Intent(Login_activity.this, SignUp_activity.class));
            }
        });
        login_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_progress.setVisibility(View.VISIBLE);
                startActivity(new Intent(Login_activity.this, ForgotPasswordActivity.class));
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_progress.setVisibility(View.VISIBLE);
                signIn();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    if (firebaseAuth.getCurrentUser() != null) {
                        updateUI(mAuth.getCurrentUser());
                    } else {
                        Toast.makeText(Login_activity.this, "Verify Your Email .", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        login_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_email!=null && login_password!=null){
                    login_progress.setVisibility(View.VISIBLE);
                    login_using_email();
                }
                else if(login_email==null){
                    login_email.setError("Enter email correctly");
                }
                else if(login_password==null){
                    login_password.setError("Enter correct Password");
                }
            }
        });

        mobile_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_progress.setVisibility(View.VISIBLE);
                if (login_mobile.getText().toString().trim().length() == 10) {
                    String mobile = login_mobile.getText().toString().trim();
                    Intent intent = new Intent(Login_activity.this, VerifyPhoneActivity.class);
                    intent.putExtra("mobile", mobile);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login_activity.this, "Not a valid mobile number", Toast.LENGTH_SHORT).show();
                }
                login_progress.setVisibility(View.GONE);
            }
        });
    }

    public void login_using_email() {
        mAuth.signInWithEmailAndPassword(login_email.getText().toString(), login_password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                updateUI(mAuth.getCurrentUser());
                            } else {
                                Toast.makeText(Login_activity.this, "Verify Your Email .", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Login_activity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            startActivity(new Intent(Login_activity.this, Home_page.class));
        }
    }

    public void initialization() {
        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);
        login_submit = (Button) findViewById(R.id.login_submit);
        signUp = (TextView) findViewById(R.id.signUp);
        login_forgot_password = (TextView) findViewById(R.id.forgot_password);
        mAuth = FirebaseAuth.getInstance();
        signInButton = (SignInButton) findViewById(R.id.google_signin_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(Login_activity.this, gso);
        login_mobile = (EditText) findViewById(R.id.login_mobile);
        mobile_submit = (Button) findViewById(R.id.mobile_submit);
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
    }

}
