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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Bean.CalenderBean;
import com.latitude22.homemdopao.Bean.CalenderDateOfDeliveryBean;
import com.latitude22.homemdopao.Bean.CalenderSubscriptionBean;
import com.latitude22.homemdopao.CalenderActivity;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.PaymentSubscribActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.Service.MapLocationService;
import com.latitude22.homemdopao.TrackDelivery;
import com.latitude22.homemdopao.TrackDeliveryActivity;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vaio1 on 05-12-2017.
 */

public class CalenderSubscriptionAdapter extends BaseAdapter {

    Context Mcontext;
    ProgressDialog progress;
    String deliveryBoyId;
    EditText editDays,editDate,editTime;
    Date pickedDate,currentDate;
    String inputDate;
    TextView item_no, tap_txt;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String orderid;
    Dialog time_dialog;
    double deslat, deslng, sourcelat, sourcelng;
    int pos = 1991;
    //  ArrayList<CalenderProductBean> productList;
    ArrayList<CalenderDateOfDeliveryBean> subscriptionList;


    //  public CalenderProductAdapter(ArrayList<CalenderProductBean> productList, Context mcontext) {
    //      this.productList  = productList;
    //     Mcontext = mcontext;
    //  }

    public CalenderSubscriptionAdapter(ArrayList<CalenderDateOfDeliveryBean> subscriptionList, Context mcontext) {
        this.subscriptionList = subscriptionList;
        Mcontext = mcontext;
    }

    String date = String.valueOf(android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()));


    @Override
    public int getCount() {
        return subscriptionList.size();
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


        convertView = layoutInflater.inflate(R.layout.calender_subscription_list_item, null);

        TypefaceHelper.typeface(convertView);


        // item_no =(TextView)convertView .findViewById(R.id.calendr_product_no);
        // tap_txt =(TextView)convertView .findViewById(R.id.tap_tv);


        TextView subsc_name_calendr = (TextView) convertView.findViewById(R.id.subsc_name_calendr);
        subsc_name_calendr.setText(subscriptionList.get(position).getDelievrySubscName());



        TextView delivry_date = (TextView) convertView.findViewById(R.id.delivry_date);
        delivry_date.setText(subscriptionList.get(position).getDated());

        TextView delievry_status = (TextView) convertView.findViewById(R.id.delievry_status);
        //  delievry_status.setText(subscriptionList.get(position).getDelivery_Status());

        TextView txt_update = (TextView) convertView.findViewById(R.id.txt_update);

        TextView delivry_time = (TextView) convertView.findViewById(R.id.delivry_time);
        delivry_time.setText(subscriptionList.get(position).getDeliveryTime());


        TextView order_id_delivery = (TextView) convertView.findViewById(R.id.order_id_delivery);
        order_id_delivery.setText(subscriptionList.get(position).getOrderDeliveryId());

        LinearLayout layout_track_delivery = (LinearLayout) convertView.findViewById(R.id.layout_track_delivery);

        if(subscriptionList.get(position).getDeliveryboyid().equals("0")||subscriptionList.get(position).getDeliveryboyid().equals("")||subscriptionList.get(position).getDeliveryboyid()==null)
        {
            layout_track_delivery.setVisibility(View.GONE);
        }
        else
        {
            layout_track_delivery.setVisibility(View.VISIBLE);
            layout_track_delivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deliveryBoyId = subscriptionList.get(position).getDeliveryboyid();
                   // Toast.makeText(Mcontext, "AdapterDelivery"+deliveryBoyId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Mcontext, TrackDeliveryActivity.class);
                    intent.putExtra("setFlag",1);
                    intent.putExtra("boyId", deliveryBoyId);
                    Mcontext.startActivity(intent);
                    ((Activity)Mcontext).finish();
                }
            });
        }
       // Toast.makeText(Mcontext, "deliveryBoyId"+deliveryBoyId, Toast.LENGTH_SHORT).show();
            // ImageView img_delivery = (ImageView)convertView.findViewById(R.id.img_delivery);

        //   TextView calendr_delvry_status =(TextView)convertView .findViewById(R.id.calendr_delvry_

        Log.e("test", "************** state : " + subscriptionList.get(position).getStatus());

        if ((subscriptionList.get(position).getStatus().equals("0"))) {
            delievry_status.setText("Pendente");
            layout_track_delivery.setVisibility(View.GONE);
            txt_update.setVisibility(View.VISIBLE);
            delievry_status.setTextColor(Mcontext.getResources().getColor(R.color.orange));
        } else if (subscriptionList.get(position).getStatus().equals("1")) {
            delievry_status.setText("Em distribuição");
            txt_update.setVisibility(View.GONE);
            layout_track_delivery.setVisibility(View.VISIBLE);
            delievry_status.setTextColor(Mcontext.getResources().getColor(R.color.green));
        }
        else
        {
            delievry_status.setText("Completado");
            txt_update.setVisibility(View.GONE);
            layout_track_delivery.setVisibility(View.GONE);
            delievry_status.setTextColor(Mcontext.getResources().getColor(R.color.red));
        }


        txt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderid = subscriptionList.get(position).getOrderDeliveryId();
                showDialog(position);
            }
        });

        return convertView;
    }

    private void setUpdatedelivery(final String orderid,final  String date,final  String time, final int position) {

        if (InternetConnection.isInternetOn(Mcontext)) {
            showProgress();
            Ion.with(Mcontext).load(Constants.BASE_URL + "webservice/editdeliverydate")
                    .setBodyParameter("orderdeliveryid",orderid)
                    .setBodyParameter("datetoupdate",date)
                    .setBodyParameter("timetoupdate",time)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {

                    Log.d("result", orderid + " orderid "+time +" time "+" date "+date);
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
                               /* getDelivery(orderid);

                                adapter.*/

                               subscriptionList.remove(position);
                               notifyDataSetChanged();
                                Toast.makeText(Mcontext, ""+jdata.getString("msg"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Mcontext, "atualizou a data com sucesso", Toast.LENGTH_SHORT).show();
                                hideProgress();
                            } else {
                                Toast.makeText(Mcontext, ""+jdata.getString("msg"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Mcontext, "Pedimos desculpa mas não conseguimos atualizar a data", Toast.LENGTH_SHORT).show();
                                hideProgress();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            hideProgress();
                        }
                    }
                    //  deliveryList.clear();
                    //  adapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(Mcontext, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
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
        } catch (Exception e) {

        }
    }

    public void showDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Mcontext);
        final View dialogView = ((Activity) Mcontext).getLayoutInflater().inflate(R.layout.custom_dialog, null);
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

        dialogBuilder.setTitle("atualizou a data com sucesso");

        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.setPositiveButton("definir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                try {

                    currentDate = new Date(System.currentTimeMillis());
                    String dateInput = editDate.getText().toString().trim();
                    String timeINput = editTime.getText().toString().trim();

                    if (editDate.getText().equals("") || editDate.getText().toString() == null || editDate.getText().toString().trim().length() == 0 || editTime.getText().equals("") || editTime.getText().toString() == null || editTime.getText().toString().trim().length() == 0) {
                        Toast.makeText(Mcontext, "Campo Vazio, Por favor preencha", Toast.LENGTH_LONG).show();
                    } else {
                        if (pickedDate.compareTo(currentDate) <= 0) {
                            Toast.makeText(Mcontext, "Não é possível agendar a entrega na data e hora pretendida", Toast.LENGTH_LONG).show();
                        } else {

                            setUpdatedelivery(orderid, dateInput, timeINput, position);
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
                //mHour = c.get(Calendar.HOUR_OF_DAY);
                //mMinute = c.get(Calendar.MINUTE);

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
                values.add("10:00");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Mcontext,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);
                SelectCategoryLIst.setAdapter(adapter);
                SelectCategoryLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selcted_time = SelectCategoryLIst.getItemAtPosition(position).toString();
                        time_dialog.dismiss();
                        editTime.setText(selcted_time);
                      //  Toast.makeText(Mcontext,"selcted_time "+selcted_time,Toast.LENGTH_LONG).show();

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
                datePickerDialog.show();
            }

        });


    }
}