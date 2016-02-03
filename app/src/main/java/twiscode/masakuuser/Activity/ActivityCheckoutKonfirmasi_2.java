package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import twiscode.masakuuser.Adapter.AdapterCheckout;
import twiscode.masakuuser.Adapter.AdapterCheckoutKonfirmasi;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckoutKonfirmasi_2 extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private TextView txtWaktu,txtConvience,txtSubtotal, txtTip, txtDelivery, txtTotal,txtTotal2, txtDiskon, noData, txtAlamat,txtNama,txtTelpon,txtNote;
    private ImageView btnBack;
    private Button btnBayar;
    private ProgressBar progress;
    private ListView mListView;

    CountDownTimer countDownTimer;
    private DecimalFormat decimalFormat;

    Map<String, String> flurryParams = new HashMap<String,String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_konfirmasi2);
        act = this;
        applicationManager = new ApplicationManager(act);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.listCheckout);
        View header = getLayoutInflater().inflate(R.layout.layout_header_checkout_konfirmasi, null);
        txtWaktu = (TextView) header.findViewById(R.id.checkoutWaktu);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_checkout_konfirmasi, null);
        txtSubtotal = (TextView) footer.findViewById(R.id.subtotalCheckout);
        txtConvience = (TextView) footer.findViewById(R.id.convienceCheckout);
        txtDelivery = (TextView) footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView) footer.findViewById(R.id.tipCheckout);
        txtDiskon = (TextView) footer.findViewById(R.id.diskonCheckout);
        txtTotal = (TextView) footer.findViewById(R.id.totalCheckout);
        txtTotal2 = (TextView) footer.findViewById(R.id.totalCheckout2);
        txtNote = (TextView) footer.findViewById(R.id.checkoutNote);
        txtAlamat = (TextView) footer.findViewById(R.id.checkoutAddress);
        txtNama = (TextView) footer.findViewById(R.id.checkoutName);
        txtTelpon = (TextView) footer.findViewById(R.id.checkoutPhone);
        btnBayar = (Button) footer.findViewById(R.id.btnKonfirmasi);
        mListView.addHeaderView(header);
        mListView.addFooterView(footer);
        List<ModelCart> LIST_MENU = ApplicationData.detailTransaksi.getCart();
        AdapterCheckoutKonfirmasi mAdapter = new AdapterCheckoutKonfirmasi(this, LIST_MENU);
        mListView.setAdapter(mAdapter);
        mListView.setScrollingCacheEnabled(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.isNullCart = true;
                finish();
            }
        });
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckOut(act).execute();
            }
        });

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        startTimer();

        //int tip = Math.round(Integer.parseInt(ApplicationData.detailTransaksi.getSubtotal())*Integer.parseInt(ApplicationData.detailTransaksi.getTip())/100);
        int tip = Integer.parseInt(ApplicationData.detailTransaksi.getTip());
        txtAlamat.setText(ApplicationData.detailTransaksi.getAlamat());
        txtNama.setText(ApplicationData.detailTransaksi.getNama());
        txtTelpon.setText(ApplicationData.detailTransaksi.getPhone());
        txtNote.setText(ApplicationData.detailTransaksi.getNote());
        txtSubtotal.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getSubtotal())));
        txtTotal.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getTotal())));
        txtTotal2.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getTotal())));
        txtConvience.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getConvience())));
        txtDelivery.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getDelivery())));
        txtTip.setText("Rp. " + decimalFormat.format(tip));




    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(ApplicationData.timer*1000, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                ApplicationData.timer = seconds;


                txtWaktu.setText(
                        String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                txtWaktu.setText("Time up!");
            }

        }.start();

    }

    private class CheckOut extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public CheckOut(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            /*
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            */
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                JSONObject response = jsControl.ConfirmPO(ApplicationData.detailTransaksi.getId(), applicationManager.getUserToken());
                Log.d("json response checkout", response.toString());
                try {
                    String status = response.getString("status");
                    if(status.equalsIgnoreCase("verifyingPayment")){

                            return "OK";

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progress.setVisibility(View.GONE);
            switch (result) {
                case "FAIL":
                    break;
                case "OK":
                    if (NetworkManager.getInstance(act).isConnectedInternet()) {
                        try {
                            final Context ctx = act;
                            new MaterialDialog.Builder(ctx)
                                    .title("Terima kasih")
                                    .content("Pesanan Anda akan segera kami proses")
                                    .positiveText("OK")
                                    .callback(new MaterialDialog.ButtonCallback() {
                                        @Override
                                        public void onPositive(MaterialDialog dialog) {
                                            Intent j = new Intent(act, ActivityCheckoutVerify.class);
                                            startActivity(j);
                                            finish();
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .typeface("GothamRnd-Medium.otf", "Gotham.ttf")
                                    .show();
                        } catch (Exception e) {

                        }
                    } else {
                        DialogManager.showDialog(act, "Mohon Maaf", "Tidak ada koneksi internet!");
                    }
                    break;
            }
            //progressDialog.dismiss();

        }


    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("PREORDER_PAYMENT", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("PREORDER_PAYMENT");
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}
