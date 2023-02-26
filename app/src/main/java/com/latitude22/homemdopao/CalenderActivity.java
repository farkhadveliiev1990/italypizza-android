package com.latitude22.homemdopao;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.CalenderProductAdapter;
import com.latitude22.homemdopao.Bean.CalenderChildBean;
import com.latitude22.homemdopao.Bean.CalenderDateOfDeliveryBean;
import com.latitude22.homemdopao.Bean.CalenderProductBean;
import com.latitude22.homemdopao.Bean.CalenderBean;
import com.latitude22.homemdopao.Fragments.DatePickerFragment;

import com.latitude22.homemdopao.Service.MapLocationService;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.roomorama.caldroid.CaldroidFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity {

    CalendarView calendar;
    String orderid;
    public static String boyId;
    EditText date_pick, et_chnge_date,time_pick;
    TextView date_delivery, calendr_product, myoreder_tv_nodelivery, delivery_header_tv;
    Dialog category_dialog;
    ProgressDialog progress;
    ImageView header_img;
    String date;
    Date date1, getDate1;
    Date date2;
    Date start_date;
    CalenderProductAdapter adapter;
    DatePickerFragment datePickerFragment;
    int mYear, mMonth, mDay,mHour, mMinute;
    ArrayList<CalenderBean> deliveryList;
    ArrayList<CalenderDateOfDeliveryBean> dateofDelievryArryList;
    ArrayList<CalenderProductBean> calendrProductArray;
    private CaldroidFragment caldroidFragment;
    GridView list_calendrProduct;
    SimpleDateFormat sdf;
    ArrayList<CalenderChildBean> childList;
    String startDate, getDate;
    Button bt_track_delivery;
    static android.support.v4.app.FragmentManager fm;
    AddressResultReceiver mResultReceiver;
    SessionManager sm;
    String userId;
    public static double deslat, deslng, sourcelat, sourcelng;
    String deliverboyid;
    int pno=0;
    boolean loadMore = false;
    int mTotalItemCount = 20;
    int m=1;
    Boolean loadingMore = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calender);
        TypefaceHelper.typeface(this);
        mResultReceiver = new AddressResultReceiver(null);
        sm = new SessionManager(this);
        userId = sm.getuserid();
        String address = sm.getuseraddress();
        Intent intent = new Intent(CalenderActivity.this, GeocodeAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        // intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);
        intent.putExtra(Constants.LOCATION_NAME_DATA_EXTRA, address);
        startService(intent);
        //  Calendar cal = Calendar.getInstance();

    /*    bt_track_delivery = (Button) findViewById(R.id.bt_track_delivery);*/
        et_chnge_date = (EditText) findViewById(R.id.et_chnge_date);
        date_delivery = (TextView) findViewById(R.id.date_delivery);
        header_img = (ImageView) findViewById(R.id.header_img);
        list_calendrProduct = (GridView) findViewById(R.id.list_calendrProduct);
       // calendr_product = (TextView) findViewById(R.id.calendr_product);
        delivery_header_tv = (TextView) findViewById(R.id.delivery_header_tv);
        myoreder_tv_nodelivery = (TextView) findViewById(R.id.myoreder_tv_nodelivery);
        //  Date greenDate = cal.getTime();
        //  greenDate.getMonth();
        deliveryList = new ArrayList<>();
        delivery_header_tv.setText("Agenda de Entrega");
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalenderActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        //initializes the calendarview
        //  initializeCalendar();
        /*Intent i = getIntent();
        if (i != null) {
            orderid = i.getStringExtra("ordrId");
            startDate = i.getStringExtra("start_date");
        }*/


        list_calendrProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {

                Intent intent = new Intent(CalenderActivity.this,CalenderSubscriptionActivity.class);
                intent.putExtra("productlist",deliveryList.get(position1).getDateDeliveryData());
                intent.putExtra("dayofDelivery",deliveryList.get(position1).getDaysNo());
                intent.putExtra("dateofdelevery",deliveryList.get(position1).getDateOfDeliveries());
                startActivity(intent);
                finish();
             /*   // if(deliveryList.size()>0)
                if ((deliveryList.get(position1).getStatus().equals("0"))) {
                    SelectFilter(deliveryList.get(position1).getOrderDeliveryId());
                    getDate = deliveryList.get(position1).getDeliveryDate();
                }
                else if ((deliveryList.get(position1).getStatus().equals("1"))) {
                    GetLocation();
                }
                else
                    {
                    Toast.makeText(CalenderActivity.this, "Product delivered", Toast.LENGTH_SHORT).show();
                }*/

               /* catch (Exception ex)
                {
                    ex.printStackTrace();
                }*/
            }
        });


        list_calendrProduct.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == mTotalItemCount)) {

                    //if (stopLoadingData == false)
                    {
                       // loading_bar.setVisibility(View.VISIBLE);
                        mTotalItemCount = mTotalItemCount + 20;
                        loadingMore = true;
                        loadMore = true;

                        getDelivery(userId);
                    }
                }
            }
        });
        getDelivery(userId);

        //  showProgress();
    }

    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();

        // Min date is last 7 days
//        cal.add(Calendar.DATE, -7);
        Date blueDate = cal.getTime();

        // Max date is next 7 days
//        cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();
        greenDate.getMonth();
        if (caldroidFragment != null) {

//            "07/06/2013"
   /*         eventdates.put(getDateObject("2015/12/9"),R.color.color1);
            eventdates.put(getDateObject("2015/12/10"),R.color.color1);
            eventdates.put(getDateObject("2015/12/11"),R.color.color1);
            eventdates.put(getDateObject("2015/12/12"),R.color.color1);
            eventdates.put(getDateObject("2015/12/13"),R.color.color1);
            eventdates.put(getDateObject("2015/12/14"),R.color.color1);
            eventdates.put(getDateObject("2015/12/15"),R.color.color1);
            eventdates.put(getDateObject("2015/12/16"), R.color.color1);
            eventdates.put(getDateObject("2015/12/17"), R.color.color1);
*/


/*

            caldroidFragment.setBackgroundResourceForDates(setdate());

            caldroidFragment.setBackgroundResourceForDate(R.color.color1,
                    blueDate);
            caldroidFragment.setBackgroundResourceForDate(R.color.color3,
                    greenDate);


            caldroidFragment.setTextColorForDate(R.color.colorPrimary, blueDate);
            caldroidFragment.setTextColorForDate(R.color.colorPrimary, greenDate);
            caldroidFragment.refreshView();

*/
        }
    }
    public void getDelivery(String userId) {
        if (InternetConnection.isInternetOn(this)) {
            if(pno == 0) showProgress();
            Log.d("result", userId + "userId");
            Ion.with(CalenderActivity.this).load(Constants.BASE_URL + "webservice/orderdeliveries")
                    .setBodyParameter("userid", userId)  .setBodyParameter("PNO", pno+"")
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(CalenderActivity.this, "0");
                    } else {
                        Log.d("result", result + "");
                        hideProgress();
                        if (e == null) {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");
                               /* if (jdata.has("msg")) {
                                    String msg = jdata.getString("msg");
                                }*/
                                //String  msg=jdata.getString("msg");
                                if (status.equals("1")) {
                                    JSONArray jitem = jdata.getJSONArray("orderdeliverylist");
                                    for (int i = 0; i < jitem.length(); i++) {
                                        JSONObject mobj = jitem.getJSONObject(i);
                                        CalenderBean bean = new CalenderBean();
                                        bean.setOrderdeliveryDate(mobj.optString("OrderDeliveryDate"));
                                        bean.setDaysNo(""+(m));
                                        m++;
                                        JSONArray jsecondArray = mobj.getJSONArray("datedelivery");
                                        dateofDelievryArryList = new ArrayList<>();
                                        JSONArray narray = new JSONArray();
                                        for(int j = 0; j < jsecondArray.length(); j++) {
                                            CalenderDateOfDeliveryBean beanSecond = new CalenderDateOfDeliveryBean();
                                            JSONObject jObject = new JSONObject();
                                            JSONObject jsecondObj = jsecondArray.getJSONObject(j);
                                            jObject.put("OrderDeliveryID", jsecondObj.optString("OrderDeliveryID"));
                                            jObject.put("OrderDeliveryBoyID", jsecondObj.optString("OrderDeliveryBoyID"));
                                            jObject.put("OrderDeliveryTime", jsecondObj.optString("OrderDeliveryTime"));
                                            jObject.put("OrderDeliveryStatus", jsecondObj.optString("OrderDeliveryStatus"));
                                            jObject.put("SubscriptionName", jsecondObj.optString("SubscriptionName"));
                                            jObject.put("OrderDeliveryDate",jsecondObj.optString("OrderDeliveryDate"));
                                            narray.put(jObject);
                                            beanSecond.setOrderDeliveryId(jsecondObj.optString("OrderDeliveryID"));
                                            beanSecond.setDeliveryboyid(jsecondObj.optString("OrderDeliveryBoyID"));
                                            beanSecond.setDeliveryTime(jsecondObj.optString("OrderDeliveryTime"));
                                            beanSecond.setStatus(jsecondObj.optString("OrderDeliveryStatus"));
                                            beanSecond.setDelievrySubscName(jsecondObj.optString("SubscriptionName"));
                                            beanSecond.setDated(jsecondObj.optString("OrderDeliveryDate"));
                                            boyId = jsecondObj.optString("OrderDeliveryBoyID");

                                            JSONArray jprorray = jsecondObj.getJSONArray("product");
                                            calendrProductArray = new ArrayList<CalenderProductBean>();
                                            JSONArray marray = new JSONArray();
                                            if(jprorray != null) {
                                                if (jprorray.length() > 0) {
                                                    for (int k = 0; k < jprorray.length(); k++) {
                                                        CalenderProductBean productBean = new CalenderProductBean();
                                                        JSONObject obj = new JSONObject();
                                                        JSONObject jobj = jprorray.getJSONObject(k);
                                                        obj.put("ProductName", jobj.optString("ProductName"));
                                                        obj.put("ProductQuantity", jobj.optString("ProductQuantity"));
                                                        marray.put(obj);
                                                        productBean.setProductName(jobj.optString("ProductName"));
                                                        productBean.setProductQty(jobj.optString("ProductQuantity"));
                                                        calendrProductArray.add(productBean);
                                                    }
                                                }
                                            }/*else
                                            {
                                                JSONObject obj = new JSONObject();
                                                obj.put("ProductName", "");
                                                obj.put("ProductQuantity", "");
                                                marray.put(obj);
                                                CalenderProductBean productBean = new CalenderProductBean();
                                                productBean.setProductName("");
                                                productBean.setProductQty("");
                                                calendrProductArray.add(productBean);
                                            }*/


                                            beanSecond.setProductlist(calendrProductArray);
                                            beanSecond.setCalenderProduct(marray.toString());
                                            dateofDelievryArryList.add(beanSecond);
                                        }
                                        bean.setDateOfDeliveries(dateofDelievryArryList);
                                        bean.setDateDeliveryData(narray.toString());
                                        deliveryList.add(bean);
                                    }
                                    Log.i(" array "," deliveryList size "+ deliveryList.size());
                                    if(deliveryList.size() >0) {
                                        pno = pno + 1;

                                        myoreder_tv_nodelivery.setVisibility(View.GONE);
                                        if (adapter == null) {

                                            adapter = new CalenderProductAdapter(deliveryList, CalenderActivity.this);
                                            // list_calendrProduct.setAdapter(new CalenderProductAdapter(deliveryList, CalenderActivity.this));
                                            list_calendrProduct.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                            // list_calendrProduct.invalidateViews();
                                            myoreder_tv_nodelivery.setVisibility(View.GONE);
                                        } else {
                                            adapter.notifyDataSetChanged();
                                            //   myoreder_tv_nodelivery.setVisibility(View.VISIBLE);
                                        }
                                    }else  myoreder_tv_nodelivery.setVisibility(View.VISIBLE);
                                setCustomResourceForDates();
                                hideProgress();
                            } else{
                                    hideProgress();
                                }
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                hideProgress();
                            }

                        } else {
                            // Toast.makeText(getActivity(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    public void SelectFilter(final String Orderid1) {

    }

    private Date getDateObject(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr = date;
            Date date1 = inputFormat.parse(inputDateStr);
            String outputDateStr = outputFormat.format(date1);
            return date1;
        } catch (Exception ex) {
            return null;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // finish();
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /* infoText.setText("Latitude: " + address.getLatitude() + "\n" +
                                "Longitude: " + address.getLongitude() + "\n" +
                                "Address: " + resultData.getString(Constants.RESULT_DATA_KEY));*/

                        deslat = address.getLatitude();
                        if(address.getLongitude()<0){
                            deslng =  Math.abs(address.getLongitude());
                        }
                        else {
                            deslng = address.getLongitude();
                        }

                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // infoText.setVisibility(View.VISIBLE);
                        // infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
        }
    }
    public void GetLocation() {
        //String id=sm.getuserid();
        //  Toast.makeText(getBaseContext(), "inffg", Toast.LENGTH_SHORT).show();
        Ion.with(getBaseContext()).load(Constants.BASE_URL + "webservice/usersdeliveryboylocation")
               // .setBodyParameter("id", deliverboyid)
                .asString().setCallback(new FutureCallback<String>() {

            @Override
            public void onCompleted(Exception e, String result) {
                //  Log.d("result", result);
                hideProgress();
                if (UtilityMethod.isStringNullOrBlank(result)) {
                    try {
                        UtilityMethod.alertforServerError(getBaseContext(), "0");
                    } catch (Exception e1) {
                    }

                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        String status = jsonObject.getString("status");

                        if (status.equals("1")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                            String lat = jsonObject1.getString("latitude");
                            String lng = jsonObject1.getString("longitude");
                            if (lat.equals("0.00") || lng.equals("0.00")||deslat<0 || deslng<0) {
                                alertforNoAddress();
                            } else {
                                Intent i = new Intent(CalenderActivity.this, TrackDeliveryActivity.class);
                                sourcelat = Double.parseDouble(lat);
                                sourcelng = Double.parseDouble(lng);

                              //  i.putExtra("deliveryboy", deliverboyid);
                                i.putExtra("sourcelat", sourcelat);
                                i.putExtra("sourcelng", sourcelng);
                                i.putExtra("lat", deslat);
                                i.putExtra("lng", deslng);

                                startActivity(i);
                                finish();
                            }
                        /* if(i==1){
                             status_txt.setText("Progress");
                         }ma
                        // lm.setOrderDeliveryStatus("Completed");
                         status_txt.setText("Completed");*/
                            //  todaysdeliveryBeen.notifyAll();
                            //     Toast.makeText(getBaseContext(), ""+msg, Toast.LENGTH_SHORT).show();
                        } else if (status.equals("0")) {

                        }
                    } catch (JSONException e1) {
                        //Toast.makeText(getBaseContext(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void alertforNoAddress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CalenderActivity.this);

        alertDialog.setMessage("Morada não encontrada!");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                // DashBoardActivity.this.finish();
                // Write your code here to invoke YES event

            }
        });
        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
