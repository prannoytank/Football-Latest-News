package com.footballLatest.app;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("ALL")
public class ChannelSelection extends TabActivity {
    // TabSpec Names
    private static final String BBC_NEWS = "BBC";
    private static final String SKY_SPORTS = "SkySports";
   // private static final String PROFILE_SPEC = "Profile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_selection_tabs);

        TabHost tabHost = getTabHost();

        // BBC Tab
        TabSpec inboxSpec = tabHost.newTabSpec(BBC_NEWS);
        // Tab Icon
        //inboxSpec.setIndicator(BBC_NEWS, getResources().getDrawable(R.drawable.activi));
        inboxSpec.setIndicator("BBC");
        Intent inboxIntent = new Intent().setClass(this, MainActivity.class);
        inboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Tab Content
        inboxSpec.setContent(inboxIntent);


        // SkySports Tab
        TabSpec outboxSpec = tabHost.newTabSpec(SKY_SPORTS);
        //outboxSpec.setIndicator(OUTBOX_SPEC, getResources().getDrawable(R.drawable.icon_outbox));
        outboxSpec.setIndicator("Fox Sports");
        Intent outboxIntent = new Intent().setClass(this, FoxSportsFeed.class);
        outboxIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        outboxSpec.setContent(outboxIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Inbox tab
        tabHost.addTab(outboxSpec); // Adding Outbox tab
        tabHost.setCurrentTab(0);

    }
}