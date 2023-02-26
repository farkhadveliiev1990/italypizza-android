package com.latitude22.homemdopao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class History extends AppCompatActivity {

    ImageView header_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        header_img = (ImageView) findViewById(R.id.header_img);
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(History.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent i = new Intent(History.this,MainActivity.class);
        startActivity(i);
        finish();
        // finish();
    }
}
