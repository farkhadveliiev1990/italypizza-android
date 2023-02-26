package com.latitude22.homemdopao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latitude22.homemdopao.Fragments.MyOrderFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {

    ImageView header_img;
    Button bt_schedule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        header_img = (ImageView)findViewById(R.id.header_img);
        bt_schedule=(Button)findViewById(R.id.bt_schedule);
        //Getting Intent
        Intent intent = getIntent();

        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ConfirmationActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        bt_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ConfirmationActivity.this,CalenderActivity.class);

                startActivity(i);
                finish();
            }
        });
        if(InternetConnection.isInternetOn(this)) {
        }
        else
        {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
        }

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
           // showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
            showDetails(jsonDetails.getJSONObject("response"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //, String paymentAmount

    private void showDetails(JSONObject jsonDetails) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
      // TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);
        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
      // textViewAmount.setText(paymentAmount+"USD");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(ConfirmationActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();
      /*  Intent i = new Intent(ConfirmationActivity.this,MainActivity.class);
        startActivity(i);
        finish();*/
    }
}