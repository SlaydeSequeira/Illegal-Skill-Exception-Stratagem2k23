package com.example.cresendo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Information extends AppCompatActivity {
    FirebaseUser fuser;
    int count;
    String temp,t;
    String[] Titles = new String[100];
    String[] Description = new String[100];
    String[] Date = new String[100];

    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        count= getIntent().getIntExtra("E",0);
        t1= findViewById(R.id.text1);
        t2= findViewById(R.id.text2);
        t3= findViewById(R.id.text3);
        t4= findViewById(R.id.text);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    temp = String.valueOf(count);
                    Titles[count] = String.valueOf(snapshot.child("disease").child(temp).getValue());
                    Description[count]= String.valueOf(snapshot.child("info").child(temp).getValue());
                    Date[count]= String.valueOf(snapshot.child("date").child(temp).getValue());
                    t = String.valueOf(snapshot.child("token").child(temp).getValue());
                    t1.setText(Titles[count]);
                    t2.setText(Description[count]);
                    t3.setText(Date[count]);
                    t4.setText(t);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}