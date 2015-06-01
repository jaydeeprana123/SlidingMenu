package info.androidhive.slidingmenu;

import android.app.Activity;
import android.content.Context;
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
public class MenuListActivity extends Activity{

    private ArrayList<HotelsMenu> hotelsMenuArrayList;

    HotelsMenuList hotelsMenuList;
    private ListView menuListView;

    private MyAppAdapter myAppAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

       // Get Menu List
        hotelsMenuList = PrefUtils.getHotelsMenu(MenuListActivity.this);

        hotelsMenuArrayList = hotelsMenuList.hotelsMenuArrayList;
        menuListView= (ListView) findViewById(R.id.menuList);
    }

    private class MyAppAdapter extends BaseAdapter{


        // Holder Class
        public class ViewHolder {
            public TextView name;
            public ImageView image;

        }

        public List<HotelsMenu> hotelMenu;
        public Context context;

        public MyAppAdapter(List<HotelsMenu> hotelMenu, Context context) {
            this.hotelMenu = hotelMenu;
            this.context = context;
        }

        @Override
        public int getCount() {
            return hotelMenu.size();
        }

        @Override
        public Object getItem(int position) {
            return hotelMenu.get(position);
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
                        .load(AppConstants.IMAGE_PATH + hotelMenu.get(position).CategoryFolderPath + hotelMenu.get(position).CategoryIcon)
                        .placeholder(R.drawable.ic_launcher)
                        .centerCrop()
                        .into(viewHolder.image);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(hotelMenu.get(position).CategoryName+"");

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });




                return rowView;
        }
    }
}
