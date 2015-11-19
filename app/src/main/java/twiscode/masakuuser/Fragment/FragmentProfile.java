package twiscode.masakuuser.Fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import twiscode.masakuuser.Activity.ActivityAbout;
import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Activity.ActivityLogin;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.NetworkManager;

public class FragmentProfile extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private LinearLayout btnAbout;
	private LinearLayout btnLogout;
	private TextView namaprofile,telponProfile;
	private ImageView btnCart;
	private Dialog dialog;
	private ProgressDialog progressDialog;
	private DatabaseHandler db;
	private ApplicationManager applicationManager;

	public static FragmentProfile newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentProfile fragment = new FragmentProfile();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// drawer labels
		db = new DatabaseHandler(getActivity());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_profile, container, false);
		InitDialog();
		namaprofile = (TextView) rootView.findViewById(R.id.namaProfile);
		telponProfile = (TextView) rootView.findViewById(R.id.telponProfile);
		btnCart = (ImageView) rootView.findViewById(R.id.btnCart);
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
				dialog.show();

			}
		});
		btnAbout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityAbout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);

			}
		});

		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});

		if(ApplicationData.phoneNumber != ""){
			telponProfile.setText(ApplicationData.phoneNumber);
		}
		if(ApplicationData.name != ""){
			namaprofile.setText(ApplicationData.name);
		}


		return rootView;
	}

	void InitDialog(){
		dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.popup_logout);
		dialog.setTitle("Logout");

		// set the custom dialog components - text, image and button

		TextView logout = (TextView) dialog.findViewById(R.id.btnLogoutPop);
		TextView device = (TextView) dialog.findViewById(R.id.btnLogoutAllPop);
		TextView cancel = (TextView) dialog.findViewById(R.id.btnCancelPop);
		// if button is clicked, close the custom dialog
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db.logout();
				Intent i = new Intent(getActivity(), ActivityLogin.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				getActivity().finish();
			}
		});

		device.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(NetworkManager.getInstance(getActivity()).isConnectedInternet()){
					//InitProgress();
					//new DoLogoutAll(getActivity()).execute();
					db.logout();
					Intent i = new Intent(getActivity(), ActivityLogin.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					getActivity().finish();
				}
			}
		});

	}

	void InitProgress(){
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Loading...");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
	}



}
