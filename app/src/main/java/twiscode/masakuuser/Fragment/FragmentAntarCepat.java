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

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;

public class FragmentAntarCepat extends Fragment {

	private ImageView btnCart;
	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelMenu> LIST_MENU = new ArrayList<>();
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	AdapterMenuNew mAdapter;


	private int mPage;

	private RecyclerView recyclerView;



	public static FragmentAntarCepat newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentAntarCepat fragment = new FragmentAntarCepat();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_antar_cepat, container, false);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		//mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_menu, null);
		//mListView.addHeaderView(header);
		mAdapter = new AdapterMenuNew(getActivity(), LIST_MENU);
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
		ModelMenu modelDeliver0 = new ModelMenu("0", "Pecel Mak Yem", "8.000", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "11");
		LIST_MENU.add(modelDeliver0);
		ModelMenu modelDeliver1 = new ModelMenu("0", "Soto Spesial Bu Winda", "14.000", "http://blog.travelio.com/wp-content/uploads/2015/03/Soto-Lamongan-Jawa-Timur-Indonesia.jpg", "15");
		LIST_MENU.add(modelDeliver1);

	}


}
