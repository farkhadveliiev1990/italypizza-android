package com.latitude22.homemdopao.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.BreadShopAdapter;
import com.latitude22.homemdopao.Adapter.WalletAdapter;
import com.latitude22.homemdopao.Bean.WalletHistory;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.UtilityMethod;
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
import java.util.List;

/**
 * Created by root on 21/7/16.
 */
public class WalletHisFragment extends Fragment {

    ProgressDialog progress;
    static FragmentManager fm;
    TextView credit_amt;
    TextView editTextAmount;
    SessionManager sm;
    ListView list_history;
    WalletAdapter adapter;
    ArrayList<WalletHistory> summary_list = new ArrayList<>();

    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {

        fm = FM;
        Fragment frag = new WalletHisFragment();
        return frag;
    }

    String userid = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.activity_wallet_history, container, false);
        TypefaceHelper.typeface(v);
        list_history = (ListView) v.findViewById(R.id.list_history);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        sm = new SessionManager(getActivity());
        userid = sm.getuserid();
        getWalletFromServer();
        return v;
    }

    public void getWalletFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet_history?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("userid", userid)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(getActivity(), "0");
                    } else {
                        Log.d("StoreResult", result + "");


                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(getActivity(), "0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");

                                    if (status.equals("1")) {
                                        JSONArray jitem = jdata.getJSONArray("data");
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject jsonData = jitem.getJSONObject(i);

                                            WalletHistory breadShopData = new WalletHistory();
                                            breadShopData.setId(jsonData.optString("id"));
                                            breadShopData.setAdded_amt(jsonData.optString("added_amt"));
                                            breadShopData.setAdded_date(jsonData.optString("added_date"));
                                            breadShopData.setDeducted_amt(jsonData.optString("deducted_amt"));
                                            breadShopData.setDeducted_date(jsonData.optString("deducted_date"));
                                            breadShopData.setWallet_amount(jsonData.optString("wallet_amount"));
                                            breadShopData.setWallet_reason(jsonData.optString("wallet_reason"));
                                            summary_list.add(breadShopData);

                                        }

                                        if (summary_list.size() > 0) {
                                            adapter = new WalletAdapter(getActivity(), summary_list);
                                            list_history.setAdapter(adapter);
                                        }
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

