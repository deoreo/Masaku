package twiscode.masakuuser.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Adapter.AdapterPagerMain;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.SlidingTabLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityHome extends ActionBarActivity {


    private TextView countCart;
    private LinearLayout wrapCount;
    private ImageView btnCart;
    private BroadcastReceiver updateCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        wrapCount = (LinearLayout) findViewById(R.id.wrapCount);
        countCart = (TextView) findViewById(R.id.countCart);
        btnCart = (ImageView) findViewById(R.id.btnCart);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new AdapterPagerMain(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setTextColor(Color.WHITE);

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

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityHome.this, ActivityCheckout.class);
                startActivity(i);
            }
        });
        if(ApplicationData.cart.size() > 0){
            countCart.setText(""+ApplicationData.cart.size());
            wrapCount.setVisibility(View.VISIBLE);
        }
        else {
            wrapCount.setVisibility(View.GONE);
        }

        updateCart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateCart");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
                    if(list.size() > 0){
                        int jml = 0;
                        for(int i = 0;i<list.size();i++){
                            jml = jml + list.get(i).getJumlah();
                        }
                        countCart.setText(""+jml);
                        wrapCount.setVisibility(View.VISIBLE);
                    }
                    else {
                        wrapCount.setVisibility(View.GONE);
                    }

                }


            }
        };
        //DialogManager.showDialog(ActivityHome.this,"Info","Service ini hanya tersedia di Surabaya");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    private void ChangeActivity(){
        //Intent loginTabIntent = new Intent(this, NotificationActivity.class);
        //startActivity(loginTabIntent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(ActivityHome.this).registerReceiver(updateCart,
                new IntentFilter("updateCart"));


    }

}
