package twiscode.masakuuser.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import info.hoang8f.android.segmented.SegmentedGroup;
import twiscode.masakuuser.Adapter.AdapterCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckout extends AppCompatActivity {
    private ImageView btnBack;
    private ListView mListView;
    AdapterCheckout mAdapter;
    private List<ModelCart> LIST_MENU = new ArrayList<>();
    SegmentedGroup segmented;
    NiceSpinner paySpiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.listCheckout);

        DummyData();

        View header = getLayoutInflater().inflate(R.layout.layout_header_checkout, null);
        mListView.addHeaderView(header);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_checkout, null);
        paySpiner = (NiceSpinner) footer.findViewById(R.id.paySpinner);
        segmented = (SegmentedGroup) footer.findViewById(R.id.segmented);
        segmented.setTintColor(Color.parseColor("#D02D2E"));
        segmented.check(R.id.button23);
        List<String> dataPay = new LinkedList<>(Arrays.asList("Cash", "E-Cash Mandiri"));
        paySpiner.attachDataSource(dataPay);
        mListView.addFooterView(footer);
        mAdapter = new AdapterCheckout(this, LIST_MENU);
        mListView.setAdapter(mAdapter);
        mListView.setScrollingCacheEnabled(false);




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void DummyData(){

        LIST_MENU = new ArrayList<ModelCart>();
        ModelCart modelDeliver0 = new ModelCart("Pecel Mak Yem", "5", "50.000");
        LIST_MENU.add(modelDeliver0);
        ModelCart modelDeliver1 = new ModelCart("Soto Spesial Bu Winda", "4", "60.000");
        LIST_MENU.add(modelDeliver1);

    }

}
