package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.MenuListActivity;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.model.AppConstants;
import info.androidhive.slidingmenu.model.Hotel;
import info.androidhive.slidingmenu.model.HotelsList;
import info.androidhive.slidingmenu.model.HotelsMenuList;
import info.androidhive.slidingmenu.utils.GetServiceCall;
import info.androidhive.slidingmenu.utils.PrefUtils;

/**
 * Created by jaydeeprana on 01-06-2015.
 */
public class HotelListFragment  extends Fragment {

    public static final String BOOK = "Book";
    HotelsMenuList hotelsMenuList;

    private MyAppAdapter customAdapter;
    private ListView hotelsListView;
    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private ProgressDialog progressDialog;
    private ArrayList<Hotel> hotelArrayList;

    String imagePath;

    public static HotelListFragment newInstance() {

        HotelListFragment contentFragment = new HotelListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), android.R.drawable.screen_background_light_transparent);
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    private void getHotelList() {

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new GetServiceCall(AppConstants.GET_HOTELS+ PrefUtils.getArea(getActivity()),GetServiceCall.TYPE_JSONOBJECT){

            @Override
            public void response(String response) {

                Log.e("response:", response + "");
                progressDialog.dismiss();

                HotelsList hotelsList = new GsonBuilder().create().fromJson(response, HotelsList.class);
                hotelArrayList=hotelsList.hotelArrayList;
                Log.e("list size", hotelArrayList.size() + "");

                customAdapter=new MyAppAdapter(hotelArrayList,getActivity());
                hotelsListView.setAdapter(customAdapter);

            }

            @Override
            public void error(VolleyError error) {

                progressDialog.dismiss();
            }
        }.call();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotels, container, false);
        hotelsListView= (ListView) rootView.findViewById(R.id.hotelsList);
        getHotelList();
        return rootView;
    }



    private class MyAppAdapter extends BaseAdapter{

        // Holder Class
        public class ViewHolder {
            public TextView name,delivery,mobile,address;
            public ImageView image;

        }


        public List<Hotel> parkingList;
        public Context context;

        public MyAppAdapter(List<Hotel> parkingList, Context context) {
            this.parkingList = parkingList;
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

                LayoutInflater inflater = getActivity().getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_hotel_list, null);

                // configure view holder
                viewHolder = new ViewHolder();

                viewHolder.name = (TextView) rowView.findViewById(R.id.name);
                viewHolder.address = (TextView) rowView.findViewById(R.id.address);
                viewHolder.mobile = (TextView) rowView.findViewById(R.id.mobile);
                viewHolder.delivery = (TextView) rowView.findViewById(R.id.delivery);
                viewHolder.image = (ImageView) rowView.findViewById(R.id.hotelImage);

                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(parkingList.get(position).HotelName+"");
            viewHolder.delivery.setText(parkingList.get(position).EmailId+"");
            viewHolder.mobile.setText(parkingList.get(position).PhoneNumber + "");
            viewHolder.address.setText(parkingList.get(position).StreetAddress+"");

            Log.e("image path:", AppConstants.IMAGE_PATH + parkingList.get(position).LogoPath + parkingList.get(position).Logo + "");

            imagePath = AppConstants.IMAGE_PATH + parkingList.get(position).LogoPath + parkingList.get(position).Logo;

            Log.e("Jaydeep path:", ""+ imagePath);

            // For Image Compress
            Glide.with(getActivity())
                    .load(AppConstants.IMAGE_PATH + parkingList.get(position).LogoPath + parkingList.get(position).Logo)
                    .centerCrop()
                    .into(viewHolder.image);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   getHotelsMenu(parkingList.get(position).HotelName.toString(),parkingList.get(position).HotelId+"");

                }

                
            });

            return rowView;
        }


    }

    private void getHotelsMenu( final String name, String id) {

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        new  GetServiceCall(AppConstants.GET_HOTELS_MENU+id, GetServiceCall.TYPE_JSONOBJECT){

            @Override
            public void response(String response) {

                Log.e("response:", response + "");
                progressDialog.dismiss();

                hotelsMenuList = new GsonBuilder().create().fromJson(response, HotelsMenuList.class);
                PrefUtils.setHotelsMenu(hotelsMenuList, getActivity());
                PrefUtils.clearCart(getActivity());

                Intent it = new Intent(getActivity(), MenuListActivity.class);

                   it.putExtra("hotel_name", name+"");
                it.putExtra("hotel_path", imagePath+"");


                startActivity(it);

            }

            @Override
            public void error(VolleyError error) {

                progressDialog.dismiss();

            }
        }.call();
    }


}
