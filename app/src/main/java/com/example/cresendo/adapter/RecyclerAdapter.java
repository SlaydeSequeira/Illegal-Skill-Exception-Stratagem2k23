package com.example.cresendo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cresendo.Information;
import com.example.cresendo.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    int count;
    String[] data2;
    String[] data;
    Context context;
    String[] picture;

    public RecyclerAdapter(Context context, String[] data, int count, String[] picture, String[] data2)
    {
        this.data = data;
        this.context= context;
        this.count= count;
        this.data2 = data2;
        this.picture=picture;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_add,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
    int position=pos;
    //its in reverse order so the latest post comes on top
    //for undo reverse do(int position =pos;)
    holder.textView.setText(data[position]);
    holder.textView2.setText("Description: "+data2[position]);
        Glide.with(context)
                .load(picture[position])
                .placeholder(R.drawable.gradient)
                .error(R.drawable.gradient)
                .into(holder.imageView);
   holder.itemView.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Toast.makeText(context, "You clicked "+data[position], Toast.LENGTH_SHORT).show();
           Intent intent= new Intent(view.getContext(), Information.class);
           String tempA=data[position], tempB=data2[position],tempC=picture[position];
           intent.putExtra("E",position);
           view.getContext().startActivity(intent);
       }
   });

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            imageView= itemView.findViewById(R.id.image);
        }
    }
}
