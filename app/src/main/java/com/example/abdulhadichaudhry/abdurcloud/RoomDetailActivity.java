package com.example.abdulhadichaudhry.abdurcloud;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RoomDetailActivity extends AppCompatActivity {
    DatabaseReference ref;
    String m_Text;
    static String nodeKey;
    private Switch availableSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        availableSwitch=findViewById(R.id.setRoomAvailability);
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("nodekey"),Toast.LENGTH_SHORT).show();
        ref= FirebaseDatabase.getInstance().getReference().child("rooms").child(getIntent().getStringExtra("nodekey"));
        nodeKey = getIntent().getStringExtra("nodekey");


        availableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Toast.makeText(getApplicationContext(),"Available",Toast.LENGTH_SHORT).show();
                    ref.child("available").setValue("yes");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Unavailable",Toast.LENGTH_SHORT).show();
                    ref.child("available").setValue("no");
                }
            }
        });
    }

    public void delRoom(View view) {
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Room deleted",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    public void setRoomPrice(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Room Cost");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                ref.child("cost").setValue(m_Text).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Value Updated, Room Cost : " + m_Text , Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void setRoomPics(View view) {
        Intent intent = new Intent(RoomDetailActivity.this, UploadRoomImagesActivity.class);
        intent.putExtra("nodekey", nodeKey);
        startActivity(intent);
    }

    public void setRoomFeatures(View view) {



    }

}