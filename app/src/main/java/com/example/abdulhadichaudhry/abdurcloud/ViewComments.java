package com.example.abdulhadichaudhry.abdurcloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewComments extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    public FirebaseRecyclerAdapter<Review, Show_Comment_ViewHolder_Client> mFirebaseAdapter;
    ProgressBar progressBar;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        firebaseDatabase = FirebaseDatabase.getInstance();

        myRef = firebaseDatabase.getReference().child("reviews");
        //myRef.keepSynced(true);

        //Recycler View
        recyclerView = (RecyclerView)findViewById(R.id.recyclercomment);

        mLinearLayoutManager = new LinearLayoutManager(ViewComments.this);
        //mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Review, Show_Comment_ViewHolder_Client>(Review.class, R.layout.commentitem, Show_Comment_ViewHolder_Client.class, myRef) {
            public void populateViewHolder(final Show_Comment_ViewHolder_Client viewHolder, Review model, final int position) {
                if (!model.getUsername().equals("Null")) {
                    viewHolder.setReviewer(model.getUsername());
                    viewHolder.setRating(model.getReview());
                    viewHolder.setReview(model.getReview());
                }
/*                //OnClick Item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(v.getContext(),"clicked",Toast.LENGTH_SHORT).show();

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
                });*/
            }
        };
        recyclerView.setAdapter(mFirebaseAdapter);
    }

    //View Holder For Recycler View
    public static class Show_Comment_ViewHolder_Client extends RecyclerView.ViewHolder {
        private final TextView reviewer, rating, review;
        private final LinearLayout layout;
        final LinearLayout.LayoutParams params;

        public Show_Comment_ViewHolder_Client(final View itemView) {
            super(itemView);
            reviewer = (TextView) itemView.findViewById(R.id.reviewer);
            rating = (TextView) itemView.findViewById(R.id.rating);
            review = (TextView) itemView.findViewById(R.id.reviewComment);

            layout = (LinearLayout)itemView.findViewById(R.id.singlecommentitemclient);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        private void setReviewer(String title) {
            reviewer.setText(title);
        }
        private void setRating(String title) {
            rating.setText(title + " / 5");
        }
        private void setReview(String title) {
            review.setText(title);
        }

    }

}
