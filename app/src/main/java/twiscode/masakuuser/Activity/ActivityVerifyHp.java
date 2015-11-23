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
import android.widget.TextView;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityVerifyHp extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSend;
    private EditText txtPhone;
    private TextView txtResend;

    Activity act;

    private String code;
    private boolean isClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_2);

        act = this;

        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSend = (RelativeLayout) findViewById(R.id.wrapperSend);
        txtResend = (TextView) findViewById(R.id.resendCode);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.temp_token = "";
                if(ApplicationData.isVerify==0){
                    Intent i = new Intent(getBaseContext(), ActivityRegister.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    if (NetworkManager.getInstance(act).isConnectedInternet()) {
                        isClick = true;
                        code = txtPhone.getText().toString();

                        new CheckCode(act).execute(code);
                    } else {
                        DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                    }
                }
            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    if (NetworkManager.getInstance(act).isConnectedInternet()) {
                        isClick = true;
                        code = txtPhone.getText().toString();

                        new ResendCode(act).execute();
                    } else {
                        DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                    }
                }

            }
        });


    }

    private class CheckCode extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public CheckCode(Activity activity) {
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

                String code = params[0];

                JSONControl jsControl = new JSONControl();
                String response = jsControl.postVerifyCode(code, ApplicationData.temp_token);
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
                    DialogManager.showDialog(activity, "Warning", "kode verifikasi salah!");
                    break;
                case "OK":
                    DatabaseHandler db = new DatabaseHandler(ActivityVerifyHp.this);
                    db.insertuser(ApplicationData.temp_user);
                    ApplicationData.login_id = ApplicationData.temp_user.getId();
                    ApplicationData.name = ApplicationData.temp_user.getNama();
                    ApplicationData.phoneNumber = ApplicationData.temp_user.getPonsel();
                    ApplicationManager.getInstance(ActivityVerifyHp.this).setUserToken(ApplicationData.temp_token);
                    ApplicationData.temp_hp = "";
                    ApplicationData.temp_password = "";
                    ApplicationData.temp_nama = "";
                    ApplicationData.temp_token = "";
                    ApplicationData.temp_user = new ModelUser();
                    Intent i = new Intent(getBaseContext(), ActivityHome.class);
                    startActivity(i);
                    finish();
                    break;
            }


        }


    }

    private class ResendCode extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public ResendCode(Activity activity) {
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
                String response = jsControl.postResendCodeVerify(ApplicationData.temp_token);
                Log.d("json response phone", response);

                if(response.contains("true")){
                    ApplicationManager.getInstance(activity).setUserToken(ApplicationData.temp_token);
                    ApplicationManager.getInstance(activity).setUser(ApplicationData.temp_user);
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
                    DialogManager.showDialog(activity, "Warning", "Resend kode gagal!");
                    break;
                case "OK":
                    break;
            }


        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
