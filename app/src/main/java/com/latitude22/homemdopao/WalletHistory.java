package com.latitude22.homemdopao;

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
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.WalletAdapter;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by root on 21/7/16.
 */
public class WalletHistory extends AppCompatActivity {

    ProgressDialog progress;
    static FragmentManager fm;
    TextView credit_amt;
    TextView editTextAmount;
    SessionManager sm;
    ListView list_history;
    WalletAdapter adapter;
    ArrayList<com.latitude22.homemdopao.Bean.WalletHistory> summary_list = new ArrayList<>();

   ImageView header_img;
    boolean loadMore = false;
    int mTotalItemCount = 10,pno=0;
    Boolean loadingMore = true;
    String userid = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_history);

       TypefaceHelper.typeface(WalletHistory.this);
        list_history = (ListView) findViewById(R.id.list_history);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        sm = new SessionManager(WalletHistory.this);
        userid = sm.getuserid();
        header_img = (ImageView)findViewById(R.id.header_img);
        header_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                finish();
            }
        });

        list_history.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                //    Log.i("StoreResult ",lastInScreen+" lastInScreen "+mTotalItemCount);
                if ((lastInScreen == mTotalItemCount)) {

                    //if (stopLoadingData == false)
                    {
                        mTotalItemCount = mTotalItemCount + 10;
                        loadingMore = true;
                        loadMore = true;

                        getWalletFromServer();
                    }
                }
            }
        });
        getWalletFromServer();
    }

    public void getWalletFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet_history?";
        if (InternetConnection.isInternetOn(WalletHistory.this)) {
            showProgress();
            Ion.with(WalletHistory.this).load(ApiData).setBodyParameter("userid", userid).setBodyParameter("page", pno+"")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(WalletHistory.this, "0");
                    } else {
                        Log.d("StoreResult", result + "");


                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(WalletHistory.this, "0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");

                                    if (status.equals("1")) {
                                        JSONArray jitem = jdata.getJSONArray("data");
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject jsonData = jitem.getJSONObject(i);

                                            com.latitude22.homemdopao.Bean.WalletHistory breadShopData = new com.latitude22.homemdopao.Bean.WalletHistory();
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
                                            pno = pno+1;
                                            if (adapter == null) {
                                                adapter = new WalletAdapter(WalletHistory.this, summary_list);
                                                list_history.setAdapter(adapter);
                                            } else {

                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    hideProgress();


                                } catch (JSONException e1) {
                                    //  Toast.makeText(WalletHistory.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(WalletHistory.this, "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(WalletHistory.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

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
            progress = new ProgressDialog(WalletHistory.this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

}

