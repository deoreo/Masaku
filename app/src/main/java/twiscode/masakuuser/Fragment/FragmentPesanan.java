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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterPesanan;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.R;

public class FragmentPesanan extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelPesanan> LIST_PESANAN = new ArrayList<>();
	private ListView mListView;
	AdapterPesanan mAdapter;
	private TextView jmlPesanan;
	private int mPage;
	private ImageView btnCart;

	private RecyclerView recyclerView;



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

		return rootView;
	}

	private void DummyData() {
		LIST_PESANAN = new ArrayList<ModelPesanan>();
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
	}


}
