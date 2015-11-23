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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityAbout;
import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Activity.ActivityLogin;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

public class FragmentProfile extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private LinearLayout btnAbout;
	private LinearLayout btnLogout;
	private TextView telponProfile;
	private EditText namaprofile;
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
		applicationManager = new ApplicationManager(getActivity());
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_profile, container, false);
		InitDialog();
		namaprofile = (EditText) rootView.findViewById(R.id.namaProfile);
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

		namaprofile.setOnEditorActionListener(
				new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							if(namaprofile.getText().toString().length() > 0){
								Log.d("nama profile", namaprofile.getText().toString());
								new Updateprofile(getActivity()).execute(namaprofile.getText().toString(),"name","Update nama profile gagal!","Update nama profile sukses!");
								return true;
							}

						}
						return false;
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
				ApplicationData.cart = new HashMap<String, ModelCart>();
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
				if (NetworkManager.getInstance(getActivity()).isConnectedInternet()) {
					//InitProgress();
					//new DoLogoutAll(getActivity()).execute();
					ApplicationData.cart = new HashMap<String, ModelCart>();
					db.logout();
					Intent i = new Intent(getActivity(), ActivityLogin.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
					getActivity().finish();
				}
			}
		});

	}

	private class Updateprofile extends AsyncTask<String, Void, String> {
		private Activity activity;
		private Context context;
		private Resources resources;
		private ProgressDialog progressDialog;
		private String messageError,messageSuccess;

		public Updateprofile(Activity activity) {
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

				String nama = params[0];
				String param = params[1];
				messageError = params[2];
				messageSuccess = params[3];
				JSONControl jsControl = new JSONControl();
				String response = jsControl.updateProfile(nama, param, applicationManager.getUserToken());
				Log.d("json response", response.toString());
				if(response.contains("true")){
					ModelUser us = applicationManager.getUser();
					if(param=="name"){
						us.setNama(nama);
						ApplicationData.name = nama;
					}
					else if(param=="phoneNumber"){
						us.setPonsel(nama);
						ApplicationData.phoneNumber = nama;
					}
					applicationManager.setUser(us);

					return "OK";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return "FAIL";

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);


			switch (result) {
				case "FAIL":
					DialogManager.showDialog(getActivity(),"Mohon Maaf",messageError);
					break;
				case "OK":
					DialogManager.showDialog(getActivity(),"Info",messageSuccess);
					break;
			}
			progressDialog.dismiss();

		}


	}







}
