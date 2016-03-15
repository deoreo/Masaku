package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;
import twiscode.masakuuser.Adapter.TutorialSliderAdapter;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ConfigManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.*;

public class ActivityTutorial extends FragmentActivity {

    private ImageView btnBack;
    private TextView btnOpen, txtSkip, txtNext;
    CircleIndicator defaultIndicator;
    ViewPager defaultViewpager;
    private WebView webview;
    RelativeLayout imageSlide;
    private LinearLayout layoutBottom;
    private final String TAG = "ActivityTutorial";
    TextView noPromo;
    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
        defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        txtNext = (TextView) findViewById(R.id.txtNext);
        txtSkip = (TextView) findViewById(R.id.txtSkip);

        TutorialSliderAdapter defaultPagerAdapter = new TutorialSliderAdapter(getSupportFragmentManager());
        defaultViewpager.setAdapter(defaultPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);

        defaultViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("masaku tutorial", ""+position);
                if (position == 2) {
                    layoutBottom.setVisibility(GONE);
                    defaultIndicator.setVisibility(GONE);
                } else {
                    layoutBottom.setVisibility(VISIBLE);
                    defaultIndicator.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("masaku tutorial", ""+position);
                if (position == 2) {
                    layoutBottom.setVisibility(GONE);
                    defaultIndicator.setVisibility(GONE);
                } else {
                    layoutBottom.setVisibility(VISIBLE);
                    defaultIndicator.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txtNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        txtSkip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }






    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
          // optional depending on your needs
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("TUTORIAL", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("TUTORIAL");
        FlurryAgent.onEndSession(this);
    }


}
