package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flurry.android.FlurryAgent;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by User on 10/27/2015.
 */
public class ActivityRegister extends Activity {

    private Activity mActivity;
    private EditText txtPhone,txtName,txtEmail,txtPassword,txtConfirm;
    private Button btnRegister,btnLogin;
    private ModelUser userLogin;
    private ImageView btnBack;
    private CheckBox showPass,showConfirm;

    Map<String, String> flurryParams = new HashMap<String,String>();

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
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtConfirm = (EditText) findViewById(R.id.txtConfirmPassword);
        showPass = (CheckBox) findViewById(R.id.showPassword);
        showConfirm = (CheckBox) findViewById(R.id.showConfirmPassword);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        if(!ApplicationData.phoneNumberLogin.isEmpty()){
            txtPhone.setText(ApplicationData.phoneNumberLogin);
        }
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
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                String confirm = txtConfirm.getText().toString();
                if (phoneNumber == null || password == null || phoneNumber.trim().isEmpty() || password.trim().isEmpty()
                        || name == null || name.trim().isEmpty()
                        || email == null || email.trim().isEmpty()
                        ) {
                    DialogManager.showDialog(mActivity, "Mohon Maaf", "Masukkan semua data Anda!");
                } else if (!email.trim().contains("@") || !email.trim().contains(".")) {
                        DialogManager.showDialog(mActivity, "Mohon Maaf", "Format email Anda tidak sesuai!");
                } else {
                    String num = phoneNumber.substring(0, 1);
                    Log.d("phone num", num);
                    Log.d("phone", phoneNumber);
                    if (num.contains("0")) {
                        Log.d("phone 1", phoneNumber);
                        DialogManager.showDialog(mActivity,"Informasi","Masukkan nomor ponsel seperti berikut : 085959084701");

                    }
                    else {
                        //phoneNumber = "8"+phoneNumber;
                        /*
                        new DoRegister(mActivity).execute(
                                name,
                                phoneNumber,
                                password
                        );
                        */
                        ApplicationData.temp_hp = phoneNumber;
                        ApplicationData.email = email;
                        ApplicationData.temp_nama = name;
                        ApplicationData.temp_password = password;
                        Intent i = new Intent(getBaseContext(), ActivityRegisterNext.class);
                        startActivity(i);
                        finish();
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
                if (!isChecked) {
                    txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        showConfirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    txtConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else  {
                    txtConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });



        if(ApplicationData.temp_hp != ""){
            txtPhone.setText(ApplicationData.temp_hp);
        }
        if(ApplicationData.temp_nama != ""){
            txtName.setText(ApplicationData.temp_nama);
        }
        if(ApplicationData.email != ""){
            txtEmail.setText(ApplicationData.email);
        }
        if(ApplicationData.temp_password != ""){
            txtPassword.setText(ApplicationData.temp_password);
            txtConfirm.setText(ApplicationData.temp_password);
        }



    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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


}
