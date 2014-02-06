package com.footballLatest.app.CustomBaseAdapter;

/**
 * Created by AshirwadTank on 2/6/14.
 */


    import android.app.Activity;
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.footballLatest.app.Bean.ListItemBean;
    import com.footballLatest.app.R;

    import java.util.ArrayList;

public class MyCustomBaseAdapterForClubs extends BaseAdapter{

        ArrayList<Object> itemList;

        public Activity context;
        public LayoutInflater inflater;

        public MyCustomBaseAdapterForClubs(Activity context,ArrayList<Object> itemList) {
            super();

            this.context = context;
            this.itemList = itemList;

            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public static class ViewHolder
        {
            ImageView imgViewLogo;
            TextView txtViewTitle;
            TextView txtViewSlug;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            ViewHolder holder;
            if(convertView==null)
            {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.image_text_list_view, null);

                holder.imgViewLogo = (ImageView) convertView.findViewById(R.id.imgViewLogo);
                holder.txtViewTitle = (TextView) convertView.findViewById(R.id.txtViewTitle);
                holder.txtViewSlug = (TextView) convertView.findViewById(R.id.txtViewSlug);


                convertView.setTag(holder);
            }
            else
                holder=(ViewHolder)convertView.getTag();

            ListItemBean bean = (ListItemBean) itemList.get(position);

            holder.imgViewLogo.setImageResource(bean.getImage());
            holder.txtViewTitle.setText(bean.getTitle());
            holder.txtViewSlug.setText(bean.getSlug());

            return convertView;
        }

    }




