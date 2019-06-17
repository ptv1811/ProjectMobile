package com.example.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText c_password;
    Button signup;
    FirebaseAuth mFirebaseAuth;

    TextView clicksignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        signup=findViewById(R.id.signup);
        password=findViewById(R.id.password_su);
        c_password=findViewById(R.id.c_password);

        clicksignin=findViewById(R.id.clicksignin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email= email.getText().toString();
                String Password= password.getText().toString();
                String CPassword= c_password.getText().toString();
                if (Email.isEmpty()){
                    email.setError("Email can not be empty!");
                    email.requestFocus();
                }
                else if (Password.isEmpty()){
                    password.setError("Password can not be empty!");
                    password.requestFocus();
                }
                else if (CPassword.isEmpty()){
                    c_password.setError("Please confirm password");
                    c_password.requestFocus();
                }
                else if (!Password.equals(CPassword)){
                    c_password.setError("Confirm password does not match!");
                    c_password.requestFocus();
                }
                else if(!(Email.isEmpty()&& Password.isEmpty()) && Password.equals(CPassword) )
                    {
                    mFirebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(Signup.this,"Failed to sign up, please try again",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(Signup.this,"Sign up successful",Toast.LENGTH_SHORT).show();
                                        //put add note activity intent here

                                        Intent i= new Intent(Signup.this,ListFood.class);

                                        String userid= task.getResult().getUser().getUid();
                                        String nemail= email.getText().toString();
                                        String npassword= password.getText().toString();

                                        i.putExtra("nusername",nemail);
                                        i.putExtra("password",npassword);
                                        i.putExtra("Uid",userid);

                                        startActivity(i);
                                    }
                                }
                            });
                }

            }
        });

        clicksignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Signup.this, LoginActitvity.class);
                startActivity(intent);
            }
        });
    }

}
