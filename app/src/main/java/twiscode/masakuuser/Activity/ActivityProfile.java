package twiscode.masakuuser.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flurry.android.FlurryAgent;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static MaterialEditText namaprofile, emailprofile;
    public static EditText phoneprofile;
    private ApplicationManager applicationManager;
    private Activity act;
    private ImageView btnBack;
    private TextView btnLogout;
    private Dialog dialog;
    private DatabaseHandler db;
    Map<String, String> flurryParams = new HashMap<String, String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        act = this;
        db = new DatabaseHandler(act);
        // drawer labels
        applicationManager = new ApplicationManager(act);
        namaprofile = (MaterialEditText) findViewById(R.id.namaProfile);
        phoneprofile = (EditText) findViewById(R.id.phoneprofile);
        emailprofile = (MaterialEditText) findViewById(R.id.emailProfile);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnLogout = (TextView) findViewById(R.id.btnLogout);
        InitDialog();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getBaseContext(), Main.class);
                startActivity(j);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = namaprofile.getText().toString();
                String phone = phoneprofile.getText().toString();
                String email = emailprofile.getText().toString();
                if (name.isEmpty() ||  email.isEmpty()) {
                    DialogManager.showDialog(act, "Mohon Maaf", "Silahkan melengkapi profil Anda!");
                } else if (!email.trim().contains("@") || !email.trim().contains(".")) {
                    DialogManager.showDialog(act, "Mohon Maaf", "Format email Anda salah!");
                } else {
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
        try {
            if (ApplicationData.phoneNumber != "") {
                String substr62=ApplicationData.phoneNumber.substring(0, 3);
                String substr0=ApplicationData.phoneNumber.substring(0, 1);
                if (substr62.equalsIgnoreCase("+62")) {
                    phoneprofile.setText(ApplicationData.phoneNumber.substring(3));
                } else if (substr0.equalsIgnoreCase("0")) {
                    phoneprofile.setText(ApplicationData.phoneNumber.substring(1));
                } else {
                    phoneprofile.setText(ApplicationData.phoneNumber);
                }
            }
            if (ApplicationData.name != "") {
                namaprofile.setText(ApplicationData.name);
            }

            emailprofile.setText(applicationManager.getUser().getEmail());

        } catch (Exception e) {

        }



    }

    void InitDialog(){
        dialog = new Dialog(ActivityProfile.this);
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
                Intent i = new Intent(ActivityProfile.this, ActivityLogin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkManager.getInstance(ActivityProfile.this).isConnectedInternet()) {
                    //InitProgress();
                    //new DoLogoutAll(getActivity()).execute();
                    ApplicationData.cart = new HashMap<String, ModelCart>();
                    db.logout();
                    Intent i = new Intent(ActivityProfile.this, ActivityLogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent j = new Intent(act, Main.class);
        startActivity(j);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

    private class Updateprofile extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        private String messageError, messageSuccess;

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
                if (response.contains("true")) {
                    ModelUser modelUser = applicationManager.getUser();
                    if (param == "name") {
                        modelUser.setNama(nama);
                        ApplicationData.name = nama;
                    } else if (param == "phoneNumber") {
                        modelUser.setPonsel(nama);
                        ApplicationData.phoneNumber = nama;
                    } else if (param == "email") {
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
                    DialogManager.showDialog(act, "Mohon Maaf", messageError);
                    break;
                case "OK":
                    DialogManager.showDialog(act, "Info", messageSuccess);
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
        private String msg = "Email sudah digunakan";
        private String response;
        private JSONObject jsonObj;

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
                String response = jsControl.updateAllProfile(nama, "+62" + phone, email, applicationManager.getUserToken());
                Log.d("json response", response.toString());
                if (response.contains("true")) {
                    ModelUser modelUser = applicationManager.getUser();

                    modelUser.setNama(nama);
                    ApplicationData.name = nama;

                    modelUser.setPonsel(phone);
                    ApplicationData.phoneNumber = phone;


                    modelUser.setEmail(email);
                    ApplicationData.email = email;

                    applicationManager.setUser(modelUser);

                    return "OK";
                }else{
                    try {
                        response.replace("\n", "");
                        response.replaceAll(".*\".*", "\\\"");
                        jsonObj = new JSONObject(response);
                        msg = jsonObj.getString("message");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
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
                    DialogManager.showDialog(act, "Mohon Maaf", msg);
                    break;
                case "OK":
                    DialogManager.showDialog(act, "Info", "Berhasil update profil");
                    //ApplicationData.hasEmail = true;
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
