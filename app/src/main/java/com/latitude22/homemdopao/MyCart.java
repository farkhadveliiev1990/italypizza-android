package com.latitude22.homemdopao;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Update;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.PaymentAdapter;
import com.latitude22.homemdopao.Adapter.SubscriptionSpinAdapter;
import com.latitude22.homemdopao.Bean.SubscriptionBean;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyCart extends AppCompatActivity {
    public static TextView cart_total_amount;
    public EditText editDays, editDate, editTime;
    public double payTotal;
    ListView cart_item_list;
    ImageView header_img;
    ArrayList<SubscriptionBean> Subscrption;
    Spinner spinner;
    String inputDate;
    Dialog time_dialog;
    Date pickedDate, currentDate;
    String SubcriptionCustomID;
    Context mcontxt;
    ProgressDialog progress;
    Button PayPayment, ClearCart;
    String SubscriptionId;
    SessionManager sm;
    int setDays = 1;
    AlertDialog b, c;
    TextView tv_done;
    AlertDialog.Builder dialogBuilder;
    String UserId;
    LinearLayout linear_one_shot, linear_layout_edt_days;
    Context Mcontext;
    SubscriptionSpinAdapter spinAdpter;
    ArrayAdapter<String> dataAdapter;
    ArrayList<AddCart> addCarts;
    int Flag = 0;
    JSONArray marray;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment_);
        TypefaceHelper.typeface(this);
        mcontxt = this;
        RelativeLayout detail_header = (RelativeLayout) findViewById(R.id.detail_header);
        cart_item_list = (ListView) findViewById(R.id.cart_item_list);
        header_img = (ImageView) findViewById(R.id.header_img);
        cart_total_amount = (TextView) findViewById(R.id.cart_total_amount);
        PayPayment = (Button) findViewById(R.id.PayPayment);
        spinner = (Spinner) findViewById(R.id.spin_payment);
        ClearCart = (Button) findViewById(R.id.ClearCart);
        // PyaAmmount
        Mcontext = this;

        sm = new SessionManager(Mcontext);
        UserId = sm.getuserid();
        spinner.setPrompt("Select Subscription");


        PayPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cart_total_amount.getText().toString().equals("00.00") || cart_total_amount.getText().toString().equals("0.0") || cart_total_amount.getText().toString().equals("0")) {
                    // PayPayment.setClickable(false);
                    Toast.makeText(MyCart.this, "Adicionar Produto", Toast.LENGTH_LONG).show();
                } else if (Utility.isStringNullOrBlank(sm.getuserid())) {
                    Intent i = new Intent(MyCart.this, LoginActivity.class);
                    // i.putExtra("PyaAmmount","3");
                    startActivity(i);
                    finish();
                } else {
                    showDialog();

                    // checkSubscription(UserId);
                }
            }
        });
        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
        cart_item_list.setAdapter(new PaymentAdapter(addCarts, this));
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyCart.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        ClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().from(AddCart.class).execute();
                cart_total_amount.setText("0.0");
                addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
            }
        });
        getDataFromServer();
        //  showProgress();
        // Drop down layout style - list view with radio button
    }

    public void getDataFromServer() {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/get_subscription";
            Ion.with(this).load(ApiData).setBodyParameter("PNO", "0")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(MyCart.this, "1");
                    } else {
                        Log.d("StoreResult", result + "");
                        hideProgress();
                        if (e == null) {
                            try {
                                Subscrption = new ArrayList<SubscriptionBean>();
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                if (status.equals("1")) {
                                    JSONArray jitem = jdata.getJSONArray("Subscriptionlist");
                                    for (int i = 0; i < jitem.length(); i++) {
                                        JSONObject mobj = jitem.getJSONObject(i);
                                        SubscriptionBean bean = new SubscriptionBean();
                                        bean.setSubscriptionID(mobj.optString("SubscriptionID"));
                                        bean.setSubscriptionName(mobj.optString("SubscriptionName"));
                                        bean.setNoofdays(mobj.optString("SubscriptionDays"));
                                        Subscrption.add(bean);
                                    }
                               /* List<String> categories = new ArrayList<String>();
                                for (int j = 0; j < Subscrption.size(); j++) {
                                    categories.add(Subscrption.get(j).getSubscriptionName());
                                }*/

                                    //  dataAdapter = new ArrayAdapter<String>(mcontxt, android.R.layout.simple_spinner_item, categories);
                                    //  dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    // spinner.setAdapter(dataAdapter);
                                    spinAdpter = new SubscriptionSpinAdapter(Subscrption, mcontxt);
                                    spinner.setAdapter(spinAdpter);
                                    //  yourSpinner.setSelection(arrayAdapter.getPosition("Your Desired Text"));
                                    //set yearly selectec at 1st position in spinner
                                    spinner.setSelection((Integer) spinAdpter.getItem(2));
                                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            String SubscriptionName = Subscrption.get(position).getSubscriptionName();
                                            onChangeSubcription(Subscrption.get(position).getSubscriptionID());
                                            // showProgress();
                                            SubscriptionId = Subscrption.get(position).getSubscriptionID();
                                            setDays = 0;
                                            if (SubscriptionId.equals("71")) {
                                                setDays = 1;
                                                showCustomDaysDialog();
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {


                                        }
                                    });

                                    hideProgress();
                                }
                            } catch (JSONException e1) {
                                //  Toast.makeText(MyCart.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }
                        } else {
                            hideProgress();
                            // Toast.makeText(MyCart.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } else {
            //  Toast.makeText(MyCart.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }

    public void insertCustomSubscription(String day) {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertsubscription";
            JSONArray marray = new JSONArray();
            try {
                for (int i = 0; i < addCarts.size(); i++) {
                    JSONObject mobj = new JSONObject();
                    mobj.put("ID", addCarts.get(i).getProductId());
                    marray.put(mobj);
                }
            } catch (Exception ae) {
            }
            if (addCarts.size() > 0) {
                Ion.with(this).load(ApiData).setBodyParameter("days", day).setBodyParameter("productids", "" + marray)
                        .asString().setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgress();
                        if (UtilityMethod.isStringNullOrBlank(result)) {

                            UtilityMethod.alertforServerError(MyCart.this, "1");
                        } else {
                            Log.e("StoreResult", result + "");
                            hideProgress();
                            if (e == null) {
                                try {
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    Log.e("StoreResult",   "SubscriptionID:  " + jdata.getString("SubscriptionID"));
                                    if (status.equals("1")) {
                                        JSONArray jitem = jdata.getJSONArray("Productlist");
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject mobj = jitem.getJSONObject(i);
                                            new Update(AddCart.class)
                                                    .set("price =?", mobj.optDouble("ProductPrice"))
                                                    .where("productId = ?", mobj.optString("ProductID")).execute();
                                            new Update(AddCart.class)
                                                    .set("unitprice =?", mobj.optDouble("ProductUnitPrice"))
                                                    .where("productId = ?", mobj.optString("ProductID")).execute();
                                        }
                                        SubcriptionCustomID = jdata.getString("SubscriptionID");
                                        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                                        cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                                        PriceTotal(addCarts);
                                    }
                                    hideProgress();
                                } catch (Exception e1) {
                                    //   Toast.makeText(MyCart.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            } else {
                                hideProgress();
                                Toast.makeText(MyCart.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            } else {
                hideProgress();
            }
        } else {
            Toast.makeText(MyCart.this, "Verifique a ligação de internet", Toast.LENGTH_LONG).show();
            hideProgress();
        }
    }

    public void onChangeSubcription(String id) {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/change_plan";
            JSONArray marray = new JSONArray();
            try {
                for (int i = 0; i < addCarts.size(); i++) {
                    JSONObject mobj = new JSONObject();
                    mobj.put("ID", addCarts.get(i).getProductId());
                    marray.put(mobj);
                }
            } catch (Exception ae) {
            }


            Log.i("subscription", id + " marray " + marray.toString());
            if (addCarts.size() > 0) {
                Ion.with(this).load(ApiData).setBodyParameter("subscription", id).setBodyParameter("productids", "" + marray)
                        .asString().setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgress();
                        if (UtilityMethod.isStringNullOrBlank(result)) {

                            UtilityMethod.alertforServerError(MyCart.this, "1");
                        } else {
                            Log.d("StoreResult", result + "");
                            hideProgress();
                            if (e == null) {
                                try {
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    if (status.equals("1")) {
                                        JSONArray jitem = jdata.getJSONArray("Productlist");
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject mobj = jitem.getJSONObject(i);
                                            new Update(AddCart.class)
                                                    .set("price =?", mobj.optDouble("ProductPrice"))
                                                    .where("productId = ?", mobj.optString("ProductID")).execute();
                                            new Update(AddCart.class)
                                                    .set("unitprice =?", mobj.optDouble("ProductUnitPrice"))
                                                    .where("productId = ?", mobj.optString("ProductID")).execute();
                                        }
                                        addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                                        cart_item_list.setAdapter(new PaymentAdapter(addCarts, mcontxt));
                                        PriceTotal(addCarts);
                                    }
                                    hideProgress();
                                } catch (Exception e1) {
                                    //   Toast.makeText(MyCart.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            } else {
                                hideProgress();
                                Toast.makeText(MyCart.this, "Erro" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            } else {
                hideProgress();
            }
        } else {
            Toast.makeText(MyCart.this, "Verifique a ligação de internet", Toast.LENGTH_LONG).show();
            hideProgress();
        }
    }

    public void showCustomDaysDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_days_dialog, null);
        dialogBuilder.setView(dialogView);
        b = dialogBuilder.create();
        // dialogBuilder.setTitle("Set No. of Days");


        TextView tv_wants_more = (TextView) dialogView.findViewById(R.id.tv_wants_more);

        Button tv_okay = (Button) dialogView.findViewById(R.id.tv_okay);

        tv_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
            }
        });
        dialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });


        tv_wants_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b.dismiss();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MyCart.this);
                LayoutInflater inflater = MyCart.this.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_days_wantsmore_dialog, null);
                dialogBuilder.setView(dialogView);
                editDays = (EditText) dialogView.findViewById(R.id.editDays);
                Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
                Button btn_done = (Button) dialogView.findViewById(R.id.btn_done);

                c = dialogBuilder.create();
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        c.dismiss();
                    }
                });

                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try {
                            int input_days = Integer.parseInt(editDays.getText().toString());

                            String ed_text = editDays.getText().toString().trim();

                            if (input_days == 0 || input_days == 1 || ed_text.length() <= 0 || ed_text.equals("") || ed_text == null || ed_text.isEmpty()) {
                                Toast.makeText(mcontxt, "Por favor introduza uma entrada diferente", Toast.LENGTH_LONG).show();

                            } else {
                                int getDays = Integer.parseInt(editDays.getText().toString().trim());
                                setDays = getDays;
                                insertCustomSubscription(editDays.getText().toString().trim());
                                c.dismiss();
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(MyCart.this, "Por favor introduza o n. de dias", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                });
                c.show();
            }
        });
        b.show();
    }

    public void getWalletFromServer(final String mTotalAmount) {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet?";
        if (InternetConnection.isInternetOn(MyCart.this)) {
            showProgress();
            Ion.with(MyCart.this).load(ApiData).setBodyParameter("userid", UserId)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(MyCart.this, "0");
                    } else {
                        Log.d("StoreResult", result + "");

                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(MyCart.this, "0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    if (status.equals("1")) {
                                        double mValue = Double.parseDouble(jdata.getString("total"));
                                        double mAmount = Double.parseDouble(mTotalAmount);
                                        Log.i(" array ", mValue + " mValue " + mAmount);
                                        if (mAmount > mValue) {
                                            if (spinner.getSelectedItem().toString().equalsIgnoreCase("3")) { //"Compra Única"

                                                if (setDays == 1) {
                                                    Flag = 1;
                                                    Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                                    intent.putExtra("SubscriptionId", SubscriptionId);
                                                    // intent.putExtra("PyaAmmount",""+payTotal);
                                                    intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                                    intent.putExtra("StartDate", editDate.getText().toString().trim());
                                                    intent.putExtra("days", setDays);
                                                    intent.putExtra("StartTime", editTime.getText().toString().trim());
                                                    intent.putExtra("SetFlag", "1");
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Flag = 0;
                                                    Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                                    intent.putExtra("SubscriptionId", SubcriptionCustomID);
                                                    // intent.putExtra("PyaAmmount",""+payTotal);
                                                    intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                                    intent.putExtra("StartDate", editDate.getText().toString().trim());
                                                    intent.putExtra("days", setDays);
                                                    intent.putExtra("StartTime", editTime.getText().toString().trim());
                                                    intent.putExtra("SetFlag", "0");
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                Flag = 1;
                                                Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                                intent.putExtra("SubscriptionId", SubscriptionId);
                                                // intent.putExtra("PyaAmmount",""+payTotal);
                                                intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                                intent.putExtra("StartDate", editDate.getText().toString().trim());
                                                intent.putExtra("days", setDays);
                                                intent.putExtra("StartTime", editTime.getText().toString().trim());
                                                intent.putExtra("SetFlag", "1");
                                                startActivity(intent);
                                                finish();
                                            }
                                        } else {
                                            Log.i(" array ", " else ");
                                            if (spinner.getSelectedItem().toString().equalsIgnoreCase("3")) { //"Compra Única"
                                                if (setDays == 1) Flag = 1;
                                                else Flag = 0;
                                            } else Flag = 1;

                                            insertOrder(Flag);
                                        }

                                    }
                                    hideProgress();
                                } catch (JSONException e1) {
                                    //  Toast.makeText(MyCart.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(MyCart.this, "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(MyCart.this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    public void insertOrder(int mFlag) {
        if (InternetConnection.isInternetOn(this)) {
            hideProgress();
            showProgress();
            String ApiData = Constants.BASE_URL + "webservice/insertoreder";
            try {
                //  addCarts = (ArrayList<AddCart>) AddCart.AddCart();
                for (int i = 0; i < addCarts.size(); i++) {
                    JSONObject mobj = new JSONObject();
                    mobj.put("ID", "" + addCarts.get(i).getProductId());
                    mobj.put("QTY", "" + addCarts.get(i).getProductquantity());
                    marray.put(mobj);
                }

            } catch (Exception ae) {
            }

            String mDays = "";
            if (mFlag == 0) mDays = setDays + "";

            Log.i(" array ", "userid=" + UserId + "&cartlist=" + marray + "&subscriptioncost=" + cart_total_amount.getText().toString().replace(",", "."));
            Log.i(" array ", "&subscriptionid=" + SubscriptionId + "&days=" + mDays + "&startdate=" + editDate.getText().toString().trim() + "&time=" + editTime.getText().toString().trim());

            Ion.with(this).load(ApiData).setBodyParameter("userid", UserId).setBodyParameter("cartlist", "" + marray)
                    .setBodyParameter("subscriptioncost", "" + cart_total_amount.getText().toString().replace(",", "."))
                    .setBodyParameter("subscriptionid", SubscriptionId)
                    .setBodyParameter("days", mDays).setBodyParameter("startdate", editDate.getText().toString().trim())
                    .setBodyParameter("time", editTime.getText().toString().trim())
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {

                    Log.d("StoreResult", result + " marray " + marray);
                    hideProgress();
                    if (e == null) {
                        try {
                            JSONObject jdata = new JSONObject(result);
                            String status = jdata.getString("status");
                            if (status.equals("1")) {
                                new Delete().from(AddCart.class).execute();
                                Toast.makeText(mcontxt, "" + jdata.optString("msg"), Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(MyCart.this, ThankYouShop.class);
                                startActivity(i);
                                finish();

                                hideProgress();
                            }

                        } catch (Exception e1) {
                            Toast.makeText(MyCart.this, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            hideProgress();
                        }
                    } else {
                        Toast.makeText(MyCart.this, "Erro" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(MyCart.this, "Verifique a liga��o de internet", Toast.LENGTH_SHORT).show();
            hideProgress();
        }
    }


    public void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        editDate = (EditText) dialogView.findViewById(R.id.editDtae);
        editTime = (EditText) dialogView.findViewById(R.id.editTime);

        editDate.setFocusableInTouchMode(true);
        editDate.setClickable(true);
        editDate.requestFocus();
        editDate.setKeyListener(null);

        editTime.setKeyListener(null);
        editTime.setFocusableInTouchMode(true);
        editTime.setClickable(true);
        editTime.requestFocus();

        dialogBuilder.setTitle("Agendar a entrega");
        dialogBuilder.setMessage("A sua entrega começa desde:");

        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.setPositiveButton("definir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                try {
                    currentDate = new Date(System.currentTimeMillis());

                    Integer  chour       = Integer.valueOf((String) DateFormat.format("HH", currentDate)); // hour
                    Integer cmins         = Integer.valueOf((String) DateFormat.format("mm", currentDate)); // min
                    Integer cday          = Integer.valueOf((String) DateFormat.format("dd",   currentDate)); // 20
                    String cmonthNumber  = (String) DateFormat.format("yyyy:MM",   currentDate);
                    Integer pday          = Integer.valueOf((String) DateFormat.format("dd",   pickedDate)); // 20
                    String pmonthNumber  = (String) DateFormat.format("yyyy:MM",   pickedDate);


                    if (editDate.getText().equals("") || editDate.getText().toString() == null || editDate.getText().toString().trim().length() == 0 || editTime.getText().equals("") || editTime.getText().toString() == null || editTime.getText().toString().trim().length() == 0) {
                        Toast.makeText(mcontxt, "Campo Vazio, Por favor preencha", Toast.LENGTH_LONG).show();
                    } else {
                        if (pickedDate.compareTo(currentDate) <= 0) {
                            Toast.makeText(mcontxt, "Não é possível agendar a entrega na data e hora pretendida.", Toast.LENGTH_LONG).show();
                        }
                        else if(cmonthNumber.equalsIgnoreCase(pmonthNumber) &&
                                (pday == cday + 1) && (chour > 19 || (chour == 19 && cmins >= 30))){
                            Toast.makeText(mcontxt, "The time limit to order for the following day is 7.30 pm.", Toast.LENGTH_LONG).show();
                        }
                        else {

                            //  getWalletFromServer(cart_total_amount.getText().toString().replace(",","."));
                            if (spinner.getSelectedItem().toString().equalsIgnoreCase("3")) { //"Compra Única"

                                if (setDays == 1) {
                                    Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                    intent.putExtra("SubscriptionId", SubscriptionId);
                                    // intent.putExtra("PyaAmmount",""+payTotal);
                                    intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                    intent.putExtra("StartDate", editDate.getText().toString().trim());
                                    intent.putExtra("days", 0);
                                    intent.putExtra("StartTime", editTime.getText().toString().trim());
                                    intent.putExtra("SetFlag", "1");
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                    intent.putExtra("SubscriptionId", SubcriptionCustomID);
                                    // intent.putExtra("PyaAmmount",""+payTotal);
                                    intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                    intent.putExtra("StartDate", editDate.getText().toString().trim());
                                    intent.putExtra("days", setDays);
                                    intent.putExtra("StartTime", editTime.getText().toString().trim());
                                    intent.putExtra("SetFlag", "0");
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                intent.putExtra("SubscriptionId", SubscriptionId);
                                // intent.putExtra("PyaAmmount",""+payTotal);        spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, CountryName));

                                intent.putExtra("PyaAmmount", "" + cart_total_amount.getText().toString().replace(",", "."));
                                intent.putExtra("StartDate", editDate.getText().toString().trim());
                                intent.putExtra("days", 0);
                                intent.putExtra("StartTime", editTime.getText().toString().trim());
                                intent.putExtra("SetFlag", "1");
                                startActivity(intent);
                                finish();
                            }

                        }
                    }

                } catch (Exception e) {

                }
            }

        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                time_dialog = new Dialog(MyCart.this);
                time_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //  category_dialog.setContentView(R.layout.filtter);

                LayoutInflater inflater = (LayoutInflater) MyCart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dislogView = inflater
                        .inflate(R.layout.time_list_dialog, null);

                time_dialog.setContentView(dislogView);

                time_dialog.setCancelable(false);

                TextView tv_hedaer = (TextView) dislogView.findViewById(R.id.tv_hedaer);
                TextView tv_cancel = (TextView) dislogView.findViewById(R.id.tv_cancel);
                tv_hedaer.setText("Selecione Horário");

                final ListView SelectCategoryLIst = (ListView) dislogView.findViewById(R.id.SelectCategoryLIst);
                final ArrayList<String> values = new ArrayList<String>();
                values.add("7:00");
                values.add("7:30");
                values.add("8:00");
                values.add("8:30");
                values.add("9:00");
                values.add("9:30");
                values.add("10:00");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MyCart.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);
                SelectCategoryLIst.setAdapter(adapter);
                SelectCategoryLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selcted_time = SelectCategoryLIst.getItemAtPosition(position).toString();
                        time_dialog.dismiss();
                        editTime.setText(selcted_time);

                        // SetFillter(position);
                        //   category_dialog.dismiss();
                    }
                });

                tv_cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        time_dialog.dismiss();

                    }
                });

                time_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                time_dialog.show();
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                //  String name = (c.se(Calendar.MONTH, Calendar.LONG, new Locale("ar") ) );
                //  c.setFirstDayOfWeek(Calendar.MONDAY);
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MyCart.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                int sMonth = monthOfYear + 1;
                                String date = ("" + dayOfMonth);
                                String month = ("-" + sMonth);
                                String sYear = ("-" + year);

                                inputDate = ("" + date + month + sYear);
                                try {
                                    pickedDate = formatter.parse(inputDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setFirstDayOfWeek(Calendar.MONDAY);

                datePickerDialog.show();
            }

        });

    }

    private void checkSubscription(String userid) {
        if (InternetConnection.isInternetOn(this)) {
            Ion.with(MyCart.this).load(Constants.BASE_URL + "webservice/check_subscription")
                    .setBodyParameter("userid", userid)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(MyCart.this, "0");
                    } else {
                        Log.d("result", result + "");
                        hideProgress();

                        if (e == null) {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                                if (jdata.has("msg")) {
                                    String msg = jdata.getString("msg");
                                }
                                //String  msg=jdata.getString("msg");
                                if (status.equals("1")) {

                                    {
                                        Intent intent = new Intent(MyCart.this, PaymentSubscribActivity.class);
                                        intent.putExtra("SubscriptionId", SubscriptionId);
                                        // intent.putExtra("PyaAmmount",""+payTotal);
                                        intent.putExtra("PyaAmmount", "" + cart_total_amount.getText());
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    hideProgress();
                                    Toast.makeText(MyCart.this, "" + jdata.getString("msg"), Toast.LENGTH_LONG).show();

                                }
                            } catch (JSONException e1) {
                                hideProgress();
                                //   e1.printStackTrace();
                                hideProgress();
                            }
                        }
                    }
                }
            });

        } else {
            Toast.makeText(MyCart.this, "Verifique a ligação de internet", Toast.LENGTH_LONG).show();
            hideProgress();
        }

    }

    public double PriceTotal(ArrayList<AddCart> Cartlist) {
        double total = 0.0;
        for (int i = 0; i < Cartlist.size(); i++) {
            total = (double) (total + (Cartlist.get(i).getProductprice() * Cartlist.get(i).getProductquantity()));
        }
        payTotal = total;
        // total =Double.parseDouble(new DecimalFormat("##.##").format(total));
        if (UtilityMethod.isStringNullOrBlank(String.valueOf(total))) {
            cart_total_amount.setText("-");
        } else {

            cart_total_amount.setText("" + new DecimalFormat("##.##").format(total).toString().replace(",", "."));
        }

        return total;
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

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        Intent i = new Intent(MyCart.this, MainActivity.class);
        startActivity(i);
        finish();
        // finish();
    }
    /*@Override
    protected void onResume() {
        super.onResume();
        Locale locale = new Locale("pt");
        Locale.setDefault(locale);
        android.content.res.Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }*/
}






