package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterVendorMenu;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelVendor;
import twiscode.masakuuser.Model.ModelVendorMenu;
import twiscode.masakuuser.R;

public class FragmentVendorMenu extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelVendorMenu> LIST_MENU = new ArrayList<>();
	private ListView mListView;
	private AdapterVendorMenu mAdapter;
	private int mPage;
	private RecyclerView recyclerView;



	public static FragmentVendorMenu newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentVendorMenu fragment = new FragmentVendorMenu();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_vendor_menu, container, false);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		mAdapter = new AdapterVendorMenu(getActivity(), LIST_MENU);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);

		return rootView;
	}

	private void DummyData(){
		LIST_MENU = new ArrayList<ModelVendorMenu>();
		ModelVendorMenu modelVendorMenu0 = new ModelVendorMenu("0","Pecel","700","30.000", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG");
		LIST_MENU.add(modelVendorMenu0);
		ModelVendorMenu modelVendorMenu1 = new ModelVendorMenu("0","Soto","500","30.000", "http://www.resepgratis.com/wp-content/uploads/2015/08/Cara-Membuat-Resep-Soto-Ayam-Yang-Nikmat.jpg");
		LIST_MENU.add(modelVendorMenu1);


	}


}
