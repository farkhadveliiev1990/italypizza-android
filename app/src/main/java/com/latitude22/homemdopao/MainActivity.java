package com.latitude22.homemdopao;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.DrawerItemAdapter;
import com.latitude22.homemdopao.Bean.DrawerItemBean;
import com.latitude22.homemdopao.Bean.PushModel;
import com.latitude22.homemdopao.Fragments.AboutUsFragment;
import com.latitude22.homemdopao.Fragments.ContactUs;
import com.latitude22.homemdopao.Fragments.CreditFragment;
import com.latitude22.homemdopao.Fragments.FaqFragment;
import com.latitude22.homemdopao.Fragments.HistoryFragment;
import com.latitude22.homemdopao.Fragments.HomeFragment;
import com.latitude22.homemdopao.Fragments.MyOrderFragment;
import com.latitude22.homemdopao.Fragments.ProductFragment;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView mDrawerList;
    DrawerLayout mDrawerLayout;
    ArrayList<DrawerItemBean> drawerItemBeans;
    Dialog addtocart_dialog;
    DrawerItemBean drawItem;
    ActionBar actionBar;
    AddCart addCart;
    LinearLayout cartlayout;
    static Context Mcntxt;
    static android.support.v4.app.FragmentManager fragmentManager;
    SessionManager sm;
    private String TAG = "MainActivity";
    public static ImageView img_profile, mDrawerToggle, main_icon, cart, icon_filter;
    public static TextView header_text, main_cart_quantity;
    TextView tv1_username, tv2_useremail_id, tv_login;
    public static TextView tv3_balance;
    public static boolean isDrawerOpen = false;

    static public boolean isSearchKey = false;

    private DatabaseReference fireTokenRef;
    private DatabaseReference fireMsgRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        TypefaceHelper.typeface(this);
        actionBar = getSupportActionBar();
//        actionBar.hide();
        Mcntxt = this;

        fragmentManager = getSupportFragmentManager();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        LayoutInflater inflater = getLayoutInflater();
        View headerlist = inflater.inflate(R.layout.drawer_header, null, false);
        mDrawerList.addHeaderView(headerlist);
        mDrawerToggle = (ImageView) findViewById(R.id.menuIcon);
        img_profile = (ImageView) findViewById(R.id.img_profile);
        main_icon = (ImageView) findViewById(R.id.main_icon);
        cart = (ImageView) findViewById(R.id.cart);
        main_cart_quantity = (TextView) findViewById(R.id.main_cart_quantity);
        icon_filter = (ImageView) findViewById(R.id.icon_filter);

        cartlayout = (LinearLayout) findViewById(R.id.cartlayout);

        tv_login = (TextView) findViewById(R.id.tv_login);
        tv1_username = (TextView) findViewById(R.id.tv1_username);
        tv2_useremail_id = (TextView) findViewById(R.id.tv2_useremail_id);
        tv3_balance= (TextView) findViewById(R.id.tv3_balance);
        icon_filter.setVisibility(View.GONE);
        header_text = (TextView) findViewById(R.id.header_text);


        sm = new SessionManager(this);
        addCart = new AddCart();
        // tv1_username.setText(sm.getUsername());
        tv1_username.setText(sm.getUserFullName());
        tv2_useremail_id.setText(sm.getEmail());
        //  Ion.with(img_profile).load(sm.getprofileImage());

        fireTokenRef =  FirebaseDatabase.getInstance().getReference("/tokens/");
        fireMsgRef =  FirebaseDatabase.getInstance().getReference("/msg/");
        showReceivedPushMessage();
        String token = FirebaseInstanceId.getInstance().getToken();
        saveFireBasePushToken(token);


        //SessionManager ssm = new SessionManager(this);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {

            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

                // layout_frame_main.setX((arg0.getWidth() * arg1));

            }

            @Override
            public void onDrawerOpened(View arg0) {
                isDrawerOpen = true;
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            }

            @Override
            public void onDrawerClosed(View arg0) {

                isDrawerOpen = false;
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            }

        });

        mDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerOpen) {
                    isDrawerOpen = false;
                    //  mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    mDrawerLayout.closeDrawers();
                } else {
                    isDrawerOpen = true;

                    mDrawerLayout.openDrawer(mDrawerList);
                }
            }
        });


        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MyCart.class);
                // Intent intent1 = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    /*    cartlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MyCart.class);
               // Intent intent1 = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent1);
                finish();
            }
        });*/

        main_cart_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MyCart.class);
                startActivity(intent1);
                finish();
            }
        });

        drawerItemBeans = new ArrayList<DrawerItemBean>();
        drawItem = new DrawerItemBean();
        drawItem.setItem_name("Inicio");
        drawItem.setItem_icon(R.drawable.icon_home);
        drawerItemBeans.add(drawItem);


        if (Utility.isStringNullOrBlank(sm.getuserid())) {

        } else {
            drawItem = new DrawerItemBean();
            drawItem.setItem_name("Entregas");
            drawItem.setItem_icon(R.drawable.my_order);
            drawerItemBeans.add(drawItem);
        }


        drawItem = new DrawerItemBean();
        drawItem.setItem_name("Produtos");
        drawItem.setItem_icon(R.drawable.icon_product);
        drawerItemBeans.add(drawItem);

        drawItem = new DrawerItemBean();
        drawItem.setItem_name("Carrinho");
        drawItem.setItem_icon(R.drawable.icon_cart);
        drawerItemBeans.add(drawItem);

        if (Utility.isStringNullOrBlank(sm.getuserid())) {

        } else {
            drawItem = new DrawerItemBean();
            drawItem.setItem_name("Histórico");
            drawItem.setItem_icon(R.drawable.order_history);
            drawerItemBeans.add(drawItem);

            drawItem = new DrawerItemBean();
            drawItem.setItem_name("Comprar crédito");
            drawItem.setItem_icon(R.drawable.ic_wallet);
            drawerItemBeans.add(drawItem);

           /* drawItem = new DrawerItemBean();
            drawItem.setItem_name("História da carteira");
            drawItem.setItem_icon(R.drawable.ic_wallet);
            drawerItemBeans.add(drawItem);*/
        }

     /*   if (Utility.isStringNullOrBlank(sm.getuserid())) {

        } else {
            drawItem = new DrawerItemBean();
            drawItem.setItem_name("Track Delivery");
            drawItem.setItem_icon(R.drawable.icon_track);
            drawerItemBeans.add(drawItem);
        }*/


        drawItem = new DrawerItemBean();
        drawItem.setItem_name("FAQ");
        drawItem.setItem_icon(R.drawable.icon_faq);
        drawerItemBeans.add(drawItem);


        drawItem = new DrawerItemBean();
        drawItem.setItem_name("Contacte-nos");
        drawItem.setItem_icon(R.drawable.icon_contact);
        drawerItemBeans.add(drawItem);

        drawItem = new DrawerItemBean();
        drawItem.setItem_name("Sobre Nós");
        drawItem.setItem_icon(R.drawable.icon_aboutus);
        drawerItemBeans.add(drawItem);


        if (Utility.isStringNullOrBlank(sm.getuserid())) {


        } else {
            drawItem = new DrawerItemBean();
            drawItem.setItem_name("Sair");
            drawItem.setItem_icon(R.drawable.icon_logout);
            drawerItemBeans.add(drawItem);
        }

        mDrawerList.setAdapter(new DrawerItemAdapter(drawerItemBeans, this));

        Bundle b = new Bundle();
        Intent i = getIntent();
        String pos = i.getStringExtra("position");
        if (UtilityMethod.isStringNullOrBlank(pos)) {
            SelectItem(0);
        } else {
            if (pos.equals("1")) {
                SelectItem(5);
            } else if (pos.equals("4")) {
                SelectItem(4);
            } else {
                SelectItem(0);
            }
        }


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    Bundle b = new Bundle();
                    SelectItem(position - 1);
                }

            }
        });
        getWalletFromServer();
    }

    public void getWalletFromServer() {

        String ApiData = Constants.BASE_URL + "webservice/get_wallet?";
        if (InternetConnection.isInternetOn(MainActivity.this)) {

            Ion.with(MainActivity.this).load(ApiData).setBodyParameter("userid", sm.getuserid())
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    if (!UtilityMethod.isStringNullOrBlank(result)) {


                        if (e == null) {
                            if (!UtilityMethod.isStringNullOrBlank(result)) {
                                try {

                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");
                                    if (status.equals("1")) {
                                        tv3_balance.setText( "Saldo : " + jdata.getString("total") + "€");
                                    }

                                } catch (JSONException e1) {
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public void onClickSearchBtn (View view) {
        SelectItem(2);
        isSearchKey = true;
    }

    public void SelectItem(int position) {

        Fragment fragment = null;

        switch (position) {

            case 0:
                getWalletFromServer();

                fragment = HomeFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "Home").addToBackStack(null).commit();
                //  Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
                break;
            case 1:

                fragment = MyOrderFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("MyOrderFragment").commit();
                break;


            case 2:

                fragment = ProductFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("ProductFragment").commit();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(((ProductFragment)fragment).txt_search, InputMethodManager.SHOW_IMPLICIT);
                // Toast.makeText(MainActivity.this, "Product", Toast.LENGTH_LONG).show();
                break;

            case 3:

                Intent intnt = new Intent(MainActivity.this, MyCart.class);
                // Intent intent1 = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intnt);
                finish();
                break;
            case 4:
                fragment = HistoryFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("HistoryFragment").commit();
                // Toast.makeText(MainActivity.this, "Product", Toast.LENGTH_LONG).show();
                break;
            case 5:

                fragment = CreditFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("CreditFragment").commit();
                // Toast.makeText(MainActivity.this, "Product", Toast.LENGTH_LONG).show();
                break;
            case 6:
                fragment = FaqFragment.getInstance(Mcntxt, fragmentManager);
                // Toast.makeText(MainActivity.this, "Faq", Toast.LENGTH_LONG).show();
                try {
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("FaqFragment").commit();
                } catch (IllegalStateException i) {

                } catch (Exception e) {

                }
                break;
            case 7:
                fragment = ContactUs.getInstance(Mcntxt, fragmentManager);
                //  Toast.makeText(MainActivity.this, "ContactUs", Toast.LENGTH_LONG).show();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("ContactUs").commit();
                break;

            case 8:
                fragment = AboutUsFragment.getInstance(Mcntxt, fragmentManager);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("AboutUsFragment").commit();
                //  Toast.makeText(MainActivity.this, "About", Toast.LENGTH_LONG).show();
                break;

            case 9:
                try{
                    new Delete().from(AddCart.class).execute();
                    sm.logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }
        //mDrawerList.setItemChecked(position, true);
        setTitle(drawerItemBeans.get(position).getItem_name());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    public void ShowFilter() {
        addtocart_dialog = new Dialog(this);
        addtocart_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  category_dialog.setContentView(R.layout.filtter);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dislogView = inflater
                .inflate(R.layout.addtocart_dialog, null);

        addtocart_dialog.setContentView(dislogView);

        addtocart_dialog.setCancelable(false);

        TextView Catgory_heading = (TextView) dislogView.findViewById(R.id.Catgory_heading);
        TextView Cancel_Catgory = (TextView) dislogView.findViewById(R.id.Cancel_Catgory);
        Catgory_heading.setText("Product Filter");

        ListView SelectCategoryLIst = (ListView) dislogView.findViewById(R.id.SelectCategoryLIst);
        final ArrayList<String> values = new ArrayList<String>();
        values.add("Category 02");
        values.add("Category 03");
        values.add("Category 04");
        values.add("Brown");
        values.add("Wheat");
        values.add("White");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        SelectCategoryLIst.setAdapter(adapter);

        Cancel_Catgory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addtocart_dialog.dismiss();

            }
        });

        addtocart_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        addtocart_dialog.show();
    }

    @Override
    public void onBackPressed() {
        Fragment CartFragment = fragmentManager.findFragmentByTag("cart");
        if (CartFragment != null) {
            if (CartFragment.isVisible()) {
                Bundle b = new Bundle();
                new MainActivity().SelectItem(0);
            }
        }
        Fragment HomeFragment = fragmentManager.findFragmentByTag("Home");
        if (HomeFragment != null) {
            if (HomeFragment.isVisible()) {
                alertforExitApp();
                //finish();
            } else {
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    alertforExitApp();
                    // this.finish();
                } else {
                    getFragmentManager().popBackStack();
                }
            }
        }
       /* if (getFragmentManager().getBackStackEntryCount() == 0) {
            alertforExitApp();
           // this.finish();
        } else {
            getFragmentManager().popBackStack();
        }*/
    }

    public void alertforExitApp() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setMessage("Tem a certeza de que pretende sair da aplicação?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                // Write your code here to invoke YES event
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Locale locale = new Locale("pt");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        fragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveFireBasePushToken(String token){
        if (!Utility.isStringNullOrBlank(sm.getuserid())) {
            String id = sm.getuserid() + "_android";
            fireTokenRef.child(id).setValue(token);
        }
    }

    public void showReceivedPushMessage() {

        Query query = fireMsgRef.orderByKey().limitToLast(10);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{
                    String text = "";
                    String receiverID = "";
                    String status = "0";
                    String key = "";
                    PushModel model = new PushModel("","", "","");
                    for(DataSnapshot child: dataSnapshot.getChildren()){
                        //Log.e("test", "******** " + child.child("comment").getValue().toString());
                        //Log.e("test", "******** " +  child.child("customerID").getValue().toString());

                        status = child.child("status").getValue().toString();
                        key = child.getKey();

                        text = child.child("comment").getValue().toString();
                        receiverID = child.child("customerID").getValue().toString();
                        model = new PushModel(child.child("orderID").getValue().toString(),
                                child.child("deliveryName").getValue().toString(),
                                child.child("customerID").getValue().toString() ,
                                child.child("comment").getValue().toString());


                        if(receiverID.equalsIgnoreCase(sm.getuserid()) && status.equalsIgnoreCase("0")){
                            fireMsgRef.child(key).setValue(model);

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                            //alertDialog.setMessage(text);
                            alertDialog.setMessage("A sua entrega do dia foi efetuada!");
                            alertDialog.setTitle("Homem Do Pão");

                            // Setting Negative "NO" Button
                            alertDialog.setNegativeButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            // Showing Alert Message
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }
                    }

                }catch (Exception e){}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });


    }
}






