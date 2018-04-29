package com.example.abdulhadichaudhry.abdurcloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class UploadRoomImagesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private UploadListAdapter mAdapter;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ArrayList<String> images;
    private ProgressBar mProgressCycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);


        String key= (String) getIntent().getExtras().get("nodekey");
        //Toast.makeText(this, key, Toast.LENGTH_SHORT).show();
        mRecyclerView=findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mStorage= FirebaseStorage.getInstance().getReference().child(key);
        mProgressCycle=findViewById(R.id.progress_cycle);

        images=new ArrayList<>();

        mDatabase=FirebaseDatabase.getInstance().getReference().child("rooms").child(key).child("photos");

       // Toast.makeText(getApplicationContext(),"key "+mDatabase.getKey().toString(), Toast.LENGTH_SHORT).show();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, String> map = new HashMap<>();
                //map= (HashMap<String, String>) dataSnapshot.getChildren();
                //Toast.makeText(getApplicationContext(),map.size(),Toast.LENGTH_SHORT).show();
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    //for(DataSnapshot booksSnapshot : snapshot.child("photos").getChildren()){
                        //loop 2 to go through all the child nodes of books node
                        String photokey = snapshot.getKey();
                        String photoValue = (String) snapshot.getValue();
                        Toast.makeText(getApplicationContext(), photoValue, Toast.LENGTH_SHORT).show();
                        images.add(photoValue);
                    //}

              //      Room upload = snapshot.getValue(Room.class);
                 //   break;
                }

                Toast.makeText(getApplicationContext(),"arraylist size "+ images.size(), Toast.LENGTH_SHORT).show();
                mAdapter= new UploadListAdapter(UploadRoomImagesActivity.this, images);
                mRecyclerView.setAdapter(mAdapter);
                mProgressCycle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressCycle.setVisibility(View.INVISIBLE);
            }
        });
    }
}