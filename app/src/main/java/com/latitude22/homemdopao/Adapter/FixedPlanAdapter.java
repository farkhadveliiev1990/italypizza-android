package com.latitude22.homemdopao.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.latitude22.homemdopao.Bean.SubscriptionBean;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.LoginActivity;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.PaymentSubscribActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SessionManager;
import com.latitude22.homemdopao.Utility;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anuved on 19-08-2016.
 */
public class FixedPlanAdapter extends BaseAdapter {

    Context Mcontext;
    ArrayList<SubscriptionBean> Subscrption;
    String SubscriptionId;
    SessionManager sm;
    Dialog time_dialog;
    String inputDate;
    String UserId;
    EditText editDate,editTime;
    Date pickedDate,currentDate;
    ProgressDialog progress;
    TextView list_price;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String priceTotal;
    ArrayList<FixedplanBean> fixedplanlist;
    public static ArrayList<FixedPlanProductBean>fixedPlanProductBeen;

    public FixedPlanAdapter(ArrayList<FixedplanBean> fixedplanlist, Context mcontext) {
        this.fixedplanlist = fixedplanlist;
        Mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return fixedplanlist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(Mcontext);

        convertView = layoutInflater.inflate(R.layout.fixedplanlistitem, null);

        TypefaceHelper.typeface(convertView);
        ImageView fixedplan_breadimg = (ImageView) convertView.findViewById(R.id.fixedplan_breadimg);
        fixedPlanProductBeen=new ArrayList<>();
        sm = new SessionManager(Mcontext);
        UserId = sm.getuserid();
        Button bt_sbs = (Button) convertView.findViewById(R.id.bt_sbs);

      /*  try{
            if (fixedplanlist.get(position).getImage()!=null&!fixedplanlist.get(position).getImage().equals""){
                Picasso.with(Mcontext).load(fixedplanlist.get(position).getImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(item_image);
            }

        }catch (Exception ae){

        }*/
        // item_image.setImageResource(listItem.get(position).getBreadImg());
        TextView list_text = (TextView) convertView.findViewById(R.id.fixedplan_breadname);
        list_text.setText(fixedplanlist.get(position).getBreadName());

        TextView plan_text = (TextView) convertView.findViewById(R.id.fixedplan_type);
        plan_text.setText(fixedplanlist.get(position).getSubscriptionType());

        list_price = (TextView) convertView.findViewById(R.id.tv_bread_price);
        list_price.setText(fixedplanlist.get(position).getPrice());


        //  TextView fixedplan_type=(TextView)convertView .findViewById(R.id.fixedplan_type);
        //  fixedplan_type.setText(fixedplanlist.get(position).sub);

        //  list_text.setText(listItem.get(position).getBreadname());
        try {
            if (fixedplanlist.get(position).getImage() != null & !fixedplanlist.get(position).getImage().equals("")) {

                Picasso.with(Mcontext).load(fixedplanlist.get(position).getImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(fixedplan_breadimg);
            }

        } catch (Exception ae) {

        }

        //   priceTotal = String.valueOf(list_price.getText());

        bt_sbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Utility.isStringNullOrBlank(sm.getuserid())) {
                Intent i = new Intent(Mcontext, LoginActivity.class);
                    Mcontext.startActivity(i);
                   ((Activity)Mcontext).finish();
              }

                else {

                priceTotal = fixedplanlist.get(position).getPrice();
                fixedPlanProductBeen=fixedplanlist.get(position).getProductlist();
                try {
                    showDialog(position);
                   // checkSubscription(UserId,fixedplanlist.get(position).getSubscriptionId());


                   // showProgress();
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
               }
            }
        });
        return convertView;
    }

   private void checkSubscription(String userid, final String subid) {
        Ion.with(Mcontext).load(Constants.BASE_URL + "webservice/check_subscription")
                .setBodyParameter("userid", userid)
                .asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
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
                            if (Utility.isStringNullOrBlank(sm.getuserid())) {
                                Intent i = new Intent(Mcontext, LoginActivity.class);
                                Mcontext.startActivity(i);
                               // ((Activity)Mcontext).finish();
                              //  Mcontext.finish();
                            }
                            else {
                                Intent  intent = new Intent(Mcontext, PaymentSubscribActivity.class);
                                intent.putExtra("SubscriptionId",subid);
                                intent.putExtra("PyaAmmount",""+priceTotal);
                                intent.putExtra("StartDate",editDate.getText().toString().trim());
                                intent.putExtra("StartTime",editTime.getText().toString().trim());
                                intent.putExtra("SetFlag","1");
                                JSONArray jsonArray=new JSONArray();
                                for (int i = 0; i <fixedPlanProductBeen.size(); i++) {
                                    JSONObject mobj1 = new JSONObject();
                                    try {
                                        mobj1.put("ID", "" + fixedPlanProductBeen.get(i).getFixedProductId());
                                        mobj1.put("QTY", "1");
                                        jsonArray.put(mobj1);
                                    } catch (JSONException e1) {
                                        e.printStackTrace();
                                    }
                                }
                                intent.putExtra("array",""+jsonArray);
                                    Mcontext.startActivity(intent);
                                //((Activity)Mcontext).finish();
                              //  finish();
                            }
                        }

                        else{
                            Toast.makeText(Mcontext, "" + jdata.getString("msg"), Toast.LENGTH_LONG).show();
                            hideProgress();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();

                    }
                }
            }
        });

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

    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }

    public  void showDialog(final int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Mcontext);
        LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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

                try
                {

                    currentDate = new Date(System.currentTimeMillis());

                    if (editDate.getText().equals("") || editDate.getText().toString() == null || editDate.getText().toString().trim().length() == 0 || editTime.getText().equals("") || editTime.getText().toString() == null || editTime.getText().toString().trim().length() == 0) {
                        Toast.makeText(Mcontext, "Campo Vazio, Por favor preencha", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (pickedDate.compareTo(currentDate) <= 0) {
                            Toast.makeText(Mcontext, "Não é possível agendar a entrega na data e hora pretendida", Toast.LENGTH_LONG).show();
                        }
                        else {

                            checkSubscription(UserId,fixedplanlist.get(pos).getSubscriptionId());

                            showProgress();
                        }
                    }

                }
                catch (Exception e)
                {

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

                time_dialog = new Dialog(Mcontext);
                time_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //  category_dialog.setContentView(R.layout.filtter);

                LayoutInflater inflater = (LayoutInflater) Mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mcontext,
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
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Mcontext,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                int sMonth = monthOfYear + 1;
                                String date = (""+dayOfMonth);
                                String month = ("-"+sMonth);
                                String sYear = ("-"+year);
                                inputDate = (""+date+month+sYear);
                                try {
                                    pickedDate = formatter.parse(inputDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        });

    }
}



