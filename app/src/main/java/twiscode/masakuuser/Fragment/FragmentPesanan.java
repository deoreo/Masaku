package twiscode.masakuuser.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Adapter.AdapterPesanan;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationManager;

public class FragmentPesanan extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelPesanan> LIST_PESANAN = new ArrayList<>();
	private ListView mListView;
	AdapterPesanan mAdapter;
	private TextView jmlPesanan;
	private int mPage;
	private ImageView btnCart;

	TextView noData;



	public static FragmentPesanan newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentPesanan fragment = new FragmentPesanan();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_pesanan, container, false);
		noData = (TextView) rootView.findViewById(R.id.noData);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_pesanan, null);
		jmlPesanan = (TextView)header.findViewById(R.id.jumlahPesanan);
		jmlPesanan.setText(LIST_PESANAN.size()+" Pesanan");
		mListView.addHeaderView(header);
		//mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
		mAdapter = new AdapterPesanan(getActivity(), LIST_PESANAN);
		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});

		if(LIST_PESANAN.size() > 0){
			mListView.setVisibility(View.VISIBLE);
			noData.setVisibility(View.GONE);
		}
		else {
			mListView.setVisibility(View.GONE);
			noData.setVisibility(View.VISIBLE);
		}

		return rootView;
	}

	private void DummyData() {

        new GetPesanan(getActivity()).execute();
		/*
		ModelPesanan modelDeliver0 = new ModelPesanan("0", "Mak Yem", "Sedang Dikirim", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "27 Oktober 2015", "15.00", "40.000");
		LIST_PESANAN.add(modelDeliver0);
		ModelPesanan modelDeliver1 = new ModelPesanan("0", "Bu Winda", "Transaksi Berhasil", "http://blog.travelio.com/wp-content/uploads/2015/03/Soto-Lamongan-Jawa-Timur-Indonesia.jpg", "24 Oktober 2015", "15.00", "22.000");
		LIST_PESANAN.add(modelDeliver1);
		ModelPesanan modelDeliver2 = new ModelPesanan("0", "Mama Tina", "Transaksi Berhasil", "https://c1.staticflickr.com/9/8474/8117817563_6cb6755539_b.jpg", "24 Oktober 2015", "15.00", "20.000");
		LIST_PESANAN.add(modelDeliver2);
		ModelPesanan modelDeliver3 = new ModelPesanan("0", "Ibu Rudi", "Transaksi Berhasil", "http://www.pegipegi.com/travel/wp-content/uploads/2014/09/nasgorindonesia.jpg", "24 Oktober 2015", "15.00", "100.000");
		LIST_PESANAN.add(modelDeliver3);
		ModelPesanan modelDeliver4 = new ModelPesanan("0", "Ibu Mirna", "Transaksi Berhasil", "http://img.hipwee.com/cdn/wp-content/uploads/2015/07/ayam-bakar-riun-tenda.jpg?0d2690", "24 Oktober 2015", "15.00", "30.000");
		LIST_PESANAN.add(modelDeliver4);
		*/
	}

    private class GetPesanan extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public GetPesanan(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                LIST_PESANAN = new ArrayList<ModelPesanan>();
                JSONControl jsControl = new JSONControl();
                JSONObject response = jsControl.getPesanan(ApplicationManager.getInstance(context).getUserToken());
                Log.d("json response", response.toString());
                JSONArray menus = response.getJSONArray("transactions");
                if(menus.length() > 0){
                    for(int i=0;i<menus.length();i++){
                        String id = menus.getJSONObject(i).getString("_id");
                        String price = menus.getJSONObject(i).getString("price");
                        String status = menus.getJSONObject(i).getString("status");;
						String create = menus.getJSONObject(i).getString("createdAt");
                        String dt = create.split("T")[0];
                        String[] dd = dt.split("-");
                        String date = dd[2]+" "+getMonth(dd[1])+" "+dd[0];
						String time = "";
                        ModelPesanan menu = new ModelPesanan(id, status, date, time, price);
                        LIST_PESANAN.add(menu);
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

            progressDialog.dismiss();
            switch (result) {
                case "FAIL":
                    //DialogManager.showDialog(activity, "Mohon maaf", "Nomor ponsel Anda belum terdaftar!");
                    break;
                case "OK":
                    //Intent i = new Intent(getBaseContext(), ActivityHome.class);
                    //startActivity(i);
                    //finish();
                    Log.d("jumlah menu : ",""+LIST_PESANAN.size());
                    mAdapter = new AdapterPesanan(getActivity(), LIST_PESANAN);
                    mListView.setAdapter(mAdapter);
                    break;

            }
        }
    }

    String getMonth(String bulan){
        String bln="";
        switch (bulan){
            case "01":
                bln = "Januari";
                break;
            case "02":
                bln = "Februari";
                break;
            case "03":
                bln = "Maret";
                break;
            case "04":
                bln = "April";
                break;
            case "05":
                bln = "Mei";
                break;
            case "06":
                bln = "Juni";
                break;
            case "07":
                bln = "Juli";
                break;
            case "08":
                bln = "Agustus";
                break;
            case "09":
                bln = "September";
                break;
            case "10":
                bln = "Oktober";
                break;
            case "11":
                bln = "November";
                break;
            case "12":
                bln = "Desember";
                break;
            default:
                bln = "Januari";
                break;
        }
        return bln;
    }




}
