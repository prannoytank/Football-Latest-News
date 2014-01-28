package com.footballLatest.app;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.footballLatest.app.Bean.FeedBean;
import com.footballLatest.app.CustomBaseAdapter.MyCustomBaseAdapterForFeed;
import com.footballLatest.app.Modal.XmlParsing;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoxSportsFeed extends Activity {
    private static final String LOGCAT = null;
    public static FoxSportsFeed instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sky_sports);
        instance=this;
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/



        class RetreiveFeedTask extends AsyncTask<String,Void,Void> {
            ListView lv;
            ArrayList<FeedBean> feedList=new ArrayList<FeedBean>();

            MyCustomBaseAdapterForFeed feedAdapter;
            public InputStream getInputStream(URL url) {
                try {
                    return url.openConnection().getInputStream();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            protected Void doInBackground(String... strings) {

                /*Checks if Internet Connection is their or not */

                if(!isNetworkAvailable(FoxSportsFeed.this)) {
                    Toast.makeText(FoxSportsFeed.this, "No Internet connection", Toast.LENGTH_LONG).show();
                    finish(); //Calling this method to close this activity when internet is not available.
                }
                XmlParsing xp=new XmlParsing();
                feedList=xp.getFeedData("http://www.foxsportsasia.com/football-rss/"); //Gets Feeds
                return null;
            }

            protected void onPreExecute()
            {
                ProgressDialog progress = new ProgressDialog(FoxSportsFeed.this);
                progress.setTitle("Loading");
                progress.setMessage("Please Wait...");
                progress.show();
                progress.dismiss();




            }



            protected void onPostExecute(Void result) {
                //super.onPostExecute();
                //ArrayAdapter adapter =new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,headlines);
                feedAdapter=new MyCustomBaseAdapterForFeed(FoxSportsFeed.this, feedList);
                //setListAdapter();

                lv=(ListView)findViewById(R.id.list_sky);
                lv.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();

            }
        }
        new RetreiveFeedTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected())
            return true;
        else
            return false;
    }

}




