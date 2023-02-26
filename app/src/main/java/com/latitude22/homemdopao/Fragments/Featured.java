package com.latitude22.homemdopao.Fragments;

/**
 * Created by Anuved on 06-08-2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.BreadShopAdapter;
import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.DetailActivity;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;
import com.norbsoft.typefacehelper.TypefaceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//Our class extending fragment
public class Featured extends Fragment {


    ListView listView;
    TextView img_home_appname;
    ArrayList<BreadShopData> breadShopList;
    ProgressDialog progress;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    private long fileSize = 0;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1, container, false);
        TypefaceHelper.typeface(view);
        ImageView img_tab1 = (ImageView) view.findViewById(R.id.img_tab1);
        listView = (ListView) view.findViewById(R.id.list_item_tab1);

        //new Delete().from(AddCart.class).execute();
        MainActivity.main_icon.setVisibility(View.VISIBLE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_cart);
        MainActivity.header_text.setVisibility(View.GONE);
        MainActivity.main_cart_quantity.setVisibility(View.VISIBLE);
        MainActivity.icon_filter.setVisibility(View.GONE);
        img_home_appname = (TextView) view.findViewById(R.id.img_home_appname);
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        breadShopList = new ArrayList<BreadShopData>();


        ImageView img = (ImageView) view.findViewById(R.id.img_tab1);
     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    startActivity(intent);
                }
            }
        });
*/
    /*    img.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               Intent intent = new Intent(getActivity(), DetailActivity.class);
                                               startActivity(intent);{"status":"1","Productlist":[{"ProductID":"102","ProductName":"Gelado Requeij\u00e3o com Ab\u00f3bora 150 ml","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":2.08,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527178737abobora.jpg"},{"ProductID":"100","ProductName":"Gelado Ovos Moles de Aveiro 150 ml","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":2.08,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527178663ovosmoles.jpg"},{"ProductID":"94","ProductName":"Bagel Artesanal 7 Sementes 100gr","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.12,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527176784bagel7sem.jpg"},{"ProductID":"93","ProductName":"Bagel Brioche Sementes Papoila 90gr","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.8,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527176727bagelpapoila.jpg"},{"ProductID":"87","ProductName":"Sumo Sonatural Mix Morango 250 ml","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.52,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527170926mixmorango.jpg"},{"ProductID":"86","ProductName":"Sumo Sonatural Mix Manga\/Laranja 250 ml","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.76,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527170890mixmangalaranja.jpg"},{"ProductID":"76","ProductName":"\u00c1gua Healsi Fucsia 1Ltr ","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.44,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527165669healsif.jpg"},{"ProductID":"74","ProductName":"\u00c1gua Healsi Turquesa 0.5Ltr ","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.96,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527165569healsib.jpg"},{"ProductID":"72","ProductName":"\u00c1gua Healsi Cristal 1Ltr ","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.44,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1527165348healsiw.jpg"},{"ProductID":"69","ProductName":"P\u00e3o de Mafra","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.12,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525353996paomafra.jpg"},{"ProductID":"68","ProductName":"P\u00e3o da Serra","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.12,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525353867paoserra.jpg"},{"ProductID":"67","ProductName":"Bolo Caco Tradicional 100gr","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.88,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525353784bolocaco.jpg"},{"ProductID":"53","ProductName":"Iogurte Grego de Leite Integral 500g","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":4.72,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525353025ygi500.jpg"},{"ProductID":"51","ProductName":"P\u00e3o Saloio Grande","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":1.04,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525352659saloiogrande.jpg"},{"ProductID":"48","ProductName":"P\u00e3o Milho Girassol","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.28,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525352390milhogirassol.jpg"},{"ProductID":"17","ProductName":"P\u00e3o Espinafre","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.28,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350901espinafres.jpg"},{"ProductID":"16","ProductName":"P\u00e3o de Centeio","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.68,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350758centeiogrande.jpg"},{"ProductID":"15","ProductName":"P\u00e3o Centeio Pq","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.28,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350690centeiopequeno.jpg"},{"ProductID":"14","ProductName":"Cacete","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.76,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350836cacete.jpg"},{"ProductID":"13","ProductName":"Bola de \u00c1gua Pq","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.21,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350557bolaagua.jpg"},{"ProductID":"12","ProductName":"Bijou","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.16,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350436bijou.jpg"},{"ProductID":"11","ProductName":"P\u00e3o de Beterraba","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.28,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525350879beterraba.jpg"},{"ProductID":"10","ProductName":"5 Sementes 60 Grs","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.28,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/15253503367sementes.jpg"},{"ProductID":"9","ProductName":"Bola R\u00fastica","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.19,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525344388bolarustica.jpg"},{"ProductID":"8","ProductName":"Vianinha","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.16,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525344296vianinha.jpg"},{"ProductID":"5","ProductName":"Carca\u00e7a","CategoryName":null,"SizeName":null,"TypeName":null,"SupplierName":null,"ProductDescription":null,"ProductPrice":0.16,"ProductIsFeatured":null,"ProductImage":"http:\/\/www.homemdopao.pt\/assets\/uploads\/products\/1525344268carcaca.jpg"}]}s
                                           }
                                       });
*/
        getDataFromServer();


        return view;
    }

    public void getDataFromServer() {
        String ApiData = Constants.BASE_URL + "webservice/get_featured_product?";
        if (InternetConnection.isInternetOn(getActivity())) {
            showProgress();
            Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO", "0")
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        Log.d("StoreResult", result + "");



                        if (e == null) {
                            if (UtilityMethod.isStringNullOrBlank(result)) {
                                hideProgress();
                                UtilityMethod.alertforServerError(getActivity(),"0");
                            } else {
                                try {
                                    hideProgress();
                                    JSONObject jdata = new JSONObject(result);
                                    String status = jdata.getString("status");

                                    if (status.equals("1")) {

                                        JSONArray jitem = jdata.getJSONArray("Productlist");
                                        Log.i(""," item "+ jitem.length());
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject jsonData = jitem.getJSONObject(i);
                                            BreadShopData breadShopData = new BreadShopData();
                                            breadShopData.setProduct_id(jsonData.optInt("ProductID"));
                                            breadShopData.setProduct_name(jsonData.optString("ProductName"));
                                            breadShopData.setCategory_name(jsonData.optString("CategoryName"));
                                            breadShopData.setSize_Name(jsonData.optString("SizeName"));
                                            breadShopData.setProduct_Description(jsonData.optString("ProductDescription"));
                                            breadShopData.setProduct_Price(jsonData.optDouble("ProductPrice"));
                                            breadShopData.setIsFeatured(jsonData.optString("ProductIsFeatured"));
                                            breadShopData.setType_Name(jsonData.optString("TypeName"));
                                            breadShopData.setSuplier_Name(jsonData.optString("SupplierName"));
                                            // breadShopData.setProduct_Description(jsonData.optString("ProductDescription"));
                                            // breadShopData.setProduct_Price(jsonData.optDouble("ProductPrice"));
                                            breadShopData.setProduct_Image(jsonData.optString("ProductImage"));
                                            breadShopList.add(breadShopData);
                                        }
                                        setAdapter();
                                        hideProgress();
                                    } else {
                                        hideProgress();
                                        Log.d("StoreResult2",  " status "+ status);
                                      //  Toast.makeText(getActivity(), "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e1) {
                                    //  Toast.makeText(getActivity(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                                    hideProgress();
                                }

                            }

                        } else {
                            Toast.makeText(getActivity(), "Erro Desconhecido", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();

        }
    }

    public void setAdapter() {

        //  breadShopList = (ArrayList<BreadShopData>) ShopinformationBean;
        if (breadShopList != null & breadShopList.size() > 0) {
            listView.setAdapter(new BreadShopAdapter(breadShopList, getActivity()));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Storename StorAddress
                    //to send data from next activity
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("Productname", breadShopList.get(i).getProduct_name());
                    intent.putExtra("Productprice", breadShopList.get(i).getProduct_Price());
                    intent.putExtra("ProductImage", breadShopList.get(i).getProduct_Image());
                    intent.putExtra("ProductDescription", breadShopList.get(i).getProduct_Description());
                    intent.putExtra("Productid", breadShopList.get(i).getProduct_id());
                    startActivity(intent);
                    //  getActivity().finish();
                }
            });
        }
    }

    public void showProgress() {
        try {
            progress = new ProgressDialog(getActivity());
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
}