package com.footballLatest.app.Bean;

/**
 * Created by PrannoyAshirwadTank on 1/27/14.
 */
public class FeedBean {


    private String title="";
    private String desc="";
    private String link="";
    private String pubDate="";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
