package com.latitude22.homemdopao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.latitude22.homemdopao.Adapter.FixedPlanAdapter;
import com.latitude22.homemdopao.Adapter.FixedPlanProductAdapter;
import com.latitude22.homemdopao.Bean.FixedPlanProductBean;
import com.latitude22.homemdopao.Bean.FixedplanBean;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

public class DetailSubscribeActivity extends AppCompatActivity {

    ActionBar actionBar;
    ArrayList<FixedplanBean> fixedplanlist;
    TextView detail_breadName,detail_price,detail_breaddescrip;
    ImageView detail_breadImg,header_img;
    Button bt_detai_sbscrbe;
    String plandescription;
    private String productPrice;
    public int Quntity;
    ListView fixedplanDetail_listItem;
    TextView detail_cart_quantity,product_description;
    FixedplanBean fixedplanItem;
    SessionManager sm;
    ArrayList<FixedPlanProductBean> productlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fixedplan_detail);
        TypefaceHelper.typeface(this);

        final TextView tv_detail_count = (TextView) findViewById(R.id.tv_detail_count);
        detail_cart_quantity= (TextView) findViewById(R.id.detail_cart_quantity);
        fixedplanDetail_listItem = (ListView) findViewById(R.id.fixedplanDetail_listItem);
        product_description = (TextView)findViewById(R.id.product_description);
      //  bt_detai_sbscrbe = (Button)findViewById(R.id.bt_detai_sbscrbe);
        fixedplanlist = new ArrayList<FixedplanBean>();
        fixedplanItem = new FixedplanBean();

        sm =new SessionManager(this);


      //  Subscribe = (Button) findViewById(R.id.Subscribe);
      //  Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
      //  setActionBar(tb);
        actionBar = getSupportActionBar();
      //  actionBar.hide();

            String listSting = getIntent().getStringExtra("productlist");
            plandescription = getIntent().getStringExtra("ProductDescription");
             productPrice = getIntent().getStringExtra("FixedPlanPrice");

        product_description.setText(Html.fromHtml(""+plandescription ));

     /*   bt_detai_sbscrbe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.isStringNullOrBlank(sm.getuserid())) {

                    Intent i = new Intent(DetailSubscribeActivity.this, LoginActivity.class);
                    startActivity(i);
                    //finish();
                } else {
                    Intent i = new Intent(DetailSubscribeActivity.this, PaymentSubscribActivity.class);
                    i.putExtra("PyaAmmount", productPrice);
                    startActivity(i);
                    finish();
                }
            }
        });
        */
        productlist = new ArrayList<FixedPlanProductBean>();
        JSONArray jpitem = null;
        try {
            jpitem = new JSONArray(listSting);


        for (int j = 0; j < jpitem.length(); j++) {

            FixedPlanProductBean bean= new FixedPlanProductBean();

            JSONObject obj=new JSONObject();
            JSONObject jData = jpitem.getJSONObject(j);
            bean.setFixedProductId(jData.optInt("ProductID"));
            bean.setFixedProductName(jData.optString("ProductName"));
            bean.setFixedProductPrice(jData.optString("ProductPrice"));
            productlist.add(bean);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        header_img = (ImageView)findViewById(R.id.header_img);
        detail_breadName = (TextView) findViewById(R.id.detail_breadName);
        detail_price = (TextView) findViewById(R.id.detail_price);
        detail_breaddescrip = (TextView) findViewById(R.id.detail_breaddescrip);
        detail_breadImg = (ImageView) findViewById(R.id.detail_breadImg);
       LinearLayout toolbar_detail = (LinearLayout) findViewById(R.id.detail_header);

        fixedplanDetail_listItem.setAdapter(new FixedPlanProductAdapter(productlist,this));

       /* Subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                detail_cart_quantity.setText("" + QuntityTotal());
            }
        });*/
        header_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setdata();
//        detail_cart_quantity.setText("" + QuntityTotal());



    }



    public void setdata(){
        Intent i= getIntent();
        if(i!=null){



             String productDescription = i.getStringExtra("ProductDescription");
            String fixedplanid = i.getStringExtra("FixedplanId");
           // String fixedproductname = i.getStringExtra("Productname");
           // String Productid = i.getStringExtra("Productid");
          //  detail_breadName.setText("productName");
           // detail_price.setText(""+productPrice);
          //  detail_breaddescrip.setText(productDescription);





        }


    }

    public int QuntityTotal(){
        int  total=0;
        ArrayList<AddCart> addCarts= (ArrayList<AddCart>) AddCart.AddCart();
        for(int i=0;i<addCarts.size();i++){
            total =total+(addCarts.get(i).getProductquantity());
        }
        MainActivity.main_cart_quantity.setText("" + total);
        return  total;
    }
}
