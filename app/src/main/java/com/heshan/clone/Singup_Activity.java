package com.heshan.clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Singup_Activity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private Button register;
    private TextView textView;

    private FirebaseAuth mAuth;
    DatabaseReference reference;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_);

        username = findViewById(R.id.username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.registerbtn);
        textView = findViewById(R.id.textview);
        mAuth = FirebaseAuth.getInstance();


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Singup_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textUsername = username.getText().toString();

                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                if (TextUtils.isEmpty(textUsername)  || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(Singup_Activity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(Singup_Activity.this, "Password too Short", Toast.LENGTH_SHORT).show();
                } else {
                    pd = new ProgressDialog(Singup_Activity.this);
                    pd.setMessage("Registering User");
                    pd.setCancelable(false);
                    pd.show();
                    registeruser(textUsername, textEmail, textPassword);
                }
            }


        });


    }

    private void registeruser(final String Username, final String Email, final String Password) {

        mAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String Uid = mAuth.getCurrentUser().getUid();

                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);

                HashMap<String,Object> map = new HashMap<>();
                map.put("Username",Username);
                map.put("Email",Email);
                map.put("Password",Password);
                map.put("UserId",Uid);
                map.put("bio","");
                map.put("ImageUrl","default");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                myRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(map);
                Toast.makeText(Singup_Activity.this, "Registration Sucessfull", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                finish();
                startActivity( new Intent(Singup_Activity.this,Login_Activity.class));

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Singup_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}