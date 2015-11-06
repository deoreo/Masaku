package twiscode.masakuuser.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Adapter.AdapterPagerMenuDetail;
import twiscode.masakuuser.Adapter.AdapterVendorFeedback;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelVendorFeedback;
import twiscode.masakuuser.Model.ModelVendorRating;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMenuDetailNew extends ActionBarActivity {

    private ImageView btnBack,btnCart;
    public TextView nameMenu;
    public TextView timeMenu;
    public TextView priceMenu;
    public ImageView imgMenu;
    private ScrollView scroll;
    private ModelMenu modelMenu;
    private List<ModelVendorFeedback> LIST_FEEDBACK = new ArrayList<>();
    private ListView mListFeedback;
    private AdapterVendorFeedback adapterFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        DummyFeedback();
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnCart = (ImageView) findViewById(R.id.btnCart);
        nameMenu = (TextView) findViewById(R.id.nameMenu);
        timeMenu = (TextView) findViewById(R.id.timeMenu);
        priceMenu = (TextView) findViewById(R.id.priceMenu);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        mListFeedback = (ListView) findViewById(R.id.feedbackList);
        scroll = (ScrollView) findViewById(R.id.scrollMenuDetail);
        scroll.smoothScrollTo(0,0);
        modelMenu = ApplicationData.modelMenu;

        nameMenu.setText(modelMenu.getNama());
        timeMenu.setText(modelMenu.getTime());
        priceMenu.setText("Rp. " + modelMenu.getPrice());
        Picasso.with(this).load(modelMenu.getFoto()).error(R.drawable.icon).fit().into(imgMenu);

        adapterFeedback = new AdapterVendorFeedback(ActivityMenuDetailNew.this, LIST_FEEDBACK);
        mListFeedback.setAdapter(adapterFeedback);
        mListFeedback.setScrollingCacheEnabled(false);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ActivityCheckout.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), ActivityHome.class);
        startActivity(i);
        finish();

    }

    private void DummyFeedback(){
        LIST_FEEDBACK = new ArrayList<ModelVendorFeedback>();
        ModelVendorFeedback modelVendorMenu0 = new ModelVendorFeedback("0","Cinta L***", "5", "23-10-2015", "Masakannya enak");
        LIST_FEEDBACK.add(modelVendorMenu0);
        ModelVendorFeedback modelVendorMenu1 = new ModelVendorFeedback("1","Ji***", "4", "23-10-2015", "Enak tapi porsinya kurang banyak :D");
        LIST_FEEDBACK.add(modelVendorMenu1);


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
