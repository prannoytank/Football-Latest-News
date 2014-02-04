package com.footballLatest.app;

/**
 * Created by AshirwadTank on 2/3/14.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AccountsListFragment extends ListFragment{

    private String accountsList[]=new String[]{
            "Twitter",
            "Facebook",
            "Google Plus"
    };
ListView accountsView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.main_menu,container, false);
        //setContentView(R.layout.main_menu);

        accountsView = (ListView)rootView.findViewById(android.R.id.list);

        //listView.setOnItemClickListener(this);


        /** Creating array adapter to set data in listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,accountsList);
        accountsView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        String category= (String) ((TextView) v).getText();
        if(category.equalsIgnoreCase("Twitter"))
        {
            Intent twitter=new Intent("com.footballLatest.app.twitterLogin");
            startActivity(twitter);
        }


    }



    @Override
    public void onStart() {
        super.onStart();
      //  getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}