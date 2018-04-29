package com.example.abdulhadichaudhry.abdurcloud;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientViewRoomsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<Room, Show_Chat_ViewHolder_Client> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_rooms);

        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference().child("rooms");
        //myRef.keepSynced(true);

        //Recycler View
        recyclerView = (RecyclerView)findViewById(R.id.recyclelist);

        mLinearLayoutManager = new LinearLayoutManager(ClientViewRoomsActivity.this);
        //mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Room, Show_Chat_ViewHolder_Client>(Room.class, R.layout.single_room_item_client, Show_Chat_ViewHolder_Client.class, myRef) {
            public void populateViewHolder(final Show_Chat_ViewHolder_Client viewHolder, Room model, final int position) {
                if (!model.getRoomtype().equals("Null")) {
                    viewHolder.roomstatus(model.getRoomstatus());
                    viewHolder.roomtype(model.getRoomtype());
                    String addressstr= new String( "Floor no. " + model.getFloor() + " Room no. " + model.getRoomno());
                    viewHolder.roomaddress(addressstr);
                    viewHolder.roomcost(model.getCost());
                    viewHolder.roomavailable(model.getAvailable());
                }
                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        DatabaseReference ref = mFirebaseAdapter.getRef(position);
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String key = dataSnapshot.getKey();
                                Intent intent = new Intent(getApplicationContext(), RoomDetailClient.class);
                                intent.putExtra("nodekey", key);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    //View Holder For Recycler View
    public static class Show_Chat_ViewHolder_Client extends RecyclerView.ViewHolder {
        private final TextView vroomstatus, vaddress, vroomcost, roomtypetitle, roomavailability;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Chat_ViewHolder_Client(final View itemView) {
            super(itemView);
            vroomstatus = (TextView) itemView.findViewById(R.id.vroomstatus);
            vroomcost = (TextView) itemView.findViewById(R.id.vroomcost);
            vaddress = (TextView) itemView.findViewById(R.id.vaddress);
            roomtypetitle = (TextView) itemView.findViewById(R.id.roomtypetitle);
            roomavailability =(TextView)itemView.findViewById(R.id.roomavailabletitle);
            layout = (LinearLayout)itemView.findViewById(R.id.singleroomitemclient);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        private void roomstatus(String title) {
            // Log.d("LOGGED", "Setting Name: ");
            vroomstatus.setText(title);
        }
        private void roomaddress(String title) {
            vaddress.setText(title);
        }
        private void roomcost(String title) {
            vroomcost.setText(title);
        }
        private void roomtype(String title) {
            roomtypetitle.setText(title);
        }
        private void Layout_hide() {
            params.height = 0;
            //itemView.setLayoutParams(params);
            layout.setLayoutParams(params);
        }
        private void roomavailable(String title){ roomavailability.setText(title);  }
    }
}