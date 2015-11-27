package twiscode.masakuuser.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.baoyz.widget.PullRefreshLayout;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Adapter.AdapterMenuPO;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;

public class FragmentMenu extends Fragment {

	private ImageView btnCart;
	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelMenu> LIST_MENU = new ArrayList<>();
	private PullRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	AdapterMenuPO mAdapter;
	NiceSpinner sort,category;
	LinearLayout noData;


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

		View rootView = inflater.inflate(R.layout.activity_menu, container, false);
		noData = (LinearLayout) rootView.findViewById(R.id.noData);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);

		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_menu, null);
		sort = (NiceSpinner) header.findViewById(R.id.sortSpinner);
		category = (NiceSpinner) header.findViewById(R.id.categorySpinner);

		List<String> dataCategory = new LinkedList<>(Arrays.asList("All", "Gorengan", "Kuah", "Masakan Korea", "Masakan Barat","Masakan Chinese","Masakan Padang","Masakan Jepang","Indonesia"));
		List<String> dataSort = new LinkedList<>(Arrays.asList("Popular", "Lowest Price", "Highest Price", "Nearest"));
		sort.attachDataSource(dataSort);
		category.attachDataSource(dataCategory);
		mListView.addHeaderView(header);
		mAdapter = new AdapterMenuPO(getActivity(), LIST_MENU);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);
		mSwipeRefreshLayout.setRefreshing(false);

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				DummyData();
			}
		});

		DummyData();

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

		LIST_MENU = new ArrayList<ModelMenu>();
		//new GetMenu(getActivity()).execute();

		/*
		ModelMenu modelDeliver0 = new ModelMenu("0", "Pecel Mak Yem", "5", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "11", "20.000");
		LIST_MENU.add(modelDeliver0);
		ModelMenu modelDeliver1 = new ModelMenu("0", "Soto Spesial Bu Winda", "4", "http://blog.travelio.com/wp-content/uploads/2015/03/Soto-Lamongan-Jawa-Timur-Indonesia.jpg", "20", "20.000");
		LIST_MENU.add(modelDeliver1);
		*/
	}

	private class GetMenu extends AsyncTask<String, Void, String> {
		private Activity activity;
		private Context context;
		private Resources resources;


		public GetMenu(Activity activity) {
			super();
			this.activity = activity;
			this.context = activity.getApplicationContext();
			this.resources = activity.getResources();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mSwipeRefreshLayout.setRefreshing(true);
		}

		@Override
		protected String doInBackground(String... params) {
			try {

				int page = Integer.parseInt(params[0]);
				JSONControl jsControl = new JSONControl();
				JSONObject response = jsControl.getMenuSpeed(page);
				Log.d("json response", response.toString());
				JSONArray menus = response.getJSONArray("menus");
				if(menus.length() > 0){
					for(int i=0;i<menus.length();i++){
						String id = menus.getJSONObject(i).getString("_id");
						String nama = menus.getJSONObject(i).getString("name");
						String foto = menus.getJSONObject(i).getJSONArray("imageUrls").getString(0);
						ModelMenu menu = new ModelMenu(id,nama,foto);
						LIST_MENU.add(menu);
					}

					return "OK";
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("json response id 0", "FAIL");
			return "FAIL";

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);


			switch (result) {
				case "FAIL":
					//DialogManager.showDialog(activity, "Mohon maaf", "Nomor ponsel Anda belum terdaftar!");
					if(LIST_MENU.size() > 0){
						mListView.setVisibility(View.VISIBLE);
						noData.setVisibility(View.GONE);
					}
					else {
						mListView.setVisibility(View.GONE);
						noData.setVisibility(View.VISIBLE);
					}
					break;
				case "OK":
					//Intent i = new Intent(getBaseContext(), ActivityHome.class);
					//startActivity(i);
					//finish();
					Log.d("jumlah menu : ",""+LIST_MENU.size());
					mAdapter = new AdapterMenuPO(activity, LIST_MENU);
					mListView.setAdapter(mAdapter);
					if(LIST_MENU.size() > 0){
						mListView.setVisibility(View.VISIBLE);
						noData.setVisibility(View.GONE);
					}
					else {
						mListView.setVisibility(View.GONE);
						noData.setVisibility(View.VISIBLE);
					}
					break;

			}
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}




}
