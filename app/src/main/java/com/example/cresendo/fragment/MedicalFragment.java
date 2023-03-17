package com.example.cresendo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cresendo.HomePage;
import com.example.cresendo.MainActivity;
import com.example.cresendo.NextAppointment;
import com.example.cresendo.R;
import com.example.cresendo.adapter.RecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicalFragment extends Fragment {
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    FirebaseUser fuser;
    String[] Titles = new String[100];
    String[] Description = new String[100];
    String[] Image = new String[100];
    String temp;
    int count;
    ImageButton button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_medical, container, false);
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        button = view.findViewById(R.id.button);
        ImageView i = view.findViewById(R.id.imageView4);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAct();
            }

        });
        recyclerView= view.findViewById(R.id.recycler);
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("MyUsers").child(fuser.getUid()).child("medical");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String c= snapshot.child("count").getValue().toString();
                count = Integer.parseInt(c);
                for (int i = 0; i < count; i++) {
                    temp = String.valueOf(i);
                    Titles[i] = String.valueOf(snapshot.child("disease").child(temp).getValue());
                    Description[i]= String.valueOf(snapshot.child("des").child(temp).getValue());
                }
                newView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void openAct() {
        Intent i = new Intent(getActivity(), NextAppointment.class);
        count= requireActivity().getIntent().getIntExtra("E",0);
        startActivity(i);
    }

    private void newView( ) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerAdapter(getActivity(), Titles, count, Image, Description);
        recyclerView.setAdapter(adapter);
    }
}