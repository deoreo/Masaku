package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelNavDrawer;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class ActivitySplash extends AppCompatActivity {
    private DatabaseHandler db;
    private ProgressBar mProgressBar;
    private int mWaited = 0;
    private Activity mActivity;
    ApplicationManager appManager;
    Context ctx;
    LocationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Gotham.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        mActivity = this;
        ctx = this;
        db = new DatabaseHandler(this);
        appManager = new ApplicationManager(ctx);
        mProgressBar = (ProgressBar) findViewById(R.id.splash_progress);
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        final Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {

                    for (int i = 0; i <= 1000; i++) {
                        sleep(2);
                        mProgressBar.setProgress(mWaited / 10);
                        mWaited += 1;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    int countUser = db.getuserCount();
                    if (countUser > 0) {
                        /*
                        Intent i = new Intent(getBaseContext(), ActivityHome.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                        */
                        new RefreshToken(mActivity).execute();
                    } else {
                        Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        splashThread.start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private class RefreshToken extends AsyncTask<String, Void, String> {
        String message="";
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        String error = "";

        public RefreshToken(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Signing Up. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            */
        }

        @Override
        protected String doInBackground(String... params) {
            try {


                JSONControl jsControl = new JSONControl();
                JSONObject responseRegister = jsControl.postRefreshToken(appManager.getUserToken());
                Log.d("json refreshtoken", responseRegister.toString());
                String token = responseRegister.getString("token");
                appManager.setUserToken(token);
                return "OK";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            switch (result) {
                case "FAIL":
                    Intent i = new Intent(getBaseContext(), ActivityLogin.class);
                    startActivity(i);
                    finish();
                    break;
                case "OK":
                    try {
                        ModelUser user = appManager.getUser();
                        ApplicationData.login_id = user.getId();
                        ApplicationData.name = user.getNama();
                        ApplicationData.phoneNumber = user.getPonsel();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    Intent j = new Intent(getBaseContext(), Main.class);
                    startActivity(j);
                    finish();
                    break;
            }
        }


    }
}
