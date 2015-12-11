package twiscode.masakuuser.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivityTerms extends AppCompatActivity {

    private ImageView btnBack;
    private TextView txtCopyright,txtGuarantee,txtTerms,txtPrivacy,txtDisclaimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtCopyright = (TextView) findViewById(R.id.txtCopyright);
        txtTerms = (TextView) findViewById(R.id.txtTerms);
        txtGuarantee = (TextView) findViewById(R.id.txtGuarantee);
        txtPrivacy = (TextView) findViewById(R.id.txtPrivacy);
        txtDisclaimer = (TextView) findViewById(R.id.txtDisclaimer);

        txtCopyright.setText(Html.fromHtml(getResources().getString(R.string.contentCopyright)));
        txtTerms.setText(Html.fromHtml(getResources().getString(R.string.contentTerms)));
        txtGuarantee.setText(Html.fromHtml(getResources().getString(R.string.contentGuarantee)));
        txtPrivacy.setText(Html.fromHtml(getResources().getString(R.string.contentPrivacy)));
        txtDisclaimer.setText(Html.fromHtml(getResources().getString(R.string.contentDisclaimer)));







    }

}
