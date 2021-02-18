package com.heshan.clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView animateimage;
    Button btnregister;
    Button btnlogin;
    LinearLayout linearLayout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregister =findViewById(R.id.registerbtn);
        btnlogin =findViewById(R.id.loginbtn);
        linearLayout = findViewById(R.id.linear_layout);
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null ){
            Intent intent = new Intent(MainActivity.this,Start_activity.class);
            startActivity(intent);
            finish();
        }




        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Singup_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}