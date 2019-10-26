package com.example.prepinsta;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Grocery extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView text_in_dynamic;
    ArrayList<Grocery_RecyclerView_Items> exampleList;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Grocery_RecyclerView_Adapter mAdapter;
    RequestQueue requestQueue;
    public static String market;
    public void initialize_things() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        market = getIntent().getStringExtra("home_item_clicked");
        buildRecyclerView();
        get_JSON_data();

    }
    private void get_JSON_data() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://192.168.137.246:8080/deats/grocers?marketName="+market, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("ans");
                            for (int i = 0; i <= jsonArray.length(); i++) {
                                String firstname = jsonArray.get(i).toString();
                                exampleList.add(new Grocery_RecyclerView_Items(firstname));
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
                        Toast.makeText(Grocery.this, "Error Occured", Toast.LENGTH_SHORT).show();

                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    public void buildRecyclerView() {
        exampleList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.grocery_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Grocery_RecyclerView_Adapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new Grocery_RecyclerView_Adapter.OnItemClickListener() {
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
        setContentView(R.layout.grocery);
        initialize_things();
//        text_in_dynamic = (TextView) findViewById(R.id.text_in_dynamic);
//        text_in_dynamic.setText(getIntent().getStringExtra("nav_item_clicked"));
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
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

}
