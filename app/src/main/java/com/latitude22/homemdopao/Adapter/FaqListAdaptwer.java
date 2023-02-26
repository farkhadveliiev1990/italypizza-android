package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.latitude22.homemdopao.Bean.FaqListBean;
import com.latitude22.homemdopao.Fragments.FaqFragment;
import com.latitude22.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Anuved on 19-08-2016.
 */
public class FaqListAdaptwer extends BaseAdapter implements Filterable {

    Context Mcontext;
    static TextView faqtext;
    ArrayList<FaqListBean> fixedplanlist;
    private ArrayList<FaqListBean> filterfplanlist;
    ArrayList<FaqListBean> fixedplanlist1;
    ImageView img_arrow;
    public boolean isClickedFirstTime = true;
    String index;
    public static TextView FaqAnswer;

    public FaqListAdaptwer(ArrayList<FaqListBean> fixedplanlist, Context mcontext, String index) {
        this.fixedplanlist = fixedplanlist;
        this.filterfplanlist = fixedplanlist;
        this.fixedplanlist1 = fixedplanlist;
        this.index = index;
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
        //  holder = new ViewHolder();

        convertView = layoutInflater.inflate(R.layout.faqitem, null);

        TypefaceHelper.typeface(convertView);
        img_arrow = (ImageView) convertView.findViewById(R.id.arrow);
      /*  try{
            if (fixedplanlist.get(position).getImage()!=null&!fixedplanlist.get(position).getImage().equals""){
                Picasso.with(Mcontext).load(fixedplanlist.get(position).getImage()).error(R.drawable.bread_images).placeholder(R.drawable.bread_images).into(item_image);
            }

        }catch (Exception ae){

        }*/
        // item_image.setImageResource(listItem.get(position).getBreadImg());
        FaqAnswer = (TextView) convertView.findViewById(R.id.FaqAnswer);
        faqtext = (TextView) convertView.findViewById(R.id.faqtext);

        faqtext.setText(fixedplanlist.get(position).getFaqQuestion());
        if (index.equals("0")) {
            if (FaqFragment.selectedindex == position) {
                FaqAnswer.setVisibility(View.VISIBLE);
                FaqAnswer.setText(fixedplanlist.get(position).getFaqAnswer().trim());
            } else {
                FaqAnswer.setVisibility(View.GONE);
                FaqAnswer.setText(fixedplanlist.get(position).getFaqAnswer().trim());
            }
        } else {
            FaqAnswer.setVisibility(View.VISIBLE);
            FaqAnswer.setText(fixedplanlist.get(position).getFaqAnswer().trim());

            if (FaqFragment.selectedindex == position) {
                FaqAnswer.setVisibility(View.VISIBLE);
                FaqAnswer.setText(fixedplanlist.get(position).getFaqAnswer().trim());
            } else {
                FaqAnswer.setVisibility(View.GONE);
                FaqAnswer.setText(fixedplanlist.get(position).getFaqAnswer().trim());
            }
        }

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
                fixedplanlist = filterfplanlist; // has the filtered values

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

                if (fixedplanlist1 == null) {
                    fixedplanlist = new ArrayList<FaqListBean>(filterfplanlist); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/

                ArrayList<FaqListBean> OriginalValues = new ArrayList<FaqListBean>(fixedplanlist);
                filterfplanlist = new ArrayList<>();
                int count = fixedplanlist1.size();
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    results.count = fixedplanlist1.size();
                    results.values = fixedplanlist1;
                    notifyDataSetChanged();
                } /*else {

                    String PrefixString = constraint.toString().toLowerCase();

                    final ArrayList<FaqListBean> NewValues = new ArrayList<FaqListBean>();

                    for(FaqListBean Word : OriginalValues){
                        String ValueText = Word.getFaqQuestion().toLowerCase();

                        if(ValueText.startsWith(PrefixString))
                            NewValues.add(Word);
                    }*/ else {
                    final ArrayList<FaqListBean> NewValues = new ArrayList<FaqListBean>();
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < count; i++) {
                        String data = fixedplanlist1.get(i).getFaqAnswer().toString();
                        String data1 = fixedplanlist1.get(i).getFaqQuestion().toString();
                        //  String data = NewValues.get(i).getFaqQuestion();
                        FaqListBean bean;
                        bean = fixedplanlist1.get(i);

                        /*if (ValueText.startsWith(PrefixString))
                            NewValues.add(Word);*/
                        if (data.toLowerCase().contains(constraint) || data1.toLowerCase().contains(constraint)) {
                            filterfplanlist.add(bean);
                        }
                        // set the Filtered result to return
                        results.count = filterfplanlist.size();
                        results.values = filterfplanlist;

                    }
                }
                return results;
            }
        };
        return filter;
    }

    private View.OnClickListener onarrowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


        }
    };

    /*private view holder class*/
    public static class ViewHolder {

        TextView offername;
        TextView offerprice;
        ImageView img_arrow;
        TextView offerdesc, plus_iv;
        LinearLayout diet_linear;
        int position;

    }
}
