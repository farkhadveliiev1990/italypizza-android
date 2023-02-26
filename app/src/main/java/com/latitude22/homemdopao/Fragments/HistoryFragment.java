package com.latitude22.homemdopao.Fragments;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.http.body.StreamBody;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.CalenderProductAdapter;
import com.latitude22.homemdopao.Adapter.HistoryAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderListAdapter;
import com.latitude22.homemdopao.Bean.HistoryBean;
import com.latitude22.homemdopao.Bean.HistoryProductBean;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.CalenderActivity;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.HistoryDetail;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.UtilityMethod;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vaio1 on 23-11-2017.
 */

public class HistoryFragment extends Fragment {
    static Context Mcontext;
    ListView list_history;
    SessionManager sm;
    String UserId;
    public static int deliveryBoyId;
    TextView history_tv_noitem;
    ArrayList<HistoryBean>  historyArrayList = new ArrayList<>();
    ProgressDialog progress;
    ArrayList<HistoryProductBean> productListArray;
    static android.support.v4.app.FragmentManager fm;
    int pno=0;  boolean loadMore = false;
    int mTotalItemCount = 20;
    Boolean loadingMore = true;
    int m=1;
    HistoryAdapter adapter;
    public static Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        Fragment frag = new HistoryFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.activity_history, container, false);
        TypefaceHelper.typeface(v);

        list_history = (ListView) v.findViewById(R.id.list_history);
        history_tv_noitem = (TextView) v.findViewById(R.id.history_tv_noitem);
        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.order_history);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Histórico de Encomendas");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.GONE);

        sm =new SessionManager(getActivity());
        UserId = sm.getuserid();

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });
        MainActivity.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        MainActivity.icon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        list_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),HistoryDetail.class);
                intent.putExtra("SubscriptionName",historyArrayList.get(position).getSubscriptionName());
                intent.putExtra("SubscriptionCost",historyArrayList.get(position).getSubscriptionCost());
                intent.putExtra("SubscriptionDate",historyArrayList.get(position).getStartDate());
                intent.putExtra("NoDelivery",historyArrayList.get(position).getNoOfDelivery());
                intent.putExtra("SubscriptionStatus",historyArrayList.get(position).getOrderCurrentStatus());
                intent.putExtra("productlist",historyArrayList.get(position).getSubscriptionProduct());

                startActivity(intent);

              //  getActivity().finish();
          /*      Bundle bun = new Bundle();
                bun.putString("no",number);
                bun.putString("ma",mail);
                bun.putString("pa",pass);
                Intent subintent = new Intent(getContext(),SubmitRegistration.class);
                subintent.putExtras(bun);
                startActivity(subintent);*/
            }
        });
        list_history.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                Log.i("StoreResult ",lastInScreen+" lastInScreen "+mTotalItemCount);
                if ((lastInScreen == mTotalItemCount)) {

                    //if (stopLoadingData == false)
                    {
                        // loading_bar.setVisibility(View.VISIBLE);
                        mTotalItemCount = mTotalItemCount + 20;
                        loadingMore = true;
                        loadMore = true;

                        getDataFromServer(UserId);
                    }
                }
            }
        });
        getDataFromServer(UserId);

        return v;
    }
    public void getDataFromServer(String userid) {
        String ApiData = Constants.BASE_URL + "webservice/order_history";
        if(InternetConnection.isInternetOn(getActivity())) {
            if(pno == 0) showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO", pno+"").setBodyParameter("userid",userid)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (e == null) {
                        if (UtilityMethod.isStringNullOrBlank(result)) {
                            hideProgress();
                            UtilityMethod.alertforServerError(getActivity(),"0");
                        }
                        else
                            {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                Log.i(" array "," status "+ status);

                                if (status.equals("1")) {
                                    // JSONObject jsonData = jdata.getJSONObject("orderdetails");
                                    JSONArray jitem = jdata.getJSONArray("orderdetails");

                                    Log.i(" array "," items "+ jitem.toString());


                                    for (int i = 0; i < jitem.length(); i++) {

                                        JSONObject jData = jitem.getJSONObject(i);

                                        HistoryBean historyBean = new HistoryBean();

                                        historyBean.setOrderId(jData.optInt("OrderID"));
                                        historyBean.setDeliveryboyId(jData.optInt("OrderDliveryBoyID"));
                                       //  historyBean.setOrderCost(jData.optString("Ordercost"));
                                        historyBean.setStartDate(jData.optString("OrderStartDate"));
                                      //  historyBean.setEndDate(jData.optString("OrderEndDate"));
                                        historyBean.setSubscriptionName(jData.optString("SubscriptionName"));
                                        historyBean.setSubscriptionCost(jData.optString("SubscriptionCost"));
                                        historyBean.setOrderCost(jData.optString("Ordercost"));

                                        JSONArray jprorray = jData.getJSONArray("product");
                                        productListArray = new ArrayList<HistoryProductBean>();
                                        JSONArray marray = new JSONArray();

                                        Log.i(" array "," jdata "+ jData);

                                        if(jprorray != null){
                                        if(jprorray.length()>0) {

                                            for (int j = 0; j < jprorray.length(); j++) {
                                                HistoryProductBean bean = new HistoryProductBean();
                                                JSONObject obj = new JSONObject();
                                                JSONObject jobj = jprorray.getJSONObject(j);
                                                obj.put("ProductName", jobj.optString("ProductName"));
                                                obj.put("Productqty", jobj.optInt("Productqty"));
                                                obj.put("ProductPrice", jobj.optString("ProductPrice"));
                                                obj.put("ProductImage", jobj.optString("ProductImage"));
                                                marray.put(obj);

                                                bean.setProductName(jobj.optString("ProductName"));
                                                bean.setProductQty(jobj.optInt("Productqty"));
                                                bean.setProductPrice(jobj.optString("ProductPrice"));
                                                bean.setProductImage(jobj.optString("ProductImage"));
                                                productListArray.add(bean);
                                            }
                                        }  }else
                                        {
                                            HistoryProductBean bean = new HistoryProductBean();
                                            JSONObject obj = new JSONObject();

                                            obj.put("ProductName", "");
                                            obj.put("Productqty", 0);
                                            obj.put("ProductPrice", "");
                                            obj.put("ProductImage", "");
                                            marray.put(obj);

                                            bean.setProductName("");
                                            bean.setProductQty(0);
                                            bean.setProductPrice("");
                                            bean.setProductImage("");
                                            productListArray.add(bean);
                                        }

                                        historyBean.setProductlist(productListArray);
                                        historyBean.setSubscriptionProduct(marray.toString());
                                        historyBean.setNoOfDelivery(jData.optString("OrderNumDelivery"));
                                        historyBean.setOrderCurrentStatus(jData.optString("OrderCurrentStatus"));
                                        historyBean.setOrderPymentStatus(jData.optString("OrderPaymentStatus"));
                                        historyBean.setSubscriptionCount(m); m++;
                                        historyArrayList.add(historyBean);
                                        //Log.i(" array "," historyarray "+ historyBean);

                                        //Toast.makeText(Mcontext, ""+historyArrayList.get(i).getNoOfDelivery()+"  "+historyArrayList.get(i).getOrderCurrentStatus()+" "+historyArrayList.get(i).getOrderPymentStatus(), Toast.LENGTH_LONG).show();
                                    }
                                    Log.i(" array "," historyarray size "+ historyArrayList.size());



                                    if(historyArrayList.size() >0) {
                                        pno = pno + 1;
                                        history_tv_noitem.setVisibility(View.GONE);
                                        if (adapter == null) {
                                            adapter = new HistoryAdapter(historyArrayList, Mcontext);
                                            list_history.setAdapter(adapter);
                                        } else {
                                            adapter.notifyDataSetChanged();
                                        }
                                    }else  history_tv_noitem.setVisibility(View.VISIBLE);

                                    hideProgress();

                                } else {
                                    hideProgress();
                                    //   Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e1) {
                                //   Toast.makeText(getActivity(), "" + e1, Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }

                        }
                    }
                    else {
                            // Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

            });
          //  Toast.makeText(Mcontext, ""+UserId, Toast.LENGTH_SHORT).show();
        }
        else
        {
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
            progress = new ProgressDialog(Mcontext);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

}
