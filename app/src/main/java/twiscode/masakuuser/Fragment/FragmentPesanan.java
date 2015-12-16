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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterPesanan;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;

public class FragmentPesanan extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelDetailTransaksi> LIST_PESANAN = new ArrayList<>();
	private ListView mListView;
	AdapterPesanan mAdapter;
	private TextView jmlPesanan;
	private int mPage;
	private ImageView btnCart;
    private final int PAGE = 1;
    private PullRefreshLayout mSwipeRefreshLayout;

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


		View rootView = inflater.inflate(R.layout.activity_pesanan, container, false);
		noData = (TextView) rootView.findViewById(R.id.noData);
		mListView = (ListView) rootView.findViewById(R.id.list_delivery);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
        mSwipeRefreshLayout = (PullRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
        mSwipeRefreshLayout.setRefreshing(false);
		View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_pesanan, null);
		jmlPesanan = (TextView)header.findViewById(R.id.jumlahPesanan);



		mListView.addHeaderView(header);
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

        mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetPesanan(getActivity()).execute();
            }
        });


        new GetPesanan(getActivity()).execute();


		return rootView;
	}

	private void DummyData() {


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


        public GetPesanan(Activity activity) {
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
                LIST_PESANAN = new ArrayList<ModelDetailTransaksi>();
                JSONControl jsControl = new JSONControl();
                JSONObject response = jsControl.getPesanan(ApplicationManager.getInstance(context).getUserToken(), PAGE);
                Log.d("json response", response.toString());
                JSONArray transaction = response.getJSONArray("transactions");
                if(transaction.length() > 0){
                    for(int t=0;t<transaction.length();t++){
                        /*
                        String id = menus.getJSONObject(i).getString("_id");
                        String address = menus.getJSONObject(i).getString("address");
                        String price = menus.getJSONObject(i).getString("price");
                        String status = menus.getJSONObject(i).getString("status");;
						String create = menus.getJSONObject(i).getString("createdAt");
                        String dt = create.split("T")[0];
                        String[] dd = dt.split("-");
                        String date = dd[2]+" "+getMonth(dd[1])+" "+dd[0];
						String time = "";

                        ModelPesanan menu = new ModelPesanan(address, status, date, time, price);
                        LIST_PESANAN.add(0,menu);
                        */
                        String _id = transaction.getJSONObject(t).getString("id");
                        String _status = transaction.getJSONObject(t).getString("status");
                        String _waktu = transaction.getJSONObject(t).getString("timeLapse");
                        String _uid = transaction.getJSONObject(t).getString("user");
                        String _alamat = transaction.getJSONObject(t).getString("address");
                        String _note = transaction.getJSONObject(t).getString("note");
                        String _subtotal = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("base");
                        String _delivery = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("shipping");
                        String _diskon = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("discount");
                        String _convenience = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("convenientFee");
                        String _total = transaction.getJSONObject(t).getString("price");
                        String _type = transaction.getJSONObject(t).getString("type");
                        String _nama = transaction.getJSONObject(t).getJSONObject("user").getString("name");
                        String _phone = transaction.getJSONObject(t).getJSONObject("user").getString("phoneNumber");
                        String _tip = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("tip");;
                        String _detailID=transaction.getJSONObject(t).getString("prettyId");
                        JSONArray _order = transaction.getJSONObject(t).getJSONArray("orders");
                        List<ModelCart> _carts = new ArrayList<>();
                        if(_order.length() > 0){
                            for(int i=0;i<_order.length();i++){
                                ModelCart c = new ModelCart();
                                c.setId(_order.getJSONObject(i).getString("_id"));
                                c.setJumlah(Integer.parseInt(_order.getJSONObject(i).getString("quantity")));
                                c.setNama(_order.getJSONObject(i).getJSONObject("menu").getString("name"));
                                c.setHarga(Integer.parseInt(_order.getJSONObject(i).getJSONObject("menu").getString("price")));
                                c.setType(_type);
                                _carts.add(c);
                            }
                        }
                        String create = transaction.getJSONObject(t).getString("createdAt");
                        String dt = create.split("T")[0];
                        String[] dd = dt.split("-");
                        String _date = dd[2]+" "+getMonth(dd[1])+" "+dd[0];
                        String _time = "";
                        ModelDetailTransaksi menu = new ModelDetailTransaksi(_id,_type,_uid,_nama,_alamat,_phone,_note,_subtotal,_convenience,_total,_waktu,_diskon,_tip,_delivery,_status,_detailID,_date,_time,_carts);
                        //ApplicationData.idLastTransaction = _id;
                        LIST_PESANAN.add(0,menu);

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
                    if(LIST_PESANAN.size() > 0){
                        mListView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    }
                    else {
                        mListView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                    break;
                case "OK":
                    Log.d("jumlah menu : ",""+LIST_PESANAN.size());
                    mAdapter = new AdapterPesanan(getActivity(), LIST_PESANAN);
                    mListView.setAdapter(mAdapter);
                    jmlPesanan.setText(LIST_PESANAN.size() + " Pesanan");
                    if(LIST_PESANAN.size() > 0){
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
                bln = "May";
                break;
            case "06":
                bln = "Jun";
                break;
            case "07":
                bln = "Jul";
                break;
            case "08":
                bln = "Aug";
                break;
            case "09":
                bln = "Sep";
                break;
            case "10":
                bln = "Oct";
                break;
            case "11":
                bln = "Nov";
                break;
            case "12":
                bln = "Dec";
                break;
            default:
                bln = "Jan";
                break;
        }
        return bln;
    }




}
