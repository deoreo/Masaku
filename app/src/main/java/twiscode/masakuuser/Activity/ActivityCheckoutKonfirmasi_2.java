package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckoutKonfirmasi_2 extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private TextView txtWaktu,txtConvience,txtSubtotal, txtTip, txtDelivery, txtTotal, txtDiskon, noData, txtAlamat,txtNama,txtTelpon,txtNote;
    private ImageView btnBack;
    private Button btnBayar;
    private ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_po);
        act = this;
        applicationManager = new ApplicationManager(act);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        View header = getLayoutInflater().inflate(R.layout.layout_header_checkout_konfirmasi, null);
        txtWaktu = (TextView) header.findViewById(R.id.checkoutWaktu);
        View footer = getLayoutInflater().inflate(R.layout.layout_footer_checkout_konfirmasi, null);
        txtSubtotal = (TextView) footer.findViewById(R.id.subtotalCheckout);
        txtConvience = (TextView) footer.findViewById(R.id.convienceCheckout);
        txtDelivery = (TextView) footer.findViewById(R.id.deliveryCheckout);
        txtTip = (TextView) footer.findViewById(R.id.tipCheckout);
        txtDiskon = (TextView) footer.findViewById(R.id.diskonCheckout);
        txtTotal = (TextView) footer.findViewById(R.id.totalCheckout);
        txtNote = (TextView) footer.findViewById(R.id.checkoutNote);
        txtAlamat = (TextView) footer.findViewById(R.id.checkoutAddress);
        txtNama = (TextView) footer.findViewById(R.id.checkoutName);
        txtTelpon = (TextView) footer.findViewById(R.id.checkoutPhone);
        btnBayar = (Button) footer.findViewById(R.id.btnKonfirmasi);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }




}
