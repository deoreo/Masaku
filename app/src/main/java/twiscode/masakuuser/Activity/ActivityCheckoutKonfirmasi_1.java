package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import twiscode.masakuuser.Adapter.AdapterPagerCheckout;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckoutKonfirmasi_1 extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private ImageView btnBack;
    private TextView txtWaktu, txtID, txtSubtotal, txtConvience, txtTotal;
    private Button btnBayar;
    private ProgressBar progress;
    private DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_konfirmasi);
        act = this;
        applicationManager = new ApplicationManager(act);
        progress = (ProgressBar) findViewById(R.id.progress);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtWaktu = (TextView) findViewById(R.id.checkoutWaktu);
        txtID = (TextView) findViewById(R.id.checkoutID);
        txtSubtotal = (TextView) findViewById(R.id.checkoutSubtotal);
        txtConvience = (TextView) findViewById(R.id.checkoutConvience);
        txtTotal = (TextView) findViewById(R.id.checkoutTotal);
        btnBayar = (Button) findViewById(R.id.btnKonfirmasi);
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

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);


    }




}
