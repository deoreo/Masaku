package twiscode.masakuuser.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import twiscode.masakuuser.R;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityContactUs extends AppCompatActivity {

    private ImageView btnBack;
    private RelativeLayout btnCallUs;
    private Button btnKirim;
    private EditText txtNama, txtFeedback;

    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnCallUs = (RelativeLayout) findViewById(R.id.btnCallUs);
        btnKirim = (Button) findViewById(R.id.btnKirim);
        txtFeedback = (EditText) findViewById(R.id.txtFeedback);
        txtNama = (EditText) findViewById(R.id.txtNama);
        btnCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:081234534488"));
                startActivity(callIntent);
            }
        });
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sales@masaku.id"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                i.putExtra(Intent.EXTRA_TEXT, txtFeedback.getText().toString() + "\nBest regards\n" + txtNama.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ActivityContactUs.this, "Tidak ada email application terinstal", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public void onStart() {
        super.onStart();
        FlurryAgent.logEvent("CONTACT_US", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("CONTACT_US");
    }

}
