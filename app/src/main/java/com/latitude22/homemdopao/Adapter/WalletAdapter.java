package com.latitude22.homemdopao.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.latitude22.homemdopao.Bean.WalletHistory;
import com.latitude22.homemdopao.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class WalletAdapter extends BaseAdapter {


	Context context;
	ArrayList<WalletHistory> summary_list;
	Typeface tf;


	public WalletAdapter(Context context, ArrayList<WalletHistory> summary_list) {
		this.context = context;
		this.summary_list = summary_list;

	}

	@Override
	public int getCount() {
		return summary_list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup container) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


		convertView = inflater.inflate(R.layout.activity_wallet_list, null);
		TextView tv_status=(TextView)convertView .findViewById(R.id.tv_status);
		TextView tv_desc=(TextView)convertView .findViewById(R.id.tv_desc);
		TextView tv_amount=(TextView)convertView .findViewById(R.id.tv_amount);

		tv_status.setText(summary_list.get(position).wallet_reason);
		tv_desc.setText((summary_list.get(position).deducted_date));


		String mDate ="-";

		if(!summary_list.get(position).added_date.equals("0000-00-00 00:00:00"))
			mDate = summary_list.get(position).added_date;
		else if(!summary_list.get(position).deducted_date.equals("0000-00-00 00:00:00"))
			mDate = summary_list.get(position).deducted_date;




		tv_desc.setText(parseDateToddMMyyyy(mDate));

		String mAmount ="-";

		if(!summary_list.get(position).added_amt.equals("0.00"))
			mAmount = summary_list.get(position).added_amt;
		else if(!summary_list.get(position).deducted_amt.equals("0.00"))
			mAmount = summary_list.get(position).deducted_amt;

		tv_amount.setText(mAmount+"€");

		return convertView;
	}



	public String parseDateToddMMyyyy(String time) {
		String inputPattern = "yyyy-MM-dd HH:mm:ss";
		String outputPattern = "dd-MMM-yyyy h:mm a";
		SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

		Date date = null;
		String str = null;

		try {
			date = inputFormat.parse(time);
			str = outputFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}


}
