package com.latitude22.homemdopao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class PaymentSubscribActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonPay,buttonPayShop;
    ImageView header_img;
    ArrayList<AddCart> addCarts;
    Context mcontxt;
    String status="",time="";
    int days;
    ProgressDialog progress;
    String payId;
    SessionManager sm;
    String UserId,Flag;
    String paymentDetails;
    ListView cart_item_list;
    private TextView editTextAmount, textView3;
    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 123;
   // public  String PAYPAL_CLIENT_ID = "AfjCSLdcDLFak-EW2T-0ALMgwrFDHxcaG4M0cKmVr9fHHjz5qxc5W4xRpX6rb4QV15543RJNDKxTM2cm";
   // public  String PAYPAL_CLIENT_ID = "AYofn-A0K29GhHB4RLLZI8gfVxPb9xlhh6WbqO7ihmDmn2C1dYoHHgiSrI_CynGSl-yRh9Nd6_wMiuVl";
   public  String PAYPAL_CLIENT_ID ="AfjCSLdcDLFak-EW2T-0ALMgwrFDHxcaG4M0cKmVr9fHHjz5qxc5W4xRpX6rb4QV15543RJNDKxTM2cm";

    //Payment Amount
    private Double paymentAmount;
    String subcriptionId,startdate,starttime;
    String array ,fixdPlanproArray;
    JSONArray marray;


    private  PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)//.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID).acceptCreditCards(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypaypay);

        buttonPay = (Button) findViewById(R.id.buttonPay);
        buttonPayShop = (Button) findViewById(R.id.buttonPayShop);
        header_img = (ImageView) findViewById(R.id.header_img);
        editTextAmount = (TextView) findViewById(R.id.editTextAmount);
        textView3 = (TextView) findViewById(R.id.textView3);
        sm = new SessionManager(this);
        marray = new JSONArray();
        UserId = sm.getuserid();
        mcontxt = this;
        buttonPay.setOnClickListener(this);

        if (InternetConnection.isInternetOn(this))
        {

        }
        else
        {
            Toast.makeText(PaymentSubscribActivity.this, "Verifique a ligao de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
        Intent i = getIntent();

        if (i != null)
        {
            paymentAmount = Double.valueOf(i.getStringExtra("PyaAmmount"));
            subcriptionId = i.getStringExtra("SubscriptionId");
            startdate = i.getStringExtra("StartDate");
            starttime = i.getStringExtra("StartTime");
            Flag = i.getStringExtra("SetFlag");
            days = i.getIntExtra("days",0);
            array=i.getStringExtra("array");
            Log.i("Array "," Array "+ array);
        }

        textView3.setText("" +paymentAmount);
        //Paypal Configuration Object
        header_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(PaymentSubscribActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonPayShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertShopOrderCustom(UserId);/*
                if(Flag.equals("0"))
                {
                    insertShopOrderCustom(UserId);
                }
                if(Flag.equals("1")) {
                    insertOrderForShop(UserId);

                }*/

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
       // getPayment();
        insertOrder();
    }

    public void insertOrder()
    {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertoreder";

            if(UtilityMethod.isStringNullOrBlank(array)){

                try
            {
                addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                for (int i = 0; i < addCarts.size(); i++)
                {
                    JSONObject mobj = new JSONObject();
                    mobj.put("ID", "" + addCarts.get(i).getProductId());
                    mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                    marray.put(mobj);
                    Log.i(" array ",addCarts.size()+" array "+ marray.toString());
                }
            }
            catch (Exception ae)
            {
            }
        }
        else {
            try {
                marray=new JSONArray(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.i(" array ","userid="+ UserId +"&cartlist="+marray+"&subscriptioncost="+paymentAmount);
            Log.i(" array ","&subscriptionid="+ subcriptionId +"&days="+days+"&startdate="+startdate+"&time="+starttime);

            Ion.with(this).load(ApiData).setBodyParameter("userid", UserId).setBodyParameter("cartlist", "" + marray)
                    .setBodyParameter("subscriptioncost", String.valueOf(paymentAmount))
                    .setBodyParameter("subscriptionid", subcriptionId)
                    .setBodyParameter("days",  String.valueOf(days)).setBodyParameter("startdate",startdate)
                    .setBodyParameter("time",starttime)  .setBodyParameter("store","0")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {

                    Log.d("StoreResult",   " result "+result);
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if (status.equals("1")) {
                                new Delete().from(AddCart.class).execute();
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(PaymentSubscribActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                                hideProgress();
                            }else if(jdata.optString("msg").equals("Saldo disponível insuficiente. Por favor carregue a sua conta."))
                                {   Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PaymentSubscribActivity.this,CreditActivity.class));
                                }else
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();


                        } catch (Exception e1) {
                            Toast.makeText(PaymentSubscribActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    } else {
                        Toast.makeText(PaymentSubscribActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(PaymentSubscribActivity.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }



    private void getPayment() {
        //Getting the amount from editText


        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf("" + paymentAmount)), "EUR", "Pagar", PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
      //  intent.putExtra(PaymentActivity.EXTRA_SKIP_CREDIT_CARD, true);
        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult

     //   intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config.acceptCreditCards(false));
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        JSONObject jdata = new JSONObject(paymentDetails);
                        JSONObject mobj = jdata.getJSONObject("response");
                        status = mobj.getString("state");
                        time = (String) mobj.get("create_time");
                        time = time.replaceAll("[^\\d.]", "");
                        payId = mobj.getString("id");
                        if(Flag.equals("0"))
                        {
                           insertOrderCustom(UserId);
                        }
                        if(Flag.equals("1")) {
                            insertOrder(UserId);
                        }
                        // showProgress();
                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }


    public  void insertShopOrderCustom(String userid)
    {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertoreder";

            if(UtilityMethod.isStringNullOrBlank(array)){

                try
                {
                    addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                    for (int i = 0; i < addCarts.size(); i++)
                    {
                        JSONObject mobj = new JSONObject();
                        mobj.put("ID", "" + addCarts.get(i).getProductId());
                        mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                        marray.put(mobj);
                    }
                }
                catch (Exception ae)
                {
                }
            }
            else {
                try {
                    marray=new JSONArray(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            Log.i(" array ","userid="+ UserId +"&cartlist="+marray+"&subscriptioncost="+paymentAmount);
            Log.i(" array ","&subscriptionid="+ subcriptionId +"&days="+days+"&startdate="+startdate+"&time="+starttime);
            Ion.with(this).load(ApiData).setBodyParameter("userid", userid).setBodyParameter("cartlist", "" + marray).setBodyParameter("subscriptioncost", String.valueOf(paymentAmount)).setBodyParameter("subscriptionid", subcriptionId)
                    .setBodyParameter("paymentid","").setBodyParameter("days", String.valueOf(days)).setBodyParameter("startdate",startdate)
                    .setBodyParameter("time",starttime).setBodyParameter("paymentdate", "").setBodyParameter("paymentstatus", "").setBodyParameter("store","1")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("StoreResult", result + "");
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            Log.i("status msg  ",jdata.optString("msg")+" status "+ status);
                            if (status.equals("1")) {
                                new Delete().from(AddCart.class).execute();
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(PaymentSubscribActivity.this, ThankYouShop.class);
                                startActivity(i);
                                finish();

                          /*  addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                            cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                            PriceTotal(addCarts);
                            */
                                hideProgress();
                            }

                        } catch (Exception e1) {
                            Toast.makeText(PaymentSubscribActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    } else {
                        Toast.makeText(PaymentSubscribActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(PaymentSubscribActivity.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }

    public void insertOrderForShop(String userid)
    {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertoreder";

            if(UtilityMethod.isStringNullOrBlank(array)){

             /*   for (int i = 0; i < FixedPlanAdapter.fixedPlanProductBeen.size(); i++)
                {
                    JSONObject mobj1 = new JSONObject();
                    try {
                        mobj1.put("ID", "" + FixedPlanAdapter.fixedPlanProductBeen.get(i).getFixedProductId());
                        mobj1.put("QTY", "1");
                        marray.put(mobj1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }*/
                try
                {
                    addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                    for (int i = 0; i < addCarts.size(); i++)
                    {
                        JSONObject mobj = new JSONObject();
                        mobj.put("ID", "" + addCarts.get(i).getProductId());
                        mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                        marray.put(mobj);
                    }

                }
                catch (Exception ae)
                {

                }
            }
            else {
                try {
                    marray=new JSONArray(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            Log.i(" array ","userid="+ UserId +"&cartlist="+marray+"&subscriptioncost="+paymentAmount);
            Log.i(" array ","&subscriptionid="+ subcriptionId +"&days="+days+"&startdate="+startdate+"&time="+starttime);
            Ion.with(this).load(ApiData).setBodyParameter("userid", userid).setBodyParameter("cartlist", "" + marray).setBodyParameter("subscriptioncost", String.valueOf(paymentAmount)).setBodyParameter("subscriptionid", subcriptionId)
                    .setBodyParameter("paymentid", "").setBodyParameter("days", days+"").setBodyParameter("startdate",startdate)
                    .setBodyParameter("time",starttime).setBodyParameter("paymentdate","").setBodyParameter("paymentstatus","").setBodyParameter("store","1")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("StoreResult", result + "");
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            Log.i("status msg",jdata.optString("msg")+" status "+ status);
                            if (status.equals("1")) {
                                new Delete().from(AddCart.class).execute();
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                               /* Intent i=new Intent(PaymentSubscribActivity.this,MainActivity.class);
                                i.putExtra("position","1");
                                startActivity(i);
                                finish();*/
                                Intent i = new Intent(PaymentSubscribActivity.this, ThankYouShop.class);
                                startActivity(i);
                                finish();

                          /*  addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                            cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                            PriceTotal(addCarts);
                            */
                                hideProgress();
                            }

                        } catch (Exception e1) {
                            Toast.makeText(PaymentSubscribActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    } else {
                        Toast.makeText(PaymentSubscribActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(PaymentSubscribActivity.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }

    public void insertOrder(String userid)
    {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertoreder";

            if(UtilityMethod.isStringNullOrBlank(array)){

                try
                {
                    addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                    for (int i = 0; i < addCarts.size(); i++)
                    {
                        JSONObject mobj = new JSONObject();
                        mobj.put("ID", "" + addCarts.get(i).getProductId());
                        mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                        marray.put(mobj);
                    }

                }
                catch (Exception ae)
                {
                }
            }
            else {
                try {
                    marray=new JSONArray(array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Ion.with(this).load(ApiData).setBodyParameter("userid", userid).setBodyParameter("cartlist", "" + marray).setBodyParameter("subscriptioncost", String.valueOf(paymentAmount)).setBodyParameter("subscriptionid", subcriptionId)
                    .setBodyParameter("paymentid", payId).setBodyParameter("days", "").setBodyParameter("startdate",startdate)
                    .setBodyParameter("time",starttime).setBodyParameter("paymentdate", "").setBodyParameter("paymentstatus", status)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("StoreResult", result + "");
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if (status.equals("1")) {
                                new Delete().from(AddCart.class).execute();
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                               /* Intent i=new Intent(PaymentSubscribActivity.this,MainActivity.class);
                                i.putExtra("position","1");
                                startActivity(i);
                                finish();*/

                                startActivity(new Intent(mcontxt, ConfirmationActivity.class)
                                        .putExtra("PaymentDetails", "" + paymentDetails)
                                        .putExtra("PaymentAmount", paymentAmount));
                                finish();

                          /*  addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                            cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                            PriceTotal(addCarts);
                            */
                                hideProgress();
                            }

                        } catch (Exception e1) {
                            Toast.makeText(PaymentSubscribActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    } else {
                        Toast.makeText(PaymentSubscribActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(PaymentSubscribActivity.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }

    public  void insertOrderCustom(String userid)
        {
            if (InternetConnection.isInternetOn(this)) {
                hideProgress();
                showProgress();
                String ApiData = Constants.BASE_URL + "webservice/insertoreder";

                if(UtilityMethod.isStringNullOrBlank(array)){

             /*   for (int i = 0; i < FixedPlanAdapter.fixedPlanProductBeen.size(); i++)
                {
                    JSONObject mobj1 = new JSONObject();
                    try {
                        mobj1.put("ID", "" + FixedPlanAdapter.fixedPlanProductBeen.get(i).getFixedProductId());
                        mobj1.put("QTY", "1");
                        marray.put(mobj1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }*/
                    try
                    {
                        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                        for (int i = 0; i < addCarts.size(); i++)
                        {
                            JSONObject mobj = new JSONObject();
                            mobj.put("ID", "" + addCarts.get(i).getProductId());
                            mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                            marray.put(mobj);
                        }

                    }
                    catch (Exception ae)
                    {
                    }
                }
                else {
                    try {
                        marray=new JSONArray(array);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("array ","marray"+ marray.toString());

                Ion.with(this).load(ApiData).setBodyParameter("userid", userid).setBodyParameter("cartlist", "" + marray).setBodyParameter("subscriptioncost", String.valueOf(paymentAmount)).setBodyParameter("subscriptionid", subcriptionId)
                        .setBodyParameter("paymentid", payId).setBodyParameter("days", String.valueOf(days)).setBodyParameter("startdate",startdate).setBodyParameter("time",starttime)
                        .setBodyParameter("paymentdate", "").setBodyParameter("paymentstatus", status)
                        .asString().setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("StoreResult", result + "");
                        hideProgress();
                        if (e == null) {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                if (status.equals("1")) {
                                    new Delete().from(AddCart.class).execute();
                                    Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                               /* Intent i=new Intent(PaymentSubscribActivity.this,MainActivity.class);
                                i.putExtra("position","1");
                                startActivity(i);
                                finish();*/

                                    startActivity(new Intent(mcontxt, ConfirmationActivity.class)
                                            .putExtra("PaymentDetails", "" + paymentDetails)
                                            .putExtra("PaymentAmount", paymentAmount));
                                    finish();

                          /*  addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                            cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                            PriceTotal(addCarts);
                            */
                                    hideProgress();
                                }

                            } catch (Exception e1) {
                                Toast.makeText(PaymentSubscribActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }
                        } else {
                            Toast.makeText(PaymentSubscribActivity.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {
                Toast.makeText(PaymentSubscribActivity.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        }

    public void hideProgress()
    {
        if (progress != null)
        {
            try
            {
                progress.dismiss();
            }
            catch (Exception e)
            {
            }
        }
    }

    public void showProgress() {
        try {
            progress = new ProgressDialog(this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(PaymentSubscribActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}
