package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckoutVerify extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private ImageView btnBack;
    private ProgressBar progress;

    Map<String, String> flurryParams = new HashMap<String,String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_verify);
        act = this;
        applicationManager = new ApplicationManager(act);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageView) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(act, Main.class);
                startActivity(j);
                finish();
            }
        });


    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("PREORDER_VERIFIED", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("PREORDER_VERIFIED");
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        Intent j = new Intent(act, Main.class);
        startActivity(j);
        finish();
    }



}
