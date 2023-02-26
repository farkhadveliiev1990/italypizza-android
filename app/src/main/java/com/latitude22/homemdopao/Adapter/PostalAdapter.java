package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.PostalCodeBean;
import com.latitude22.homemdopao.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
/**
 * Created by Anuved on 02-01-2017.
 */

public class PostalAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<PostalCodeBean>  arrayList;
    private String COUNTRY_URL = "http://www.l22.co.in/homemdopao/webservice/getpostalcode";

    public PostalAdapter(Context context, int resource,ArrayList<PostalCodeBean> arrayList) {
        super(context, resource);
        this.arrayList =arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return  arrayList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        //get data from the web
                       // String term = constraint.toString();
                   //     arrayList = (ArrayList) new DownloadCountry().execute(term).get();
                       // notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("HUS", "EXCEPTION " + e);
                    }
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.postal_search_item, parent, false);

        //get Country
        PostalCodeBean bean = (PostalCodeBean) arrayList.get(position);



        TextView postal_code = (TextView) view.findViewById(R.id.tv_postal_code);
      //  postal_code.setText("Hello");
        postal_code.setText(arrayList.get(position).getPostalCode());

        return view;
    }

    //download mCountry list
    private class DownloadCountry extends AsyncTask
    {
/*
      @Override
        protected ArrayList doInBackground (String params){
        try {
            //Create a new COUNTRY SEARCH url Ex "search.php?term=india"
            String NEW_URL = COUNTRY_URL + URLEncoder.encode(params[0], "UTF-8");
            Log.d("HUS", "JSON RESPONSE URL " + NEW_URL);

            URL url = new URL(NEW_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            //parse JSON and store it in the list
            String jsonString = sb.toString();
            ArrayList arrayList1 = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                //store the country name
                PostalCodeBean bean = new PostalCodeBean();
                bean.setPostalCode(jo.getInt("PostalCode"));
                arrayList1.add(bean);
            }

            //return the countryList
            return arrayList1;

        } catch (Exception e) {
            Log.d("HUS", "EXCEPTION " + e);
            return null;
        }
    }
*/


        @Override
        protected ArrayList doInBackground(Object[] params) {
            try {
                //Create a new COUNTRY SEARCH url Ex "search.php?term=india"
                String NEW_URL = COUNTRY_URL + URLEncoder.encode(String.valueOf(params[0]), "UTF-8");
                Log.d("HUS", "JSON RESPONSE URL " + NEW_URL);

                URL url = new URL(NEW_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                //parse JSON and store it in the list
                String jsonString = sb.toString();
                ArrayList arrayList1 = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    //store the country name
                    PostalCodeBean bean = new PostalCodeBean();
                  //  bean.setPostalCode(jo.getInt("PostalCode"));
                    arrayList1.add(bean);
                }

                //return the countryList
                return arrayList1;

            } catch (Exception e) {
                Log.d("HUS", "EXCEPTION " + e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

}