package com.footballLatest.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.footballLatest.app.Util.AccountsDetails;
import com.footballLatest.app.Util.Constants;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class twitterLogin extends Activity implements View.OnClickListener {
    private static final String LOGCAT = null;
    private Twitter mTwitter;
    Button login,logout;
    ImageView twitterLogo,profilePic;
    private RequestToken mReqToken;
    private static SharedPreferences mSharedPreferences;
    private static Twitter twitter;
    Bitmap bitmap;
    TextView screenName;
    private static RequestToken requestToken;

    //static String PREFERENCE_NAME = "twitter_oauth";
    // Twitter oauth urls
    //static final String URL_TWITTER_AUTH = "auth_url";
    // static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_login);

        /*if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
*/
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);

        screenName=(TextView)findViewById(R.id.username);
        twitterLogo=(ImageView)findViewById(R.id.twitterLogo);
        profilePic=(ImageView)findViewById(R.id.profileImage);
        login=(Button)findViewById(R.id.btnLogin);
        logout=(Button)findViewById(R.id.btnLogout);

        mTwitter = new TwitterFactory().getInstance();


        // Tell twitter4j that we want to use it with our app
        mTwitter.setOAuthConsumer(AccountsDetails.TWITTER_CONSUMER_KEY, AccountsDetails.TWITTER_CONSUMER_SECRET);
        login=(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

            if (!isTwitterLoggedInAlready()) {
                Uri uri = getIntent().getData();
                if (uri != null && uri.toString().startsWith(Constants.TWITTER_CALLBACK_URL)) {
                    // oAuth verifier
                    String verifier = uri.getQueryParameter(Constants.TWITTER_OAUTH_VERIFIER);

                    try {
                        // Get the access token
                        AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
                        long userID = accessToken.getUserId();
                        User user = twitter.showUser(userID);

                        // Shared Preferences
                        SharedPreferences.Editor e = mSharedPreferences.edit();

                        e.putString(Constants.TWITTER_PREF_KEY_OAUTH_TOKEN, accessToken.getToken());  // Access Token
                        e.putString(Constants.TWITTER_PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret()); //Access Secret
                        e.putBoolean(Constants.TWITTER_PREF_KEY_TWITTER_LOGIN, true);   // Login Status
                        e.putString(Constants.TWITTER_USERNAME, user.getName()); //username
                        e.putString(Constants.TWITTER_SCREEN_NAME,user.getScreenName()); //screen name
                        e.putString(Constants.TWITTER_IMAGE_URL,user.getProfileImageURL()); // profile image
                        e.commit(); // save changes
 
                        login.setVisibility(View.GONE);
                        logout.setVisibility(View.VISIBLE);
                        screenName.setVisibility(View.VISIBLE);
                        profilePic.setVisibility(View.VISIBLE);
                        URL url = new URL(user.getProfileImageURL());
                        bitmap = BitmapFactory.decodeStream(url.openStream());
                        profilePic.setImageBitmap(bitmap);
                        screenName.setText(user.getScreenName());
                    } catch (Exception e) {
                        // Check log for login errors
                        Log.e("Twitter Login Error", "> " + e.getMessage());
                    }
                }
            }
            else{
                String name=mSharedPreferences.getString(Constants.TWITTER_SCREEN_NAME,null);
                String imageUrl=mSharedPreferences.getString(Constants.TWITTER_IMAGE_URL,null);
                login.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
                screenName.setVisibility(View.VISIBLE);
                profilePic.setVisibility(View.VISIBLE);
                URL url = null;

                try {
                    url = new URL(imageUrl);
                    bitmap = BitmapFactory.decodeStream(url.openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                profilePic.setImageBitmap(bitmap);
                screenName.setText(name);
            }
 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.twitter_login, menu);
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

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnLogin:
              new doTwitterLogin().execute();
              break;
            case R.id.btnLogout:
                new doTwitterLogout().execute();
                break;

        }
    }

    /* Twitter Login Asyn Task */
    class doTwitterLogin extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            loginToTwitter();
            return null;
        }
    }

    /* Twitter Logout Asyn Task */
    class doTwitterLogout extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {

            twitterLogout();
            logout.setVisibility(View.GONE);
            screenName.setVisibility(View.GONE);
            profilePic.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            return null;
        }
    }

      void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(AccountsDetails.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(AccountsDetails.TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();

            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken(Constants.TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Already Logged into twitter", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(Constants.TWITTER_PREF_KEY_TWITTER_LOGIN, false);
    }

    private void twitterLogout()
    {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.remove(Constants.TWITTER_PREF_KEY_OAUTH_TOKEN); // will delete key name
        editor.remove(Constants.TWITTER_PREF_KEY_OAUTH_SECRET); // will delete key email
        editor.remove(Constants.TWITTER_PREF_KEY_TWITTER_LOGIN);
        editor.remove(Constants.TWITTER_IMAGE_URL);
        editor.remove(Constants.TWITTER_SCREEN_NAME);
        editor.remove(Constants.TWITTER_USERNAME);
        editor.commit();
    }

}


