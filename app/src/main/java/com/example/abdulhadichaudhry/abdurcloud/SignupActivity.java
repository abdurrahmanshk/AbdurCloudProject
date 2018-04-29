package com.example.abdulhadichaudhry.abdurcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    EditText email, number, password, name, rePassword;
    Button signupBtn;
    String emailStr, passwordStr, numberStr, nameStr, rePasswordStr;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase,mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mProgress = new ProgressDialog(this);
        email = (EditText) findViewById(R.id.email);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.repassword);
        name = (EditText) findViewById(R.id.name);
        signupBtn = (Button) findViewById(R.id.signupBtn);
    }

    public void signupBtnClick(View view) {
        emailStr = email.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        nameStr = name.getText().toString().trim();
        numberStr = number.getText().toString().trim();
        rePasswordStr = rePassword.getText().toString().trim();
        if(!passwordStr.equals(rePasswordStr))
        {
            Toast.makeText(this, "password mismatched...", Toast.LENGTH_SHORT).show();
            password.setText("");
            rePassword.setText("");
        }
        else if(!TextUtils.isEmpty(emailStr) || !TextUtils.isEmpty(passwordStr) || !TextUtils.isEmpty(numberStr))
        {
            mProgress.setTitle("Signing Up...");
            mProgress.setMessage("Please wait while we create your account!");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();
            registerUser(emailStr,numberStr,nameStr);
        }

    }

    private void registerUser(final String emailStr, final String numberStr, final String nameStr) {
        mAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId= mAuth.getCurrentUser().getUid();
                            DatabaseReference childDatabase = mDatabase.child(userId);
                            childDatabase.child("email").setValue(emailStr);
                            childDatabase.child("name").setValue(nameStr);
                            childDatabase.child("number").setValue(numberStr);
                            childDatabase.child("photo").setValue("null");
                            childDatabase.child("type").setValue("client");
                            mProgress.dismiss();
                            Intent into = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(into);
                        }
                        else
                        {
                            mProgress.hide();
                            Toast.makeText(SignupActivity.this, "password must contain a mix of characters and numbers",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}