package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import twiscode.masakuuser.Activity.ActivityAbout;
import twiscode.masakuuser.Activity.ActivityRegister;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DataFragmentHelper;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.PersistenceDataHelper;

public class FragmentBantuan extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;
	private LinearLayout btnAbout, btnContactUs, btnFAQ, btnTerms;
	private RecyclerView recyclerView;
	private DataFragmentHelper datafragmentHelper = PersistenceDataHelper.GetInstance().FragmentHelper;


	public static FragmentBantuan newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentBantuan fragment = new FragmentBantuan();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_bantuan, container, false);
		btnAbout = (LinearLayout) rootView.findViewById(R.id.btnAbout);
		btnContactUs = (LinearLayout) rootView.findViewById(R.id.btnContactUs);
		btnFAQ = (LinearLayout) rootView.findViewById(R.id.btnFAQ);
		btnTerms = (LinearLayout) rootView.findViewById(R.id.btnTerms);
		btnAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityAbout.class);
				startActivity(i);
			}
		});
		btnContactUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentContactUs();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("Contact Us");
			}
		});
		btnFAQ.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQ();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});
		btnTerms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentTerms();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
			}
		});


		return rootView;
	}


}
