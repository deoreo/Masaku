package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.DialogManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by User on 10/27/2015.
 */
public class ActivityRegister extends Activity {

    private Activity mActivity;
    private EditText txtPhone,txtName,txtPassword,txtConfirm;
    private Button btnRegister,btnLogin;
    private ModelUser userLogin;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mActivity = this;
        //db = new DatabaseHandler(mActivity);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtName = (EditText) findViewById(R.id.txtNama);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirm = (EditText) findViewById(R.id.txtConfirmPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String phoneNumber = txtPhone.getText().toString();
                String password = txtPassword.getText().toString();
                String confirm = txtConfirm.getText().toString();
                if (phoneNumber == null || password == null || phoneNumber.trim().isEmpty() || password.trim().isEmpty() || confirm == null || confirm.trim().isEmpty() || name == null || name.trim().isEmpty()) {
                    DialogManager.showDialog(mActivity, "Warning", "Masukkan semua data Anda!");
                } else if (!confirm.equals(password)) {
                    DialogManager.showDialog(mActivity, "Warning", "Password tidak sesuai!");
                } else {
                    String num = phoneNumber.substring(0, 1);
                    Log.d("phone num", num);
                    Log.d("phone", phoneNumber);
                    if (num.contains("0")) {
                        Log.d("phone 1", phoneNumber);
                        /*
                        phoneNumber = phoneNumber.substring(1);

                        */
                        DialogManager.showDialog(mActivity,"Informasi","Masukkan nomor ponsel seperti berikut : 085959084701");

                    }
                    else {
                        phoneNumber = "8"+phoneNumber;
                        new DoRegister(mActivity).execute(
                                name,
                                phoneNumber,
                                password
                        );
                    }

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
                JSONControl jsControl = new JSONControl();
                JSONObject responseRegister = jsControl.postRegister(name,phoneNumber, password);
                Log.d("json responseRegister", responseRegister.toString());
                if(! responseRegister.toString().contains("errors")){
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
                    DialogManager.showDialog(mActivity,"Warning", message);
                    break;
                case "OK":
                    Intent i = new Intent(getBaseContext(), ActivityHome.class);
                    startActivity(i);
                    finish();
                    break;
            }
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
