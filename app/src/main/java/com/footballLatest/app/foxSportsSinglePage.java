package com.footballLatest.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class foxSportsSinglePage extends Activity {

    TextView foxTitle,foxContent,foxDate,foxHeading;
    ImageView foxImage;
    String url;
    SharedPreferences mSharedPreferences;
    Elements title;
    TextView count1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fox_sports_single_page);

        /** Enabling dropdown list navigation for the action bar */
        //getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

        mSharedPreferences=getApplicationContext().getSharedPreferences("MyPref", 0);
        /** Defining Navigation listener */
        foxHeading=(TextView)findViewById(R.id.channelTitle);
        foxTitle=(TextView)findViewById(R.id.FoxTitle);
        foxDate=(TextView)findViewById(R.id.feedDate);
        foxContent=(TextView)findViewById(R.id.FoxContent);
        foxImage=(ImageView)findViewById(R.id.FoxImage);

        foxHeading.setText("FOX SPORTS - FOOTBALL");
        foxHeading.setBackgroundColor(Color.parseColor("#1b3353"));
        foxHeading.setTextColor(Color.WHITE);


        Intent intent = getIntent();
        url=intent.getStringExtra("URL");


        class foxSportsSingle extends AsyncTask<String,Void,Void> {


            String url;
            Document doc;
            Elements description,image,fdate;
            String MainContent;
            String MainImage;
            ProgressDialog mProgressDialog = new ProgressDialog(foxSportsSinglePage.this);
            Bitmap bitmap;
            foxSportsSingle(String url){
            this.url=url;
            }

            @Override
            protected Void doInBackground(String... strings) {

                try {
                    doc = Jsoup.connect(url).get();

                    title=doc.select("article header h1");
                    fdate=doc.select("article .editor time");
                    description = doc.select("div.content p");
                    image=doc.select("article header img");
                    if(image.toString() == null || image.toString().isEmpty() || image.toString() == "")
                    {
                        image=doc.select("header #brand a img");
                    }
                    Log.i("Image Path",image.toString());

                    MainImage=image.attr("abs:src");
                    URL url = new URL(MainImage);
                    bitmap = BitmapFactory.decodeStream(url.openStream());

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
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
               // mProgressDialog.show();
            }


            protected void onPostExecute(Void result) {



                foxTitle.setText(title.text().toString()); //Sets the title
                foxImage.setImageBitmap(bitmap);
                foxContent.setText(MainContent);
                foxDate.setText(fdate.text().toString());
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
        if(id == R.id.twitter_tweet)
        {
            if(!isTwitterLoggedInAlready())
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(foxSportsSinglePage.this);

                // set title
                alertDialogBuilder.setTitle("Twitter Account Not Configured");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Do you want to configure your twitter account!!!")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                Intent noTwitterAccount=new Intent(foxSportsSinglePage.this,twitterLogin.class);
                                startActivity(noTwitterAccount);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            else
            {
                showCustomDialog();
            }
            }

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showCustomDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(foxSportsSinglePage.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.twitter_custom_dialog);

        final EditText editText = (EditText)dialog.findViewById(R.id.tweetBox);
        Button yesTweet= (Button)dialog.findViewById(R.id.yesTweet);
        Button cancelTweet=(Button)dialog.findViewById(R.id.noTweet);
        TextView tweetName=(TextView)dialog.findViewById(R.id.tweetName);
        String screenName=mSharedPreferences.getString(Constants.TWITTER_SCREEN_NAME,null);
        tweetName.setText("@"+screenName);

        count1 =(TextView)dialog.findViewById(R.id.count);
        editText.append("Fox Sports: "+title.text().toString()+"\n"+url);
        count1.setText(String.valueOf(140-editText.length()));


          TextWatcher mTextEditorWatcher = new TextWatcher() {
             String c;
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                c=String.valueOf(140-s.length());
                count1.setText(c);

            }

             @Override
             public void afterTextChanged(Editable editable) {
                 //count1.setText(c);

             }


        };
        editText.addTextChangedListener(mTextEditorWatcher);




        yesTweet.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new updateTwitterStatus(editText.getText().toString(), dialog).execute();

            }
        });
        cancelTweet.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v){

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    class updateTwitterStatus extends AsyncTask<String, String, String> {
        ProgressDialog pDialog;

        Dialog dialog = new Dialog(foxSportsSinglePage.this);
        String tweetText;

        updateTwitterStatus(String tweetText,Dialog dialog)
        {
            this.tweetText=tweetText;
            this.dialog=dialog;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(!isTwitterLoggedInAlready())
            {
                Intent noTwitterAccount=new Intent(foxSportsSinglePage.this,twitterLogin.class);
                startActivity(noTwitterAccount);
            }
            else
            {
            pDialog = new ProgressDialog(foxSportsSinglePage.this);
            pDialog.setMessage("Updating to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            }
            }
        protected String doInBackground(String... args) {
            //Log.d("Tweet Text", "> " + args[0]);
            //String status = args[0];
            try {

                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(AccountsDetails.TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(AccountsDetails.TWITTER_CONSUMER_SECRET);
                // Access Token
                String access_token = mSharedPreferences.getString(Constants.TWITTER_PREF_KEY_OAUTH_TOKEN, null);
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(Constants.TWITTER_PREF_KEY_OAUTH_SECRET,null);

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // Update status
                twitter4j.Status response = null;

                response = twitter.updateStatus(tweetText);
                dialog.dismiss();
            }catch (TwitterException e) {
                Log.i("Error",e.getMessage());
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
    public boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(Constants.TWITTER_PREF_KEY_TWITTER_LOGIN, false);
    }


}

