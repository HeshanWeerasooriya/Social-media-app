package com.heshan.clone.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heshan.clone.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mcontext;
    private List<Post> mPosts;
    private  FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mcontext = mContext;
        this.mPosts = mPost;
       // firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.activity_post_item,parent,false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Post post = mPosts.get(position);
       Picasso.get().load(post.getImageUrl()).into(holder.postimage);
//       holder.title.setText(post.getTitle());
       holder.description.setText(post.getDescription());
 //      holder.contactNo.setText(post.getContactNo());
 //      holder.location.setText(post.getLocation());


//        Picasso.get()
//                .load(post.getImageUrl())
//                .fit()
//                .into(holder.postimage);




        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(post.getPublisher()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);



//                if(user.getImageUrl().equals("default")){
//                    holder.imageprofile.setImageResource(R.drawable.profile);
//                }else{
//                    Picasso.get().load(user.getImageUrl()).into(holder.imageprofile);}


                Picasso.get().load(user != null ? user.getImageUrl() : null).into(holder.imageprofile);
                holder.username.setText(user != null ? user.getUsername() : null);
              //  holder.author.setText(user.getName());





//                holder.username.setText(user.getUsername());
//                holder.author.setText(user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
        public int getItemCount () {
            return mPosts.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageprofile;
            public ImageView postimage;
            public TextView username;
            public TextView title, description, contactNo, location;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageprofile = itemView.findViewById(R.id.profile_image);
                postimage = itemView.findViewById(R.id.post_image);
                username = itemView.findViewById(R.id.username);
                description =itemView.findViewById(R.id.description);
              //  title = itemView.findViewById(R.id.title);
               // contactNo = itemView.findViewById(R.id.contactNo);
               // location = itemView.findViewById(R.id.location);




            }
        }
    }


