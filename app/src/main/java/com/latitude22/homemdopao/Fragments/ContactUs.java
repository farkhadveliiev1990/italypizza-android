package com.latitude22.homemdopao.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.UtilityMethod;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anuved on 10-08-2016.
 */
public class ContactUs extends android.support.v4.app.Fragment {
    ListView product_list;
    SessionManager sm;
    static Context Mcontext ;
    ProgressDialog progress;
    EditText    et_contact_name,et_contact_mail,et_login_mail,et_contact_text;
    Button bt_contact_sned;
    static android.support.v4.app.FragmentManager  fm;
    public static android.support.v4.app.Fragment getInstance(Context Mcntx,FragmentManager FM){
        Mcontext = Mcntx;
        fm=FM;
        android.support.v4.app.Fragment frag = new ContactUs();
        return frag;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.contact_us_fragment, container, false);
       // header_img= (ImageView)v.findViewById(R.id.header_img);
        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_contact);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Contacte-nos");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);



        bt_contact_sned = (Button) v.findViewById(R.id.bt_contact_sned);
      /*  et_contact_name = (EditText) v.findViewById(R.id.et_contact_name);
        et_contact_mail  = (EditText) v.findViewById(R.id.et_contact_mail);*/
       // et_login_mail  = (EditText) v.findViewById(R.id.et_login_mail);
        et_contact_text = (EditText) v.findViewById(R.id.et_contact_text);
        sm = new SessionManager(getActivity());
      //  LinearLayout contactus_toolbar = (LinearLayout) v.findViewById(R.id.toolbar);
        LinearLayout toolbar = (LinearLayout)v.findViewById(R.id.toolbar);
      //  ImageView img = (ImageView) toolbar.findViewById(R.id.main_icon);
       // img.setVisibility(v.GONE);
        //ImageView cart_img = (ImageView) toolbar.findViewById(R.id.cart);
       // cart_img.setVisibility(v.GONE);
       // toolbar.setVisibility(v.GONE);
        /*et_contact_text.setFilters(new InputFilter[]
                { filter
                });
        et_contact_name.setFilters(new InputFilter[]
                { filter
                });
                */

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

                getConatctNo();

            }
        });

        bt_contact_sned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           /*     if (!Utility.checkEmail(et_login_mail.getText().toString().trim())) {
                    Utility.showToast("Please enter valid email id", getActivity());
                }
                else if (et_login_mail.getText().toString().trim().equals("")) {
                    Utility.showToast("Please enter email id", getActivity());
                }
                else if (et_contact_mail.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Please enter Contact no", Toast.LENGTH_SHORT).show();
                } else if (et_contact_name.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Username Can't be empty", Toast.LENGTH_SHORT).show();
                }
                else if (et_contact_name.getText().toString().trim().equals(' '))
                {
                    Toast.makeText(getActivity(), "Enter Username", Toast.LENGTH_SHORT).show();
                }
                else if (et_contact_text.getText().toString().trim().equals("")) {

                    Toast.makeText(getActivity(), "Enter Text", Toast.LENGTH_SHORT).show();
                }*/
                if (et_contact_text.getText().toString().equals(' '))
                {
                    Toast.makeText(getActivity(), "escreva a sua mensagem", Toast.LENGTH_SHORT).show();
                }
                else{
                    setdata();
                }

            }
        });

        return v;
    }
    InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        }
    };

    public void setdata(){
        if(InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(Mcontext).load(Constants.BASE_URL + "webservice/contactus")
                    .setBodyParameter("contactname", sm.getUserFullName())
                    .setBodyParameter("contactphone",sm.getUsercontact())
                    .setBodyParameter("contactemail", sm.getEmail())
                    .setBodyParameter("contactmessage", et_contact_text.getText().toString())
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("result", result + "");
                    hideProgress();
                    if (e == null) {
                        if (UtilityMethod.isStringNullOrBlank(result)) {
                            hideProgress();
                            UtilityMethod.alertforServerError(Mcontext,"0");
                        } else {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                String msg = jdata.getString("msg");
                                if (status.equals("1")) {
                                    Toast.makeText(Mcontext, "Guardado com Sucesso", Toast.LENGTH_SHORT).show();
                                    et_contact_text.setText(null);

                                } else {
                                    Toast.makeText(Mcontext, "" + jdata.getString("msg"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "Problema de Rede", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
            else
            {
                Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
            }
    }


    private void getConatctNo()
    {
        if(InternetConnection.isInternetOn(getActivity()))
        {
            showProgress();

            Ion.with(getActivity()).load(Constants.BASE_URL + "webservice/get_contactusnumber")
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
                                    String contactNo = jdata.getString("contactno");
                                    String number = "tel:" + contactNo;
                                    hideProgress();
                                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                                    startActivity(callIntent);
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
