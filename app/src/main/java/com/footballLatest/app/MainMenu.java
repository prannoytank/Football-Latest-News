package com.footballLatest.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity implements AdapterView.OnItemClickListener {
    ListView listView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        listView = (ListView) findViewById(android.R.id.list);
        listView.setOnItemClickListener(this);
    }

    /*
     * Parameters:
        adapter - The AdapterView where the click happened.
        view - The view within the AdapterView that was clicked
        position - The position of the view in the adapter.
        id - The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

        String action= (String) ((TextView) view).getText();

        if(action.equals("News"))
        {
            Intent news=new Intent("com.footballLatest.app.ChannelSelection");
            startActivity(news);
        }
        else if(action.equals("Twitter"))
        {
            Intent twitter=new Intent("com.footballLatest.app.twitterLogin");
            startActivity(twitter);

        }

    }


}