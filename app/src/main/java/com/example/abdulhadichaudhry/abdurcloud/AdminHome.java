package com.example.abdulhadichaudhry.abdurcloud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminHome extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String userUID,userEmail;
    private Toolbar mToolbar;
    static String LoggedIn_User_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if (currentUser==null)
        {
            sendToSigninPage();
        }
        else
        {
            userUID = mAuth.getCurrentUser().getUid();
            userEmail = mAuth.getCurrentUser().getEmail();

            LoggedIn_User_Email = userEmail;
            if (!userEmail.equals("admin@admin.com"))
            {
                sendToMainPage();
            }
        }
    }

    private void sendToMainPage() {
        Intent into = new Intent(AdminHome.this, MainActivity.class);
        startActivity(into);
        finish();
    }

    private void sendToSigninPage() {
        Intent into = new Intent(AdminHome.this,LoginActivity.class);

        startActivity(into);
        finish();
    }

    public void logoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        sendToSigninPage();
    }

    public void addRoom(View view) {
        Intent into = new Intent(AdminHome.this,AddRoomActivity.class);
        startActivity(into);
    }

    public void viewRoom(View view) {
        Intent into = new Intent(AdminHome.this,ViewRoomActivity.class);
        startActivity(into);
    }

    public void viewComments(View view) {
        Intent into = new Intent(AdminHome.this,ViewComments.class);
        startActivity(into);
    }
}