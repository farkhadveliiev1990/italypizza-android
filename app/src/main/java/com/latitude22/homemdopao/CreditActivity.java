package com.latitude22.homemdopao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.norbsoft.typefacehelper.TypefaceHelper;
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

/**
 * Created by vaio1 on 23-11-2017.
 */

public class CreditActivity extends AppCompatActivity {
    ProgressDialog progress;
    Button buttonPay;
    EditText price_unit;
    TextView editTextAmount;
    SessionManager sm; Spinner spin_amt;
    ImageView history;
    RelativeLayout detail_header;
    String paymentid="",paymentstatus="",paymentdate="",paymentDetails="",spin_value="";
    public static final int PAYPAL_REQUEST_CODE = 123;
   // public  String PAYPAL_CLIENT_ID = "AYofn-A0K29GhHB4RLLZI8gfVxPb9xlhh6WbqO7ihmDmn2C1dYoHHgiSrI_CynGSl-yRh9Nd6_wMiuVl";
    public  String PAYPAL_CLIENT_ID ="AfjCSLdcDLFak-EW2T-0ALMgwrFDHxcaG4M0cKmVr9fHHjz5qxc5W4xRpX6rb4QV15543RJNDKxTM2cm";
    ImageView header_img;
    ArrayList<String> list_value = new ArrayList<>();
    String userid="";
    private PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)//.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID).acceptCreditCards(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        TypefaceHelper.typeface(this);

        detail_header = (RelativeLayout) findViewById(R.id.detail_header);
        detail_header.setVisibility(View.VISIBLE);
        price_unit = (EditText) findViewById(R.id.price_unit);
        buttonPay = (Button) findViewById(R.id.buttonPay);
        editTextAmount= (TextView) findViewById(R.id.editTextAmount);
        sm = new SessionManager(CreditActivity.this);
        userid = sm.getuserid();
        spin_amt = (Spinner)findViewById(R.id.spin_amt);
        history = (ImageView)findViewById(R.id.history);

        header_img = (ImageView)findViewById(R.id.header_img);

        header_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                finish();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreditActivity.this, WalletHistory.class));
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();

              /*  String mPriceunit = price_unit.getText().toString();
                if (!mPriceunit.equals("")) {


                } else
                    Toast.makeText(CreditActivity.this, "Enter Amount", Toast.LENGTH_SHORT).show();*/

            }
        });
        spin_amt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                spin_value = spin_amt.getSelectedItem().toString();
                /*if(spin_amt.getSelectedItemPosition() !=5)
                price_unit.setText(spin_amt.getSelectedItem().toString());
                else      price_unit.setText("");*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getlistFromServer();
    }
    public void getWalletFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet?";
        if (InternetConnection.isInternetOn(CreditActivity.this)) {
            showProgress();
            Ion.with(CreditActivity.this).load(ApiData).setBodyParameter("userid",userid )
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(CreditActivity.this,"0");
                    } else {
                        Log.d("StoreResult", result + "");



                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(CreditActivity.this,"0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    if(status.equals("1"))
                                    {
                                        editTextAmount.setText("\n" + "Saldo disponível : "+jdata.getString("total")+"€");

                                   //     MainActivity.tv3_balance.setText( "Saldo : " + jdata.getString("total") + "€");
                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(CreditActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(CreditActivity.this, "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });
        } else {
            Toast.makeText(CreditActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }


    public void getlistFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet_range?";
        if (InternetConnection.isInternetOn(CreditActivity.this)) {
            showProgress();
            Ion.with(CreditActivity.this).load(ApiData).setBodyParameter("userid",userid )
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(CreditActivity.this,"0");
                    } else {
                        Log.d("StoreResult", result + "");

                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(CreditActivity.this,"0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    if(status.equals("1"))
                                    {
                                        JSONArray jitem = jdata.getJSONArray("range");
                                        for(int i=0;i<jitem.length();i++){

                                            list_value.add(jitem.get(i).toString());
                                        }

                                        if(list_value.size()>0)
                                            spin_amt.setAdapter(new ArrayAdapter<String>(CreditActivity.this, android.R.layout.simple_spinner_dropdown_item, list_value));


                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(CreditActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(CreditActivity.this, "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }

                    getWalletFromServer();
                }
            });
        } else {
            Toast.makeText(CreditActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        Log.i("paypal",requestCode+" onActivityResult "+resultCode);
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
                        paymentstatus = mobj.getString("state");
                        paymentdate = (String) mobj.get("create_time");
                    //    paymentdate = paymentdate.replaceAll("[^\\d.]", "");
                        paymentid = mobj.getString("id");
                        AddWallettoServer();
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
    public void AddWallettoServer() {
        SessionManager sm = new SessionManager(CreditActivity.this);
        String ApiData = Constants.BASE_URL + "webservice/add_wallet?";
        if (InternetConnection.isInternetOn(CreditActivity.this)) {
            showProgress();

            Log.i("payment",paymentid+" date "+paymentdate+" sta "+paymentstatus);
            Ion.with(CreditActivity.this).load(ApiData).setBodyParameter("userid", sm.getuserid()).setBodyParameter("amt", spin_value)
                    .setBodyParameter("paymentid", paymentid)   .setBodyParameter("paymentdate", paymentdate)   .setBodyParameter("paymentstatus", paymentstatus)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(CreditActivity.this,"0");
                    } else {
                        Log.d("StoreResult", result + "");



                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(CreditActivity.this,"0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");

                                    if(status.equals("1"))
                                    {
                                        Toast.makeText(CreditActivity.this,"Conta foi creditada",Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(CreditActivity.this,MainActivity.class);
                                        startActivity(i);CreditActivity.this.finish();

                                      //  editTextAmount.setText("\n" + "Quantidade de crédito : "+jdata.getString("total")+"€");
                                       // getWalletFromServer();
                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(CreditActivity.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(CreditActivity.this, "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(CreditActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    private void getPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf("" + spin_value)), "EUR", "Carregamento", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(CreditActivity.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        CreditActivity.this.startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }

    public void showProgress() {
        try {
            progress = new ProgressDialog(CreditActivity.this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

}
