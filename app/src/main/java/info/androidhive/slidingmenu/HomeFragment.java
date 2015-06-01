package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {

        HomeFragment contentFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), android.R.drawable.screen_background_light_transparent);
        contentFragment.setArguments(bundle);

        return contentFragment;
    }
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         
        return rootView;
    }
}
