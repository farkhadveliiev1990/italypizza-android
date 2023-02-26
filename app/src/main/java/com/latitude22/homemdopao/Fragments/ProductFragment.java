package com.latitude22.homemdopao.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.latitude22.homemdopao.Adapter.BreadShopAdapter;
import com.latitude22.homemdopao.Adapter.CategoryAdapter;
import com.latitude22.homemdopao.Bean.BreadShopData;
import com.latitude22.homemdopao.Bean.CategoryBean;
import com.latitude22.homemdopao.Constants;
import com.latitude22.homemdopao.DetailActivity;
import com.latitude22.homemdopao.InternetConnection;
import com.latitude22.homemdopao.MainActivity;
import com.latitude22.homemdopao.MyCart;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.UtilityMethod;
import com.melnykov.fab.FloatingActionButton;
import com.norbsoft.typefacehelper.TypefaceHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Anuved on 12-08-2016.
 */
public class ProductFragment extends android.support.v4.app.Fragment {
    GridView product_list;
    ListView SelectCategoryLIst;
    TextView tv_product;
    public EditText txt_search;
    ArrayList<BreadShopData> breadShopList;
    ArrayList<BreadShopData> showList = new ArrayList<>();
    ArrayList<CategoryBean> values;
    Dialog category_dialog;

    static Context Mcontext;
    ProgressDialog progress;
    static android.support.v4.app.FragmentManager fm;
    TextView loading_bar;

    int mTotalItemCount = 10;
    public int cat_id=13,pno=0, old_cat_id=13;
    boolean no_value= false;
    BreadShopAdapter adapter;

    Timer timer;

    public static android.support.v4.app.Fragment getInstance(Context Mcntx, FragmentManager FM) {
        Mcontext = Mcntx;
        fm = FM;
        android.support.v4.app.Fragment frag = new ProductFragment();
        return frag;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View v = inflater.inflate(R.layout.product_fragment, container, false);
        TypefaceHelper.typeface(v);
        product_list = (GridView) v.findViewById(R.id.product_list);
        tv_product = (TextView) v.findViewById(R.id.tv_product);
        loading_bar= (TextView) v.findViewById(R.id.loading_bar);
        breadShopList = new ArrayList<BreadShopData>();
        txt_search = v.findViewById(R.id.product_txt_word);
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                pno = 0;
                                breadShopList.clear();
                                mTotalItemCount = 10;
                                getDataFromServerForCateg(cat_id, txt_search.getText().toString());
                            }
                        },
                        500
                );
            }
        });

//        txt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME) {
//                    //
//                }
//                return false;
//            }
//        });

        Button btn_cancel = v.findViewById(R.id.product_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_search.setText("");
            }
        });

        MainActivity.main_icon.setVisibility(View.GONE);
        MainActivity.cart.setVisibility(View.VISIBLE);
        MainActivity.cart.setImageResource(R.drawable.icon_cart);
        MainActivity.header_text.setVisibility(View.VISIBLE);
        MainActivity.header_text.setText("Produtos");
        MainActivity.main_cart_quantity.setVisibility(View.VISIBLE);
        MainActivity.icon_filter.setVisibility(View.VISIBLE);

        MainActivity.icon_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectFilter();
            }
        });
        try{
            showProgress();
            getDataFromServerForCateg(cat_id, "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

       // showProgress();


        MainActivity.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MyCart.class);
                // Intent intent1 = new Intent(MainActivity.this, MyCartActivity.class);
                startActivity(intent1);
                getActivity().finish();
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

        product_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                //
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == mTotalItemCount)) {
                    loading_bar.setVisibility(View.VISIBLE);
                    mTotalItemCount = mTotalItemCount + 10;

                    getDataFromServerForCateg(cat_id, txt_search.getText().toString());
                }
            }
        });

        return v;
    }

    public void getDataFromServerForCateg(final int id, String key ) {
        loading_bar.setVisibility(View.GONE);
        Log.d("StoreResult",  cat_id+" getDataFromServerForCateg "+pno+" id "+id);
        String ApiData = Constants.BASE_URL + "webservice/get_products";
        if(InternetConnection.isInternetOn(getActivity())) {
            // breadShopList.clear();
            Ion.with(getActivity()).load(ApiData)
                    .setBodyParameter("PNO", String.valueOf(pno))
                    .setBodyParameter("category", String.valueOf(id))
                    .setBodyParameter("search_terms", key)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        UtilityMethod.alertforServerError(getActivity(),"0");
                    } else {
                        if (e == null) {
                            try {
                                JSONObject jdata = new JSONObject(result);
                                String status = jdata.getString("status");

                                if(status.equals("0"))
                                {
                                    Log.d("StoreResult 0 ", cat_id + " cat_id "+old_cat_id);
                                    no_value = false;
                                    cat_id = old_cat_id;
                                    hideProgress();
                                }else if (status.equals("1")) {
                                    old_cat_id = cat_id;

                                    Log.d("StoreResult 1 ", cat_id + " old cat_id "+old_cat_id);
                                    JSONArray jitem = jdata.getJSONArray("Productlist");
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
                                        breadShopData.setProduct_Image(jsonData.optString("ProductImage"));
                                        breadShopList.add(breadShopData);
                                    }
                                    if(breadShopList.size()>0)
                                    {
                                        setAdapter();
                                    }
                                    else
                                    { tv_product.setVisibility(View.VISIBLE);
                                        hideProgress();
                                    }
                                }
                            } catch (JSONException e1) {
                                hideProgress();
                            }
                        } else {
                            hideProgress();
                        }
                    }
                }
            });
        }
        else
        {
            Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
        }
    }

    public String cvtPrToEn (String target) {
        target = Normalizer.normalize(target, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return target;
    }

    public void getShowList () {
        showList.clear();
        if (breadShopList.size() == 0) {
            return;
        }
        String schStr = txt_search.getText().toString();
        if (schStr.length() == 0) {
            showList = (ArrayList<BreadShopData>) breadShopList.clone();
        } else {
            String[] schKeys = schStr.split(" ");
            for (BreadShopData item : breadShopList) {
                boolean flag = true;
                for (String key: schKeys) {
                    String target = item.getProduct_name();
                    target = cvtPrToEn(target);

                    key = cvtPrToEn(key);
                    key = key.toLowerCase();

                    if (target.toLowerCase().contains(key)) {
                        continue;
                    }

                    flag = false;
                }
                if (flag) {
                    showList.add(item);
                }
            }
        }
    }

    public void setAdapter() {
        hideProgress();

        if (MainActivity.isSearchKey) {
            MainActivity.isSearchKey = false;
            txt_search.requestFocus();
        }

        Log.i("storeResult "," adapter "+ breadShopList.size());
        if (breadShopList != null & breadShopList.size() > 0) {
            this.pno  = pno + 1;
            if (adapter == null) {
                adapter = new BreadShopAdapter(breadShopList, getActivity());
                product_list.setAdapter(adapter);
            } else adapter.notifyDataSetChanged();

            product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("Productid",breadShopList.get(i).getProduct_id());
                    intent.putExtra("Productname", breadShopList.get(i).getProduct_name());
                    intent.putExtra("Productprice", breadShopList.get(i).getProduct_Price());
                    intent.putExtra("ProductImage", breadShopList.get(i).getProduct_Image());
                    intent.putExtra("ProductDescription", breadShopList.get(i).getProduct_Description());
                    startActivity(intent);
                }
            });
        }
    }

    public void SelectFilter() {
        category_dialog = new Dialog(getActivity());
        category_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  category_dialog.setContentView(R.layout.filtter);

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dislogView = inflater
                .inflate(R.layout.productlist_dialog, null);

        category_dialog.setContentView(dislogView);

        category_dialog.setCancelable(false);

        TextView Catgory_heading = (TextView) dislogView.findViewById(R.id.Catgory_heading);
        TextView Cancel_Catgory = (TextView) dislogView.findViewById(R.id.Cancel_Catgory);
        Catgory_heading.setText("Filtro");


        SelectCategoryLIst = (ListView) dislogView.findViewById(R.id.SelectCategoryLIst);
        values = new ArrayList<CategoryBean>();

            String ApiData = Constants.BASE_URL + "webservice/get_categories";
            if(InternetConnection.isInternetOn(getActivity())) {
                showProgress();
                Ion.with(getActivity()).load(ApiData).setBodyParameter("PNO","0")
                        .asString().setCallback(new FutureCallback<String>() {

                    @Override
                    public void onCompleted(Exception e, String result) {
                        hideProgress();
                        if (UtilityMethod.isStringNullOrBlank(result)) {

                            UtilityMethod.alertforServerError(getActivity(),"0");
                        }
                        else {
                            Log.d("StoreResult", result + "");
                            hideProgress();
                            if (e == null) {

                                try {
                                    JSONObject jdata = new JSONObject(result);

                                    String status = jdata.getString("status");
                                    if (status.equals("1")) {

                                        JSONArray jitem = jdata.getJSONArray("Categorylist");
                                        for (int i = 0; i < jitem.length(); i++) {
                                            JSONObject jsonData = jitem.getJSONObject(i);
                                            CategoryBean category = new CategoryBean();
                                            category.setCategoryId(jsonData.optString("CategoryID"));
                                            category.setCategoryName(jsonData.optString("CategoryName"));
                                            values.add(category);
                                        }

                                        CategoryBean category = new CategoryBean();
                                        category.setCategoryId("13");
                                        category.setCategoryName("Mostrar tudo");
                                        values.add(category);
                                        hideProgress();
                                        SelectCategoryLIst.setAdapter(new CategoryAdapter(values,getActivity()));
                                    } else {
                                        hideProgress();
                                    }
                                } catch (JSONException e1) {
                                    hideProgress();
                                }

                            } else {
                                hideProgress();
                            }

                        }
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(), "Verifique a ligação de internet", Toast.LENGTH_SHORT).show();
            }

        SelectCategoryLIst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int categoryID = Integer.parseInt(values.get(position).getCategoryId());
                cat_id = categoryID;  breadShopList.clear();
                pno=0;mTotalItemCount = 10;
               breadShopList.clear();
                adapter.notifyDataSetChanged();
                getDataFromServerForCateg(categoryID, txt_search.getText().toString());
                category_dialog.dismiss();
            }
        });

        Cancel_Catgory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                category_dialog.dismiss();

            }
        });

        category_dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        category_dialog.show();
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
        if (!progress.isShowing()) {
            return;
        }
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }

}