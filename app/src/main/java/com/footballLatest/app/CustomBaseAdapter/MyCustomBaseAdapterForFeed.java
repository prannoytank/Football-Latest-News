package com.footballLatest.app.CustomBaseAdapter;

import java.util.ArrayList;


import com.footballLatest.app.Bean.FeedBean;

import android.content.Context;
//import android.support.v7.appcompat.R;
import com.footballLatest.app.R;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCustomBaseAdapterForFeed extends BaseAdapter
{
	private static ArrayList<FeedBean> feedList;
    private static final String LOGCAT = null;
	private LayoutInflater mInflater;

	public MyCustomBaseAdapterForFeed(Context context, ArrayList<FeedBean> feedResults)
	{

        feedList = feedResults;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return feedList.size();
	}

	public Object getItem(int position) {
		return feedList.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.custom_row_view, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView)convertView.findViewById(R.id.title);
			holder.txtHeadline = (TextView) convertView.findViewById(R.id.headline);
			holder.txtLink = (TextView) convertView.findViewById(R.id.link);
			holder.txtPubDate=(TextView)convertView.findViewById(R.id.pubDate);
			holder.iv=(ImageView)convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}
        //Log.i(LOGCAT, "Title-->" + feedList.get(position).getTitle());
		holder.txtTitle.setText(feedList.get(position).getTitle());
		holder.txtHeadline.setText(feedList.get(position).getDesc());
		holder.txtLink.setText(feedList.get(position).getLink());
		holder.txtPubDate.setText(feedList.get(position).getPubDate());
       // holder.iv.setImageResource(R.id.ico);
		
		
		return convertView;
	}

	static class ViewHolder
	{
		TextView txtTitle;
		TextView txtHeadline;
		TextView txtLink;
		TextView txtPubDate;
		ImageView iv;
	}
}
