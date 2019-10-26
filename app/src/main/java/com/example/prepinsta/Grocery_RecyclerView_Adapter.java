package com.example.prepinsta;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Grocery_RecyclerView_Adapter extends RecyclerView.Adapter<Grocery_RecyclerView_Adapter.ExampleViewHolder> {
    ArrayList<Grocery_RecyclerView_Items> mList;
    OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onPresentClick(int position);

        void onAbsentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView mGrocery;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mGrocery = (TextView) itemView.findViewById(R.id.mgrocery);
        }
    }

    public Grocery_RecyclerView_Adapter(ArrayList<Grocery_RecyclerView_Items> exampleList) {
        mList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        Grocery_RecyclerView_Items currentitem = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent in = new Intent(v.getContext(), Products.class);
                in.putExtra("grocery_item_clicked",mList.get(position).getGrocery());
                v.getContext().startActivity(in);
            }
        });
        holder.mGrocery.setText(currentitem.getGrocery());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
