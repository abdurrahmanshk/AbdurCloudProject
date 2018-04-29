package com.example.abdulhadichaudhry.abdurcloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Login Activity";
    EditText email, password;
    Button signinBtn, signupBtn;

    String emailStr, passwordStr;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        signinBtn = (Button) findViewById(R.id.signinBtn);
        signupBtn = (Button) findViewById(R.id.signupBtn);
    }

    public void signinBtnClick(View view) {
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        if(!TextUtils.isEmpty(passwordStr) || !TextUtils.isEmpty(emailStr))
        {
            mProgress.setTitle("Signing in...");
            mProgress.setMessage("Please wait");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();
            loginUser(emailStr, passwordStr);
        }
    }

    private void loginUser(final String emailStr, String passwordStr) {
        mAuth.signInWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    mProgress.dismiss();
                    Intent into = new Intent(LoginActivity.this, MainActivity.class);
                    into.putExtra("user_email",emailStr);
                    into.putExtra("user_id",mAuth.getCurrentUser().getUid());
                    startActivity(into);
                    finish();
                }
                else
                {
                    mProgress.hide();
                    Toast.makeText(LoginActivity.this, "error in signing in!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signupBtnClick(View view) {
        Intent into = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(into);
    }


}