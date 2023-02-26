package com.latitude22.homemdopao.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.FaqListAdaptwer;
import com.latitude22.homemdopao.Bean.FaqListBean;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anuved on 10-08-2016.
 */
public class FaqFragment extends android.support.v4.app.Fragment {

    public static int selectedindex = 0;
    static Context Mcontext;
    static android.support.v4.app.FragmentManager fm;
    ListView faqspinner;
    ArrayList<FaqListBean> faqlist;
    EditText et_contact_name, et_contact_text;
    // public  static TextView FaqAnswer;
    FaqListAdaptwer adapter;
    ProgressDialog progress;
    Button detail_et_mail;
    EditText etSearch_list;
    private boolean flag_loading = false;
    private boolean isCanDownloadMore = true;
    private int cntPage = 0;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new FaqFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.faqfragment, container, false);
        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_faq);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("FAQ'S");
        MainActivity.main_cart_quantity.setVisibility(View.GONE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        faqlist = new ArrayList<>();
        faqspinner = (ListView) v.findViewById(R.id.faqspinner);
        etSearch_list = (EditText) v.findViewById(R.id.etSearch_list);

        faqspinner.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    if (!flag_loading) {
                        if(isCanDownloadMore){
                            flag_loading = true;
                            cntPage = cntPage + 1;
                            getDataFromServer(cntPage);
                        }

                    }
                }
            }
        });


        // FaqAnswer= (TextView) v.findViewById(R.id.FaqAnswer);
        /* et_contact_name= (EditText) v.findViewById(R.id.et_contact_name);
        et_contact_text= (EditText) v.findViewById(R.id.et_contact_text);
        detail_et_mail= (Button) v.findViewById(R.id.detail_et_mail);
        detail_et_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.isStringNullOrBlank(et_contact_text.getText().toString())){
                    Utility.showToast("Please enter Email id",Mcontext);
                }else if(Utility.isStringNullOrBlank(et_contact_text.getText().toString())){
                    Utility.showToast("Please enter Description",Mcontext);
                }else{
                    AddQuestion();
                    showProgress();
                }
            }
        });
        */

        MainActivity.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(getActivity(),MainActivity.class);
                i.putExtra("position","4");
                getActivity().startActivity(i);
                getActivity().finish();*/
            }
        });
        MainActivity.icon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getDataFromServer(cntPage);
        //  showProgress();
        etSearch_list.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter.getFilter().filter(s.toString());
                if (s.length() <= 0) {
                    adapter = new FaqListAdaptwer(faqlist, getActivity(), "1");
                    faqspinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                // FaqListAdaptwer.FaqAnswer.setText(faqlist.get(position).getFaqAnswer().trim());

            }
        });

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
        return v;
    }

    public void getDataFromServer(final int pagenum) {
        String ApiData = Constants.BASE_URL + "webservice/get_faq";
        if (InternetConnection.isInternetOn(getActivity())) {
            if(pagenum < 1) showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO", String.valueOf(pagenum))
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(getActivity(), "0");
                    } else {
                        //Log.e("test", result + "\n" + pagenum);
                        hideProgress();
                        if (e == null) {

                            try {
                                JSONObject jdata = new JSONObject(result);

                                String status = jdata.getString("status");
                                if (status.equals("1")) {
                                    JSONArray jitem = jdata.getJSONArray("faqlist");
                                    if(jitem.length() < 10)
                                        isCanDownloadMore = false;

                                    for (int i = 0; i < jitem.length(); i++) {
                                        JSONObject jsonData = jitem.getJSONObject(i);
                                        FaqListBean fixedplanbean = new FaqListBean();
                                        fixedplanbean.setFaqID(jsonData.optString("FaqID"));
                                        fixedplanbean.setFaqAnswer(jsonData.optString("FaqAnswer"));
                                        fixedplanbean.setFaqQuestion(jsonData.optString("FaqQuestion"));
                                        faqlist.add(fixedplanbean);
                                    }
                                    setAdapter();
                                    hideProgress();
                                } else {
                                    hideProgress();
                                    Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e1) {
                                Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }

                        } else {
                            hideProgress();
                            Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    /*
        public void AddQuestion() {
            String ApiData = "http://latitude22.com/homemdopao/webservice/add_faq";

            Ion.with(getActivity()).load(ApiData).setBodyParameter("email", et_contact_name.getText().toString())
                    .setBodyParameter("question", et_contact_text.getText().toString())
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
                                Toast.makeText(getActivity(), ""+jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e1) {
                            Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
        */
    public void setAdapter() {
        if (faqlist != null & faqlist.size() > 0) {
            adapter = new FaqListAdaptwer(faqlist, getActivity(), "0");
            faqspinner.setAdapter(adapter);
            faqspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedindex = position;
                    adapter.notifyDataSetChanged();
                }


            });

        }
    }

    public void hideProgress() {
        flag_loading = false;
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
            progress.setMessage("Loading...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

}