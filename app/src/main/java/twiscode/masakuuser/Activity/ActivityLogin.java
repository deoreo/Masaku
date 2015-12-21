package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ActivityLogin extends Activity{

    private TextView btnForget;
    private Activity mActivity;
    private EditText txtPhone,txtPassword;
    private Button btnLogin, btnRegister;
    private ModelUser userLogin;
    private DatabaseHandler db;
    private CheckBox showPass;
    int idShowPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;
        db = new DatabaseHandler(mActivity);

        btnForget = (TextView) findViewById(R.id.forgetPassword);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        showPass = (CheckBox) findViewById(R.id.showPassword);

        idShowPass = showPass.getInputType();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityRegister.class);
                startActivity(i);
                finish();
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityForgetPassword_1.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = txtPhone.getText().toString();
                String password = txtPassword.getText().toString();

                if (phone == null || password == null || phone.trim().isEmpty() || password.trim().isEmpty()) {
                    DialogManager.showDialog(mActivity, "Mohon Maaf", "Isi Nomor Ponsel dan Password Anda!");
                } else {
                    hideKeyboard();
                    String num=phone.substring(0,1);
                    Log.d("phone num",num);
                    Log.d("phone", phone);
                    if (num.contains("0")) {
                        Log.d("phone 1", phone);
                        /*
                        phoneNumber = phoneNumber.substring(1);

                        */
                        DialogManager.showDialog(mActivity, "Informasi", "Masukkan nomor ponsel seperti berikut : 085959084701");

                    }
                    else {
                        if (!NetworkManager.getInstance(mActivity).isConnectedInternet()) {
                            DialogManager.showDialog(mActivity, "Peringatan", "Tidak ada koneksi internet!");
                        } else {
                            phone = "08"+phone;
                            new DoLogin(mActivity).execute(
                                    phone,
                                    password
                            );
                        }

                    }

                }
            }
        });

        txtPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    txtPhone.setHint("xxxxxx");
                } else {
                    txtPhone.setHint("");
                }
            }
        });

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else  {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        ApplicationData.temp_hp = "";
        ApplicationData.temp_password = "";
        ApplicationData.temp_nama = "";
        ApplicationData.temp_token = "";
        ApplicationData.temp_user = new ModelUser();



    }

    public void hideKeyboard(){
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class DoLogin extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public DoLogin(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Signing in. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String phone = params[0];
                String password = params[1];
                JSONControl jsControl = new JSONControl();
                Log.d("json login", phone+" - "+password);
                JSONObject response = jsControl.postLogin(phone, password);
                Log.d("json response", response.toString());
                try {
                    String token = response.getString("token");
                    Log.d("json response id",token);
                    JSONObject responseUser = response.getJSONObject("user");
                    String _id = responseUser.getString("_id");
                    String name = responseUser.getString("name");
                    String phoneNumber = responseUser.getString("phoneNumber");

                    Log.d("json response id",_id.toString());
                    if(_id!=null){
                        userLogin = new ModelUser();
                        userLogin.setPonsel(phone);
                        userLogin.setNama(name);
                        String verify = responseUser.getString("verified");
                        JSONObject objRefreshToken = jsControl.postRefreshToken(token);
                        String token_refresh = objRefreshToken.getString("token");
                        //verify = "true";
                        if(verify.equalsIgnoreCase("true")){
                            db.insertuser(userLogin);
                            ApplicationData.login_id = _id.toString();
                            ApplicationData.name = name;
                            ApplicationData.phoneNumber = phone;
                            ApplicationManager.getInstance(activity).setUserToken(token_refresh);
                            ApplicationManager.getInstance(activity).setUser(userLogin);
                            Log.d("json response id", "OK " + token_refresh);
                            return "OK";
                        }
                        else {
                            ApplicationData.temp_user = userLogin;
                            ApplicationData.temp_token = token_refresh;
                            return "VERIFY";
                        }

                    }
                    else {
                        Log.d("json response id", "FAIL");
                        return "FAIL";
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("json response id 0", "FAIL");
            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            switch (result) {
                case "FAIL":
                        try {
                            final Context ctx = activity;
                            new MaterialDialog.Builder(ctx)
                                    .title("Mohon Maaf")
                                    .content("Nomor ponsel Anda belum terdaftar")
                                    .positiveText("OK")
                                    .callback(new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            ApplicationData.phoneNumberLogin = txtPhone.getText().toString();
                                            Intent i = new Intent(getBaseContext(), ActivityRegister.class);
                                            startActivity(i);
                                            finish();

                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                            //isClicked = false;
                        } catch (Exception e) {

                        }

                    break;
                case "OK":
                    Intent i = new Intent(getBaseContext(), Main.class);
                    startActivity(i);
                    finish();
                    break;
                case "VERIFY":
                    ApplicationData.isVerify = 1;
                    Intent j = new Intent(getBaseContext(), ActivityVerifyHp.class);
                    startActivity(j);
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

