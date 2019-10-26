package com.example.prepinsta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Product_RecyclerView_Adapter extends RecyclerView.Adapter<Product_RecyclerView_Adapter.ExampleViewHolder> {
    ArrayList<Product_RecyclerView_Items> mExampleList;
    OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onPresentClick(int position);
        void onAbsentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        TextView mSubject,mAttended,mPrice;
        Button mPresent,mAbsent;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mSubject = (TextView)  itemView.findViewById(R.id.msubject);
            mPrice = (TextView) itemView.findViewById(R.id.mfinal_percent);
            mAttended = itemView.findViewById(R.id.attended_class);
            mPresent = itemView.findViewById(R.id.mark_present);
            mAbsent = itemView.findViewById(R.id.mark_absent);
            mPresent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onPresentClick(position);
                        }
                    }
                }
            });

            mAbsent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onAbsentClick(position);
                        }
                    }
                }
            });

        }
    }

    public Product_RecyclerView_Adapter(ArrayList<Product_RecyclerView_Items> exampleList){
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Product_RecyclerView_Items currentitem = mExampleList.get(position);
        holder.mSubject.setText(currentitem.getItemName());
        Double perc = Double.valueOf(currentitem.getPriceProduct());
        holder.mPrice.setText(perc.toString());
        holder.mAttended.setText(currentitem.getQuantity().toString());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
