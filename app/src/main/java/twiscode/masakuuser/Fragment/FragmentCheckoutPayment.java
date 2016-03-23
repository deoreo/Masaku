package twiscode.masakuuser.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_2;
import twiscode.masakuuser.Activity.ActivityCheckoutVerify;
import twiscode.masakuuser.Activity.Main;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by Unity on 01/09/2015.
 */
public class FragmentCheckoutPayment extends Fragment {
    private Activity act;
    private ApplicationManager appManager;
    private Button btnConfirm,btnTransfer, btnCOD;
    private TextView txtSubtotal, txtTransactionCode, txtTotal;
    private LinearLayout layoutTransfer,layoutCOD;
    private int METHOD;
    private final int TRANSFER = 0,COD=1;

    public static FragmentCheckoutPayment newInstance() {
        FragmentCheckoutPayment fragment = new FragmentCheckoutPayment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCheckoutPayment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        appManager = new ApplicationManager(act);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(R.layout.fragment_checkout_payment, container, false);
            btnConfirm = (Button) v.findViewById(R.id.btnConfirm);

            btnCOD = (Button) v.findViewById(R.id.btnCOD);
            btnTransfer = (Button) v.findViewById(R.id.btnTransfer);
            layoutTransfer = (LinearLayout) v.findViewById(R.id.layoutTransfer);
            layoutCOD = (LinearLayout) v.findViewById(R.id.layoutCOD);
            txtSubtotal = (TextView) v.findViewById(R.id.txtSubtotal);
            txtTransactionCode = (TextView) v.findViewById(R.id.txtTransactionCode);
            txtTotal = (TextView) v.findViewById(R.id.txtTotal);
            txtSubtotal.setText(ApplicationData.total);
            txtTotal.setText(ApplicationData.total);
            paymentSelected(TRANSFER);

            btnCOD.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) return true;
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) return false;
                    paymentSelected(COD);
                    return true;
                }
            });

            btnTransfer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) return true;
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) return false;
                    paymentSelected(TRANSFER);
                    return true;
                }
            });

            btnCOD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paymentSelected(COD);
                }
            });

            btnTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    paymentSelected(TRANSFER);
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(METHOD == TRANSFER){
                        new ChekoutTransfer(act).execute(
                                ApplicationData.promocode,
                                ApplicationData.address,
                                ApplicationData.note
                        );
                    }else{
                        new ChekoutCOD(act).execute(
                                ApplicationData.promocode,
                                ApplicationData.address,
                                ApplicationData.note
                        );
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }

    private void paymentSelected(int payment){
        switch (payment){
            case TRANSFER:
                btnTransfer.setSelected(true);
                btnTransfer.setPressed(true);
                btnCOD.setSelected(false);
                btnCOD.setPressed(false);
                layoutTransfer.setVisibility(VISIBLE);
                layoutCOD.setVisibility(GONE);
                METHOD = TRANSFER;
                break;
            case COD:
                btnTransfer.setSelected(false);
                btnTransfer.setPressed(false);
                btnCOD.setSelected(true);
                btnCOD.setPressed(true);
                layoutTransfer.setVisibility(GONE);
                layoutCOD.setVisibility(VISIBLE);
                METHOD = COD;
                break;
            default:
                break;
        }

    }

    private class ChekoutTransfer extends AsyncTask<String, Void, String> {
        private Activity activity;
        private String msg;

        public ChekoutTransfer(Activity activity) {
            super();
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String kode = params[0];
                String address = params[1];
                String note = params[2];
                String tips = ApplicationData.tips;

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                LatLng posFrom = appManager.getGeocode();
                JSONObject response = jsControl.checkOut(kode, address, note, tips, posFrom, appManager.getUserToken(), cart);

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
                            JSONObject responseKonfirm = jsControl.ConfirmPO(ApplicationData.detailTransaksi.getId(), appManager.getUserToken());
                            try {
                                String status = responseKonfirm.getString("status");
                                if(status.equalsIgnoreCase("verifyingPayment")){

                                    return "OK";

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                                                //Intent j = new Intent(act, ActivityCheckoutKonfirmasi_2.class);
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

    private class ChekoutCOD extends AsyncTask<String, Void, String> {
        private Activity activity;
        private String msg;

        public ChekoutCOD(Activity activity) {
            super();
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String kode = params[0];
                String address = params[1];
                String note = params[2];
                String tips = ApplicationData.tips;

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                LatLng posFrom = appManager.getGeocode();
                JSONObject response = jsControl.checkOutCOD(kode, address, note, tips, posFrom, appManager.getUserToken(), cart);
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

    private class ConfirmPayment extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;

        public ConfirmPayment(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                JSONControl jsControl = new JSONControl();
                List<ModelCart> cart = new ArrayList<ModelCart>(ApplicationData.cart.values());
                JSONObject response = jsControl.ConfirmPO(ApplicationData.detailTransaksi.getId(), appManager.getUserToken());
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
                                            act.finish();
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

}
