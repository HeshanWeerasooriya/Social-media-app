package com.heshan.clone.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import com.heshan.clone.EditProfile;
import com.heshan.clone.MainActivity;
import com.heshan.clone.R;
import com.heshan.clone.Singup_Activity;
import com.heshan.clone.Start_activity;
import com.heshan.clone.model.PhotoAdapter;
import com.heshan.clone.model.Post;
import com.heshan.clone.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment implements PhotoAdapter.OnPhotoListener {

    private static final String TAG = "place";
    private Button signOutButton;
    private Button editprofile;
    private TextView username;
    RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    List<Post> mypostlist;
    private CircleImageView imageprofile;
    private ImageView photo;



    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private StorageReference storageReference;
    private String profileId;
    DatabaseReference databaseReference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String data = getContext().getSharedPreferences("Profile", MODE_PRIVATE).getString("ProfileId","");
        if (data.equals("")){
            profileId = firebaseUser.getUid();
        }else{
            profileId = data;
        }

        signOutButton = view.findViewById(R.id.signOut_btn);
        imageprofile = view.findViewById(R.id.profile_image);
        username = view.findViewById(R.id.username);
        editprofile = view.findViewById(R.id.edit_profile);

        recyclerView = view.findViewById(R.id.recyclerview_pictures);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        mypostlist = new ArrayList<>();
        photoAdapter = new PhotoAdapter(getContext(),mypostlist, this);
        recyclerView.setAdapter(photoAdapter);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        myPhotos();
        userinfo();


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileId.equals(firebaseUser.getUid())){
                     startActivity(new Intent(getContext(), EditProfile.class));

                }else{

                }
            }
        });



        return view;
    }

    private void signOut() {
        if(user != null && firebaseAuth != null) {
            firebaseAuth.signOut();

            startActivity(new Intent(getActivity(),
                    MainActivity.class));
        }

    }

    private void myPhotos() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mypostlist.clear();
                for (DataSnapshot np : snapshot.getChildren()) {
                    Post post = np.getValue(Post.class);
                    assert post != null;
                    if (post.getPublisher().equals(profileId)) {
                        mypostlist.add(post);
                    }
                }
                Collections.reverse(mypostlist);
                photoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//        private void userinfo() {
//            FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    User user = snapshot.getValue(User.class);
//
////                    if(user.getImageUrl().equals("default")) {
////                        imageprofile.setImageResource(R.drawable.profile);
////                    }
//////                    }else{
//////                        Picasso.get().load(user != null ? user.getImageUrl() : null).into(imageprofile);}
//
//                        username.setText(user != null ? user.getUsername() : null);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }


    private void userinfo() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
//                if (user.getImageUrl().equals("default")) {
//                    imageprofile.setImageResource(R.drawable.profile);
//                } else {
//                  //  Picasso.get().load(user.getImageUrl()).into(imageprofile);
//
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onPhotoClick(int position) {
        //Toast.makeText(getActivity(),"on", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onPotoClick: clicked." + position);

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
//                .getReference("Posts").child("PostId");
//        databaseReference.removeValue();

//        startActivity(new Intent(getActivity(),
//                DeletePost.class));









    }
}



