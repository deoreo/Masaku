package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;

public class FragmentMenu extends Fragment {

	private ImageView btnCart;
	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelMenu> LIST_MENU = new ArrayList<>();
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	AdapterMenu mAdapter;
	NiceSpinner sort,category;


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
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		//mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_menu, null);
		sort = (NiceSpinner) header.findViewById(R.id.sortSpinner);
		category = (NiceSpinner) header.findViewById(R.id.categorySpinner);

		List<String> dataCategory = new LinkedList<>(Arrays.asList("All", "Gorengan", "Kuah", "Masakan Korea", "Masakan Barat","Masakan Chinese","Masakan Padang","Masakan Jepang","Indonesia"));
		List<String> dataSort = new LinkedList<>(Arrays.asList("Popular", "Lowest Price", "Highest Price", "Nearest"));
		sort.attachDataSource(dataSort);
		category.attachDataSource(dataCategory);
		mListView.addHeaderView(header);
		mAdapter = new AdapterMenu(getActivity(), LIST_MENU);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);
		//mSwipeRefreshLayout.setRefreshing(false);

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});

		return rootView;
	}

	private void DummyData(){

		LIST_MENU = new ArrayList<ModelMenu>();
		ModelMenu modelDeliver0 = new ModelMenu("0", "Pecel Mak Yem", "5", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "11", "20.000");
		LIST_MENU.add(modelDeliver0);
		ModelMenu modelDeliver1 = new ModelMenu("0", "Soto Spesial Bu Winda", "4", "http://blog.travelio.com/wp-content/uploads/2015/03/Soto-Lamongan-Jawa-Timur-Indonesia.jpg", "20", "20.000");
		LIST_MENU.add(modelDeliver1);

	}


}
