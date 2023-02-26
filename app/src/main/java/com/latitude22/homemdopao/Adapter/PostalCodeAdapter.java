package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.FaqListBean;
import com.latitude22.homemdopao.Bean.PostalCodeBean;
import com.latitude22.homemdopao.R;
import com.latitude22.homemdopao.SignUpActivity;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 05-01-2017.
 */

public class PostalCodeAdapter extends BaseAdapter implements Filterable {

    Context Mcontext;
    static TextView faqtext;
  public static   ArrayList<PostalCodeBean> arrayList;
    private ArrayList<PostalCodeBean> filterarraylist;
    ArrayList<PostalCodeBean> filterarraylist1;

    public PostalCodeAdapter(ArrayList<PostalCodeBean> arrayList, Context mcontext) {
        this.arrayList = arrayList;
        this.filterarraylist = arrayList;
        this.filterarraylist1=arrayList;
        Mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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

        convertView = layoutInflater.inflate(R.layout.postal_search_item, null);

        TypefaceHelper.typeface(convertView);

      /*  try{
            if (fixedplanlist.get(position).getImage()!=null&!fixedplanlist.get(position).getImage().equals""){
                Picasso.with(Mcontext).load(fixedplanlist.get(position).getImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(item_image);
            }

        }catch (Exception ae){

        }*/
        // item_image.setImageResource(listItem.get(position).getBreadImg());

        TextView postal_code = (TextView) convertView.findViewById(R.id.tv_postal_code);
        //  postal_code.setText("Hello");
        postal_code.setText(arrayList.get(position).getPostalCode());

        postal_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.textView_postal.setText(arrayList.get(position).getPostalCode());
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               /* if(results.count > 0)

                {
                    addAll(((ArrayList<FaqListBean>) results.values));
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }*/
                arrayList =filterarraylist; // has the filtered values

                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetChanged();
                }

              /*  fixedplanlist = (ArrayList<FaqListBean>) results.values; // has the filtered values
                notifyDataSetChanged();
                // notifies the data with new filtered values
              if (results.count==0){
                //  Utility.showToast("demo",Mcontext);
//                  notifyDataSetChanged();
              }else{
                  notifyDataSetInvalidated();
              }*/

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<FaqListBean> FilteredArrList = new ArrayList<FaqListBean>();

                if (filterarraylist1 == null) {
                    arrayList = new ArrayList<PostalCodeBean>(filterarraylist); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/


                filterarraylist=new ArrayList<>();
                int count = filterarraylist1.size();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = filterarraylist1.size();
                    results.values = filterarraylist1;
                    notifyDataSetChanged();
                } /*else {

                    String PrefixString = constraint.toString().toLowerCase();

                    final ArrayList<FaqListBean> NewValues = new ArrayList<FaqListBean>();

                    for(FaqListBean Word : OriginalValues){
                        String ValueText = Word.getFaqQuestion().toLowerCase();

                        if(ValueText.startsWith(PrefixString))
                            NewValues.add(Word);
                    }*/
                else {
                    final ArrayList<FaqListBean> NewValues = new ArrayList<FaqListBean>();
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < count; i++) {
                        String data = filterarraylist1.get(i).getPostalCode();
                        //  String data = NewValues.get(i).getFaqQuestion();
                        PostalCodeBean bean;
                        bean = filterarraylist1.get(i);

                        /*if (ValueText.startsWith(PrefixString))
                            NewValues.add(Word);*/
                        if (data.contains(constraint)) {
                            filterarraylist.add(bean);
                        }
                        // set the Filtered result to return
                        results.count = filterarraylist.size();
                        results.values = filterarraylist;

                    }
                }
                return results;
            }
        };
        return filter;
    }

}
