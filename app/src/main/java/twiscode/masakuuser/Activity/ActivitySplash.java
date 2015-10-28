package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;

import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.R;


public class ActivitySplash extends AppCompatActivity {
    private DatabaseHandler db;
    private ProgressBar mProgressBar;
    private int mWaited = 0;
    private Activity mActivity;
    private BroadcastReceiver lastOrder;
    Context ctx;
    LocationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_splash);
        mActivity = this;
        ctx = this;
        db = new DatabaseHandler(this);
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
                        Intent i = new Intent(getBaseContext(), ActivityHome.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
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
}
