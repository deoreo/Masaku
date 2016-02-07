package twiscode.masakuuser.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityProfile extends Activity {

	public static final String ARG_PAGE = "ARG_PAGE";
	private Button btnConfirm;
	public static EditText namaprofile, phoneprofile, emailprofile;
	private ApplicationManager applicationManager;
	private Activity act;
	private ImageView btnBack;
	Map<String, String> flurryParams = new HashMap<String,String>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		act = this;
		// drawer labels
		applicationManager = new ApplicationManager(act);
		namaprofile = (EditText) findViewById(R.id.namaProfile);
		phoneprofile = (EditText) findViewById(R.id.phoneprofile);
		emailprofile = (EditText) findViewById(R.id.emailProfile);
		btnBack = (ImageView) findViewById(R.id.btnBack);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);



		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent j = new Intent(getBaseContext(), Main.class);
				startActivity(j);
				finish();
			}
		});

		btnConfirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String name = namaprofile.getText().toString();
				String phone = phoneprofile.getText().toString();
				String email = emailprofile.getText().toString();
				if(name.isEmpty() || phone.isEmpty() || email.isEmpty()){
					DialogManager.showDialog(act, "Mohon Maaf", "Silahkan melengkapi profil Anda!");
				}else if (!email.trim().contains("@") || !email.trim().contains(".")) {
					DialogManager.showDialog(act, "Mohon Maaf", "Format email Anda salah!");
				}else {
					new UpdateAllProfile(act).execute(name, phone, email);
				}
			}
		});

		/*
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
				*/
		phoneprofile.setKeyListener(null);

		if(!ApplicationData.phoneNumber.isEmpty()){
			phoneprofile.setText(ApplicationData.phoneNumber);
		}
		if(!ApplicationData.name.isEmpty()){
			namaprofile.setText(ApplicationData.name);
		}
		if(!ApplicationData.email.isEmpty()){
			emailprofile.setText(ApplicationData.email);
		}

	}

	@Override
	public void onBackPressed() {
		Intent j = new Intent(act, Main.class);
		startActivity(j);
		finish();
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
			progressDialog.setMessage("Save your profile. . .");
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
					ModelUser modelUser = applicationManager.getUser();
					if(param=="name"){
						modelUser.setNama(nama);
						ApplicationData.name = nama;
					}
					else if(param=="phoneNumber"){
						modelUser.setPonsel(nama);
						ApplicationData.phoneNumber = nama;
					}
					else if(param=="email"){
						modelUser.setEmail(nama);
						ApplicationData.email = nama;
					}
					applicationManager.setUser(modelUser);

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
					DialogManager.showDialog(act,"Mohon Maaf",messageError);
					break;
				case "OK":
					DialogManager.showDialog(act,"Info",messageSuccess);
					break;
			}
			progressDialog.dismiss();

		}


	}

	private class UpdateAllProfile extends AsyncTask<String, Void, String> {
		private Activity activity;
		private Context context;
		private Resources resources;
		private ProgressDialog progressDialog;
		//private String messageError,messageSuccess;

		public UpdateAllProfile(Activity activity) {
			super();
			this.activity = activity;
			this.context = activity.getApplicationContext();
			this.resources = activity.getResources();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage("Save your profile. . .");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			try {

				String nama = params[0];
				String phone = params[1];
				String email = params[2];
				//String param = params[3];
				JSONControl jsControl = new JSONControl();
				String response = jsControl.updateAllProfile(nama, "+62"+phone, email, applicationManager.getUserToken());
				Log.d("json response", response.toString());
				if(response.contains("true")){
					ModelUser modelUser = applicationManager.getUser();

						modelUser.setNama(nama);
						ApplicationData.name = nama;

						modelUser.setPonsel(phone);
						ApplicationData.phoneNumber = phone;


						modelUser.setEmail(email);
						ApplicationData.email = email;

					applicationManager.setUser(modelUser);

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
					DialogManager.showDialog(act,"Mohon Maaf","Update profil gagal");
					break;
				case "OK":
					DialogManager.showDialog(act,"Info","Berhasil update profil");
					break;
			}
			progressDialog.dismiss();

		}


	}

	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(act, ConfigManager.FLURRY_API_KEY);
		FlurryAgent.logEvent("PROFILE", flurryParams, true);
	}

	public void onStop() {
		super.onStop();
		FlurryAgent.endTimedEvent("PROFILE");
		FlurryAgent.onEndSession(act);
	}

	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}





}
