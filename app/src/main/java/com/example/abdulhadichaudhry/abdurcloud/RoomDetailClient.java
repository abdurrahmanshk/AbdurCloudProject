package com.example.abdulhadichaudhry.abdurcloud;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomDetailClient extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private UploadListAdapterClient mAdapter;
    private DatabaseReference mDatabase, mref;
    private StorageReference mStorage;
    private ArrayList<String> images;
    private ProgressBar mProgressCycle;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail_client);

        uid= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String key= (String) getIntent().getExtras().get("nodekey");
        mRecyclerView=findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStorage= FirebaseStorage.getInstance().getReference().child(key);
        mProgressCycle=findViewById(R.id.progress_cycle);
        mref = FirebaseDatabase.getInstance().getReference().child("rooms").child(key).child("bookedby");

        images=new ArrayList<>();
        mProgressCycle.setVisibility(View.VISIBLE);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("rooms").child(key).child("photos");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> map = new HashMap<>();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    String photoValue = (String) snapshot.getValue();
                    Toast.makeText(getApplicationContext(), photoValue, Toast.LENGTH_SHORT).show();
                    images.add(photoValue);

                }
                mAdapter= new UploadListAdapterClient(RoomDetailClient.this, images);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCycle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCycle.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void bookRoom(View view) {

        mref.setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RoomDetailClient.this, "Booked by : " + uid, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelbookRoom(View view) {
        mref.setValue("Null");
    }
}