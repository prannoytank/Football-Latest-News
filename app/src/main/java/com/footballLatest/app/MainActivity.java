package com.footballLatest.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.footballLatest.app.Bean.FeedBean;
import com.footballLatest.app.CustomBaseAdapter.MyCustomBaseAdapterForFeed;
import com.footballLatest.app.Modal.XmlParsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import twitter4j.Twitter;


public class MainActivity extends Activity implements View.OnClickListener{
    private static final String LOGCAT = null;
    public static MainActivity instance;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance=this;
      lv=(ListView)findViewById(android.R.id.list);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/



        class RetreiveFeedTask extends AsyncTask<String,Void,Void> {

            ArrayList<FeedBean> feedList=new ArrayList<FeedBean>();
            ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
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

                if(!isNetworkAvailable(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "No Internet connection", Toast.LENGTH_LONG).show();
                    finish(); //Calling this method to close this activity when internet is not available.
                }
                XmlParsing xp=new XmlParsing();
                feedList=xp.getFeedData("http://feeds.bbci.co.uk/sport/0/football/rss.xml"); //Gets Feeds
                return null;
            }

// Binding data
            protected void onPreExecute()
            {
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
            protected void onPostExecute(Void result) {
                //super.onPostExecute();
                //ArrayAdapter adapter =new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,headlines);
                feedAdapter=new MyCustomBaseAdapterForFeed(MainActivity.this, feedList);
                //setListAdapter();

                lv=(ListView)findViewById(android.R.id.list);
                lv.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();
                if(mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }

            }
        }
        new RetreiveFeedTask().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                Object dataObject = parent.getItemAtPosition(position);
                FeedBean fullObject = (FeedBean)dataObject;
                Intent i = new Intent(MainActivity.this,bbcNewsSinglePage.class);
                // i.setData(Uri.parse(fullObject.getLink()));
                i.putExtra("URL",fullObject.getLink());
                i.putExtra("TITLE",fullObject.getTitle());
                startActivity(i);

            }
        });


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

    @Override
    public void onClick(View view) {

    }
}




