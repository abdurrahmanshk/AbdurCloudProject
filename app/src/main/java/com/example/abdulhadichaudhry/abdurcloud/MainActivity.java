package com.example.abdulhadichaudhry.abdurcloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String userUID,userEmail;
    private Toolbar mToolbar;
    static String LoggedIn_User_Email;
    private float m_text;
    private String m_text2;
    private String username;
    private String uid;
    private DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            if (userEmail.equals("admin@admin.com"))
            {
                sendToAdminHomePage();
            }

        }

    }

    private void sendToAdminHomePage() {
        Intent into = new Intent(MainActivity.this, AdminHome.class);
        startActivity(into);
        finish();
    }

    private void sendToSigninPage() {
        Intent into = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(into);
        finish();
    }

    public void logoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        sendToSigninPage();
    }

    public void editProfile(View view) {
        Intent into = new Intent(MainActivity.this,EditProfileActivity.class);
        startActivity(into);
        finish();
    }

    public void addReview(View view) {
        Intent into = new Intent(MainActivity.this,AddReviewActivity.class);
        startActivity(into);
        finish();
    }

    public void viewRoom(View view) {
        Intent into = new Intent(MainActivity.this,ClientViewRoomsActivity.class);
        startActivity(into);
    }
}