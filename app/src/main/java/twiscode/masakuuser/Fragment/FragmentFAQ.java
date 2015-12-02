package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DataFragmentHelper;
import twiscode.masakuuser.Utilities.PersistenceDataHelper;

public class FragmentFAQ extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

	private RecyclerView recyclerView;
	private LinearLayout btnFaq1, btnFaq2, btnFaq3, btnFaq4,btnFaq5,
			btnFaq6, btnFaq7, btnFaq8, btnFaq9, btnFaq10, btnFaq11;
	private TextView txtFaq1, txtFaq2, txtFaq3, txtFaq4, txtFaq5,
			txtFaq6, txtFaq7, txtFaq8, txtFaq9, txtFaq10, txtFaq11;
	private DataFragmentHelper datafragmentHelper = PersistenceDataHelper.GetInstance().FragmentHelper;


	public static FragmentFAQ newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentFAQ fragment = new FragmentFAQ();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_faq, container, false);
		btnFaq1 = (LinearLayout) rootView.findViewById(R.id.btnFaq1);
		btnFaq2 = (LinearLayout) rootView.findViewById(R.id.btnFaq2);
		btnFaq3 = (LinearLayout) rootView.findViewById(R.id.btnFaq3);
		btnFaq4 = (LinearLayout) rootView.findViewById(R.id.btnFaq4);
		btnFaq5 = (LinearLayout) rootView.findViewById(R.id.btnFaq5);
		btnFaq6 = (LinearLayout) rootView.findViewById(R.id.btnFaq6);
		btnFaq7 = (LinearLayout) rootView.findViewById(R.id.btnFaq7);
		btnFaq8 = (LinearLayout) rootView.findViewById(R.id.btnFaq8);
		btnFaq9 = (LinearLayout) rootView.findViewById(R.id.btnFaq9);
		btnFaq10 = (LinearLayout) rootView.findViewById(R.id.btnFaq10);
		btnFaq11 = (LinearLayout) rootView.findViewById(R.id.btnFaq11);

		txtFaq1 = (TextView) rootView.findViewById(R.id.txtFaq1);
		txtFaq2 = (TextView) rootView.findViewById(R.id.txtFaq2);
		txtFaq3 = (TextView) rootView.findViewById(R.id.txtFaq3);
		txtFaq4 = (TextView) rootView.findViewById(R.id.txtFaq4);
		txtFaq5 = (TextView) rootView.findViewById(R.id.txtFaq5);
		txtFaq6 = (TextView) rootView.findViewById(R.id.txtFaq6);
		txtFaq7 = (TextView) rootView.findViewById(R.id.txtFaq7);
		txtFaq8 = (TextView) rootView.findViewById(R.id.txtFaq8);
		txtFaq9 = (TextView) rootView.findViewById(R.id.txtFaq9);
		txtFaq10 = (TextView) rootView.findViewById(R.id.txtFaq10);
		txtFaq11 = (TextView) rootView.findViewById(R.id.txtFaq11);

		btnFaq1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq1.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq1);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq2.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq2);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq3.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq3);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq4.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq4);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq5.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq5);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq6.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq6);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq7.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq7);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq8.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq8);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq9.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq9);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq10.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq10);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});

		btnFaq11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ApplicationData.question = txtFaq11.getText().toString();
				ApplicationData.answer = getResources().getString(R.string.AnsFaq11);
				android.support.v4.app.Fragment fragment = null;
				fragment = new FragmentFAQDetail();
				FragmentManager fragmentManager = getFragmentManager();
				datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
				datafragmentHelper.ChangeFragment(fragment);
				ApplicationData.titleBar.setText("FAQ");
			}
		});


		return rootView;
	}


}
