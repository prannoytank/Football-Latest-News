package com.footballLatest.app.Util;

import android.webkit.WebView;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

/**
 * Created by AshirwadTank on 1/29/14.
 */
public class TwitterHandler {

    private Twitter mTwitter;
    /** The request token signifies the unique ID of the request you are sending to twitter  *//*
    private RequestToken mReqToken;
    AccountsDetails ad=new AccountsDetails();
    private void loginNewUser() {
        try {
            //Log.i(TAG, "Request App Authentication");
            mReqToken = mTwitter.getOAuthRequestToken(ad.TWITTER_ACCESS_TOKEN);

            //Log.i(TAG, "Starting Webview to login to twitter");
            WebView twitterSite = new WebView(this);
            twitterSite.loadUrl(mReqToken.getAuthenticationURL());
            //setContentView(twitterSite);

        } catch (TwitterException e) {
            Toast.makeText(this, "Twitter Login error, try again later", Toast.LENGTH_SHORT).show();
        }
    }
*/

}
