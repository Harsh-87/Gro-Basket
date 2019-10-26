package com.example.prepinsta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    TextView pro_name,pro_id;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        pro_name = (TextView) findViewById(R.id.pro_name);
        pro_id = (TextView) findViewById(R.id.pro_id);
        if(FirebaseAuth.getInstance().getCurrentUser().getEmail() !=null){
            id = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        }else{
            id = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString();
        }
        pro_id.setText(id);
        pro_name.setText("ABCD");
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
