package twiscode.masakuuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityFAQ extends AppCompatActivity {

    private ImageView btnBack;
    private LinearLayout btnFaq1, btnFaq2, btnFaq3, btnFaq4,btnFaq5,
            btnFaq6, btnFaq7, btnFaq8, btnFaq9, btnFaq10, btnFaq11;
    private TextView txtFaq1, txtFaq2, txtFaq3, txtFaq4, txtFaq5,
            txtFaq6, txtFaq7, txtFaq8, txtFaq9, txtFaq10, txtFaq11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnFaq1 = (LinearLayout) findViewById(R.id.btnFaq1);
        btnFaq2 = (LinearLayout) findViewById(R.id.btnFaq2);
        btnFaq3 = (LinearLayout) findViewById(R.id.btnFaq3);
        btnFaq4 = (LinearLayout) findViewById(R.id.btnFaq4);
        btnFaq5 = (LinearLayout) findViewById(R.id.btnFaq5);
        btnFaq6 = (LinearLayout) findViewById(R.id.btnFaq6);
        btnFaq7 = (LinearLayout) findViewById(R.id.btnFaq7);
        btnFaq8 = (LinearLayout) findViewById(R.id.btnFaq8);
        btnFaq9 = (LinearLayout) findViewById(R.id.btnFaq9);
        btnFaq10 = (LinearLayout) findViewById(R.id.btnFaq10);
        btnFaq11 = (LinearLayout) findViewById(R.id.btnFaq11);

        txtFaq1 = (TextView) findViewById(R.id.txtFaq1);
        txtFaq2 = (TextView) findViewById(R.id.txtFaq2);
        txtFaq3 = (TextView) findViewById(R.id.txtFaq3);
        txtFaq4 = (TextView) findViewById(R.id.txtFaq4);
        txtFaq5 = (TextView) findViewById(R.id.txtFaq5);
        txtFaq6 = (TextView) findViewById(R.id.txtFaq6);
        txtFaq7 = (TextView) findViewById(R.id.txtFaq7);
        txtFaq8 = (TextView) findViewById(R.id.txtFaq8);
        txtFaq9 = (TextView) findViewById(R.id.txtFaq9);
        txtFaq10 = (TextView) findViewById(R.id.txtFaq10);
        txtFaq11 = (TextView) findViewById(R.id.txtFaq11);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnFaq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq1.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq1);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq2.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq2);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq3.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq3);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq4.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq4);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq5.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq5);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq6.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq6);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq7.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq7);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq8.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq8);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq9.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq9);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq10.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq10);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });

        btnFaq11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.question = txtFaq11.getText().toString();
                ApplicationData.answer = getResources().getString(R.string.AnsFaq11);
                Intent i = new Intent(getBaseContext(), ActivityFAQDetail.class);
                startActivity(i);
            }
        });





    }

}
