package com.example.prepinsta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Products extends AppCompatActivity {
    ArrayList<Product_RecyclerView_Items> exampleList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Product_RecyclerView_Adapter mAdapter;
    RequestQueue requestQueue;
    public static String grocery;
    Button add_to_cart,view_cart;

    public void initialize_things() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        add_to_cart = (Button) findViewById(R.id.add_to_cart);
        view_cart = (Button) findViewById(R.id.view_cart);
        grocery = getIntent().getStringExtra("grocery_item_clicked");
        buildRecyclerView();
        get_JSON_data();
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_items_to_cart();
                Intent in = new Intent(Products.this,Cart.class);
                in.putExtra("products_out","add");
                startActivity(in);
            }
        });
        view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Products.this,Cart.class);
                in.putExtra("products_out","view");
                startActivity(in);
            }
        });
    }

    public void update_add(int position) {
        exampleList.get(position).add();
        mAdapter.notifyDataSetChanged();
    }

    public void update_remove(int position) {
        exampleList.get(position).remove();
        mAdapter.notifyDataSetChanged();
    }

    private void get_JSON_data() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.137.246:8080/deats/products?groceryName="+grocery+"&marketName="+Grocery.market, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ans");
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                String name = jsonArray.getJSONObject(i).getString("name");
                                String price = jsonArray.getJSONObject(i).getString("price");
                                exampleList.add(new Product_RecyclerView_Items(name, Double.parseDouble(price), 0));
                                mAdapter.notifyDataSetChanged();
                            }

                            Toast.makeText(Products.this, "It just happened", Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Products.this, "Error Occured", Toast.LENGTH_SHORT).show();

                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void add_items_to_cart(){
        for(int i=0;i<exampleList.size();i++){
            if(!exampleList.get(i).getQuantity().toString().equals("0")){
                String data_itemId = Integer.valueOf(i).toString();
                String data_quantity = exampleList.get(i).getQuantity().toString();
                String data_market=Grocery.market;
                String data_grocers=grocery;
                requestQueue = Volley.newRequestQueue(this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.137.246:8080/deats/cart/add?cartId="+Home_page.cartId+"&productIndex="+data_itemId+"&market="+data_market+"&grocer="+data_grocers+"&quantity="+data_quantity, null,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Products.this, "Error Occured", Toast.LENGTH_SHORT).show();

                            }
                        });
                requestQueue.add(jsonObjectRequest);
            }
        }
    }


    public void buildRecyclerView() {
        exampleList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Product_RecyclerView_Adapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Product_RecyclerView_Adapter.OnItemClickListener() {

            @Override
            public void onPresentClick(int position) {
                update_add(position);
            }

            @Override
            public void onAbsentClick(int position) {
                update_remove(position);
            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
        initialize_things();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cart.total=0.0;
        finish();
        startActivity(getIntent());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
