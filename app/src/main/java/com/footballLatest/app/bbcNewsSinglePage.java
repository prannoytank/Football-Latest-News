package com.footballLatest.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

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

                    title=doc.select(".story-body .article #headline h1");
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



                foxTitle.setText(title.text().toString()); //Sets the title
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



}