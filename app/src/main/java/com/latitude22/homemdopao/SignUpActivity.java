package com.latitude22.homemdopao;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.PostalCodeAdapter;
import com.latitude22.homemdopao.Bean.PostalCodeBean;
import com.yinglan.keyboard.HideUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button bt_signup;
    String id, postal_code;
    Dialog postalCode_dialog;
    ArrayList<PostalCodeBean> arrayList;
    ProgressDialog progress;
    private ArrayAdapter<String> adapter;
    public static  AutoCompleteTextView textView_postal;
    EditText et_signup_contact, et_signup_email, et_signup_ussername, et_signup_pass, et_signup_confrmpass, et_signup_address, et_signup_nif;
    String regex = "^[0-9]{4}(?:-[0-9]{3})?$";
    Matcher matcher;
    Pattern pattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideUtil.init(SignUpActivity.this);
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        actionBar = getSupportActionBar();
        //    actionBar.hide();
        // header_img = (ImageView) findViewById(R.id.header_img);
        et_signup_ussername = (EditText) findViewById(R.id.et_signup_ussername);
        et_signup_ussername.requestFocus();
        et_signup_contact = (EditText) findViewById(R.id.et_signup_contact);
        et_signup_email = (EditText) findViewById(R.id.et_signup_email);
        textView_postal = (AutoCompleteTextView) findViewById(R.id.et_signup_postal);
        et_signup_pass = (EditText) findViewById(R.id.et_signup_pass);
        et_signup_confrmpass = (EditText) findViewById(R.id.et_signup_confrmpass);
        et_signup_address = (EditText) findViewById(R.id.et_signup_address);
        et_signup_nif = (EditText) findViewById(R.id.et_signup_nif);
        bt_signup = (Button) findViewById(R.id.bt_signup);

        pattern = Pattern.compile(regex);


        textView_postal.addTextChangedListener(new TextWatcher() {

            int length_before = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length_before = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length_before < s.length()) {
                    if (s.length() == 4)
                        s.append("-");
                    if (s.length() > 4) {
                        if (Character.isDigit(s.charAt(4)))
                            s.insert(4, "-");
                    }
                }
            }
        });
        textView_postal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // String s = textView_postal.getText().toString();

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                matcher = pattern.matcher(textView_postal.getText().toString());
                if (hasFocus) {
                    textView_postal.setTextColor(getResources().getColor(R.color.black));
                    textView_postal.setBackgroundResource(R.color.colorAccent);
                } else {
                    if (!matcher.matches()) {
                        textView_postal.setTextColor(getResources().getColor(R.color.red));
                        // textView_postal.setTypeface(null, Typeface.BOLD);
                        textView_postal.setBackgroundResource(R.drawable.errorborder);
                    }
                    // Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        arrayList = new ArrayList<>();

        getPostalcode();
        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //  boolean mCode = getCode(textView_postal.getText().toString());
               matcher = pattern.matcher(textView_postal.getText().toString());
                if (et_signup_ussername.getText().toString().trim().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Nome Completo deve ser preenchido", Toast.LENGTH_SHORT).show();
                } else if (!Utility.checkEmail(et_signup_email.getText().toString().trim())) {
                    Utility.showToast("Por Introduza válido Email", SignUpActivity.this);
                } else if (et_signup_address.getText().toString().trim().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Por Introduza Morada Completa", Toast.LENGTH_SHORT).show();
                }else if (textView_postal.getText().toString().trim().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Por Introduza Código Postal", Toast.LENGTH_SHORT).show();
                } /*else if (!mCode) {
                    Toast.makeText(SignUpActivity.this, "Digite o código postal válido", Toast.LENGTH_SHORT).show();
                }*/  else if (et_signup_contact.getText().toString().trim().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Por Introduza Telemovel", Toast.LENGTH_SHORT).show();
                } else if (et_signup_pass.getText().toString().trim().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Palavra-passe vazia", Toast.LENGTH_SHORT).show();
                } else if (!(et_signup_pass.getText().toString().trim().equals("" + et_signup_confrmpass.getText().toString().trim()))) {
                    Toast.makeText(SignUpActivity.this, "As palavras-passe não coincidem", Toast.LENGTH_SHORT).show();
                } else if (!matcher.matches()) {
                    Toast.makeText(getApplicationContext(), "Por Introduza código postal(xxxx-xxx)", Toast.LENGTH_LONG).show();
                } else {
                   /* String postalcode = textView_postal.getText().toString();

                    boolean code = false;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getPostalCode().equals(postalcode)) {
                            // id = String.valueOf(arrayList.get(i).getPostalId());
                            //id = String.valueOf(arrayList.get(i).getPostalId());
                            code = true;
                        }
                    }*/


                   getSignUpToServer();
                }

            }


        });


    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean getCode(String mCode)
    {
        for(int i=0;i<arrayList.size();i++)
        {
            if(mCode.equals(arrayList.get(i).getPostalCode())) {
               Log.i("postal", mCode + " postal " + arrayList.get(i).getPostalCode());
                return true;
            }
        }
        return false;
    }


    private void getPostalcode() {
        if (InternetConnection.isInternetOn(this)) {
            showProgress();

            Ion.with(SignUpActivity.this).load(Constants.BASE_URL + "webservice/getpostalcode")
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                   // Log.d("result", result + "");
                    //hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            //  String msg = jdata.getString("msg");
                            if (status.equals("1")) {

                                JSONArray jarray = jdata.getJSONArray("postalcode");
                                for (int i = 0; i < jarray.length(); i++) {
                                    JSONObject jsonData = jarray.getJSONObject(i);
                                    if(jsonData.optString("PostalStatus").equals("1")) {
                                        PostalCodeBean bean = new PostalCodeBean();
                                        bean.setPostalCode(jsonData.optString("PostalCode"));
                                        bean.setPostalId(jsonData.optInt("PostalID"));
                                        bean.setPostalStatus(Integer.parseInt(jsonData.optString("PostalStatus")));
                                        bean.setPostalZoneid(jsonData.optInt("PostalZoneID"));
                                        bean.setPostalCreatedDate(jsonData.optInt("PostalCreatedDate"));
                                        arrayList.add(bean);
                                    }
                                    setAdapter(arrayList);
                                    hideProgress();
                                }

                            } else {
                                hideProgress();
                                Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }

                    } else {
                        //  Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        else {
            Toast.makeText(SignUpActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void setAdapter(final ArrayList<PostalCodeBean> arrayList) {
        final PostalCodeAdapter adapter = new PostalCodeAdapter(arrayList, SignUpActivity.this);
        textView_postal.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //when autocomplete is clicked
        textView_postal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (PostalCodeAdapter.arrayList != null && PostalCodeAdapter.arrayList.size() >= position) {
                    String PostalCode = PostalCodeAdapter.arrayList.get(position).getPostalCode();
                    textView_postal.setText(PostalCode);
                }
            }
        });
    }

    private void getSignUpToServer() {
        if (InternetConnection.isInternetOn(this)) {
            showProgress();

            for (int i = 0; i < arrayList.size(); i++) {
                if (textView_postal.getText().toString().equals(arrayList.get(i).getPostalCode())) {
                    // id = String.valueOf(arrayList.get(i).getPostalId());
                    id = String.valueOf(arrayList.get(i).getPostalId());
                } else {

                }
            }
            Ion.with(SignUpActivity.this).load(Constants.BASE_URL + "webservice/registration")
                    .setBodyParameter("fullname", et_signup_ussername.getText().toString())
                    .setBodyParameter("contactno", et_signup_contact.getText().toString())
                    .setBodyParameter("email", et_signup_email.getText().toString())
                    .setBodyParameter("password", et_signup_pass.getText().toString())
                    .setBodyParameter("postalcode", textView_postal.getText().toString())
                    .setBodyParameter("address", et_signup_address.getText().toString())
                    .setBodyParameter("taxno", et_signup_nif.getText().toString())
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("result", result + "");
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            String msg = jdata.getString("msg");

                            if (status.equals("1")) {


                                Toast.makeText(SignUpActivity.this, "" + jdata.getString("msg"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();


                                /*  String postalcode = textView_postal.getText().toString();

                                boolean code = false;
                                for (int i = 0; i < arrayList.size(); i++) {
                                    if (arrayList.get(i).getPostalCode().equals(postalcode)) {
                                        // id = String.valueOf(arrayList.get(i).getPostalId());
                                        //id = String.valueOf(arrayList.get(i).getPostalId());
                                        code = true;
                                    }
                                }

                                if (!code) {
                                    SelectFilter();
                                    // getSignUpToServer();


                                }
                                else
                                {
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

                                    Toast.makeText(SignUpActivity.this, ""+jdata.getString("msg"), Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                    //  getSignUpToServer();
                                }*/
                            } else
                            {
                                Toast.makeText(SignUpActivity.this, "" + jdata.getString("msg"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            hideProgress();
                        }
                    }
                }
            });
            hideProgress();
        } else
        {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
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
            progress = new ProgressDialog(this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }

    public void SelectFilter() {
        // postalCode_dialog = new AlertDialog.Builder(this).create();
        postalCode_dialog = new Dialog(this);
        postalCode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  category_dialog.setContentView(R.layout.filtter);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dislogView = inflater
                .inflate(R.layout.postal_code_alert, null);

        postalCode_dialog.setContentView(dislogView);
        postalCode_dialog.setCancelable(true);


        final TextView cancel_Catgory = (TextView) dislogView.findViewById(R.id.cancel_Catgory);

        cancel_Catgory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postalCode_dialog.dismiss();
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        postalCode_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        postalCode_dialog.show();
    }

    private void sendPostalCode(String emailid, String postalCode)
    {
        if (InternetConnection.isInternetOn(this))
        {
            showProgress();

            Ion.with(SignUpActivity.this).load(Constants.BASE_URL + "webservice/postalcodesubscription")
                    .setBodyParameter("postalcode", postalCode)
                    .setBodyParameter("email", emailid)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result)
                {
                    Log.d("result", result + "");
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if (jdata.has("msg"))
                            {
                                String msg = jdata.getString("msg");
                            }
                            //String  msg=jdata.getString("msg");
                            if (status.equals("1"))
                            {
                                //Toast.makeText(SignUpActivity.this, "Send your PostalCode succesfully", Toast.LENGTH_SHORT).show();
                                hideProgress();
                                et_signup_contact.setText(null);
                                et_signup_ussername.setText(null);
                                et_signup_email.setText(null);
                                et_signup_pass.setText(null);
                                textView_postal.setText(null);
                                et_signup_confrmpass.setText(null);
                                et_signup_address.setText(null);
                            }
                            else
                            {
                                Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1)
                        {
                            e1.printStackTrace();
                            hideProgress();
                        }

                    }
                }
            });
            hideProgress();
        }
        else
        {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
        }
    }
}






