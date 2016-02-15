package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flurry.android.FlurryAgent;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterPromo;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelPromo;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ConfigManager;

public class FragmentPromo extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelPromo> LIST_MENU = new ArrayList<>();
	private PullRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	AdapterPromo mAdapter;
	TextView noData;


	private int mPage;

	private RecyclerView recyclerView;

	Map<String, String> flurryParams = new HashMap<String,String>();



	public static FragmentPromo newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentPromo fragment = new FragmentPromo();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_promo, container, false);
		noData = (TextView) rootView.findViewById(R.id.noData);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);

		//mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		//mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);

		mAdapter = new AdapterPromo(getActivity(), LIST_MENU);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);
		//mSwipeRefreshLayout.setRefreshing(false);



		if(LIST_MENU.size() > 0){
			mListView.setVisibility(View.VISIBLE);
			noData.setVisibility(View.GONE);
		}
		else {
			mListView.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		}

		return rootView;
	}

	private void DummyData(){

		LIST_MENU = new ArrayList<ModelPromo>();
		String syarat =
						"<p>1. Tidak dapat digabungkan dengan promo lain</p>" +
						"<p>2. Tidak dapat digunakan setelah masa berlaku habis</p>";
		int poto = R.drawable.banner_promo_masaku;;
		ModelPromo promo = new ModelPromo("1", "1 Februari 2016", "29 Februari 2016", "Saat anda melakukan pembelian di Delihome dapat memasukan kode happy2016 dalam kolom promo code di halaman checkout. Dan dapatkan free ongkos kirim ke seluruh area Surabaya. ",syarat, poto);
		LIST_MENU.add(promo);


	}
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), ConfigManager.FLURRY_API_KEY);
		FlurryAgent.logEvent("PROMO", flurryParams, true);
	}

	public void onStop() {
		super.onStop();
		FlurryAgent.endTimedEvent("PROMO");
		FlurryAgent.onEndSession(getActivity());
	}


}
