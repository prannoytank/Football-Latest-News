package com.footballLatest.app.Modal;

import android.util.Log;

import com.footballLatest.app.Bean.FeedBean;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AshirwadTank on 1/27/14.
 */
public class XmlParsing {
    private static final String LOGCAT = null;
 public ArrayList<FeedBean> getFeedData(String urlPath)
    {
       ArrayList<FeedBean> data=new ArrayList<FeedBean>();
        List feedTitle=new ArrayList();
        List feedLinks=new ArrayList();
        List feedDesc=new ArrayList();
        List feedPubDate=new ArrayList();
      // List feedImageUrl=new ArrayList();
        FeedBean feedObj = null;


        //FeedBean feedObj=null;

        try {
            URL url = new URL(urlPath);   // url of the feed site
            // Log.i(LOGCAT,"Url"+url);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();

            // We will get the XML from an input stream
            xpp.setInput(getInputStream(url), "UTF_8");

        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
            boolean insideItem = false;
            int ImageCount=0;
            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    if (xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                        ImageCount=0;
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        if (insideItem){
                            String title = xpp.nextText();
                           feedTitle.add(title);
                            //feedObj.setTitle(title); //extract the title
                            //Log.i(LOGCAT, "Title from xml-->" +feedObj.getTitle());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("link")) {
                        if (insideItem)
                        {
                            String link=xpp.nextText();
                            feedLinks.add(link);
                           // / feedObj.setLink(link); //extract the link of article
                            //Log.i(LOGCAT, "link from xml-->" +feedObj.getLink());

                        }
                    }
                      else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem){
                            String pubDate=xpp.nextText();
                             feedPubDate.add(pubDate);
                           // feedObj.setPubDate(pubDate); //extract the date of article
                            //Log.i(LOGCAT, "pubDate from xml-->" +feedObj.getPubDate());

                        }
                     }
                      else if (xpp.getName().equalsIgnoreCase("description")) {
                        if (insideItem){
                            String desc=xpp.nextText();
                            feedDesc.add(desc);
                            //feedObj.setDesc(desc); //extract the description of article
                           // Log.i(LOGCAT, "desc from xml-->" +feedObj.getDesc());
                        }
                     }
                 /*  else if (xpp.getName().trim().equalsIgnoreCase("media:thumbnail")) {
                        if (insideItem){

                            if(ImageCount==1)
                            {
                            String imageUrl=xpp.getAttributeValue(null, "url");
                            //Log.i(LOGCAT, "Image Link from xml-->" +imageUrl);
                            feedImageUrl.add(imageUrl);

                            }
                            ++ImageCount;
                            //feedObj.setDesc(desc); //extract the description of article
                            // Log.i(LOGCAT, "desc from xml-->" +feedObj.getDesc());
                        }
                    }*/


                   // Log.i(LOGCAT, "Size" +data.size());
                       // add feedbean object to the data ArrayList
                }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                    insideItem=false;
                }



                //Log.i('info',headlines);
                eventType = xpp.next(); //move to next element
            }
         } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 /*   if(feedImageUrl.isEmpty())
    {
        for(int i=0;i<=100;i++)
        {
            feedImageUrl.add("null");
        }
    }*/
        for(int i=0;i<feedTitle.size();i++)
        {
            feedObj=new FeedBean();
            feedObj.setTitle(feedTitle.get(i).toString());
            feedObj.setDesc(feedDesc.get(i).toString());
            feedObj.setDesc(feedDesc.get(i).toString());
            feedObj.setPubDate(feedPubDate.get(i).toString());
           // feedObj.setImageLink(feedImageUrl.get(i).toString());
            data.add(feedObj);

        }

        return data;   //return the ArrayList
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
