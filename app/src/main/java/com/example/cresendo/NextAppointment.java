package com.example.cresendo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Random;

public class NextAppointment extends AppCompatActivity {

    FirebaseUser fuser;
    String temp,temp1,temp2,temp3,temp4;
    int count;
    EditText t1,t2,t3,t4;
    Button btn;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_appointment);
        t1= findViewById(R.id.edittext1);
        t2= findViewById(R.id.edittext2);
        t3= findViewById(R.id.edittext3);
        t4= findViewById(R.id.edittext4);
        btn=findViewById(R.id.loginbtn);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical");
        DatabaseReference myRef1 = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical").child("disease");
        DatabaseReference myRef2 = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical").child("date");
        DatabaseReference myRef3 = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical").child("info");
        DatabaseReference myRef4 = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical").child("des");
        DatabaseReference myRef5 = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical").child("token");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        temp=String.valueOf(snapshot.child("count").getValue());
                        count=Integer.parseInt(temp);
                        if(flag==1)
                        {
                            flag=0;
                            temp1=t1.getText().toString();
                            temp2=t2.getText().toString();
                            temp3=t3.getText().toString();
                            temp4=t4.getText().toString();
                            HashMap<String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put(temp,temp1);
                            myRef1.updateChildren(hashMap1);
                            HashMap<String, Object> hashMap2 = new HashMap<>();
                            hashMap2.put(temp,temp2);
                            myRef2.updateChildren(hashMap2);
                            HashMap<String, Object> hashMap3 = new HashMap<>();
                            hashMap3.put(temp,temp3);
                            myRef3.updateChildren(hashMap3);
                            HashMap<String, Object> hashMap4 = new HashMap<>();
                            hashMap4.put(temp,temp4);
                            myRef4.updateChildren(hashMap4);
                            Random rand = new Random();
                            int randomNum = rand.nextInt(30) + 1;
                            HashMap<String,Object>hashMap5 = new HashMap<>();
                            hashMap5.put(temp,randomNum);
                            myRef5.updateChildren(hashMap5);
                            count++;
                            temp=String.valueOf(count);
                            databaseReference.child("count").setValue(temp);
                            openAct();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            private void openAct() {
                Intent i = new Intent(NextAppointment.this,HomePage.class);
                startActivity(i);
            }
        });
    }
}