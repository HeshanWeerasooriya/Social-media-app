package com.heshan.clone.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heshan.clone.R;
import com.heshan.clone.model.Post;
import com.heshan.clone.model.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerviewpost;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<String> followinglist;
    private ImageView camera;
    private  ImageView gotomessage;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerviewpost = view.findViewById(R.id.recyclerview_posts);


        recyclerviewpost.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerviewpost.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postList);

        recyclerviewpost.setItemViewCacheSize(20);
        recyclerviewpost.setDrawingCacheEnabled(true);
        recyclerviewpost.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerviewpost.setNestedScrollingEnabled(false);

        recyclerviewpost.setAdapter(postAdapter);
        readUploads();

        return view;
    }



    private void readUploads() {
        FirebaseDatabase.getInstance().getReference().child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                    Post post = npsnapshot.getValue(Post.class);

                            postList.add(post);

                    }



                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}