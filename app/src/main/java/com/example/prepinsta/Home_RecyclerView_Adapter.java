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

public class Home_RecyclerView_Adapter extends RecyclerView.Adapter<Home_RecyclerView_Adapter.ExampleViewHolder> {
    ArrayList<Home_RecyclerView_Items> mExampleList;
    OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onPresentClick(int position);

        void onAbsentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView mMarket;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mMarket = (TextView) itemView.findViewById(R.id.msubject);
        }
    }

    public Home_RecyclerView_Adapter(ArrayList<Home_RecyclerView_Items> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, final int position) {
        Home_RecyclerView_Items currentitem = mExampleList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent in = new Intent(v.getContext(), Grocery.class);
                    in.putExtra("home_item_clicked",mExampleList.get(position).getSubject());
                    v.getContext().startActivity(in);
            }
        });
        holder.mMarket.setText(currentitem.getSubject());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
