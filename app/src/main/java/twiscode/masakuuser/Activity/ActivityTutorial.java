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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityTutorial extends FragmentActivity {

    private ImageView btnBack;
    private TextView btnOpen;
    CircleIndicator defaultIndicator;
    ViewPager defaultViewpager;
    private WebView webview;
    RelativeLayout imageSlide;
    private final String TAG = "ActivityTutorial";
    TextView noPromo;
    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        defaultViewpager = (ViewPager) findViewById(R.id.viewpager_default);
        defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_default);

        TutorialSliderAdapter defaultPagerAdapter = new TutorialSliderAdapter(getSupportFragmentManager());
        defaultViewpager.setAdapter(defaultPagerAdapter);
        defaultIndicator.setViewPager(defaultViewpager);

        defaultViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==3){
                    defaultIndicator.setVisibility(View.GONE);
                }
                else {
                    defaultIndicator.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });






    }






    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();  // optional depending on your needs
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("TUTORIAL", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("TUTORIAL");
    }


}
