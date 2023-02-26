package com.latitude22.homemdopao.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.MyOrderAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderListAdapter;
import com.latitude22.homemdopao.Adapter.MyOrderProductAdapter;
import com.latitude22.homemdopao.Bean.MyOrderBean;
import com.latitude22.homemdopao.Bean.MyOrderListBean;
import com.latitude22.homemdopao.Bean.MysubscriptionProductBean;
import com.latitude22.homemdopao.CalenderActivity;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.DetailSubscribeActivity;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.Service.MapLocationService;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.SubcriptionDetail;
import com.latitude22.homemdopao.UtilityMethod;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anuved on 26-08-2016.
 */
public class MyOrderFragment extends Fragment {

    static Context Mcontext;
    SessionManager sm;
    String UserId;
    ListView list_myoredr;
    Button bt_calender;
    int numdelvry;
    String delvery;
    AlertDialog b;
    ProgressDialog progress;
    String orderId,start_dtae,deliveryId;
    String subscriptioname;
    int productQty,numofDelivery;
    public static String boyId;
    String cst;
    TextView num_delevery,subscription,cost,myoreder_tv_noitem;


   public  ArrayList<MyOrderListBean> myList;
    ArrayList<MyOrderBean> myorderList;
    ArrayList<MysubscriptionProductBean> productListArray;
    static android.support.v4.app.FragmentManager fm;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new MyOrderFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.myorder, container, false);
        TypefaceHelper.typeface(v);
        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        MainActivity.header_text.setText("Subscrições");
        list_myoredr = (ListView) v.findViewById(R.id.list_myoredr);
      //  num_delevery = (TextView) v.findViewById(R.id.num_delevery);
        myoreder_tv_noitem = (TextView) v.findViewById(R.id.myoreder_tv_noitem);
      //  subscription  = (TextView) v.findViewById(R.id.subscription);
      //  cost = (TextView) v.findViewById(R.id.cost);
        myorderList = new ArrayList<>();

        bt_calender = (Button) v.findViewById(R.id.bt_calender);
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


    getDataFromServer(UserId);


   //showProgress();

        bt_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CalenderActivity.class);
               /* i.putExtra("ordrId", orderId);
                i.putExtra("start_date",start_dtae);*/
                startActivity(i);
                //getActivity().finish();
            }
        });


     list_myoredr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             /*Intent intent = new Intent(getActivity(),SubcriptionDetail.class);
             intent.putExtra("productlist",myList.get(position).getSubscriptionProduct());
             startActivity(intent);*/
             showDaialog(position);
            // Toast.makeText(Mcontext, "orderiID"+myList.get(position).getOrderId(), Toast.LENGTH_SHORT).show();
         }
     });

        return v;
    }
    public void getDataFromServer(String userid) {
        String ApiData = Constants.BASE_URL + "webservice/myorder?";
        if(InternetConnection.isInternetOn(getActivity())) {
            hideProgress();
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO", "0").setBodyParameter("userid", userid)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(getActivity(),"0");
                    }
                    else {
                         hideProgress();
                        if (e == null) {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                if (status.equals("1")) {

                                    // JSONObject jsonData = jdata.getJSONObject("orderdetails");
                                    JSONArray jitem = jdata.getJSONArray("orderdetails");
                                    myList = new ArrayList<MyOrderListBean>();
                                    for (int i = 0; i < jitem.length(); i++) {

                                        JSONObject jData = jitem.getJSONObject(i);
                                        MyOrderListBean orderListBean = new MyOrderListBean();
                                        orderListBean.setSubscriptionName(jData.optString("SubscriptionName"));
                                        orderListBean.setOrderId(jData.optString("OrderID"));
                                        orderListBean.setStartDate(jData.optString("OrderStartDate"));
                                        orderListBean.setDeliveryBoyID(jData.optString("OrderDliveryBoyID"));
                                        boyId = jData.optString("OrderDliveryBoyID");
                                       // orderListBean.setEndDate(jData.optString("OrderEndDate"));
                                        JSONArray jprorray = jData.getJSONArray("product");
                                       // Toast.makeText(getActivity(), "Fragment"+boyId, Toast.LENGTH_SHORT).show();
                                        productListArray = new ArrayList<MysubscriptionProductBean>();
                                        JSONArray marray = new JSONArray();
                                        Log.i(" list ",i+" i "+jitem.length()+" mylist "+ jprorray.length());

                                        for(int j = 0; j < jprorray.length() ;j++)
                                        {
                                            MysubscriptionProductBean bean = new MysubscriptionProductBean();
                                            JSONObject obj = new JSONObject();
                                            JSONObject jobj = jprorray.getJSONObject(j);
                                            obj.put("ProductName", jobj.optString("ProductName"));
                                            obj.put("Productqty", jobj.optInt("Productqty"));
                                            obj.put("ProductPrice",jobj.optString("ProductPrice"));
                                            obj.put("ProductImage",jobj.optString("ProductImage"));
                                            marray.put(obj);
                                            bean.setName(jobj.optString("ProductName"));
                                            bean.setQty(jobj.optInt("Productqty"));
                                            bean.setProductImage(jobj.optString("ProductPrice"));
                                            bean.setProductImage(jobj.optString("ProductImage"));
                                            productListArray.add(bean);
                                        }
                                        orderListBean.setSubscriptionCount(i+1);
                                        orderListBean.setNoOfPending(jData.optString("OrderNumDelivery"));
                                        orderListBean.setProductlist(productListArray);
                                        orderListBean.setSubscriptionProduct(marray.toString());
                                        myList.add(orderListBean);
                                }
                                    if (myList.size() == 0) {
                                        myoreder_tv_noitem.setVisibility(View.VISIBLE);
                                    } else {
                                        myoreder_tv_noitem.setVisibility(View.GONE);
                                        list_myoredr.setAdapter(new MyOrderListAdapter(myList,getActivity()));
                                        //setListViewHeightBasedOnChildren(list_myoredr);
                             //           list_myoredr.setAdapter(new MyOrderListAdapter(myList, getActivity()));

                                    }

                                    hideProgress();
                                    //   }
                                    // setAdapter();
                                } else {
                                    hideProgress();
                                    //   Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e1) {
                                //   Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }

                        } else {
                            // Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
       //   Toast.makeText(Mcontext, ""+UserId, Toast.LENGTH_SHORT).show();
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
        }
        catch (Exception e) {

        }
    }

    public void showDaialog(int position)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.subscription_product_dialog, null);
        dialogBuilder.setView(dialogView);
        TypefaceHelper.typeface(dialogView);
        b = dialogBuilder.create();
        b.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        b.setCancelable(true);
        b.getWindow().setGravity (Gravity.CENTER);
        b.show();
      //  dialogBuilder.setTitle("Set No. of Days");
        ArrayList<MysubscriptionProductBean> productListArray = new ArrayList<>();
        ListView  productList = (ListView) dialogView.findViewById(R.id.dilaog_list);
        ImageView heder_back = (ImageView) dialogView.findViewById(R.id.heder_back);



        JSONArray jpitem = null;

        String listSting = myList.get(position).getSubscriptionProduct();
        try {
            jpitem = new JSONArray(listSting);

            for (int j = 0; j < jpitem.length(); j++) {

                MysubscriptionProductBean bean= new MysubscriptionProductBean();

                JSONObject obj=new JSONObject();
                JSONObject jData = jpitem.getJSONObject(j);
                bean.setName(jData.optString("ProductName"));
                bean.setQty(jData.optInt("Productqty"));
                bean.setProductPrice(jData.optString("ProductPrice"));
                bean.setProductImage(jData.optString("ProductImage"));
                productListArray.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        productList.setAdapter(new MyOrderProductAdapter(productListArray,Mcontext));
        heder_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            b.dismiss();
            }
        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
