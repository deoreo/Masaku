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

import org.angmarch.views.NiceSpinner;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityRegisterNext extends AppCompatActivity {

    ImageView btnBack;
    Activity mActivity;
    NiceSpinner genderSpiner;
    EditText txtTahun;
    Button btnRegister,btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        mActivity = this;
        btnBack = (ImageView) findViewById(R.id.btnBack);
        genderSpiner = (NiceSpinner) findViewById(R.id.genderSpinner);
        txtTahun = (EditText) findViewById(R.id.txtTahun);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        List<String> dataPay = new LinkedList<>(Arrays.asList("male", "female"));
        genderSpiner.attachDataSource(dataPay);

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
                if(NetworkManager.getInstance(mActivity).isConnectedInternet()){
                    new DoRegister(mActivity).execute(
                            ApplicationData.temp_nama,
                            ApplicationData.temp_hp,
                            ApplicationData.temp_password,
                            gender,
                            tahun
                    );
                }
                else {
                    DialogManager.showDialog(mActivity,"Mohon Maaf","Tidak ada koneksi internet !");
                }

            }
        });


    }

    private class DoRegister extends AsyncTask<String, Void, String> {
        String message="";
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
                String password = params[2];
                String gender = params[3];
                String tahun = params [4];

                JSONControl jsControl = new JSONControl();
                JSONObject responseRegister = jsControl.postRegister(name,phoneNumber, password, gender, tahun);
                Log.d("json responseRegister", responseRegister.toString());
                if(! responseRegister.toString().contains("errors")){
                    ApplicationData.temp_token = responseRegister.getString("token");
                    ModelUser user = new ModelUser();
                    String _id = responseRegister.getJSONObject("user").getString("_id");
                    String _name = responseRegister.getJSONObject("user").getString("name");
                    String _phone = responseRegister.getJSONObject("user").getString("phoneNumber");
                    user.setId(_id);
                    user.setNama(_name);
                    user.setPonsel(_phone);
                    ApplicationData.temp_user = user;
                    return "OK";
                }
                else {
                    message = responseRegister.getString("message");
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
                    DialogManager.showDialog(mActivity, "Warning", message);
                    break;
                case "OK":
                    ApplicationData.isVerify = 0;
                    ApplicationData.temp_nama = "";
                    ApplicationData.temp_password = "";
                    ApplicationData.temp_password = "";
                    Intent i = new Intent(getBaseContext(), ActivityVerifyHp.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }


    }




}
