package com.example.abdulhadichaudhry.abdurcloud;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UploadListAdapter extends RecyclerView.Adapter<UploadListAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<String> mUploads;

    public UploadListAdapter(Context context, ArrayList<String> uploads)
    {
        mContext=context;
        mUploads=uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_single,parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String img=mUploads.get(position);
        Toast.makeText(mContext,"HEHE",Toast.LENGTH_SHORT).show();
        Picasso.with(mContext).load(img).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.roomImage);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView roomImage;
        public ImageViewHolder(View itemView) {
            super(itemView);
            roomImage = itemView.findViewById(R.id.roomImage);

        }
    }
}
