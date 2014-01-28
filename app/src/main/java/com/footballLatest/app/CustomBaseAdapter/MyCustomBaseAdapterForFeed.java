package com.footballLatest.app.CustomBaseAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import com.footballLatest.app.Bean.FeedBean;

import android.content.Context;
//import android.support.v7.appcompat.R;
import com.footballLatest.app.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
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
    private Handler hand = new Handler();
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
         int p=position;
	     ViewHolder holder;
        String s;
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
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            Date date = formatter.parse(feedList.get(position).getPubDate());
             s = formatter.format(date);
            holder.txtPubDate.setText(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    /* if(feedList.get(p).getImageLink()!= null || feedList.get(p).getImageLink()!= "null" || feedList.get(p).getImageLink()!= "")
     {
        new Thread(new Runnable() {
            public void run() {
                try {

                    URL url = new URL(feedList.get(p).getImageLink());
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    final Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    hand.post(new Runnable() {
                        public void run() {
                            holder.iv.setImageBitmap(myBitmap);
                        }
                    });

                    connection.disconnect();

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

     }
        else{
        holder.iv.setImageResource(R.id.icon);
     }*/
		
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
