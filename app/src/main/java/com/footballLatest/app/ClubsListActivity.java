package com.footballLatest.app;

/**
 * Created by AshirwadTank on 2/6/14.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.footballLatest.app.Bean.ListItemBean;
import com.footballLatest.app.CustomBaseAdapter.MyCustomBaseAdapterForClubs;

public class ClubsListActivity extends Activity implements OnItemClickListener {

    ListView lview3;
    MyCustomBaseAdapterForClubs adapter;
    private ArrayList<Object> itemList;
    private ListItemBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareArrayLits();
        lview3 = (ListView) findViewById(android.R.id.list);
        adapter = new MyCustomBaseAdapterForClubs(this, itemList);
        lview3.setAdapter(adapter);

        lview3.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        ListItemBean bean = (ListItemBean) adapter.getItem(position);
        Toast.makeText(this, "Title =>; "+bean.getTitle()+" n Description =&gt; "+bean.getSlug(), Toast.LENGTH_SHORT).show();
    }

    /* Method used to prepare the ArrayList,
     * Same way, you can also do looping and adding object into the ArrayList.
     */
    public void prepareArrayLits()
    {
        itemList = new ArrayList<Object>();

        AddObjectToList(R.drawable.manutd,"Manchester United","manchesterunited");
        AddObjectToList(R.drawable.chelsea,"Chelsea","chelsea");
        AddObjectToList(R.drawable.realmadrid,"Real Madrid","realmadrid");



    }

    // Add one item into the Array List
    public void AddObjectToList(int image, String title,String slug)
    {
        bean = new ListItemBean();
        bean.setImage(image);
        bean.setTitle(title);
        bean.setSlug(slug);
        itemList.add(bean);
    }


}
