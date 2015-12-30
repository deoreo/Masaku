package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityForgetPassword_3 extends AppCompatActivity {

    private EditText txtPass,txtConfirm;
    private ImageView btnBack;
    private RelativeLayout btnSend;
    Activity act;

    private boolean isClick = false;
    private String password = "";

    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_3);

        act = this;

        txtPass = (EditText) findViewById(R.id.txtPassword);
        txtConfirm = (EditText) findViewById(R.id.txtConfirmPassword);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSend = (RelativeLayout) findViewById(R.id.wrapperSend);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityForgetPassword_2.class);
                startActivity(i);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isClick){

                    if(NetworkManager.getInstance(act).isConnectedInternet()){
                        String pass = txtPass.getText().toString();
                        String confirm = txtConfirm.getText().toString();
                        if(confirm.equals(pass)){
                            isClick = true;
                            password = pass;
                            new ResetPassword(act).execute();
                        }
                        else {
                            DialogManager.showDialog(act, "Peringatan", "konfirmasi password tidak sama!");
                        }
                    }
                    else {
                        DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                    }
                }
            }
        });


    }

    private class ResetPassword extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public ResetPassword(Activity activity) {
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

                JSONControl jsControl = new JSONControl();
                String response = jsControl.postResetPassword(ApplicationData.phoneNumber, ApplicationData.tokenPass, password);
                Log.d("json response phone", response);

                if(response.contains("true")){
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

            progressDialog.dismiss();
            isClick = false;
            switch (result) {
                case "FAIL":
                    DialogManager.showDialog(activity, "Warning", "Reset password gagal!");
                    break;
                case "OK":
                    ApplicationData.phoneNumber="";
                    ApplicationData.tokenPass="";
                    //DialogManager.showDialog(activity, "Warning", "Reset password sukses!");
                    Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                    startActivity(i);
                    finish();
                    break;
            }


        }


    }

    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("FORGET_PASSWORD", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("FORGET_PASSWORD");
    }



}
