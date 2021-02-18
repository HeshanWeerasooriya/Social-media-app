package com.heshan.clone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class PostActivity extends AppCompatActivity {

    private Uri imageuri;
    private String imageUrl;
    ImageView close;
    ImageView imageadded;
    TextView post, name;
    AutoCompleteTextView description;
    EditText title, cotactNo, location;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        imageadded = findViewById(R.id.imageadded);
        post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        name = findViewById(R.id.name);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });

        CropImage.activity().start(PostActivity.this);
    }

    private void upload() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.setCancelable(false);
        pd.show();
        if (imageuri!=null){
            final StorageReference filepath = FirebaseStorage.getInstance().getReference("Posts").child(System.currentTimeMillis()+"."+ getExtension(imageuri));
            StorageTask uploadtask  = filepath.putFile(imageuri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Uri  downloadUri = task.getResult();
                            imageUrl = downloadUri.toString();
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                          //  FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                           // String PostId= ref.push().getKey();

                            String PostId= ref.child("posts").push().getKey();
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("PostId",PostId);
                            map.put("ImageUrl",imageUrl);
                            map.put("Description",description.getText().toString());
//                            map.put("Title", title.getText().toString());
//                            map.put("ContactNo", cotactNo.getText().toString());
//                            map.put("Location", location.getText().toString());
                            map.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                           // map.put("Name",name.getText().toString());
                           // map.put("Name", firebaseUser.getCurrentUser().getName());
                            ref.child(PostId).setValue(map);

                            pd.dismiss();
                            Toast.makeText(PostActivity.this, "Upload Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(PostActivity.this,Start_activity.class));
                            finish();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                    Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            pd.dismiss();
            Toast.makeText(this, "No Image Was Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
            imageuri = activityResult.getUri();
            imageadded.setImageURI(imageuri);
        }else{
            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,Start_activity.class));
            finish();
        }
    }
}