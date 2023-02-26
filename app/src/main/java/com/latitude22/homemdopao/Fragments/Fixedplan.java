package com.latitude22.homemdopao.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.FixedPlanAdapter;
import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.DetailSubscribeActivity;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anuved on 19-08-2016.
 */
public class Fixedplan extends Fragment {

    ArrayList<FixedplanBean> fixedplanlist;
    ListView listView;
    FixedplanBean fixedplanItem;
    ProgressDialog progress;
    TextView img_home_appname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1, container, false);
        TypefaceHelper.typeface(view);
        ImageView img_tab1 = (ImageView) view.findViewById(R.id.img_tab1);
        listView = (ListView) view.findViewById(R.id.list_item_tab1);

        fixedplanlist = new ArrayList<FixedplanBean>();
        img_home_appname = (TextView) view.findViewById(R.id.img_home_appname);


        MainActivity.main_icon.setVisibility(View.VISIBLE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_cart);
        MainActivity.header_text.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.VISIBLE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        fixedplanItem = new FixedplanBean();

      //  Toast.makeText(getActivity(), "Hello how r u?", Toast.LENGTH_SHORT).show();

        getDataFromServer();
       // showProgress();
        return view;
    }
    public void getDataFromServer()
    {
        String ApiData = Constants.BASE_URL + "webservice/get_plans?";
        if(InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO", "0")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {

                    Log.d("StoreResult", result + "");
                    hideProgress();
                    if (e == null) {
                        if (UtilityMethod.isStringNullOrBlank(result)) {
                            hideProgress();
                            UtilityMethod.alertforServerError(getActivity(),"0");
                        }
                        else {
                            try {
                                hideProgress();
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                if (status.equals("1")) {

                                    JSONArray jitem = jdata.getJSONArray("Productlist");

                                    for (int i = 0; i < jitem.length(); i++) {


                                        JSONObject jsonData = jitem.getJSONObject(i);
                                        FixedplanBean fixedplanbean = new FixedplanBean();


                                        fixedplanbean.setId(jsonData.optInt("FixedplanID"));
                                        fixedplanbean.setBreadName(jsonData.optString("FixedplanName"));
                                        fixedplanbean.setPrice(jsonData.optString("FixedplanPrice"));
                                        fixedplanbean.setDescription(jsonData.optString("FixedplanDescription"));
                                        fixedplanbean.setImage(jsonData.optString("FixedplanImage"));
                                        fixedplanbean.setSubscriptionType(jsonData.optString("SubscriptionName"));
                                        fixedplanbean.setSubscriptionId(jsonData.optString("SubscriptionID"));

                                        JSONArray jpitem = jsonData.getJSONArray("product");
                                        ArrayList<FixedPlanProductBean> productlist = new ArrayList<FixedPlanProductBean>();
                                        JSONArray marray = new JSONArray();
                                        for (int j = 0; j < jpitem.length(); j++) {

                                            FixedPlanProductBean bean = new FixedPlanProductBean();

                                            JSONObject obj = new JSONObject();
                                            JSONObject jData = jpitem.getJSONObject(j);
                                            obj.put("ProductID", jData.optInt("ProductID"));
                                            obj.put("ProductName", jData.optString("ProductName"));
                                            obj.put("ProductPrice", jData.optString("ProductPrice"));
                                            marray.put(obj);
                                            bean.setFixedProductId(jData.optInt("ProductID"));
                                            bean.setFixedProductName(jData.optString("ProductName"));
                                            bean.setFixedProductPrice(jData.optString("ProductPrice"));
                                            productlist.add(bean);
                                        }
                                        fixedplanbean.setProductlist(productlist);
                                        fixedplanbean.setFixedpalnproduct(marray.toString());
                                        fixedplanlist.add(fixedplanbean);
                                    }

                                    setAdapter();
                                    hideProgress();
                                } else {
                                    hideProgress();
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e1) {

                                //   Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }
                        }
                    }
                    else {
                        hideProgress();
                        // Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
            else
            {
                Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

            }
    }

    public void setAdapter() {
        //  breadShopList = (ArrayList<BreadShopData>) ShopinformationBean;
        if ( fixedplanlist != null &  fixedplanlist.size() > 0) {
            listView.setAdapter(new FixedPlanAdapter(fixedplanlist, getActivity()));
          //  Toast.makeText(getActivity(), "Hello"+fixedplanlist.get(0).getProductname(), Toast.LENGTH_SHORT).show();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Storename StorAddress
                    //to send data from next activity
                    Intent intent = new Intent(getActivity(), DetailSubscribeActivity.class);
                    intent.putExtra("FixedplanId",fixedplanlist.get(i).getId());
                    intent.putExtra("productlist",fixedplanlist.get(i).getFixedpalnproduct());
                    intent.putExtra("Productname",fixedplanlist.get(i).getProductname());
                    intent.putExtra("Productprice",fixedplanlist.get(i).getProductPrice());
                    intent.putExtra("FixedPlanPrice",fixedplanlist.get(i).getPrice());
                    intent.putExtra("ProductDescription",fixedplanlist.get(i).getDescription());

                    //  intent.putExtra("Productid", fixedplanlist.get(i).get);
                    startActivity(intent);
                    //getActivity().finish();
                }
            });
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
    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }
}


