package com.heshan.clone.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.heshan.clone.Login_Activity;
import com.heshan.clone.R;
import com.heshan.clone.Singup_Activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private Context mcontext;
    private List<Post> mpost;
    private OnPhotoListener mPhotoListener;

    public PhotoAdapter(Context mcontext, List<Post> mpost, OnPhotoListener onPhotoListener) {
        this.mcontext = mcontext;
        this.mpost = mpost;
        this.mPhotoListener = onPhotoListener;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_photo_item,parent,false);
        return new PhotoAdapter.ViewHolder(view, mPhotoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = mpost.get(position);
        Picasso.get().load(post.getImageUrl()).into(holder.postimage);

        holder.postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mcontext.getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("postid", post.getPostId());
                editor.apply();

//                ((Activity)mcontext).AppTask().beginTransaction().replace(R.id.fragment_container,
//                        new DeletePost()).commit();

               // mcontext.startActivity(new Intent(String.valueOf(DeletePost.class)));

               // startActivity( new Intent(Singup_Activity.this,Login_Activity.class));


            }
        });
    }





//    @Override
//    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
//        final Post post = mpost.get(position);
//
//        Picasso.get().load(post.getImageUrl()).placeholder(R.drawable.loading).into(holder.postimage);
//        holder.postimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mcontext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit().putString("postid",post.getPostId()).apply();
////                ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PostDetailsFragment())
////                        .commit();
//
//
//            }
//
//
//        });
//
//    }

    @Override
    public int getItemCount() {
        return mpost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView postimage;
        OnPhotoListener onPhotoListener;

        public ViewHolder(@NonNull View itemView, OnPhotoListener onPhotoListener) {
            super(itemView);
            postimage = itemView.findViewById(R.id.post_image);
            this.onPhotoListener = onPhotoListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPhotoListener.onPhotoClick(getAdapterPosition());
        }
    }

    public interface OnPhotoListener {
        void onPhotoClick(int position);
    }
}
