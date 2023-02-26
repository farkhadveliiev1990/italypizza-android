package com.latitude22.homemdopao.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.HistoryAdapter;
import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.Bean.HistoryBean;
import com.latitude22.homemdopao.Bean.HistoryProductBean;
import com.latitude22.homemdopao.ConfirmationActivity;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.HistoryDetail;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.UtilityMethod;
import com.latitude22.homemdopao.WalletHistory;
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

public class CreditFragment extends Fragment {
    ProgressDialog progress;
    static FragmentManager fm;
    RelativeLayout rlIfthenpayResult;
    RelativeLayout rlPhoneConfirmView;
    LinearLayout llIfThenPayInfoView;
    Button buttonPay, buttonMbpay, buttonIfThenPay, btnIfTPayClose, btnPhoneOk;
    EditText price_unit, etPhoneNumber;
    TextView editTextAmount, mbwayDescText, alert_view_title;
    SessionManager sm; Spinner spin_amt;
    ImageView history;
    String paymentid="",paymentstatus="",paymentdate="",paymentDetails="",spin_value="";
    TextView txt_enti, txt_ref, txt_val;

    public static final int PAYPAL_REQUEST_CODE = 123;
   // public  String PAYPAL_CLIENT_ID = "AYofn-A0K29GhHB4RLLZI8gfVxPb9xlhh6WbqO7ihmDmn2C1dYoHHgiSrI_CynGSl-yRh9Nd6_wMiuVl";
    public  String PAYPAL_CLIENT_ID ="AfjCSLdcDLFak-EW2T-0ALMgwrFDHxcaG4M0cKmVr9fHHjz5qxc5W4xRpX6rb4QV15543RJNDKxTM2cm";
    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        fm = FM;
        Fragment frag = new CreditFragment();
        return frag;
    }
    ArrayList<String> list_value = new ArrayList<>();
    String userid="";
    private PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)//.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID).acceptCreditCards(true);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.pay_layout, container, false);
        TypefaceHelper.typeface(v);
        price_unit = (EditText) v.findViewById(R.id.price_unit);
        buttonPay = (Button) v.findViewById(R.id.buttonPay);
        buttonMbpay = (Button) v.findViewById(R.id.buttonPayMbway);
        buttonIfThenPay = (Button) v.findViewById(R.id.buttonPayIfthenPay);
        rlIfthenpayResult = (RelativeLayout) v.findViewById(R.id.rl_ifthenpay_result);
        btnIfTPayClose = (Button) v.findViewById(R.id.bt_mb_dg_close);
        llIfThenPayInfoView = (LinearLayout) v.findViewById(R.id.ll_ifthenpay_info);
        rlPhoneConfirmView =(RelativeLayout) v.findViewById(R.id.rl_confirm_phone_view);
        etPhoneNumber = (EditText) v.findViewById(R.id.et_phone);
        mbwayDescText = (TextView) v.findViewById(R.id.txt_mbway_desc);
        txt_enti = (TextView) v.findViewById(R.id.txt_entidade);
        txt_ref = (TextView) v.findViewById(R.id.txt_ref);
        txt_val = (TextView) v.findViewById(R.id.txt_valor);
        editTextAmount= (TextView) v.findViewById(R.id.editTextAmount);
        alert_view_title = (TextView) v.findViewById(R.id.alert_view_title);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        sm = new SessionManager(getActivity());
        userid = sm.getuserid();
        spin_amt = (Spinner)v.findViewById(R.id.spin_amt);
        history = (ImageView)v.findViewById(R.id.history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletHistory.class));
            }
        });

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();

              /*  String mPriceunit = price_unit.getText().toString();
                if (!mPriceunit.equals("")) {


                } else
                    Toast.makeText(getActivity(), "Enter Amount", Toast.LENGTH_SHORT).show();*/

            }
        });
        buttonMbpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhoneNumber.setText("");
                rlPhoneConfirmView.setVisibility(View.VISIBLE);
            }
        });
        buttonIfThenPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPaymentByIfthenPay();
            }
        });
        btnIfTPayClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rlIfthenpayResult != null)
                    rlIfthenpayResult.setVisibility(View.GONE);
            }
        });
        btnPhoneOk = (Button) v.findViewById(R.id.bt_phone_ok);
        btnPhoneOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etPhoneNumber.getText().toString();
                if(phone.isEmpty())
                    return;
                rlPhoneConfirmView.setVisibility(View.GONE);
                getPaymentByMBWay(phone);
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
        return v;
    }
    public void getWalletFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid",userid )
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        Log.d("StoreResult", result + "");



                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(getActivity(),"0");
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
                                    //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(getActivity(), "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }


    public void getlistFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet_range?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid",userid )
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        Log.d("StoreResult", result + "");

                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(getActivity(),"0");
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
                                            spin_amt.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list_value));


                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(getActivity(), "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }

                    getWalletFromServer();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

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
        SessionManager sm = new SessionManager(getActivity());
        String ApiData = Constants.BASE_URL + "webservice/add_wallet?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();

            Log.i("payment",paymentid+" date "+paymentdate+" sta "+paymentstatus);
            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid", sm.getuserid()).setBodyParameter("amt", spin_value)
                    .setBodyParameter("paymentid", paymentid)   .setBodyParameter("paymentdate", paymentdate)   .setBodyParameter("paymentstatus", paymentstatus)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        Log.d("StoreResult", result + "");



                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(getActivity(),"0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");

                                    if(status.equals("1"))
                                    {
                                        Toast.makeText(getActivity(),"Conta foi creditada",Toast.LENGTH_LONG).show();
                                        Intent i=new Intent(getActivity(),MainActivity.class);
                                        startActivity(i);getActivity().finish();

                                      //  editTextAmount.setText("\n" + "Quantidade de crédito : "+jdata.getString("total")+"€");
                                       // getWalletFromServer();
                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(getActivity(), "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    private void getPayment() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf("" + spin_value)), "EUR", "Carregamento", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        getActivity().startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void getPaymentByMBWay(String phone) {
        //user_id, phone n0, value

        SessionManager sm = new SessionManager(getActivity());
        String ApiData = Constants.BASE_URL + "webservice/pay_mb_mobile?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();

            Log.e("test", "userid: "+ sm.getuserid()+" phone: "+ phone+" amount: "+spin_value);
            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid", sm.getuserid()).setBodyParameter("amount", spin_value)
                    .setBodyParameter("phone_no", phone)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        Log.e("test", "storeResut:" + result + "");
                        try {

                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");

                            //{"status":"1","msg":"success","data":"\t\n\t\t\n\t\n\t{\"ent_id\":12178,\"reference\":\"224253667\",\"value\":\"1,00\",\"htmlcode\":\"<pre>\\n\\n<b>Entidade:    <\\\/b>12178\\n\\n<b>Refer\\u00eancia:  <\\\/b>224 253 667\\n\\n<b>Valor: &euro;&nbsp;<\\\/b>1,00<\\\/pre>\"}"}

                            if(status.equals("1"))
                            {


                                String data = jdata.getString("data");

                                data = data.replace("\t", "");
                                data = data.replace("\n", "");
                                data = data.replace("\\", "");


                                JSONObject detail = new JSONObject(data);

                                String ent_id = detail.getString("ent_id");
                                String ref = detail.getString("reference");
                                String v = detail.getString("value");

                                txt_enti.setText(ent_id);
                                txt_ref.setText(ref);
                                txt_val.setText(v);

                                rlIfthenpayResult.setVisibility(View.VISIBLE);
                                llIfThenPayInfoView.setVisibility(View.GONE);
                                mbwayDescText.setVisibility(View.VISIBLE);
                                mbwayDescText.setText("O seu pedido de pagamento MBway foi enviado. Obrigado");
                                alert_view_title.setText("MBway");
                                /*rlIfthenpayResult.setVisibility(View.VISIBLE);
                                llIfThenPayInfoView.setVisibility(View.VISIBLE);
                                mbwayDescText.setVisibility(View.GONE);*/

                                getWalletFromServer();
                            }

                        } catch (JSONException e1) {
                            //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    private void getPaymentByIfthenPay() {
        //user_id,  value


        SessionManager sm = new SessionManager(getActivity());
        String ApiData = Constants.BASE_URL + "webservice/getMultibancoCode?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();

            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid", sm.getuserid()).setBodyParameter("amount", spin_value)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {

                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if(status.equals("1"))
                            {
                                String data = jdata.getString("data");

                                data = data.replace("\t", "");
                                data = data.replace("\n", "");
                                data = data.replace("\\", "");


                                JSONObject detail = new JSONObject(data);

                                String ent_id = detail.getString("ent_id");
                                String ref = detail.getString("reference");
                                String v = detail.getString("value");

                                Log.e("test", "*******" + ent_id + "/" + ref + "/" + v);

                                txt_enti.setText(ent_id);
                                txt_ref.setText(ref);
                                txt_val.setText(v);
                                rlIfthenpayResult.setVisibility(View.VISIBLE);
                                llIfThenPayInfoView.setVisibility(View.VISIBLE);
                                mbwayDescText.setVisibility(View.GONE);
                                alert_view_title.setText("Referência Multibanco");

                                getWalletFromServer();
                            }

                        } catch (JSONException e1) {
                            //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
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
            progress = new ProgressDialog(getActivity());
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

}
