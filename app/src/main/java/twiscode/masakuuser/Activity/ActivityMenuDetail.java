package twiscode.masakuuser.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import twiscode.masakuuser.Adapter.AdapterPagerMenuDetail;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelVendorMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.SlidingTabLayout;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMenuDetail extends ActionBarActivity {

    private ViewPager viewPager;
    private ImageView btnBack;
    private TextView namaVendor, jumlahPenjualan, minOrder, txtRate, labelNamaVendor;
    private RatingBar ratingBar;
    private RoundedImageView profile_image;
    private ModelMenu modelMenu;
    private ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);

        viewPager = (ViewPager) findViewById(R.id.pager);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        namaVendor = (TextView) findViewById(R.id.namaVendor);
        jumlahPenjualan = (TextView) findViewById(R.id.jumlahPenjualan);
        minOrder = (TextView) findViewById(R.id.minOrder);
        txtRate = (TextView) findViewById(R.id.txtRate);
        ratingBar = (RatingBar) findViewById(R.id.rateVendor);
        labelNamaVendor = (TextView) findViewById(R.id.label);
        profile_image = (RoundedImageView) findViewById(R.id.profile_image);
        scroll = (ScrollView) findViewById(R.id.scroll);
        modelMenu = ApplicationData.modelMenu;

        labelNamaVendor.setText(modelMenu.getNama());
        namaVendor.setText(modelMenu.getNama());
        jumlahPenjualan.setText(modelMenu.getJumlahorder() + " order perulan");
        minOrder.setText("Min order Rp."+modelMenu.getMinOrder());
        txtRate.setText(modelMenu.getRating()+"/5");
        ratingBar.setRating(Float.parseFloat(modelMenu.getRating()));
        Picasso.with(this).load(modelMenu.getFoto()).error(R.drawable.icon).fit().into(profile_image);

        viewPager.setAdapter(new AdapterPagerMenuDetail(getSupportFragmentManager()));
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setTextColor(Color.RED);
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                scroll.scrollTo(0, 0);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        scroll.scrollTo(0,0);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), ActivityHome.class);
        startActivity(i);
        finish();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
