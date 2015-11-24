package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.HashMap;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Fragment.FragmentProfile;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by User on 11/24/2015.
 */
public class ActivityProfileNama extends Activity {

    private EditText namaProfile;
    private RelativeLayout btnSimpan;
    private DatabaseHandler db;
    private ApplicationManager applicationManager;
    private Activity act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_nama);
        namaProfile = (EditText) findViewById(R.id.namaProfile);
        btnSimpan = (RelativeLayout) findViewById(R.id.btnSimpan);
        act = ActivityProfileNama.this;
        db = new DatabaseHandler(act);
        applicationManager = new ApplicationManager(act);

        if(ApplicationData.name != ""){
            namaProfile.setText(ApplicationData.name);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Updateprofile(act).execute(namaProfile.getText().toString(),"name","Update nama profile gagal!","Update nama profile sukses!");
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
                    DialogManager.showDialog(activity, "Mohon Maaf", messageError);
                    break;
                case "OK":
                    try {
                        final Context ctx = ActivityProfileNama.this;
                        new MaterialDialog.Builder(ctx)
                                .title("Informasi")
                                .content(messageSuccess)
                                .positiveText("OK")
                                .callback(new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        if (NetworkManager.getInstance(ActivityProfileNama.this).isConnectedInternet()) {
                                            FragmentProfile.namaprofile.setText(ApplicationData.name);
                                            onBackPressed();
                                        } else {
                                            DialogManager.showDialog(ActivityProfileNama.this, "Mohon Maaf", "Tidak ada koneksi internet!");
                                        }
                                        dialog.dismiss();
                                    }
                                })
                                .cancelable(false)
                                .typeface("GothamRnd-Medium.otf", "Gotham.ttf")
                                .show();
                    } catch (Exception e) {

                    }

                    break;
            }
            progressDialog.dismiss();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
