package com.example.prepinsta;

import android.content.Intent;

public class Cart_RecyclerView_Items {

    private String mItem,mMarket,mGrocer;
    private double mPrice;
    private  int mQuantity;


    public Cart_RecyclerView_Items(String item, double price, int quantity, String market, String grocer) {
        mItem = item;
        mPrice = price;
        mQuantity = quantity;
        mMarket = market;
        mGrocer = grocer;
    }


    public String getitem() {
        return mItem;
    }

    public Double getPrice() {
        return mPrice;
    }

    public Integer getQuantity(){
        return mQuantity;
    }

    public String getMarket(){
        return mMarket;
    }
    public String getGrocer(){
        return mGrocer;
    }


}
