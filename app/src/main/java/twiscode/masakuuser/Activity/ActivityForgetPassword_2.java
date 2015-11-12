package twiscode.masakuuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import twiscode.masakuuser.R;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityForgetPassword_2 extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_2);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSend = (RelativeLayout) findViewById(R.id.wrapperSend);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityForgetPassword_1.class);
                startActivity(i);
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), ActivityForgetPassword_3.class);
                startActivity(i);
                finish();
            }
        });


    }

}
