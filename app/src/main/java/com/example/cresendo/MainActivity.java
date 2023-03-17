package com.example.cresendo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    EditText userETLogin, passETlogin;
    Button RegisterBtn;
    ImageButton loginbtn;

    // Firebase:
    FirebaseAuth auth;
    FirebaseUser firebaseUser;


    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        // Checking for users existance: Saving the current user
        if (firebaseUser !=null){
            FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference("MyUsers").child(firebaseUser.getUid()).child("role");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String a=String.valueOf(snapshot.getValue());
                    int A = Integer.parseInt(a);
                    if(A==0) {
                        Intent i = new Intent(MainActivity.this, HomePage.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(MainActivity.this, AdminPage.class);
                        startActivity(i);
                        finish();
                    }                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userETLogin = findViewById(R.id.edittext);
        passETlogin = findViewById(R.id.edittext2);
        loginbtn    = findViewById(R.id.loginbtn);
        RegisterBtn = findViewById(R.id.button);

        // Firebase Auth:
        auth = FirebaseAuth.getInstance();



        // Register Button:
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });



        // Login Button:
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = userETLogin.getText().toString();
                String pass_text  = passETlogin.getText().toString();


                // Checking if it is empty:
                if (TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)){
                    Toast.makeText(MainActivity.this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    auth.signInWithEmailAndPassword(email_text, pass_text)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = firebaseDatabase.getReference("MyUsers")
                                                .child(task.getResult().getUser().getUid())
                                                .child("role");
                                        myRef.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                String a=String.valueOf(snapshot.getValue());
                                                int A = Integer.parseInt(a);
                                                if(A==0) {
                                                    Intent i = new Intent(MainActivity.this, HomePage.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(i);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Intent i = new Intent(MainActivity.this, AdminPage.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });
    }
}