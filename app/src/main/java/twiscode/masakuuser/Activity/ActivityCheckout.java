package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.angmarch.views.NiceSpinner;
import org.json.JSONObject;

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
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckout extends AppCompatActivity {

    private EditText txtKode;
    private ImageView btnBack;
    private ListView mListView;
    private TextView txtSubtotal,txtTip,txtDelivery,txtTotal,txtDiskon,noData;
    AdapterCheckout mAdapter;
    private List<ModelCart> LIST_MENU = new ArrayList<>();
    SegmentedGroup segmented;
    NiceSpinner paySpiner;
    int delivery = 10000;
    int subtotal = 0;
    int tip = 0;
    int total = 0;
    int diskon = 0;
    private DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.listCheckout);
        noData = (TextView)findViewById(R.id.noData);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        DummyData();

        View header = getLayoutInflater().inflate(R.layout.layout_header_checkout, null);
        txtKode = (EditText) header.findViewById(R.id.kodePromoCheckout);
        mListView.addHeaderView(header);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_checkout, null);
        txtSubtotal = (TextView)footer.findViewById(R.id.subtotalCheckout);
        txtDelivery = (TextView)footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView)footer.findViewById(R.id.tipCheckout);
        txtDiskon = (TextView)footer.findViewById(R.id.diskonCheckout);
        txtTotal = (TextView)footer.findViewById(R.id.totalCheckout);
        txtDiskon.setText("Rp. "+decimalFormat.format(diskon));
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
                switch (checkedId) {
                    case R.id.button21:
                        tip = 0;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button22:
                        tip = (int) Math.round(subtotal * 0.05);
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button23:
                        tip = (int) Math.round(subtotal * 0.1);
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button24:
                        tip = (int) Math.round(subtotal * 0.15);
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button25:
                        tip = (int) Math.round(subtotal * 0.2);
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    default:
                        // Nothing to do
                }

            }
        });

        txtKode.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Log.d("kupon",txtKode.getText().toString());
                            new CalculatePrice(ActivityCheckout.this).execute(txtKode.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });


        if(ApplicationData.cart.size() > 0){
            mListView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
        else {
            mListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }


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
        total = subtotal+tip+delivery-diskon;
    }

    private class CalculatePrice extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public CalculatePrice(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String kode = params[0];
                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                JSONObject response = jsControl.calculatePrice(kode, cart);
                Log.d("json response", response.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }

            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            switch (result) {
                case "FAIL":

                    break;
                case "OK":


                    break;
            }
            progressDialog.dismiss();

        }


    }



}
