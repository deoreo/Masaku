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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.baoyz.widget.PullRefreshLayout;
import com.flurry.android.FlurryAgent;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Activity.ActivityChangeLocation;
import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Adapter.AdapterMenuPO;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FragmentMenu extends Fragment {

	private ImageView btnCart;
	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelMenuSpeed> LIST_MENU = new ArrayList<>();
	private PullRefreshLayout mSwipeRefreshLayout,mSwipeRefreshLayoutNoData;
	private ListView mListView;
	AdapterMenuPO mAdapter;
	NiceSpinner sort,category;
	LinearLayout noData, layoutAlamat;
	RelativeLayout firstLay;
private TextView txtAlamat;

	private int mPage = 1;

	private BroadcastReceiver spaceLayout;

	private HashMap<String,ModelMenuSpeed> speedmenu = new HashMap<>();

	ApplicationManager applicationManager;
	ArrayList<ModelMenuSpeed> listEvent = new ArrayList<>();
	ArrayList<ModelMenuSpeed> listNonEvent = new ArrayList<>();

	View header;

	Map<String, String> flurryParams = new HashMap<String,String>();


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
		applicationManager = new ApplicationManager(getActivity());
		View rootView = inflater.inflate(R.layout.activity_menu, container, false);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		noData = (LinearLayout) rootView.findViewById(R.id.spaceLayout);
		layoutAlamat = (LinearLayout) rootView.findViewById(R.id.layoutAlamat);
		txtAlamat = (TextView) rootView.findViewById(R.id.txtAlamat);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
		mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
		header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_menu, null);
		sort = (NiceSpinner) header.findViewById(R.id.sortSpinner);
		category = (NiceSpinner) header.findViewById(R.id.categorySpinner);

		List<String> dataCategory = new LinkedList<>(Arrays.asList("All", "Gorengan", "Kuah", "Masakan Korea", "Masakan Barat","Masakan Chinese","Masakan Padang","Masakan Jepang","Indonesia"));
		List<String> dataSort = new LinkedList<>(Arrays.asList("Popular", "Lowest Price", "Highest Price", "Nearest"));
		sort.attachDataSource(dataSort);
		category.attachDataSource(dataCategory);
		LIST_MENU = new ArrayList<ModelMenuSpeed>();
		mListView.setScrollingCacheEnabled(false);
		mSwipeRefreshLayout.setRefreshing(false);

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});

		if(applicationManager.getAlamat() != null) {
			txtAlamat.setText(applicationManager.getAlamat());
		}else{
			txtAlamat.setText("address");
		}

		layoutAlamat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityChangeLocation.class);
				startActivity(i);
				ApplicationData.isFromMenu = true;
				getActivity().finish();
			}
		});

		mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				DummyData();
			}
		});

		spaceLayout = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Extract data included in the Intent
				Log.d("", "broadcast updateCart");
				String message = intent.getStringExtra("message");
				if(message=="on"){
					noData.setVisibility(View.VISIBLE);

				}
				else {
					noData.setVisibility(View.GONE);
				}


			}
		};

		//mListView.setVisibility(View.GONE);
		DummyData();



		return rootView;
	}

	private void DummyData(){

		LIST_MENU = new ArrayList<ModelMenuSpeed>();
		new GetMenu(getActivity()).execute(Integer.toString(mPage));

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
		private List<String> datacategory;

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
				JSONObject response = jsControl.getMenuPreOrder(page, applicationManager.getUserToken());
				JSONArray responseCategory = jsControl.getCategories(applicationManager.getUserToken());
				try {
					String responseDeviceToken = jsControl.postDeviceToken(ApplicationData.PARSE_DEVICE_TOKEN, ApplicationManager.getInstance(activity).getUserToken());
					Log.d("device token", responseDeviceToken);
				}catch (Exception e){
					Log.d("device token", "fail");
					e.printStackTrace();
				}
				Log.d("json response", response.toString());
				Log.d("category", responseCategory.toString());
				datacategory = new LinkedList<>();
				datacategory.add("Semua Kategori");
				for(int i = 0;i<responseCategory.length();i++){
					datacategory.add(responseCategory.getString(i));
				}

				JSONArray menus = response.getJSONArray("menus");
				if(menus.length() > 0){
					for(int i=0;i<menus.length();i++){
						String id = menus.getJSONObject(i).getString("_id");
						String nama = menus.getJSONObject(i).getString("name");
						String foto = "";
						try{
							foto = menus.getJSONObject(i).getJSONArray("imageUrls").getString(0);
						}
						catch (Exception x){

						}

						String price = menus.getJSONObject(i).getString("price");
						String open = menus.getJSONObject(i).getString("openAt");
						String isEevent = menus.getJSONObject(i).getString("isEvent");
						String eventName = menus.getJSONObject(i).getString("eventName");
						String time = "";
						String openAt = "";
						String dt = open.split("T")[0];
						String[] dd = dt.split("-");
						String input_date=dd[2]+"-"+dd[1]+"-"+dd[0];
						SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
						Date dt1=format1.parse(input_date);
						DateFormat format2=new SimpleDateFormat("EEEE");
						String finalDay=format2.format(dt1);
						Log.d("delivery 2", ""+input_date+" - "+finalDay);
						openAt = finalDay+", "+dd[2]+" "+getMonth(dd[1])+" "+dd[0];

						String desc = menus.getJSONObject(i).getString("description");
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
						ModelMenuSpeed menu = new ModelMenuSpeed(id,nama,price,foto,time,desc,feedback,hashtag,openAt,isEevent,eventName,"po");
						//LIST_MENU.add(menu);

						if(speedmenu.size() > 0){
							if(!speedmenu.containsKey(id)){
								speedmenu.put(id,menu);
								if(menu.getIsEvent()=="true"){
									listEvent.add(menu);
								}
								else {
									listNonEvent.add(menu);
								}
							}
						}
						else {
							speedmenu.put(id,menu);
							if(menu.getIsEvent()=="true"){
								listEvent.add(menu);
							}
							else {
								listNonEvent.add(menu);
							}
						}

					}
					ArrayList<ModelMenuSpeed> dt = new ArrayList<>(speedmenu.values());

					//LIST_MENU = new ArrayList<>(speedmenu.values());
					//ApplicationData.tempPO = LIST_MENU;
					LIST_MENU = new ArrayList<>();

					for(int l=0;l<listNonEvent.size();l++){

						LIST_MENU.add(listNonEvent.get(l));

					}
					for(int m=0;m<listEvent.size();m++){
						LIST_MENU.add(0,listEvent.get(m));

					}
					ApplicationData.tempPO = LIST_MENU;
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
					mAdapter = new AdapterMenuPO(activity, LIST_MENU);
					mListView.setAdapter(mAdapter);
					mSwipeRefreshLayout.setRefreshing(false);
					break;
				case "OK":
					//Intent i = new Intent(getBaseContext(), ActivityHome.class);
					//startActivity(i);
					//finish();
					Log.d("jumlah menu : ",""+LIST_MENU.size());
					//mListView.addHeaderView(header);
					category.attachDataSource(datacategory);
					mAdapter = new AdapterMenuPO(activity, LIST_MENU);
					mListView.setAdapter(mAdapter);
					mSwipeRefreshLayout.setRefreshing(false);
					break;

			}

			//mListView.setVisibility(View.VISIBLE);
		}
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

	public void onResume() {
		super.onResume();

		if(ApplicationData.cart.size() > 0){
			//ApplicationData.cart = new HashMap<>();
			List<ModelCart>list = new ArrayList<ModelCart>();
			ArrayList<ModelCart> newlist = new ArrayList<ModelCart>(ApplicationData.cart.values());
			for(int i=0;i<newlist.size();i++){
				ModelCart c = newlist.get(i);
				if(c.getType()=="po"){
					list.add(c);
				}
			}
			if(list.size()==0){
				LIST_MENU = ApplicationData.tempPO;
				mAdapter = new AdapterMenuPO(getActivity(), LIST_MENU);
				mListView.setAdapter(mAdapter);
				mListView.setVisibility(View.VISIBLE);
			}
		}
		else {
			if(ApplicationData.isNullCart){
				ApplicationData.isNullCart = false;
				LIST_MENU = ApplicationData.tempPO;
				mAdapter = new AdapterMenuPO(getActivity(), LIST_MENU);
				mListView.setAdapter(mAdapter);
				mListView.setVisibility(View.VISIBLE);
			}

		}

		SendBroadcast("updateCart","true");

		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(spaceLayout,
				new IntentFilter("spaceLayout"));



	}

	private void SendBroadcast(String typeBroadcast,String type){
		Intent intent = new Intent(typeBroadcast);
		// add data
		intent.putExtra("message", type);
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
	}

	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(getActivity(), ConfigManager.FLURRY_API_KEY);
		FlurryAgent.logEvent("MENU_PREORDER", flurryParams, true);
	}

	public void onStop() {
		super.onStop();
		FlurryAgent.endTimedEvent("MENU_PREORDER");
		FlurryAgent.onEndSession(getActivity());
	}





}
