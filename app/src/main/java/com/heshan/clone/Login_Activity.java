package com.heshan.clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Login_Activity extends AppCompatActivity {

    TextView signup;
    EditText emailname;
    private  EditText password;
    Button login;
    FirebaseAuth mauth;
    ProgressDialog pd;
    TextView forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        signup = findViewById(R.id.textview);
        emailname = findViewById(R.id.Emaillogin);
        password = findViewById(R.id.Passwordlogin);
        login = findViewById(R.id.btnLogin);
        forgetpassword = findViewById(R.id.forgetpasswoed);
        mauth = FirebaseAuth.getInstance();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Login_Activity.this,Singup_Activity.class));
                finish();

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailname.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(Login_Activity.this, "All fields are required!", Toast.LENGTH_LONG).show();
                }
                else{
                    pd = new ProgressDialog(Login_Activity.this);
                    pd.setMessage("Please wait...");
                    pd.setCancelable(false);
                    pd.show();
                    signup(email,pass);
                }
            }
        });
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MaterialEditText resetmail = new MaterialEditText(view.getContext());
                final AlertDialog ad =  new AlertDialog.Builder(view.getContext()).create();
                ad.setTitle("Reset Password");
                ad.setMessage("Enter Your Email to recieve Reset link");
                ad.setView(resetmail);
                ad.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ad.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetmail.getText().toString();
                        mauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                ad.dismiss();
                                Toast.makeText(Login_Activity.this, "Reset Link sent to your mail", Toast.LENGTH_SHORT).show();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        ad.dismiss();
                                        Toast.makeText(Login_Activity.this, "Error!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
                ad.setCanceledOnTouchOutside(false);

                ad.show();
            }
        });



    }

    private void signup(String email, String pass) {
        mauth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login_Activity.this, "Login Sucessful", Toast.LENGTH_LONG).show();
                            pd.dismiss();
                            finish();
                            Intent intent = new Intent(Login_Activity.this,Start_activity.class);
                            startActivity(intent);



                        } else {
                            // If sign in fails, display a message to the user.
                            pd.dismiss();

                            Toast.makeText(getApplicationContext(), "Login failed!",
                                    Toast.LENGTH_LONG).show();


                        }

                        // ...
                    }
                });
    }
}