package com.example.abdulhadichaudhry.abdurcloud;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddRoomActivity extends AppCompatActivity {

    EditText roomtype, roomno, roomfloor, roomavailability,roomcost, roomfeatures;
    Button addRoomBtn, addRoomImg;
    String roomtypestr, roomnostr, roomfloorstr, roomavailabilitystr,roomcoststr, roomfeaturesstr;
    private FirebaseAuth mAuth;
    private StorageReference storageRef, storageRef2;
    private DatabaseReference mDatabase, mDatabase2, tempDatabase;
    public static final int PICK_IMAGE = 1;
    private Uri uri;
    private ArrayList<String>photos;
    private boolean multiple=false;
    private Intent dataMultiple;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        mAuth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("rooms");
        mDatabase2=FirebaseDatabase.getInstance().getReference().child("hotel").child("rooms");
        storageRef =  FirebaseStorage.getInstance().getReference();

        addRoomBtn = (Button) findViewById(R.id.addRoomBtn);
        addRoomImg = (Button) findViewById(R.id.addRoomImg);
        roomtype = (EditText) findViewById(R.id.roomtype);
        roomno = (EditText) findViewById(R.id.roomno);
        roomfloor = (EditText) findViewById(R.id.roomfloor);
        roomavailability = (EditText) findViewById(R.id.roomavailability);
        roomcost = (EditText) findViewById(R.id.roomcost);
        roomfeatures= (EditText) findViewById(R.id.roomfeatures);
        addRoomBtn = (Button) findViewById(R.id.addRoomBtn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if(data.getClipData()!=null)
            {
                multiple=true;
                dataMultiple=data;
                int totalPics = data.getClipData().getItemCount();
                for(int i=0;i<totalPics;i++)
                {
                    Uri fileuri=data.getClipData().getItemAt(i).getUri();
                    //String filename = getFileName(fileuri);
                }
            }
            else if (data.getData()!= null)
            {
                multiple=false;
                uri = data.getData();
                addRoomImg.setEnabled(false);
            }
        }
    }

    public void addroomBtn(View view) {
        roomtypestr = roomtype.getText().toString().trim();
        roomnostr = roomno.getText().toString().trim();
        roomfloorstr = roomfloor.getText().toString().trim();
        roomavailabilitystr = roomavailability.getText().toString().trim();
        roomcoststr = roomcost.getText().toString().trim();
        roomfeaturesstr = roomfeatures.getText().toString().trim();

        if(!TextUtils.isEmpty(roomavailabilitystr) || !TextUtils.isEmpty(roomnostr) || !TextUtils.isEmpty(roomfloorstr))
        {
            Room roomobj=new Room("Null", roomfeaturesstr, roomfloorstr, roomcoststr, roomnostr, roomavailabilitystr, roomtypestr, "yes" ,null);
            addroom(roomobj);
        }
    }

    private void addroom(final Room roomobj) {
        photos=new ArrayList<String>();
        final String roomkey=mDatabase.push().getKey();
        tempDatabase=mDatabase.child(roomkey).child("photos");
        mDatabase.child(roomkey).setValue(roomobj).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    addRoomBtn.setEnabled(false);
                    //Toast.makeText(getApplicationContext(),"room added",Toast.LENGTH_SHORT).show();
                    mDatabase2.push().setValue(roomkey);
                    if(multiple)
                    {
                        int totalPics = dataMultiple.getClipData().getItemCount();
                        Toast.makeText(getApplicationContext(),"Uploading : " + totalPics ,Toast.LENGTH_SHORT).show();
                        for(int i=0;i<totalPics;i++)
                        {
                            Uri uri=dataMultiple.getClipData().getItemAt(i).getUri();
                            storageRef2 = storageRef.child(roomkey).child(uri.getLastPathSegment());
//                          Toast.makeText(getApplicationContext(), storageRef2.getDownloadUrl().toString(),Toast.LENGTH_LONG).show();
                            storageRef2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //startActivity(new Intent(AddRoomActivity.this, AdminHome.class ));
                                    photos.add(taskSnapshot.getDownloadUrl().toString());
                                    tempDatabase.push().setValue(taskSnapshot.getDownloadUrl().toString());
                                    Toast.makeText(getApplicationContext(),taskSnapshot.getDownloadUrl().toString(),Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Upload Failed",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        multiple=false;
                    }
                    else
                    {
                        //Toast.makeText(getApplicationContext(),roomkey,Toast.LENGTH_SHORT).show();
                        storageRef2 = storageRef.child(roomkey).child(uri.getLastPathSegment());
                        photos.add(storageRef2.getDownloadUrl().toString());
                        storageRef2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(getApplicationContext(),"Upload Done",Toast.LENGTH_LONG);
                                //startActivity(new Intent(AddRoomActivity.this, AdminHome.class ));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Upload Failed",Toast.LENGTH_SHORT);
                            }
                        });
                    }

                }
                multiple=false;
            }
        });
    }

    public void addroomImg(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}