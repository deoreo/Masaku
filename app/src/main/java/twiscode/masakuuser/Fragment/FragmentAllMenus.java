package twiscode.masakuuser.Fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterAllMenus;
import twiscode.masakuuser.Adapter.AdapterWishlist;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelAllMenus;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;

public class FragmentAllMenus extends Fragment {

	private TextView countCart;
	private LinearLayout wrapCount;
	private ImageView btnCart;
	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelAllMenus> LIST_MENU = new ArrayList<>();
	private PullRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
	private AdapterAllMenus mAdapter;
	//private LinearLayout noData;

	int page =1;
	boolean isNodata = false;

	private ProgressBar progress;

	private BroadcastReceiver doLike;

	private HashMap<String,ModelAllMenus> allMenus = new HashMap<>();

	ApplicationManager appManager;

	Map<String, String> flurryParams = new HashMap<String,String>();


	public static FragmentAllMenus newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentAllMenus fragment = new FragmentAllMenus();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		appManager = new ApplicationManager(getActivity());
		View rootView = inflater.inflate(R.layout.activity_all_menus, container, false);
		progress = (ProgressBar) rootView.findViewById(R.id.progress);
		wrapCount = (LinearLayout) rootView.findViewById(R.id.wrapCount);
		countCart = (TextView) rootView.findViewById(R.id.countCart);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);

		//mListView.setScrollingCacheEnabled(false);
		mSwipeRefreshLayout.setRefreshing(false);

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
				getActivity().finish();
			}
		});


		/*
		updateCart = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Extract data included in the Intent
				Log.d("", "broadcast updateCart");
				String message = intent.getStringExtra("message");
				if (message.equals("true")) {
					List<ModelCart>list = new ArrayList<ModelCart>(ApplicationData.cart.values());
					if(list.size() > 0){
						int jml = 0;
						for(int i = 0;i<list.size();i++){
							jml = jml + list.get(i).getJumlah();
						}
						countCart.setText(""+jml);
						wrapCount.setVisibility(View.VISIBLE);
					}
					else {
						wrapCount.setVisibility(View.GONE);
					}

				}


			}
		};
		*/
		doLike = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Extract data included in the Intent
				Log.d("", "broadcast doLike");
				String message = intent.getStringExtra("message");
				int wishlistCount = appManager.getWishlist();
				if (message.equals("like")) {
					wishlistCount++;
					appManager.setWishlist(wishlistCount);
					SendBroadcast("wishlistFull", "true");
					new DoLike().execute(
							ApplicationData.idLike,"like"
					);
				}
				else{
					if(wishlistCount>0){
						wishlistCount--;
						appManager.setWishlist(wishlistCount);
						if(wishlistCount==0){
							appManager.setWishlist(wishlistCount);
							SendBroadcast("wishlistFull","false");
						}
					}
					else if(wishlistCount==0){
						wishlistCount=0;
						appManager.setWishlist(wishlistCount);
						SendBroadcast("wishlistFull","false");
					}
					new DoLike().execute(
							ApplicationData.idLike,"dislike"
					);
				}


			}
		};
		mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				isNodata = false;
				DummyData(isNodata);
			}
		});

		new GetWishlist().execute();
		DummyData(isNodata);

		return rootView;
	}

	private void DummyData(boolean isnodata){
		isNodata = isnodata;
		LIST_MENU = new ArrayList<ModelAllMenus>();
		String p = Integer.toString(page);
		new GetAllMenus(getActivity()).execute(
				p
		);

	}

	private class GetAllMenus extends AsyncTask<String, Void, String> {
		private Activity activity;
		private Context context;
		private Resources resources;


		public GetAllMenus(Activity activity) {
			super();
			this.activity = activity;
			this.context = activity.getApplicationContext();
			this.resources = activity.getResources();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(isNodata){
			}
			else {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			try {

				int page = Integer.parseInt(params[0]);
				JSONControl jsControl = new JSONControl();
				JSONObject response = jsControl.getAllMenus(page, appManager.getUserToken());
				Log.d("json response", response.toString());
				JSONArray menus = response.getJSONArray("menus");
				if(menus.length() > 0){
					for(int i=0;i<menus.length();i++){
						String id = menus.getJSONObject(i).getString("_id");
						String nama = menus.getJSONObject(i).getString("name");
						String foto = menus.getJSONObject(i).getJSONArray("imageUrls").getString(0);
						String price = menus.getJSONObject(i).getString("price");
						String time = "";//menus.getJSONObject(i).getJSONObject("speed").getString("waitingTime");
						String desc = menus.getJSONObject(i).getString("description");
						boolean added = Boolean.parseBoolean(menus.getJSONObject(i).getString("isLiked"));
						String openAt = "";
						try {
							String open = menus.getJSONObject(i).getString("openAt");
							String dt = open.split("T")[0];
							String[] dd = dt.split("-");
							String input_date=dd[2]+"-"+dd[1]+"-"+dd[0];
							SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
							Date dt1=format1.parse(input_date);
							DateFormat format2=new SimpleDateFormat("EEEE");
							String finalDay=format2.format(dt1);
							Log.d("delivery 2 - open ", ""+open);
							Log.d("delivery 2", ""+finalDay);
							openAt = finalDay+", "+dd[2]+" "+getMonth(dd[1])+" "+dd[0];
						}
						catch (Exception ex){
							ex.printStackTrace();
						}

						JSONArray feedback = new JSONArray();//menus.getJSONObject(i).getJSONArray("feedbacks");
						try{
							feedback = menus.getJSONObject(i).getJSONArray("feedbacks");
						}
						catch (Exception ex){
							feedback = new JSONArray();
						}
						JSONArray category = new JSONArray();
						try{
							category = menus.getJSONObject(i).getJSONArray("category");
						}
						catch (Exception ex){
							category = new JSONArray();
						}
						String hashtag = "";
						if(category.length() > 0){
							for(int j=0;j<category.length();j++){
								if(j==0){
									hashtag = "#"+category.getString(j);
								}
								else {
									hashtag = hashtag+" #"+category.getString(j);
								}
							}
						}
						ModelAllMenus menu = new ModelAllMenus(id,nama,price,foto,time,desc,feedback,added,hashtag,openAt);
						//LIST_MENU.add(menu);
						if(allMenus.size() > 0){
							if(!allMenus.containsKey(id)){
								allMenus.put(id,menu);
							}
						}
						else {
							allMenus.put(id, menu);
						}
						LIST_MENU = new ArrayList<>(allMenus.values());
						ApplicationData.allmenus = allMenus;
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


			//progressDialog.dismiss();
			progress.setVisibility(View.GONE);
			switch (result) {
				case "FAIL":
					//DialogManager.showDialog(activity, "Mohon maaf", "Nomor ponsel Anda belum terdaftar!");
					mSwipeRefreshLayout.setRefreshing(false);
					if(LIST_MENU.size() > 0){

						mListView.setVisibility(View.VISIBLE);
						//noData.setVisibility(View.GONE);
						mSwipeRefreshLayout.setVisibility(View.VISIBLE);
						Log.d("datalist","ada");
					}
					else {
						mListView.setVisibility(View.GONE);
						//noData.setVisibility(View.VISIBLE);
						mSwipeRefreshLayout.setVisibility(View.GONE);
					}
					break;
				case "OK":
					//Intent i = new Intent(getBaseContext(), ActivityHome.class);
					//startActivity(i);
					//finish();
					Log.d("jumlah menu : ",""+LIST_MENU.size());
					mAdapter = new AdapterAllMenus(activity, LIST_MENU);
					mListView.setAdapter(mAdapter);
					mSwipeRefreshLayout.setRefreshing(false);
					if(LIST_MENU.size() > 0){

						mListView.setVisibility(View.VISIBLE);
						//noData.setVisibility(View.GONE);
						mSwipeRefreshLayout.setVisibility(View.VISIBLE);
						int countHeart = 0;
						for(int x=0;x<LIST_MENU.size();x++){
							if(LIST_MENU.get(x).isAdded()){
								countHeart++;
							}
						}
						if(countHeart > 0){
							SendBroadcast("doWishlistFull", "true");
						}
						else {
							SendBroadcast("doWishlistFull", "false");
						}
						Log.d("datalist","ada");
					}
					else {
						mListView.setVisibility(View.GONE);
						//noData.setVisibility(View.VISIBLE);
						mSwipeRefreshLayout.setVisibility(View.GONE);
						SendBroadcast("doWishlistFull", "false");
					}
					break;

			}
		}
	}

	private class DoLike extends AsyncTask<String, Void, String> {
		private Activity activity;
		private Context context;
		private Resources resources;


		public DoLike() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			try {

				String id = params[0];
				String tipe = params[1];
				JSONControl jsControl = new JSONControl();
				if(tipe=="like"){

					Log.d("like", ConfigManager.LIKE+" - "+id);
					String response = jsControl.LikeMenu(id, appManager.getUserToken());
					Log.d("json response", response.toString());

					if(response.contains("true")){
						ApplicationData.allmenus.get(ApplicationData.idLike).setAdded(true);
						allMenus = new HashMap<>(ApplicationData.allmenus);
						return "OK";
					}
				}
				else {

					Log.d("dislike", ConfigManager.DISLIKE+" - "+id);
					String response = jsControl.DislikeMenu(id, appManager.getUserToken());
					Log.d("json response", response.toString());

					if(response.contains("true")){
						ApplicationData.allmenus.get(ApplicationData.idLike).setAdded(false);
						allMenus = new HashMap<>(ApplicationData.allmenus);
						return "OK";
					}
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


			//progressDialog.dismiss();
			progress.setVisibility(View.GONE);
			switch (result) {
				case "FAIL":
					//DialogManager.showDialog(activity, "Mohon maaf", "Nomor ponsel Anda belum terdaftar!");

					break;
				case "OK":
					//Intent i = new Intent(getBaseContext(), ActivityHome.class);
					//startActivity(i);
					//finish();
					Log.d("jumlah menu : ", "" + LIST_MENU.size());


					break;

			}
			//LIST_MENU = new ArrayList<>(allMenus.values());
			//mAdapter = new AdapterAllMenus(getActivity(), LIST_MENU);
			//mListView.setAdapter(mAdapter);
		}
	}

	private class GetWishlist extends AsyncTask<String, Void, String> {



		public GetWishlist() {
			super();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (isNodata) {
			} else {
				mSwipeRefreshLayout.setRefreshing(true);
			}
		}

		@Override
		protected String doInBackground(String... params) {
			try {

				JSONControl jsControl = new JSONControl();
				JSONArray response = jsControl.getWishlist(appManager.getUserToken());
				Log.d("json response wishlist", response.toString());
				Log.d("user token wishlist", appManager.getUserToken());
				if (response.length() > 0) {
					appManager.setWishlist(response.length());
					SendBroadcast("wishlistFull", "true");
					return "OK";
				}
				else if(response.length()<=0){
					appManager.setWishlist(response.length());
					SendBroadcast("wishlistFull", "false");
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


			//progressDialog.dismiss();
			progress.setVisibility(View.GONE);
			switch (result) {
				case "FAIL":

					break;
				case "OK":

					break;

			}
		}
	}


	public void onResume() {
		super.onResume();

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(doLike,
				new IntentFilter("doLike"));

	}

	String getMonth(String bulan){
		String bln="";
		switch (bulan){
			case "01":
				bln = "Jan";
				break;
			case "02":
				bln = "Feb";
				break;
			case "03":
				bln = "Mar";
				break;
			case "04":
				bln = "Apr";
				break;
			case "05":
				bln = "Mei";
				break;
			case "06":
				bln = "Jun";
				break;
			case "07":
				bln = "Jul";
				break;
			case "08":
				bln = "Agu";
				break;
			case "09":
				bln = "Sep";
				break;
			case "10":
				bln = "Okt";
				break;
			case "11":
				bln = "Nov";
				break;
			case "12":
				bln = "Des";
				break;
			default:
				bln = "Jan";
				break;
		}
		return bln;
	}

	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), ConfigManager.FLURRY_API_KEY);
		FlurryAgent.logEvent("MENU_ALL", flurryParams, true);
	}

	public void onStop() {
		super.onStop();
		FlurryAgent.endTimedEvent("MENU_ALL");
		FlurryAgent.onEndSession(getActivity());
	}

	private void SendBroadcast(String typeBroadcast,String type){
		Intent intent = new Intent(typeBroadcast);
		// add data
		intent.putExtra("message", type);
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
	}


}
