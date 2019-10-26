package com.example.prepinsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    ArrayList<Cart_RecyclerView_Items> exampleList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Cart_RecyclerView_Adapter mAdapter;
    RequestQueue requestQueue;
    public static TextView total_text;
    Button cart_order;
    public static Double total=0.0;
    public static void total_cal(){
        total_text.setText(total.toString());
    }

    public void initialize_things() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        total_text = (TextView) findViewById(R.id.total_amount);
        cart_order=(Button) findViewById(R.id.order);
        cart_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.137.246:8080/deats/cart/checkout?cartId="+Home_page.cartId, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Cart.this, "Order Placed", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Cart.this, "Error Occured", Toast.LENGTH_SHORT).show();

                            }
                        });
                requestQueue.add(jsonObjectRequest);
                Intent in = new Intent(Cart.this,Acknowledgement.class);
                startActivity(in);
            }
        });
        buildRecyclerView();
        get_JSON_data();

    }

    private void get_JSON_data() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.137.246:8080/deats/cart/view?cartId="+Home_page.cartId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("products");

                            for (int i = 0; i <= jsonArray.length(); i++) {
                                String name = jsonArray.getJSONObject(i).getString("name");
                                String price = jsonArray.getJSONObject(i).getString("price");
                                String quantity = jsonArray.getJSONObject(i).getString("quantity");
                                String market = jsonArray.getJSONObject(i).getString("market");
                                String grocer = jsonArray.getJSONObject(i).getString("grocer");
                                exampleList.add(new Cart_RecyclerView_Items(name, Double.parseDouble(price),Integer.parseInt(quantity),market,grocer));
                                total+=Double.parseDouble(price);
                                total_text.setText(total.toString());
                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cart.this, "Error Occured", Toast.LENGTH_SHORT).show();

                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    public void buildRecyclerView() {
        exampleList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.product_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Cart_RecyclerView_Adapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Cart_RecyclerView_Adapter.OnItemClickListener() {

            @Override
            public void onPresentClick(int position) {

            }

            @Override
            public void onAbsentClick(int position) {

            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
