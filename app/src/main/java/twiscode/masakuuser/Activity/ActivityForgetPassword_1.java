package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityForgetPassword_1 extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSend;
    private EditText txtPhone;
    private boolean isClick = false;
    ApplicationManager appManager;
    Activity act;

    private String phone;

    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_1);

        act = this;
        //appManager = new ApplicationManager(act);


        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSend = (RelativeLayout) findViewById(R.id.wrapperSend);
        txtPhone = (EditText) findViewById(R.id.txtPhone);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent i = new Intent(getBaseContext(), ActivityForgetPassword_2.class);
                startActivity(i);
                finish();
                */
                if(!isClick){
                    if(NetworkManager.getInstance(act).isConnectedInternet()){
                        isClick = true;
                        phone = txtPhone.getText().toString();
                        if (phone == null || phone.trim().isEmpty() ) {
                            isClick = false;
                            DialogManager.showDialog(act, "Mohon Maaf", "Isi Nomor Ponsel Anda!");
                        } else {
                            /*
                            String num=phone.substring(0,1);
                            Log.d("phone num", num);
                            Log.d("phone",phone);
                            if(num.contains("0")){
                                phone = phone.substring(1);
                                Log.d("phone 1",phone);
                            }
                            */
                            new ForgotPassword(act).execute(phone);
                        }

                    }
                    else {
                        DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                    }
                }

            }
        });


    }

    private class ForgotPassword extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public ForgotPassword(Activity activity) {
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

                String phone = params[0];
                JSONControl jsControl = new JSONControl();
                String response = jsControl.postForgotPassword(phone);
                Log.d("json response phone", response);

                if(response.contains("true") && !response.contains("error")){
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
                    DialogManager.showDialog(activity, "Mohon Maaf", "User tidak ditemukan");
                    break;
                case "OK":
                    ApplicationData.phoneNumber = phone;
                    Intent i = new Intent(getBaseContext(), ActivityForgetPassword_2.class);
                    startActivity(i);
                    finish();
                    break;
            }


        }


    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("FORGET_PASSWORD", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("FORGET_PASSWORD");
        FlurryAgent.onEndSession(this);
    }

}
