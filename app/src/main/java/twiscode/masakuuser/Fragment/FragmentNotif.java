package twiscode.masakuuser.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterNotif;
import twiscode.masakuuser.Adapter.AdapterPesanan;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelNotif;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;

public class FragmentNotif extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelNotif> LIST_NOTIF = new ArrayList<>();
	private ListView mListView;
	private AdapterNotif mAdapter;
	private TextView jmlPesanan;
	private int mPage;
	private ImageView btnCart;
    private final int PAGE = 1;
    private PullRefreshLayout mSwipeRefreshLayout;

	TextView noData;

    Map<String, String> flurryParams = new HashMap<String,String>();



	public static FragmentNotif newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentNotif fragment = new FragmentNotif();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View rootView = inflater.inflate(R.layout.activity_notif, container, false);
		noData = (TextView) rootView.findViewById(R.id.noData);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
        mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
        mSwipeRefreshLayout.setRefreshing(false);
		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_pesanan, null);
		jmlPesanan = (TextView)header.findViewById(R.id.jumlahPesanan);



		mListView.addHeaderView(header);
		mAdapter = new AdapterNotif(getActivity(), LIST_NOTIF);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);

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
                new GetNotif(getActivity()).execute();
            }
        });


        new GetNotif(getActivity()).execute();


		return rootView;
	}



    private class GetNotif extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;


        public GetNotif(Activity activity) {
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
                LIST_NOTIF = new ArrayList<ModelNotif>();
                JSONControl jsControl = new JSONControl();
                JSONObject response = jsControl.getNotif(ApplicationManager.getInstance(context).getUserToken(), PAGE);
                Log.d("json response", response.toString());
                JSONArray notification = response.getJSONArray("notification");
                if(notification.length() > 0){
                    for(int t=0;t<notification.length();t++){
                        String _id = notification.getJSONObject(t).getString("id");
                        String _menuId = notification.getJSONObject(t).getString("menuId");
                        String _userId = notification.getJSONObject(t).getString("userId");
                        String _message = notification.getJSONObject(t).getString("message");
                        String _createdAt = notification.getJSONObject(t).getString("createdAt");
                        String dt = _createdAt.split("T")[0];
                        String[] dd = dt.split("-");
                        String _date = dd[2]+" "+getMonth(dd[1])+" "+dd[0];

                        ModelNotif menu = new ModelNotif(_id,_userId,_menuId,_message,_date);
                        //ApplicationData.idLastTransaction = _id;
                        LIST_NOTIF.add(0, menu);

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
                    //DialogManager.showDialog(activity, "Mohon maaf", "Anda Tidak Terhubung dengan Internet!");
                    if(LIST_NOTIF.size() > 0){
                        mListView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    }
                    else {
                        mListView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                    break;
                case "OK":
                    Log.d("jumlah menu : ", "" + LIST_NOTIF.size());
                    try{
                        mAdapter = new AdapterNotif(getActivity(), LIST_NOTIF);
                        mListView.setAdapter(mAdapter);
                        jmlPesanan.setText(LIST_NOTIF.size() + " Notifications");
                        if(LIST_NOTIF.size() > 0){
                            mListView.setVisibility(View.VISIBLE);
                            noData.setVisibility(View.GONE);
                        }
                        else {
                            mListView.setVisibility(View.GONE);
                            noData.setVisibility(View.VISIBLE);
                        }
                    }
                    catch (Exception x){
                        x.printStackTrace();
                    }

                    break;
            }
            mSwipeRefreshLayout.setRefreshing(false);
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

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(getActivity(), ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("MENU_HISTORY", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("MENU_HISTORY");
        FlurryAgent.onEndSession(getActivity());
    }




}
