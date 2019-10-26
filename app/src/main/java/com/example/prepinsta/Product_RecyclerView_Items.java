package com.example.prepinsta;

public class Product_RecyclerView_Items {

    private String mItem;
    private int mQuantity;
    private double mPrice;


    public Product_RecyclerView_Items(String item, double price, int quantity) {
        mItem = item;
        mQuantity = quantity;
        mPrice = price;
    }


    public String getItemName() {
        return mItem;
    }

    public String getPriceProduct() {
        return ""+mPrice;
    }

    public Integer getQuantity() {
        return mQuantity;
    }

    public void add() {
        mQuantity++;
    }

    public void remove(){
        if(mQuantity>0)
        mQuantity--;
    };

}
