package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;

import twiscode.masakuuser.Adapter.AdapterPagerCheckout;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityCheckoutKonfirmasi_1 extends AppCompatActivity {

    Activity act;
    ApplicationManager applicationManager;
    private ImageView btnBack;
    private TextView txtWaktu, txtID, txtSubtotal, txtConvience, txtTotal, txtDelivery, txtTip;
    private Button btnBayar;
    private ProgressBar progress;
    private DecimalFormat decimalFormat;

    CountDownTimer countDownTimer;


    // Change by Lucifer
    long seconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_konfirmasi);
        act = this;
        applicationManager = new ApplicationManager(act);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtWaktu = (TextView) findViewById(R.id.checkoutWaktu);
        txtID = (TextView) findViewById(R.id.checkoutID);
        txtSubtotal = (TextView) findViewById(R.id.checkoutSubtotal);
        txtDelivery = (TextView) findViewById(R.id.checkoutDelivery);
        txtConvience = (TextView) findViewById(R.id.checkoutConvience);
        txtTotal = (TextView) findViewById(R.id.checkoutTotal);
        txtTip = (TextView) findViewById(R.id.checkoutTip);
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
                Intent i = new Intent(getBaseContext(), ActivityCheckoutKonfirmasi_2.class);
                startActivity(i);
                finish();
            }
        });

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        //int tip = Math.round(Integer.parseInt(ApplicationData.detailTransaksi.getSubtotal())*Integer.parseInt(ApplicationData.detailTransaksi.getTip())/100);
        int tip = Integer.parseInt(ApplicationData.detailTransaksi.getTip());

        txtID.setText(ApplicationData.detailTransaksi.getDetailID());
        txtDelivery.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getDelivery())));
        txtSubtotal.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getSubtotal())));
        txtTotal.setText("Rp. "+decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getTotal())));
        txtConvience.setText("Rp. " + decimalFormat.format(Integer.parseInt(ApplicationData.detailTransaksi.getConvience())));
        txtTip.setText("Rp. " + decimalFormat.format(tip));


        startTimer();




    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(Integer.parseInt(ApplicationData.detailTransaksi.getWaktu())*60*1000, 1000) {
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






}
