package com.footballLatest.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.footballLatest.app.Util.AccountsDetails;
import com.footballLatest.app.Util.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by AshirwadTank on 1/31/14.
 */
public class bbcNewsSinglePage extends Activity {

    TextView foxTitle,foxContent;
    ImageView foxImage;
    String[] actions = new String[] {
            "Share",
            "Twitter",

    };
    String feedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fox_sports_single_page);


        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        foxTitle=(TextView)findViewById(R.id.FoxTitle);

        foxContent=(TextView)findViewById(R.id.FoxContent);
        foxImage=(ImageView)findViewById(R.id.FoxImage);

        Intent intent = getIntent();
        String url=intent.getStringExtra("URL");
        feedTitle=intent.getStringExtra("TITLE");

        class foxSportsSingle extends AsyncTask<String,Void,Void> {
            //ImageView image;
            TextView content;
            String url;
            //TextView title;
            Document doc,titleDoc;
            Elements description,image,title;
            String MainContent;
            String MainTitle;
            String MainImage;
            ProgressDialog mProgressDialog = new ProgressDialog(bbcNewsSinglePage.this);
            Bitmap bitmap;
            foxSportsSingle(String url)
            {
                //this.image=image;
                this.content=content;
                this.url=url;
                //this.title=title;
            }

            @Override
            protected Void doInBackground(String... strings) {

                try {
                    doc = Jsoup.connect(url).get();
                   if(doc.select(".story-body .article #headline h1").hasText() == true)
                   {
                    title=doc.select(".story-body .article #headline h1");
                    MainTitle=title.text().toString();
                   }
                    else
                   {
                      MainTitle=feedTitle;
                   }
                    // titleDoc=Jsoup.parse(title.toString());

                    description = doc.select(".story-body .article p");
                    image=doc.select(".story-body .story-feature img");
                    MainImage=image.attr("abs:src");
                    URL url = new URL(MainImage);
                    bitmap = BitmapFactory.decodeStream(url.openStream());
                    //image=image.attr("src");
                    Document doc = Jsoup.parse(description.toString());
                    doc.select("p").prepend("\\n\\n");
                    MainContent=doc.text().replace("\\n", "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                // mProgressDialog.setTitle("Android Basic JSoup Tutorial");
                mProgressDialog.setMessage("Fetching...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }


            protected void onPostExecute(Void result) {
                foxTitle.setText(MainTitle); //Sets the title
                foxImage.setImageBitmap(bitmap);
                foxContent.setText(MainContent);
                if(mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                }

            }
        }
        new foxSportsSingle(url).execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.social_share, menu);
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

    class updateTwitterStatus extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;
        SharedPreferences mSharedPreferences;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(bbcNewsSinglePage.this);
            pDialog.setMessage("Updating to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            //Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(AccountsDetails.TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(AccountsDetails.TWITTER_CONSUMER_SECRET);
                // Access Token
                String access_token = mSharedPreferences.getString(Constants.TWITTER_PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(Constants.TWITTER_PREF_KEY_OAUTH_SECRET, "");

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // Update status
                twitter4j.Status response = null;

                    response = twitter.updateStatus(status);
            }catch (TwitterException e) {
                    e.printStackTrace();
                }



            return null;

        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Status tweeted successfully", Toast.LENGTH_SHORT)
                            .show();
                    // Clearing EditText field
                    //sts.setText("");
                }
            });
        }
    }





}