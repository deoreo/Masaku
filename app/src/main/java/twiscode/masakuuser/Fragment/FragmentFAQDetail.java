package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import twiscode.masakuuser.R;

public class FragmentFAQDetail extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

	private RecyclerView recyclerView;
	private LinearLayout btnFaq1, btnFaq2, btnFaq3, btnFaq4,btnFaq5,
			btnFaq6, btnFaq7, btnFaq8, btnFaq9, btnFaq10, btnFaq11;
	private TextView txtFaq1, txtFaq2, txtFaq3, txtFaq4, txtFaq5,
			txtFaq6, txtFaq7, txtFaq8, txtFaq9, txtFaq10, txtFaq11;



	public static FragmentFAQDetail newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentFAQDetail fragment = new FragmentFAQDetail();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_faq, container, false);
		btnFaq1 = (LinearLayout) rootView.findViewById(R.id.btnFaq1);
		btnFaq2 = (LinearLayout) rootView.findViewById(R.id.btnFaq2);

		return rootView;
	}


}
