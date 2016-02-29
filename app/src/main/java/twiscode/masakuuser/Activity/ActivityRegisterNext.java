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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.flurry.android.FlurryAgent;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;

//import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
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
public class ActivityRegisterNext extends AppCompatActivity {

    ImageView btnBack;
    Activity mActivity;
    MaterialSpinner genderSpiner;
    EditText txtTahun;
    Button btnRegister,btnLogin;
    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        mActivity = this;
        btnBack = (ImageView) findViewById(R.id.btnBack);
        genderSpiner = (MaterialSpinner ) findViewById(R.id.genderSpinner);
        txtTahun = (EditText) findViewById(R.id.txtTahun);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        //List<String> dataPay = new LinkedList<>(Arrays.asList("male", "female"));
        //genderSpiner.attachDataSource(dataPay);
        genderSpiner.setItems("male", "female");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityRegister.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tahun = txtTahun.getText().toString();
                String gender = genderSpiner.getText().toString();
                if(tahun.isEmpty()){
                    DialogManager.showDialog(mActivity, "Mohon Maaf", "Isi tahun lahir Anda");
                }else {

                    if (NetworkManager.getInstance(mActivity).isConnectedInternet()) {
                        new DoRegister(mActivity).execute(
                                ApplicationData.temp_nama,

                                ApplicationData.temp_hp,
                                ApplicationData.email,
                                ApplicationData.temp_password,
                                gender,
                                tahun
                        );
                    } else {
                        DialogManager.showDialog(mActivity, "Mohon Maaf", "Tidak ada koneksi internet !");
                    }
                }

            }
        });


    }

    private class DoRegister extends AsyncTask<String, Void, String> {
        String message="Anda tidak terhubung dengan server";
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        String error = "";

        public DoRegister(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Signing Up. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String name = params[0];
                String phoneNumber = params[1];
                String email = params [2];
                String password = params[3];
                String gender = params[4];
                String tahun = params [5];

                JSONControl jsControl = new JSONControl();
                JSONObject responseRegister = jsControl.postRegister(name,phoneNumber, email, password, gender, tahun);
                    Log.d("json responseRegister", responseRegister.toString());
                    if (!responseRegister.toString().contains("errors")) {
                        ApplicationData.temp_token = responseRegister.getString("token");
                        ModelUser user = new ModelUser();
                        String _id = responseRegister.getJSONObject("user").getString("_id");
                        String _name = responseRegister.getJSONObject("user").getString("name");
                        String _phone = responseRegister.getJSONObject("user").getString("phoneNumber");
                        String _email = responseRegister.getJSONObject("user").getString("email");
                        user.setId(_id);
                        user.setNama(_name);
                        user.setPonsel(_phone);
                        user.setEmail(_email);
                        user.setTrusted("false");
                        ApplicationManager.getInstance(activity).setUser(user);
                        ApplicationData.temp_user = user;
                        VisitorInfo visitorData = new VisitorInfo.Builder()
                                .name(ApplicationManager.getInstance(activity).getUser().getNama())
                                .email(ApplicationManager.getInstance(activity).getUser().getEmail())
                                .phoneNumber(ApplicationManager.getInstance(activity).getUser().getPonsel())
                                .build();
                        ZopimChat.setVisitorInfo(visitorData);
                        return "OK";
                    } else {
                        //message = responseRegister.getString("message");
                        JSONArray errors = responseRegister.getJSONArray("errors");
                        message = errors.getJSONObject(0).getString("message");
                        return "FAIL";
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
            switch (result) {
                case "FAIL":
                    DialogManager.showDialog(mActivity, "Mohon Maaf", message);
                    break;

                case "OK":
                    //ApplicationData.hasEmail = true;
                    ApplicationData.isVerify = 0;
                    ApplicationData.temp_nama = "";
                    ApplicationData.temp_password = "";
                    ApplicationData.temp_password = "";
                    ApplicationData.email = "";



                    Intent i = new Intent(getBaseContext(), ActivityVerifyHp.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }


    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("REGISTER", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("REGISTER");
        FlurryAgent.onEndSession(this);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
