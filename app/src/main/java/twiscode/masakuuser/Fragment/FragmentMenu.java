package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;

public class FragmentMenu extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelMenu> LIST_MENU = new ArrayList<>();
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	AdapterMenu mAdapter;

	private int mPage;

	private RecyclerView recyclerView;



	public static FragmentMenu newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentMenu fragment = new FragmentMenu();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_menu, container, false);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		//mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		mAdapter = new AdapterMenu(getActivity(), LIST_MENU);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);
		//mSwipeRefreshLayout.setRefreshing(false);

		return rootView;
	}

	private void DummyData(){
		LIST_MENU = new ArrayList<ModelMenu>();
		ModelMenu modelDeliver0 = new ModelMenu("0", "Mak Yem", "5", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "10", "40000");
		LIST_MENU.add(modelDeliver0);
		ModelMenu modelDeliver1 = new ModelMenu("0", "Bu Winda", "4", "http://www.resepgratis.com/wp-content/uploads/2015/08/Cara-Membuat-Resep-Soto-Ayam-Yang-Nikmat.jpg", "3", "22000");
		LIST_MENU.add(modelDeliver1);
		ModelMenu modelDeliver2 = new ModelMenu("0", "Mama Tina", "3", "https://c1.staticflickr.com/9/8474/8117817563_6cb6755539_b.jpg", "7", "20000");
		LIST_MENU.add(modelDeliver2);
		ModelMenu modelDeliver3 = new ModelMenu("0", "Ibu Rudi", "4", "http://www.pegipegi.com/travel/wp-content/uploads/2014/09/nasgorindonesia.jpg", "5", "100000");
		LIST_MENU.add(modelDeliver3);
		ModelMenu modelDeliver4 = new ModelMenu("0", "Ibu Mirna", "4", "http://img.hipwee.com/cdn/wp-content/uploads/2015/07/ayam-bakar-riun-tenda.jpg?0d2690", "6", "30000");
		LIST_MENU.add(modelDeliver4);
	}


}
