package twiscode.masakuuser.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.android.segmented.SegmentedGroup;
import twiscode.masakuuser.Adapter.AdapterCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckout extends AppCompatActivity {
    private ImageView btnBack;
    private ListView mListView;
    private TextView txtSubtotal,txtTip,txtDelivery,txtTotal;
    AdapterCheckout mAdapter;
    private List<ModelCart> LIST_MENU = new ArrayList<>();
    SegmentedGroup segmented;
    NiceSpinner paySpiner;
    int delivery = 10000;
    int subtotal = 0;
    int tip = 0;
    int total = 0;
    private DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.listCheckout);

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        DummyData();

        View header = getLayoutInflater().inflate(R.layout.layout_header_checkout, null);
        mListView.addHeaderView(header);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_checkout, null);
        txtSubtotal = (TextView)footer.findViewById(R.id.subtotalCheckout);
        txtDelivery = (TextView)footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView)footer.findViewById(R.id.tipCheckout);
        txtTotal = (TextView)footer.findViewById(R.id.totalCheckout);
        txtSubtotal.setText("Rp. "+decimalFormat.format(subtotal));
        txtDelivery.setText("Rp. "+decimalFormat.format(delivery));
        txtTip.setText("Rp. "+decimalFormat.format(tip));
        txtTotal.setText("Rp. "+decimalFormat.format(total));
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
        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("segmented id : ",""+checkedId);
                String id = Integer.toString(checkedId);
                if (id.contains("2131689663")) {
                    tip = 0;
                }
                else if (id.contains("2131689664")) {
                    tip = (int)Math.round(subtotal*0.05);
                }
                else if (id.contains("2131689665")) {
                    tip = (int)Math.round(subtotal*0.1);
                }
                else if (id.contains("2131689666")) {
                    tip = (int)Math.round(subtotal*0.15);
                }
                else if (id.contains("2131689667")) {
                    tip = (int)Math.round(subtotal*0.2);
                }
                total = subtotal+tip+delivery;
                txtTip.setText("Rp. "+decimalFormat.format(tip));
                txtTotal.setText("Rp. "+decimalFormat.format(total));
            }
        });





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void DummyData(){

        LIST_MENU = new ArrayList<ModelCart>();
        /*
        ModelCart modelDeliver0 = new ModelCart("1","Pecel Mak Yem", "5", "50.000");
        LIST_MENU.add(modelDeliver0);
        ModelCart modelDeliver1 = new ModelCart("2","Soto Spesial Bu Winda", "4", "60.000");
        LIST_MENU.add(modelDeliver1);
        */
        LIST_MENU = new ArrayList<ModelCart>(ApplicationData.cart.values());
        if(LIST_MENU.size()>0){
            for(int i=0;i<LIST_MENU.size();i++){
                subtotal = subtotal+(LIST_MENU.get(i).getJumlah()*LIST_MENU.get(i).getHarga());
            }
            tip = (int)Math.round(subtotal/10);
        }
        total = subtotal+tip+delivery;
    }

}
