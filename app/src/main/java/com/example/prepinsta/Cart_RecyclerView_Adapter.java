package com.example.prepinsta;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Cart_RecyclerView_Adapter extends RecyclerView.Adapter<Cart_RecyclerView_Adapter.ExampleViewHolder> {
    ArrayList<Cart_RecyclerView_Items> mList;
    OnItemClickListener mListener;
    RequestQueue requestQueue;
    public interface OnItemClickListener {
        void onPresentClick(int position);

        void onAbsentClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView mCartItem,mPrice,mCartQuantity;
        Button mCartRemove;
        WebView web;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mCartItem = (TextView) itemView.findViewById(R.id.cart_item);
            mPrice = (TextView) itemView.findViewById(R.id.cart_item_price);
            mCartQuantity = (TextView) itemView.findViewById(R.id.cart_quantity);
            mCartRemove=(Button) itemView.findViewById(R.id.cart_remove);
            web= (WebView) itemView.findViewById(R.id.web);
        }
    }

    public Cart_RecyclerView_Adapter(ArrayList<Cart_RecyclerView_Items> exampleList) {
        mList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ExampleViewHolder holder, final int position) {
        final Cart_RecyclerView_Items currentitem = mList.get(position);
        holder.mCartItem.setText(currentitem.getitem());
        holder.mPrice.setText(currentitem.getPrice().toString());
        holder.mCartQuantity.setText(currentitem.getQuantity().toString());
        holder.mCartRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = currentitem.getitem();
                int pos=5;
                if(s.equals("Apple")){
                    pos=0;
                }else if(s.equals("Banana")){
                    pos=1;
                }else if(s.equals("rice")){
                    pos=2;
                }else if(s.equals("wheat")){
                    pos=3;
                }else if(s.equals("apple")){
                    pos=4;
                }
                holder.web.getSettings().setJavaScriptEnabled(true);
                holder.web.loadUrl("http://192.168.137.246:8080/deats/cart/remove?cartId="+Home_page.cartId+"&productIndex="+pos+"&market="+currentitem.getMarket()+"&grocer="+currentitem.getGrocer() +"&quantity="+currentitem.getQuantity());
                Cart.total-=currentitem.getPrice();
                Cart.total_cal();
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
