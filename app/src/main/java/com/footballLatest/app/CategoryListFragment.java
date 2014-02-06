package com.footballLatest.app; /**
 * Created by AshirwadTank on 2/3/14.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CategoryListFragment extends ListFragment{

    /** An array of items to display in ArrayList */
    String myfriends_list[] = new String[]{
           "Football(Global)",
           "Clubs"
    };
   ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_menu,container, false);
        //setContentView(R.layout.main_menu);

        listView = (ListView)rootView.findViewById(android.R.id.list);

        //listView.setOnItemClickListener(this);


        /** Creating array adapter to set data in listview */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1,myfriends_list);
        listView.setAdapter(adapter);




      return rootView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

    String category= (String) ((TextView) v).getText();
     if(category.equalsIgnoreCase("Football(Global)"))
     {
         Intent news=new Intent("com.footballLatest.app.ChannelSelection");
         startActivity(news);
     }
     else if(category.equalsIgnoreCase("Clubs"))
     {
         Intent clubs=new Intent("com.footballLatest.app.ClubsListActivity");
         startActivity(clubs);
     }


    }

    @Override
    public void onStart() {
        super.onStart();
        /** Setting the multiselect choice mode for the listview */
       // getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}
