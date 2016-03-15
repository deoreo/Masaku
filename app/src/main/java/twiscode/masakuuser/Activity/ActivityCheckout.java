package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import twiscode.masakuuser.Fragment.FragmentCheckoutPO;
import twiscode.masakuuser.Fragment.FragmentCheckoutDelivery;
import twiscode.masakuuser.Fragment.FragmentCheckoutPayment;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckout extends AppCompatActivity {

    private Activity act;
    private ApplicationManager applicationManager;
    private ImageView btnBack;
    private ProgressBar progress;
    private TextView txtTotal;
    private FrameLayout viewPager;
    private PagerSlidingTabStrip tabsStrip;
    private View lineReview, lineDelivery, linePayment;
    private RelativeLayout layoutSubtotal;
    private BroadcastReceiver updateTotal, gotoDelivery, gotoPayment;
    private int DISPLAY = 0;
    private final int REVIEW = 0, DELIVERY = 1, PAYMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        act = this;
        applicationManager = new ApplicationManager(act);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        viewPager = (FrameLayout) findViewById(R.id.pager);
        lineReview = (View) findViewById(R.id.lineReview);
        lineDelivery = (View) findViewById(R.id.lineDelivery);
        linePayment = (View) findViewById(R.id.linePayment);
        layoutSubtotal = (RelativeLayout) findViewById(R.id.layoutSubtotal);
        if (ApplicationData.isFromCheckoutDelivery) {
            try {
                displayView(DELIVERY);
            } catch (Exception e) {

            }
        } else {
            try {
                displayView(REVIEW);
            } catch (Exception e) {

            }
        }
        //viewPager.setAdapter(new AdapterPagerCheckout(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        //tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        //tabsStrip.setViewPager(viewPager);
        //tabsStrip.setTextColor(Color.WHITE);
/*
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {

            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
*/
        updateTotal = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateTotal");
                String message = intent.getStringExtra("message");
                txtTotal.setText(message);
            }
        };

        gotoDelivery = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateTotal");
                String message = intent.getStringExtra("message");
                try {
                    displayView(DELIVERY);
                } catch (Exception e) {

                }
            }
        };

        gotoPayment = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateTotal");
                String message = intent.getStringExtra("message");
                try {
                    displayView(PAYMENT);
                } catch (Exception e) {

                }
            }
        };

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getBaseContext(), Main.class);
                startActivity(j);
                finish();
            }
        });
    }

    private void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case REVIEW:
                fragment = new FragmentCheckoutPO();
                DISPLAY = REVIEW;
                lineReview.setBackgroundColor(getResources().getColor(R.color.colorRed));
                lineDelivery.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                linePayment.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                layoutSubtotal.setVisibility(VISIBLE);
                break;
            case DELIVERY:
                fragment = new FragmentCheckoutDelivery();
                ApplicationData.isFromCheckoutDelivery = false;
                DISPLAY = DELIVERY;
                lineReview.setBackgroundColor(getResources().getColor(R.color.colorRed));
                lineDelivery.setBackgroundColor(getResources().getColor(R.color.colorRed));
                linePayment.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                layoutSubtotal.setVisibility(GONE);
                break;
            case PAYMENT:
                fragment = new FragmentCheckoutPayment();
                DISPLAY = PAYMENT;
                lineReview.setBackgroundColor(getResources().getColor(R.color.colorRed));
                lineDelivery.setBackgroundColor(getResources().getColor(R.color.colorRed));
                linePayment.setBackgroundColor(getResources().getColor(R.color.colorRed));
                layoutSubtotal.setVisibility(GONE);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.pager, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

    }

    @Override
    public void onBackPressed() {
        if (DISPLAY == REVIEW) {
            Intent j = new Intent(act, Main.class);
            startActivity(j);
            finish();
        }
        else if(DISPLAY == DELIVERY)
        {
            displayView(REVIEW);
        }
        else if(DISPLAY == PAYMENT)
        {
            displayView(DELIVERY);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(act).registerReceiver(updateTotal,
                new IntentFilter("updateTotal"));

        LocalBroadcastManager.getInstance(act).registerReceiver(gotoDelivery,
                new IntentFilter("gotoDelivery"));

        LocalBroadcastManager.getInstance(act).registerReceiver(gotoPayment,
                new IntentFilter("gotoPayment"));


    }


}
