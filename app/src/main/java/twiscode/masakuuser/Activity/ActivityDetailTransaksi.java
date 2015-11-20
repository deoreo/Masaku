package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.android.segmented.SegmentedGroup;
import twiscode.masakuuser.Adapter.AdapterCheckout;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityDetailTransaksi extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private TextView txtKode,txtNote;
    private ImageView btnBack;
    private ListView mListView;
    private TextView txtSubtotal,txtTip,txtDelivery,txtTotal,txtDiskon,noData,txtAlamat;
    AdapterCheckout mAdapter;
    private List<ModelCart> LIST_MENU = new ArrayList<>();
    int delivery = 0;
    int subtotal = 0;
    int tip = 0;
    int total = 0;
    int diskon = 0;
    private DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi);
        act = this;
        applicationManager = new ApplicationManager(act);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.listCheckout);
        noData = (TextView)findViewById(R.id.noData);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        DummyData();

        View header = getLayoutInflater().inflate(R.layout.layout_header_detail_transaksi, null);
        txtKode = (TextView) header.findViewById(R.id.kodePromoCheckout);
        txtNote = (TextView) header.findViewById(R.id.noteCheckout);
        txtAlamat = (TextView)header.findViewById(R.id.alamatCheckout);
        mListView.addHeaderView(header);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_detail_transaksi, null);
        txtSubtotal = (TextView)footer.findViewById(R.id.subtotalCheckout);
        txtDelivery = (TextView)footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView)footer.findViewById(R.id.tipCheckout);
        txtDiskon = (TextView)footer.findViewById(R.id.diskonCheckout);
        txtTotal = (TextView)footer.findViewById(R.id.totalCheckout);
/*

        txtDiskon.setText("Rp. "+decimalFormat.format(diskon));
        txtSubtotal.setText("Rp. "+decimalFormat.format(subtotal));
        txtDelivery.setText("Rp. "+decimalFormat.format(delivery));
        txtTip.setText("Rp. "+decimalFormat.format(tip));
        txtTotal.setText("Rp. "+decimalFormat.format(total));

        String alamat = ApplicationData.location;
        if(alamat!=""){
            txtAlamat.setText(alamat);
        }
        */

        mListView.addFooterView(footer);
        mAdapter = new AdapterCheckout(this, LIST_MENU);
        mListView.setAdapter(mAdapter);
        mListView.setScrollingCacheEnabled(false);




        if(ApplicationData.cart.size() > 0){
            mListView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
        else {
            mListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }


        new DetailTransaksi(ActivityDetailTransaksi.this).execute();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void DummyData(){

        LIST_MENU = new ArrayList<ModelCart>();
        if(LIST_MENU.size()>0){
            for(int i=0;i<LIST_MENU.size();i++){
                subtotal = subtotal+(LIST_MENU.get(i).getJumlah()*LIST_MENU.get(i).getHarga());
            }
            tip = (int)Math.round(subtotal/10);
        }
        total = subtotal+tip+delivery-diskon;
    }

    private class DetailTransaksi extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public DetailTransaksi(Activity activity) {
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


                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                JSONObject response = jsControl.detailTransaksi(ApplicationData.pesanan.getId(), applicationManager.getUserToken());
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




    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(act).sendBroadcast(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
