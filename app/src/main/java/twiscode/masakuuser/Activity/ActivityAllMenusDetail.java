package twiscode.masakuuser.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.InputStream;
import java.security.KeyStore;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import twiscode.masakuuser.Adapter.AdapterVendorFeedback;
import twiscode.masakuuser.Model.ModelAllMenus;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelVendorFeedback;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.MySSLSocketFactoryManager;
import twiscode.masakuuser.Utilities.PicassoTrustAll;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityAllMenusDetail extends ActionBarActivity {

    public LinearLayout layCounter;
    public TextView btnMinus;
    public TextView btnPlus;
    public TextView txtCount;
    private Button btnAdd;
    private ImageView btnBack,btnCart;
    public TextView nameMenu;
    public TextView timeMenu;
    public TextView priceMenu;
    public TextView txtDeskripsi;
    public ImageView imgMenu;
    private ScrollView scroll;
    private ModelAllMenus modelMenu;
    private List<ModelVendorFeedback> LIST_FEEDBACK = new ArrayList<>();
    private ListView mListFeedback;
    private AdapterVendorFeedback adapterFeedback;
    private TextView countCart,deliveryMenu;
    private LinearLayout wrapCount, timeLayout, deliveryLayout;
    private BroadcastReceiver updateCart;
    private ProgressBar progress;
    private DecimalFormat decimalFormat;
    int noImage = R.drawable.masaku_dummy_480x360;
    //private RelativeLayout layoutTimePO;

    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalFormatSymbols(otherSymbols);

        DummyFeedback();
        //layoutTimePO = (RelativeLayout) findViewById(R.id.layoutTimePO);
        progress = (ProgressBar) findViewById(R.id.progress);
        wrapCount = (LinearLayout) findViewById(R.id.wrapCount);
        deliveryLayout = (LinearLayout) findViewById(R.id.deliveryLayout);
        countCart = (TextView) findViewById(R.id.countCart);
        layCounter = (LinearLayout) findViewById(R.id.layCounter);
        deliveryMenu = (TextView) findViewById(R.id.deliveryMenu);
        btnMinus = (TextView) findViewById(R.id.btnMinus);
        btnPlus = (TextView) findViewById(R.id.btnPlus);
        txtCount = (TextView) findViewById(R.id.txtCount);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnCart = (ImageView) findViewById(R.id.btnCart);
        nameMenu = (TextView) findViewById(R.id.nameMenu);
        timeMenu = (TextView) findViewById(R.id.timeMenu);
        priceMenu = (TextView) findViewById(R.id.priceMenu);
        txtDeskripsi = (TextView) findViewById(R.id.txtDeskripsi);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        mListFeedback = (ListView) findViewById(R.id.feedbackList);
        wrapCount = (LinearLayout) findViewById(R.id.wrapCount);
        timeLayout = (LinearLayout) findViewById(R.id.timeLayout);
        modelMenu = ApplicationData.modelAllMenus;

        //layoutTimePO.setVisibility(View.GONE);

        try{
            if(modelMenu.getDelivery()==""){
                deliveryLayout.setVisibility(View.GONE);
            }
            else {
                deliveryLayout.setVisibility(View.VISIBLE);
                deliveryMenu.setText(modelMenu.getDelivery());
            }
        }
        catch (Exception x){
            deliveryLayout.setVisibility(View.GONE);
        }


        nameMenu.setText(modelMenu.getNama());
        timeMenu.setText(modelMenu.getTime());
        priceMenu.setText("Rp. " + decimalFormat.format(Double.parseDouble(modelMenu.getPrice())));
        txtDeskripsi.setText(modelMenu.getDeskripsi());
        //Picasso.with(this).load(modelMenu.getFoto()).error(R.drawable.icon).fit().into(imgMenu);
        if(modelMenu.getFoto().length()==0 || modelMenu.getFoto()==""){
            imgMenu.setImageResource(noImage);
            progress.setVisibility(View.GONE);
        }
        else {
            /*
            new DownloadImageTask(imgMenu)
                    .execute(modelMenu.getFoto());
                    */
            PicassoTrustAll.getInstance(this)
                    .load(modelMenu.getFoto())
                    .placeholder(noImage)
                    .into(imgMenu);
            progress.setVisibility(View.GONE);

        }

        adapterFeedback = new AdapterVendorFeedback(ActivityAllMenusDetail.this, LIST_FEEDBACK);
        mListFeedback.setAdapter(adapterFeedback);
        mListFeedback.setScrollingCacheEnabled(false);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                finish();
            }
        });

        btnMinus.setVisibility(View.GONE);
        btnPlus.setVisibility(View.GONE);
        btnAdd.setVisibility(View.GONE);
        btnCart.setVisibility(View.GONE);
        txtCount.setVisibility(View.GONE);
        wrapCount.setVisibility(View.GONE);
        timeLayout.setVisibility(View.GONE);

        flurryParams.put("ID_MENU",modelMenu.getId());
        FlurryAgent.logEvent("MENU_DETAIL", flurryParams, true);










    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), Main.class);
        startActivity(i);
        finish();

    }

    private void DummyFeedback(){
        LIST_FEEDBACK = new ArrayList<ModelVendorFeedback>();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private HttpClient createDevelopmentHttpClientInstance() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            org.apache.http.conn.ssl.SSLSocketFactory sf = new MySSLSocketFactoryManager(trustStore);
            sf.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Log.d("url promo slider", urldisplay);
            Bitmap mIcon11 = null;
            try {
                /*
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                */
                DefaultHttpClient  httpClient = (DefaultHttpClient)createDevelopmentHttpClientInstance();
                HttpGet httpGet = new HttpGet(urldisplay);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();

                BufferedHttpEntity b_entity = new BufferedHttpEntity(httpEntity);
                InputStream input = b_entity.getContent();
                mIcon11 = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result != null){
                bmImage.setImageBitmap(result);
            }
            else {
                bmImage.setImageResource(noImage);
            }

            if (progress != null) {
                progress.setVisibility(View.GONE);
            }

        }
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("MENU_DETAIL");
        FlurryAgent.onEndSession(this);
    }





}
