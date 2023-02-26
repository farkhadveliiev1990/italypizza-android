package com.latitude22.homemdopao;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.firebase.appindexing.Action;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.appindexing.internal.Thing;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Bean.Userdetail;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.yinglan.keyboard.HideUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    ActionBar actionBar;
    Button bt_login;
    Dialog category_dialog;
    EditText et_login_mail, et_login_pass;
    SessionManager sm;
    ProgressDialog progress;
    TextView login_register;
    Context Mcontext;
    EditText emial_id;
    TextView forgot_pass;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideUtil.init(LoginActivity.this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        TypefaceHelper.typeface(this);
        actionBar = getSupportActionBar();
//        actionBar.hide();

        Mcontext = this;
        sm = new SessionManager(Mcontext);
        bt_login = (Button) findViewById(R.id.bt_login);

        et_login_mail = (EditText) findViewById(R.id.et_login_mail);
        et_login_pass = (EditText) findViewById(R.id.et_login_pass);
        forgot_pass = (TextView) findViewById(R.id.forgot_pass);


        login_register = (TextView) findViewById(R.id.login_register);

        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                //finish();

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFilter();
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isStringNullOrBlank(et_login_mail.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Por Introduza o seu Email", Toast.LENGTH_SHORT).show();

                } else if (!Utility.checkEmail(et_login_mail.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "Por Introduza válido Email", Toast.LENGTH_SHORT).show();
                }
                else if (Utility.isStringNullOrBlank(et_login_pass.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Por Introduza a palavra-passe", Toast.LENGTH_SHORT).show();
                }
                else {
                    //  HashMap<String, Object> postParams = new HashMap<String, Object>();
                    //  postParams.put("Email", "" + et_login_mail.getText().toString());
                    // postParams.put("Phone", "" + et_login_mail.getText().toString());
                    // showProgress();
                    //  new Logintask(new loginlistner(), mContext, postParams);


                    getLoginFromServer();
                    // showProgress();

                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void getLoginFromServer() {
        if (InternetConnection.isInternetOn(this)) {
            showProgress();
            Ion.with(LoginActivity.this).load(Constants.BASE_URL + "webservice/login")
                    .setBodyParameter("email", et_login_mail.getText().toString())
                    .setBodyParameter("pwd", et_login_pass.getText().toString())
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    Log.d("result", result + "");
                    hideProgress();
                    if (e == null)
                    {
                        try
                        {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if (jdata.has("msg")) {
                                String msg = jdata.getString("msg");
                            }
                            //String  msg=jdata.getString("msg");
                            if (status.equals("1")) {
                                JSONObject Mobj = jdata.getJSONObject("Userdetails");



                                Userdetail detail = new Userdetail();
                                detail.setId(Mobj.getString("id"));
                                detail.setUserTitle(Mobj.getString("UserTitle"));
                                detail.setUserGender(Mobj.getString("UserGender"));
                                detail.setUserDOB(Mobj.getString("UserDOB"));
                                detail.setProfileImage(Mobj.getString("UserProfileImage"));
                                detail.setUserAddress(Mobj.getString("UserAddress"));
                                detail.setUserMobileno(Mobj.getString("UserMobileNo"));
                                detail.setUserDeliveryNote(Mobj.getString("UserDeliveryNote"));
                                detail.setUserName(Mobj.getString("UserFullName"));
                                detail.setPassword(Mobj.getString("password"));
                                detail.setEmail(Mobj.getString("email"));
                                detail.setActivated(Mobj.getString("activated"));
                                detail.setBanned(Mobj.getString("banned"));
                                detail.setBannedReason(Mobj.getString("ban_reason"));
                                detail.setNewPassKey(Mobj.getString("new_password_key"));
                                detail.setNewPasswordRequested(Mobj.getString("new_password_requested"));
                                detail.setNewEmail(Mobj.getString("new_email"));
                                detail.setNewEmailKey(Mobj.getString("new_email_key"));
                                detail.setLastIp(Mobj.getString("last_ip"));
                                detail.setLastLogin(Mobj.getString("last_login"));
                                detail.setUserType(Mobj.getString("usertype"));
                                detail.setSubscriptionId(Mobj.getString("SubscriptionID"));
                                detail.setCreated(Mobj.getString("created"));
                                detail.setModified(Mobj.getString("modified"));
                                sm.save(detail);
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                et_login_mail.setText(null);
                                et_login_pass.setText(null);

                                finish();
                                hideProgress();

                            } else {
                                hideProgress();
                                Toast.makeText(LoginActivity.this, jdata.getString("msg")+"", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Erro Desconhecido" , Toast.LENGTH_LONG).show();
                            hideProgress();
                        }
                    }
                }
            });
        } else
        {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }

    }
    public void onForgorpassword(String emailId) {
        if (InternetConnection.isInternetOn(this)) {
            showProgress();
            Ion.with(LoginActivity.this).load(Constants.BASE_URL + "webservice/forget_password")
                    .setBodyParameter("email", emailId)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (e == null) {
                        try {
                            hideProgress();
                            JSONObject mObj = new JSONObject(result);
                            String Status = mObj.optString("status");
                            String message = mObj.optString("msg");
                            if (Status.equals("1")) {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ae) {
                            hideProgress();
                            Toast.makeText(LoginActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        hideProgress();
                        Toast.makeText(LoginActivity.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }

    }

    public void SelectFilter() {
        category_dialog = new Dialog(this);
        category_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  category_dialog.setContentView(R.layout.filtter);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dislogView = inflater
                .inflate(R.layout.email_dialog, null);

        category_dialog.setContentView(dislogView);
        category_dialog.setCancelable(false);

        TextView cancel_Catgory = (TextView) dislogView.findViewById(R.id.cancel_Catgory);
        Button submit = (Button) dislogView.findViewById(R.id.bt_sbt);
        emial_id = (EditText) dislogView.findViewById(R.id.emial_id);

      /*  date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment = new DatePickerFragment() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        super.onDateSet(view, year, monthOfYear, dayOfMonth);
                        date_pick.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                };
                datePickerFragment.show(getSupportFragmentManager(),"initialdate");
            }
        });
*/
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = emial_id.getText().toString();
                if (Utility.isStringNullOrBlank(emial_id.getText().toString())) {
                    Utility.showToast("Por Introduza o seu Email", LoginActivity.this);
                } else if (!Utility.checkEmail(emial_id.getText().toString())) {
                    Utility.showToast("Por Introduza válido Email", LoginActivity.this);
                } else {
                    // showProgress();
                    onForgorpassword(email);
                    category_dialog.dismiss();
                }

            }
        });

        cancel_Catgory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category_dialog.dismiss();
            }
        });

        category_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        category_dialog.show();
    }
  /*  private Date getDateObject(String date) {
        try {

            // This object can interpret strings representing dates in the format MM/dd/yyyy
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

            // Convert from String to Date

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr=date;
            Date date1 = inputFormat.parse(inputDateStr);
            String outputDateStr = outputFormat.format(date1);
            return date1;
        } catch (Exception ex) {
            Log.e("", " date exception :: " + ex);
            return null;
        }
    }
    */

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    public Action getIndexApiAction() {
//        Thing object = new Thing.Builder()
//                .setName("Login Page") // TODO: Define a title for the content shown.
//                // TODO: Make sure this auto-generated URL is correct.
//                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
//                .build();
//        return new Action.Builder(Action.TYPE_VIEW)
//                .setObject(object)
//                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
//                .build();
//    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        //AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
