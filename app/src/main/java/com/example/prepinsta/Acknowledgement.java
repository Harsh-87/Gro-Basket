package com.example.prepinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Acknowledgement extends AppCompatActivity {

    Button gotohome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acknowledgement);
        gotohome = (Button) findViewById(R.id.go_to_home);
        gotohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Acknowledgement.this,Home_page.class);
                startActivity(in);
            }
        });
    }
}
