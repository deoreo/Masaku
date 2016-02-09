package twiscode.masakuuser.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.maps.model.LatLng;

import org.angmarch.views.NiceSpinner;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import info.hoang8f.android.segmented.SegmentedGroup;
import twiscode.masakuuser.Activity.ActivityChangeLocation;
import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_1;
import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_2;
import twiscode.masakuuser.Activity.Main;
import twiscode.masakuuser.Adapter.AdapterCheckout;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Unity on 01/09/2015.
 */
public class FragmentCheckoutPO extends Fragment {
    private static final String ARG_COLOR = "color";
    Activity act;
    ApplicationManager applicationManager;
    private EditText txtNote,txtCodePromo;
    private ImageView btnBack;
    private ListView mListView;
    private LinearLayout laySpecial,layPembayaran,laypromoInfo;
    private TextView txtSubtotal, txtTip, txtDelivery, txtTotal, txtDiskon, noData, txtAlamat,txtSpecial,txtPromoInfo,txtKode;
    private Button btnKonfirmasi, btnApply;
    AdapterCheckout mAdapter;
    private List<ModelCart> LIST_MENU = new ArrayList<>();
    SegmentedGroup segmented;
    NiceSpinner paySpiner;
    int delivery = 0;
    int subtotal = 0;
    int tip = 0;
    int total = 0;
    int diskon = 0;
    String tips = "";
    private DecimalFormat decimalFormat;
    private ProgressBar progress;
    private BroadcastReceiver updateCart;
    String eventMessage;
    int pembayaran = 1;
    ModelUser user;
    //boolean isClicked = false;
    Map<String, String> flurryParams = new HashMap<String,String>();

    private Dialog dialogPromoCode;
    TextView error;

    public static FragmentCheckoutPO newInstance() {
        FragmentCheckoutPO fragment = new FragmentCheckoutPO();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCheckoutPO() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_checkout_po, container, false);
        act = getActivity();
        applicationManager = new ApplicationManager(act);
        user = applicationManager.getUser();
        progress = (ProgressBar) v.findViewById(R.id.progress);
        mListView = (ListView) v.findViewById(R.id.listCheckout);
        noData = (TextView) v.findViewById(R.id.noData);
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        DummyData();


        View header = getActivity().getLayoutInflater().inflate(R.layout.layout_header_checkout, null);
        txtSpecial = (TextView) header.findViewById(R.id.specialText);
        laySpecial = (LinearLayout) header.findViewById(R.id.specialLayout);
        laypromoInfo = (LinearLayout) header.findViewById(R.id.promoInfo);
        txtKode = (TextView) header.findViewById(R.id.kodePromoCheckout);
        btnApply = (Button) header.findViewById(R.id.btnApply);
        txtNote = (EditText) header.findViewById(R.id.noteCheckout);
        txtAlamat = (TextView) header.findViewById(R.id.alamatCheckout);
        txtPromoInfo = (TextView) header.findViewById(R.id.promoInfoText);
        mListView.addHeaderView(header);
        View footer = getActivity().getLayoutInflater().inflate(R.layout.layout_footer_checkout_po, null);
        txtSubtotal = (TextView) footer.findViewById(R.id.subtotalCheckout);
        txtDelivery = (TextView) footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView) footer.findViewById(R.id.tipCheckout);
        txtDiskon = (TextView) footer.findViewById(R.id.diskonCheckout);
        txtTotal = (TextView) footer.findViewById(R.id.totalCheckout);
        btnKonfirmasi = (Button) footer.findViewById(R.id.btnKonfirmasi);
        layPembayaran = (LinearLayout) footer.findViewById(R.id.layPembayaran);
        txtDiskon.setText("Rp. " + decimalFormat.format(diskon));
        txtSubtotal.setText("Rp. " + decimalFormat.format(subtotal));
        txtDelivery.setText("Rp. " + decimalFormat.format(delivery));
        txtTip.setText("Rp. " + decimalFormat.format(tip));
        txtTotal.setText("Rp. " + decimalFormat.format(total));

        tips = "10";

        String alamat = applicationManager.getAlamat();
        if (alamat != "") {
            txtAlamat.setText(alamat);
        }
        paySpiner = (NiceSpinner) footer.findViewById(R.id.paySpinner);
        List<String> dataPay = new LinkedList<>(Arrays.asList("Transfer","COD"));
        paySpiner.attachDataSource(dataPay);
        segmented = (SegmentedGroup) footer.findViewById(R.id.segmented);
        segmented.setTintColor(Color.parseColor("#D02D2E"));
        segmented.check(R.id.button23);
        mListView.addFooterView(footer);
        mAdapter = new AdapterCheckout(getActivity(), LIST_MENU);
        mListView.setAdapter(mAdapter);
        mListView.setScrollingCacheEnabled(false);
        paySpiner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pembayaran = position + 1;
                Log.d("position", "" + pembayaran);
            }
        });
        segmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button21:
                        tips = "0";
                        tip = 0;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button22:
                        tips = "5";
                        tip = (int) Math.floor(subtotal * 0.05 / 100) * 100;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button23:
                        tips = "10";
                        tip = (int) Math.floor(subtotal * 0.1 / 100) * 100;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button24:
                        tips = "15";
                        tip = (int) Math.floor(subtotal * 0.15 / 100) * 100;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    case R.id.button25:
                        tips = "20";
                        tip = (int) Math.floor(subtotal * 0.2 / 100) * 100;
                        total = subtotal + tip + delivery - diskon;
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        return;
                    default:
                        // Nothing to do
                }

            }
        });

        txtAlamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.isFromMenu = false;
                Intent i = new Intent(getActivity(), ActivityChangeLocation.class);
                startActivity(i);
                getActivity().finish();
            }
        });

/*
        txtKode.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Log.d("kupon", txtKode.getText().toString());
                            new CalculatePrice(getActivity()).execute(txtKode.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
*/
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("kupon", txtKode.getText().toString());
                new CalculatePrice(getActivity()).execute(txtKode.getText().toString());
            }
        });

        txtKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("kupon", txtKode.getText().toString());
                //new CalculatePrice(getActivity()).execute(txtKode.getText().toString());
                dialogPromoCode.show();
                error.setText("");
            }
        });

        /*
        if(ApplicationData.cart.size() > 0){
            mListView.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }
        else {
            mListView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        */


        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (isClicked == false) {
                   // isClicked = true;
                    if (NetworkManager.getInstance(act).isConnectedInternet()) {
                        if (txtAlamat.getText().toString().isEmpty()) {
                            DialogManager.showDialog(act, "Mohon Maaf", "Isi alamat anda");
                       ////     isClicked = false;
                        } else {
                            try {
                                final Context ctx = getActivity();
                                new MaterialDialog.Builder(ctx)
                                        .title("Anda yakin untuk order?")
                                        .positiveText("OK")
                                        .callback(new MaterialDialog.ButtonCallback() {
                                            @Override
                                            public void onPositive(MaterialDialog dialog) {
                                                if(pembayaran==1){
                                                    new CheckOut(act).execute(
                                                            txtKode.getText().toString(),
                                                            txtAlamat.getText().toString(),
                                                            txtNote.getText().toString()
                                                    );
                                                    dialog.dismiss();
                                                }
                                                else {
                                                    new CheckOutCOD(act).execute(
                                                            txtKode.getText().toString(),
                                                            txtAlamat.getText().toString(),
                                                            txtNote.getText().toString()
                                                    );
                                                    dialog.dismiss();
                                                }

                                            }
                                        })
                                        .negativeText("Tidak")
                                        .cancelable(false)
                                        .typeface("GothamRnd-Medium.otf", "Gotham.ttf")
                                        .show();
                            } catch (Exception e) {

                            }
                        }

                    }else{
                       // isClicked = false;
                        DialogManager.showDialog(act,"Mohon Maaf", "Anda tidak terhubung dengan internet, Silahkan coba lagi!");
                    }
                }
            //}
        });

        updateCart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateCart");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    if(ApplicationData.cart.size() > 0){
                        LIST_MENU = new ArrayList<ModelCart>(ApplicationData.cart.values());
                        if (LIST_MENU.size() > 0) {
                            subtotal = 0;
                            for (int i = 0; i < LIST_MENU.size(); i++) {
                                subtotal = subtotal + (LIST_MENU.get(i).getJumlah() * LIST_MENU.get(i).getHarga());
                            }
                            tip = (int) Math.floor(subtotal / 10 / 100)*100;
                        }
                        total = subtotal + tip + delivery - diskon;
                        txtDiskon.setText("Rp. " + decimalFormat.format(diskon));
                        txtSubtotal.setText("Rp. " + decimalFormat.format(subtotal));
                        txtDelivery.setText("Rp. " + decimalFormat.format(delivery));
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                    }




                }
                else if(message.equals("delete")){
                    if(ApplicationData.cart.size() > 0){
                        LIST_MENU = new ArrayList<ModelCart>(ApplicationData.cart.values());
                        boolean isEvent = false;
                        if (LIST_MENU.size() > 0) {
                            subtotal = 0;

                            for (int i = 0; i < LIST_MENU.size(); i++) {
                                subtotal = subtotal + (LIST_MENU.get(i).getJumlah() * LIST_MENU.get(i).getHarga());
                                if(LIST_MENU.get(i).getIsEvent()=="true"){
                                    isEvent = true;
                                }
                            }
                            tip = (int) Math.floor(subtotal / 10 / 100)*100;

                        }
                        total = subtotal + tip + delivery - diskon;
                        txtDiskon.setText("Rp. " + decimalFormat.format(diskon));
                        txtSubtotal.setText("Rp. " + decimalFormat.format(subtotal));
                        txtDelivery.setText("Rp. " + decimalFormat.format(delivery));
                        txtTip.setText("Rp. " + decimalFormat.format(tip));
                        txtTotal.setText("Rp. " + decimalFormat.format(total));
                        mAdapter = new AdapterCheckout(act, LIST_MENU);
                        mListView.setAdapter(mAdapter);
                        if(isEvent){
                            laySpecial.setVisibility(View.VISIBLE);
                        }
                        else {
                            laySpecial.setVisibility(View.GONE);
                        }
                    }
                    else{
                        //act.finish();
                        if(ApplicationData.cart.size() > 0){
                            LIST_MENU = new ArrayList<ModelCart>();
                            ArrayList<ModelCart> newlist = new ArrayList<ModelCart>(ApplicationData.cart.values());
                            boolean isEvent = false;
                            for(int i=0;i<newlist.size();i++){
                                ModelCart c = newlist.get(i);
                                if(c.getType()=="po"){
                                    LIST_MENU.add(c);
                                }
                                if(c.getIsEvent()=="true"){
                                    isEvent = true;
                                }
                            }
                            if(LIST_MENU.size() < 1){
                                mListView.setVisibility(View.GONE);
                                noData.setVisibility(View.VISIBLE);
                            }
                            if(isEvent){
                                laySpecial.setVisibility(View.VISIBLE);
                            }
                            else {
                                laySpecial.setVisibility(View.GONE);
                            }

                        }
                        else {
                            act.finish();
                        }
                    }
                }


            }
        };

        if(user.getTrusted().contains("false")){
            layPembayaran.setVisibility(View.GONE);
        }


        if(LIST_MENU.size() > 0){

                txtKode.setText(ApplicationData.promocode);

            new CalculatePrice(getActivity()).execute(txtKode.getText().toString());
        }
        else {

                noData.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);

        }

        InitDialogPromoCode();


        return v;
    }

    private void InitDialogPromoCode(){
        dialogPromoCode = new Dialog(getActivity());
        dialogPromoCode.setContentView(R.layout.popup_promo);
        dialogPromoCode.setTitle("Promo Code");

        // set the custom dialog components - text, image and button

        error = (TextView) dialogPromoCode.findViewById(R.id.errorPromoCode);
        txtCodePromo = (EditText) dialogPromoCode.findViewById(R.id.txtCodePromo);
        Button cancel = (Button) dialogPromoCode.findViewById(R.id.btnCancel);
        Button confirm = (Button) dialogPromoCode.findViewById(R.id.btnConfirm);

        error.setText("");
        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPromoCode.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.promocode = txtCodePromo.getText().toString();
                new CalculatePrice(getActivity()).execute(txtCodePromo.getText().toString());
            }
        });
    }

    private void DummyData() {

        LIST_MENU = new ArrayList<ModelCart>();
        ArrayList<ModelCart> newlist = new ArrayList<ModelCart>(ApplicationData.cart.values());
        for(int i=0;i<newlist.size();i++){
            ModelCart c = newlist.get(i);
            if(c.getType()=="po"){
                LIST_MENU.add(c);
            }
        }
        /*
        ModelCart modelDeliver0 = new ModelCart("1","Pecel Mak Yem", "5", "50.000");
        LIST_MENU.add(modelDeliver0);
        ModelCart modelDeliver1 = new ModelCart("2","Soto Spesial Bu Winda", "4", "60.000");
        LIST_MENU.add(modelDeliver1);
        */
        //LIST_MENU = new ArrayList<ModelCart>(ApplicationData.cart.values());
        if (LIST_MENU.size() > 0) {
            for (int i = 0; i < LIST_MENU.size(); i++) {
                subtotal = subtotal + (LIST_MENU.get(i).getJumlah() * LIST_MENU.get(i).getHarga());
            }
            tip = (int)Math.floor(subtotal / 10 /100)*100;
            //
        }

        total = subtotal + tip + delivery - diskon;
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
            /*
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

                String kode = params[0];
                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                JSONObject response = jsControl.calculatePrice(kode, applicationManager.getUserToken(), cart);
                Log.d("json response", response.toString());
                try {
                    JSONArray transaksi = response.getJSONArray("transactions");
                    if (transaksi.length() > 0) {
                        for (int i = 0; i < transaksi.length(); i++) {
                            String discountPrice = transaksi.getJSONObject(i).getString("discountPrice");
                            diskon = Integer.parseInt(discountPrice);
                            String shippingPrice = transaksi.getJSONObject(i).getString("shippingPrice");
                            eventMessage = "";
                            try{
                                eventMessage = transaksi.getJSONObject(i).getString("eventMessage");
                            }
                            catch (Exception x){
                                eventMessage = "";
                            }

                            delivery = Integer.parseInt(shippingPrice);
                        }
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
                    if (delivery == 0) {
                        delivery = ApplicationData.def_delivery;
                    }
                    diskon = 0;
                    total = subtotal + tip + delivery - diskon;
                    txtDiskon.setText("Rp. " + decimalFormat.format(diskon));
                    txtDelivery.setText("Rp. " + decimalFormat.format(delivery));
                    txtTotal.setText("Rp. " + decimalFormat.format(total));
                    try {
                        error.setText("Sorry, the promotion code entered is invalid");
                    }
                    catch (Exception c){
                        c.printStackTrace();
                    }
                    break;
                case "OK":
                    total = subtotal + tip + delivery - diskon;
                    txtDiskon.setText("Rp. " + decimalFormat.format(diskon));
                    txtDelivery.setText("Rp. " + decimalFormat.format(delivery));
                    txtTotal.setText("Rp. " + decimalFormat.format(total));
                    txtKode.setText(ApplicationData.promocode);

                    Log.d("diskon", "" + diskon);
                    if(eventMessage != ""){
                        laySpecial.setVisibility(View.VISIBLE);
                        txtSpecial.setText(eventMessage);
                    }
                    else {
                        laySpecial.setVisibility(View.GONE);
                    }
                    try{
                        if(diskon>0 || delivery<ApplicationData.def_delivery){
                            txtKode.setText(ApplicationData.promocode);
                            dialogPromoCode.dismiss();
                        }
                        else {
                            error.setText("Sorry, the promotion code entered is invalid");
                        }


                    }
                    catch (Exception c){
                        c.printStackTrace();
                    }
                    break;
            }
            //progressDialog.dismiss();
            if (ApplicationData.cart.size() > 0) {
                mListView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.GONE);
            } else {
                mListView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }

        }


    }


    private class CheckOut extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        private String msg;

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

                String kode = params[0];
                String address = params[1];
                String note = params[2];

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                LatLng posFrom = applicationManager.getGeocode();
                JSONObject response = jsControl.checkOut(kode, address, note, tips, posFrom, applicationManager.getUserToken(), cart);
                Log.d("json response checkout", response.toString());
                try {
                    JSONArray transaction = response.getJSONArray("transaction");


                    if (transaction.length() > 0) {
                        for (int t = 0; t < transaction.length(); t++) {
                            String _id = transaction.getJSONObject(t).getString("id");
                            String _status = transaction.getJSONObject(t).getString("status");
                            String _waktu = transaction.getJSONObject(t).getString("timeLapse");
                            String _uid = transaction.getJSONObject(t).getString("user");
                            String _alamat = transaction.getJSONObject(t).getString("address");
                            String _note = transaction.getJSONObject(t).getString("note");
                            String _subtotal = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("base");
                            String _delivery = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("shipping");
                            String _diskon = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("discount");
                            String _convenience = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("convenientFee");
                            String _total = transaction.getJSONObject(t).getString("price");
                            String _type = transaction.getJSONObject(t).getString("type");
                            String _nama = transaction.getJSONObject(t).getJSONObject("user").getString("name");
                            String _phone = transaction.getJSONObject(t).getJSONObject("user").getString("phoneNumber");
                            //String _convience = "0";
                            String _tip = "0";
                            try {
                                _tip = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("tip");
                            } catch (Exception e) {
                                _tip = "0";
                            }

                            String _detailID = transaction.getJSONObject(t).getString("prettyId");
                            JSONArray _order = transaction.getJSONObject(t).getJSONArray("orders");
                            List<ModelCart> _carts = new ArrayList<>();
                            if (_order.length() > 0) {
                                for (int i = 0; i < _order.length(); i++) {
                                    ModelCart c = new ModelCart();
                                    c.setId(_order.getJSONObject(i).getString("_id"));
                                    c.setNama(_order.getJSONObject(i).getJSONObject("menu").getString("name"));
                                    c.setHarga(Integer.parseInt(_order.getJSONObject(i).getJSONObject("menu").getString("price")));
                                    c.setJumlah(Integer.parseInt(_order.getJSONObject(i).getString("quantity")));
                                    c.setType(_type);
                                    _carts.add(c);
                                }
                            }
                            ApplicationData.detailTransaksi = new ModelDetailTransaksi(_id, _type, _uid, _nama, _alamat, _phone, _note, _subtotal, _convenience, _total, _waktu, _diskon, _tip, _delivery, _status, _detailID, _carts);
                            ApplicationData.idLastTransaction = _id;
                            return "OK";
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    msg = response.getString("message");

                }

            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }

            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progress.setVisibility(View.GONE);
            switch (result) {
                case "FAIL":
                    //isClicked = false;
                    DialogManager.showDialog(activity, "Informasi", msg);
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
                                            if (NetworkManager.getInstance(act).isConnectedInternet()) {
                                                ApplicationData.cart = new HashMap<String, ModelCart>();

                                                for(int i=0;i<LIST_MENU.size();i++){
                                                    if(LIST_MENU.get(i).getType()=="po"){
                                                        ApplicationData.cart.remove(LIST_MENU.get(i).getId());
                                                    }
                                                }
                                                Intent j = new Intent(act, ActivityCheckoutKonfirmasi_2.class);
                                                startActivity(j);
                                                ApplicationData.promocode = "";
                                                act.finish();
                                            } else {
                                                //isClicked = false;
                                                DialogManager.showDialog(act, "Mohon Maaf", "Tidak ada koneksi internet!");
                                            }
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .typeface("GothamRnd-Medium.otf", "Gotham.ttf")
                                    .show();
                            //isClicked = false;
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

    private void SendBroadcast(String typeBroadcast, String type) {
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(act).sendBroadcast(intent);
    }


    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(act).registerReceiver(updateCart,
                new IntentFilter("updateCart"));


    }

    private class CheckOutCOD extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        private String msg = "";

        public CheckOutCOD(Activity activity) {
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

                String kode = params[0];
                String address = params[1];
                String note = params[2];

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                LatLng posFrom = applicationManager.getGeocode();
                JSONObject response = jsControl.checkOutCOD(kode, address, note, tips, posFrom, applicationManager.getUserToken(), cart);
                Log.d("json response checkout", response.toString());
                try {
                    JSONArray transaction = response.getJSONArray("transaction");
                    if(transaction.length() > 0){
                        for(int t=0;t<transaction.length();t++){
                            String _id = transaction.getJSONObject(t).getString("id");
                            String _status = transaction.getJSONObject(t).getString("status");
                            String _waktu = transaction.getJSONObject(t).getString("timeLapse");
                            String _uid = transaction.getJSONObject(t).getString("user");
                            String _alamat = transaction.getJSONObject(t).getString("address");
                            String _note = transaction.getJSONObject(t).getString("note");
                            String _subtotal = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("base");
                            String _delivery = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("shipping");
                            String _diskon = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("discount");
                            String _convenience = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("convenientFee");
                            String _total = transaction.getJSONObject(t).getString("price");
                            String _type = transaction.getJSONObject(t).getString("type");
                            String _nama = transaction.getJSONObject(t).getJSONObject("user").getString("name");
                            String _phone = transaction.getJSONObject(t).getJSONObject("user").getString("phoneNumber");
                            //String _convience = "0";
                            String _tip = "0";
                            try{
                                _tip = transaction.getJSONObject(t).getJSONObject("detailedPrice").getString("tip");
                            }catch (Exception e){
                                _tip = "0";
                            }

                            String _detailID=transaction.getJSONObject(t).getString("prettyId");
                            JSONArray _order = transaction.getJSONObject(t).getJSONArray("orders");
                            List<ModelCart> _carts = new ArrayList<>();
                            if(_order.length() > 0){
                                for(int i=0;i<_order.length();i++){
                                    ModelCart c = new ModelCart();
                                    c.setId(_order.getJSONObject(i).getString("_id"));
                                    c.setNama(_order.getJSONObject(i).getJSONObject("menu").getString("name"));
                                    c.setHarga(Integer.parseInt(_order.getJSONObject(i).getJSONObject("menu").getString("price")));
                                    c.setJumlah(Integer.parseInt(_order.getJSONObject(i).getString("quantity")));
                                    c.setType(_type);
                                    _carts.add(c);
                                }
                            }
                            ApplicationData.detailTransaksi = new ModelDetailTransaksi(_id,_type,_uid,_nama,_alamat,_phone,_note,_subtotal,_convenience,_total,_waktu,_diskon,_tip,_delivery,_status,_detailID,_carts);
                            ApplicationData.idLastTransaction = _id;
                            return "OK";
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    msg = response.getString("message");
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
                    //isClicked = false;
                    DialogManager.showDialog(activity, "Mohon maaf", msg);
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
                                            if (NetworkManager.getInstance(act).isConnectedInternet()) {
                                                //ApplicationData.cart = new HashMap<String, ModelCart>();
                                                for(int i=0;i<LIST_MENU.size();i++){
                                                    if(LIST_MENU.get(i).getType()=="po"){
                                                        ApplicationData.cart.remove(LIST_MENU.get(i).getId());
                                                    }
                                                }
                                                Intent j = new Intent(act, Main.class);
                                                startActivity(j);
                                                ApplicationData.promocode = "";
                                                act.finish();
                                            } else {
                                                //isClicked = false;
                                                DialogManager.showDialog(act, "Mohon Maaf", "Tidak ada koneksi internet!");
                                            }
                                            dialog.dismiss();
                                        }
                                    })
                                    .cancelable(false)
                                    .typeface("GothamRnd-Medium.otf", "Gotham.ttf")
                                    .show();
                            //isClicked = false;
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
        FlurryAgent.onStartSession(getActivity(), ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("CHECKOUT_PREORDER", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("CHECKOUT_PREORDER");
        FlurryAgent.onEndSession(getActivity());
    }


}
