package com.example.abdulhadichaudhry.abdurcloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 2;
    private String uid;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;
    private ImageView profilePic;
    private EditText fullname,email,number;
    private String emailstr, namestr, numberstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profilePic = findViewById(R.id.userImage);
        mProgress= new ProgressDialog(this);
        uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        fullname= findViewById(R.id.userFullname);
        email=findViewById(R.id.userEmail);
        number = findViewById(R.id.userNumber);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User obj = dataSnapshot.getValue(User.class);

                namestr=obj.getName();
                emailstr=obj.getEmail();
                numberstr = obj.getNumber();

                email.setText(emailstr);
                email.setEnabled(false);
                number.setText(numberstr);
                fullname.setText(namestr);
                Toast.makeText(getApplicationContext(),"name : " + namestr, Toast.LENGTH_SHORT).show();
                if(!obj.getPhoto().equals("null"))
                {
                    Picasso.with(getApplicationContext()).load(obj.getPhoto()).fit().into(profilePic);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void updateProfile(View view) {
        mDatabase.child("name").setValue(fullname.getText().toString().trim());
        mDatabase.child("number").setValue(number.getText().toString().trim());
        Toast.makeText(getApplicationContext(), "Updated Profile", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void openGallery(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            mProgress.setMessage("Uploading...");
            mProgress.show();
            Uri uri = data.getData();
            final DatabaseReference mDatabase2= mDatabase.child("photo");
            StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("ProfilePic").child(uid);
            mStorage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDatabase2.setValue(taskSnapshot.getDownloadUrl().toString());
                    mProgress.dismiss();
                    Picasso.with(getApplicationContext()).load(taskSnapshot.getDownloadUrl()).fit().into(profilePic);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"Image Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
