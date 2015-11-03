package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import twiscode.masakuuser.Activity.ActivityLogin;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.R;

public class FragmentProfile extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private LinearLayout btnAbout;
	private LinearLayout btnLogout;

	public static FragmentProfile newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentProfile fragment = new FragmentProfile();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_profile, container, false);
		btnAbout = (LinearLayout) rootView.findViewById(R.id.btnAboutProfile);
		btnLogout = (LinearLayout) rootView.findViewById(R.id.btnLogout);
		btnAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityLogin.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				getActivity().finish();

			}
		});


		return rootView;
	}


}
