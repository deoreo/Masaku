package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityVerifyHp extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSend;
    private EditText txt1, txt2, txt3, txt4;
    private TextView txtResend;
    private BroadcastReceiver smsCode;
    Activity act;

    private String code;
    private boolean isClick = false;
    Map<String, String> flurryParams = new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_2);

        act = this;

        txt1 = (EditText) findViewById(R.id.txtPhone1);
        txt2 = (EditText) findViewById(R.id.txtPhone2);
        txt3 = (EditText) findViewById(R.id.txtPhone3);
        txt4 = (EditText) findViewById(R.id.txtPhone4);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSend = (RelativeLayout) findViewById(R.id.wrapperSend);
        txtResend = (TextView) findViewById(R.id.resendCode);

        smsCode = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String messageCode = intent.getStringExtra("messageCode");
                txt1.setText(messageCode.substring(0,1));
                txt2.setText(messageCode.substring(1,2));
                txt3.setText(messageCode.substring(2,3));
                txt4.setText(messageCode.substring(3));
                code = messageCode;
                txt4.requestFocus();
                if (NetworkManager.getInstance(act).isConnectedInternet()) {
                    isClick = true;
                    code = txt1.getText().toString() +
                            txt2.getText().toString() +
                            txt3.getText().toString() +
                            txt4.getText().toString()
                    ;

                    new CheckCode(act).execute(code);
                } else {
                    DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                }
            }
        };

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.temp_token = "";
                if (ApplicationData.isVerify == 0) {
                    Intent i = new Intent(getBaseContext(), ActivityRegister.class);
                    startActivity(i);
                    finish();
                } else {
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
                        code = txt1.getText().toString() +
                                txt2.getText().toString() +
                                txt3.getText().toString() +
                                txt4.getText().toString()
                        ;

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
                        code = txt1.getText().toString() +
                                txt2.getText().toString() +
                                txt3.getText().toString() +
                                txt4.getText().toString()
                        ;
                        new ResendCode(act).execute();
                    } else {
                        DialogManager.showDialog(act, "Peringatan", "Tidak ada koneksi internet!");
                    }
                }

            }
        });

        txt1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    txt2.requestFocus();
                }
            }
        });

        txt2.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    txt3.requestFocus();
                }
            }
        });

        txt3.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    txt4.requestFocus();
                }
            }
        });

        txt4.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    txt3.requestFocus();
                }
            }
        });

        txt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (txt2.getText().toString().isEmpty()) {
                        txt1.requestFocus();
                        txt1.setText("");
                    }
                }
                return false;
            }
        });

        txt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (txt3.getText().toString().isEmpty()) {
                        txt2.requestFocus();
                        txt2.setText("");
                    }
                }
                return false;
            }
        });

        txt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    if (txt4.getText().toString().isEmpty()) {

                        txt3.requestFocus();
                        txt3.setText("");
                    }
                }
                return false;
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
                    ApplicationManager.getInstance(ActivityVerifyHp.this).setUser(ApplicationData.temp_user);
                    ApplicationData.temp_hp = "";
                    ApplicationData.temp_password = "";
                    ApplicationData.temp_nama = "";
                    ApplicationData.temp_token = "";
                    ApplicationData.temp_user = new ModelUser();
                    ApplicationData.isHelp = false;
                    Intent i = new Intent(getBaseContext(), ActivityTutorial.class);
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
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(smsCode,
                new IntentFilter("smsCode"));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("VERIFICATION_CODE", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("VERIFICATION_CODE");
        FlurryAgent.onEndSession(this);
    }



}
