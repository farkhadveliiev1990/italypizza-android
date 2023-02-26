package com.latitude22.homemdopao.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anuved on 10-08-2016.
 */
public class AboutUsFragment extends android.support.v4.app.Fragment {

    ListView product_list;
    ProgressDialog progress;
    static Context Mcontext;
    TextView aboutUs_text;
    static android.support.v4.app.FragmentManager fm;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new AboutUsFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.about_us_fragment, container, false);

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_aboutus);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Sobre Nós");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        aboutUs_text = (TextView) v.findViewById(R.id.textView5);


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
        getAboutUs();
        return v;

    }

    private void getAboutUs()
    {
        if(InternetConnection.isInternetOn(getActivity()))
        {
            showProgress();

            Ion.with(getActivity()).load(Constants.BASE_URL + "webservice/get_pages?pagename=about_us")
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("result", result + "");
                    //hideProgress();
                    if (e == null) {
                        if (UtilityMethod.isStringNullOrBlank(result)) {
                            hideProgress();
                            UtilityMethod.alertforServerError(Mcontext,"0");
                        } else {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                //  String msg = jdata.getString("msg");
                                if (status.equals("1")) {
                                    JSONObject jobj = jdata.getJSONObject("pagedetail");
                                  //  JSONObject Jobject = jdata.get("pagedetail");
                                    String aboutUs = jobj.getString("PageDescription");
                                  // hideProgress();
                                    aboutUs_text.setText(Html.fromHtml(aboutUs));
                                    hideProgress();
                                }
                                else {
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }

                }
            });
        }
        else
        {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

            hideProgress();
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
}
