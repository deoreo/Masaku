package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import twiscode.masakuuser.R;

public class FragmentPesanan extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

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

		View rootView = inflater.inflate(R.layout.activity_pesanan, container, false);


		return rootView;
	}


}
