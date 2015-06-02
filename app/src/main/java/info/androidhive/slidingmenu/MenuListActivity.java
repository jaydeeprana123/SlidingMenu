package info.androidhive.slidingmenu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.model.AppConstants;
import info.androidhive.slidingmenu.model.HotelsMenu;
import info.androidhive.slidingmenu.model.HotelsMenuList;
import info.androidhive.slidingmenu.utils.PrefUtils;

/**
 * Created by jaydeeprana on 01-06-2015.
 */
public class MenuListActivity extends Activity {




    private ArrayList<HotelsMenu> hotelsMenuArrayList;


    HotelsMenuList hotelsMenuList;
    private ListView menuListView;

    private MyAppAdapter myAppAdapter;

    private TextView hotelName;
    private ImageView hotelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        setToolbar();

       // Get Menu List
        hotelsMenuList = PrefUtils.getHotelsMenu(MenuListActivity.this);

        hotelsMenuArrayList = hotelsMenuList.hotelsMenuArrayList;
        menuListView= (ListView) findViewById(R.id.menuList);

        myAppAdapter=new MyAppAdapter(hotelsMenuArrayList,MenuListActivity.this);
        menuListView.setAdapter(myAppAdapter);


        hotelName = (TextView) findViewById(R.id.hotelName);
        hotelImage = (ImageView)findViewById(R.id.hotelImage);

        hotelName.setText(getIntent().getStringExtra("hotel_name"));

        Glide.with(MenuListActivity.this)
                .load(getIntent().getStringExtra("hotel_path"))
                .placeholder(R.drawable.ic_launcher)
                .centerCrop()
                .into(hotelImage);

    }

    private void setToolbar() {
    }

    private class MyAppAdapter extends BaseAdapter{


        // Holder Class
        public class ViewHolder {
            public TextView name;
            public ImageView image;

        }

        public List<HotelsMenu> parkingList;
        public Context context;


        public MyAppAdapter(List<HotelsMenu> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {

                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_menu_name, null);

                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.image= (ImageView) rowView.findViewById(R.id.hotelImage);

                Glide.with(MenuListActivity.this)
                        .load(AppConstants.IMAGE_PATH + parkingList.get(position).CategoryFolderPath + parkingList.get(position).CategoryIcon)
                        .placeholder(R.drawable.ic_launcher)
                        .centerCrop()
                        .into(viewHolder.image);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(parkingList.get(position).CategoryName+"");

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PrefUtils.setHotelsMenuItems(parkingList.get(position), MenuListActivity.this);

                    Intent it = new Intent(MenuListActivity.this, MenuItemListActivity.class);
                    it.putExtra("hotel_name", getIntent().getStringExtra("hotel_name"));
                    it.putExtra("hotel_path", getIntent().getStringExtra("hotel_path"));
                    it.putExtra("hotel_category",parkingList.get(position).CategoryName+"");
                    startActivity(it);



                }
            });




                return rowView;
        }
    }
}
