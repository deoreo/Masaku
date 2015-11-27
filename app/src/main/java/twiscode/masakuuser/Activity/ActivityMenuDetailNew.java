package twiscode.masakuuser.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

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
import java.util.List;
import java.util.Locale;

import twiscode.masakuuser.Adapter.AdapterPagerMenuDetail;
import twiscode.masakuuser.Adapter.AdapterVendorFeedback;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelVendorFeedback;
import twiscode.masakuuser.Model.ModelVendorRating;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.MySSLSocketFactoryManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityMenuDetailNew extends ActionBarActivity {

    public LinearLayout layCounter;
    public TextView btnMinus;
    public TextView btnPlus;
    public TextView txtCount;
    private Button btnAdd;
    private ImageView btnBack,btnCart;
    public TextView nameMenu;
    public TextView timeMenu;
    public TextView priceMenu;
    public ImageView imgMenu;
    private ScrollView scroll;
    private ModelMenuSpeed modelMenu;
    private List<ModelVendorFeedback> LIST_FEEDBACK = new ArrayList<>();
    private ListView mListFeedback;
    private AdapterVendorFeedback adapterFeedback;
    private TextView countCart;
    private LinearLayout wrapCount;
    private BroadcastReceiver updateCart;
    private ProgressBar progress;
    private DecimalFormat decimalFormat;


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
        progress = (ProgressBar) findViewById(R.id.progress);
        wrapCount = (LinearLayout) findViewById(R.id.wrapCount);
        countCart = (TextView) findViewById(R.id.countCart);
        layCounter = (LinearLayout) findViewById(R.id.layCounter);
        btnMinus = (TextView) findViewById(R.id.btnMinus);
        btnPlus = (TextView) findViewById(R.id.btnPlus);
        txtCount = (TextView) findViewById(R.id.txtCount);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnCart = (ImageView) findViewById(R.id.btnCart);
        nameMenu = (TextView) findViewById(R.id.nameMenu);
        timeMenu = (TextView) findViewById(R.id.timeMenu);
        priceMenu = (TextView) findViewById(R.id.priceMenu);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        mListFeedback = (ListView) findViewById(R.id.feedbackList);
        scroll = (ScrollView) findViewById(R.id.scrollMenuDetail);
        scroll.smoothScrollTo(0,0);
        modelMenu = ApplicationData.modelMenuSpeed;

        nameMenu.setText(modelMenu.getNama());
        timeMenu.setText(modelMenu.getTime());
        priceMenu.setText("Rp. " + decimalFormat.format(Double.parseDouble(modelMenu.getPrice())));

        //Picasso.with(this).load(modelMenu.getFoto()).error(R.drawable.icon).fit().into(imgMenu);
        new DownloadImageTask(imgMenu)
                .execute(modelMenu.getFoto());

        adapterFeedback = new AdapterVendorFeedback(ActivityMenuDetailNew.this, LIST_FEEDBACK);
        mListFeedback.setAdapter(adapterFeedback);
        mListFeedback.setScrollingCacheEnabled(false);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), ActivityCheckout.class);
                startActivity(i);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCart cart = new ModelCart(modelMenu.getId(), modelMenu.getNama(), 1, Integer.parseInt(modelMenu.getPrice()));
                AddCount(modelMenu.getId(), cart);
                SendBroadcast("updateCart", "true");
            }

        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelCart cart = new ModelCart(modelMenu.getId(),modelMenu.getNama(),1,Integer.parseInt(modelMenu.getPrice()));
                AddCount(modelMenu.getId(),cart);
                int jml = ApplicationData.cart.get(modelMenu.getId()).getJumlah();
                txtCount.setText("" + jml);
                SendBroadcast("updateCart", "true");
            }

        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int jml = ApplicationData.cart.get(modelMenu.getId()).getJumlah();
                if(jml > 1){
                    int last = jml-1;
                    ApplicationData.cart.get(modelMenu.getId()).setJumlah(last);
                    txtCount.setText(""+last);
                }
                else {
                    ApplicationData.cart.remove(modelMenu.getId());
                    btnAdd.setVisibility(View.VISIBLE);
                    layCounter.setVisibility(View.GONE);
                }
                SendBroadcast("updateCart", "true");
            }

        });

        CheckCounter(modelMenu.getId());

        if(ApplicationData.cart.size() > 0){
            List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
            int jml = 0;
            for(int i = 0;i<list.size();i++){
                jml = jml + list.get(i).getJumlah();
            }
            countCart.setText(""+jml);
            wrapCount.setVisibility(View.VISIBLE);
        }
        else {
            wrapCount.setVisibility(View.GONE);
        }

        updateCart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateCart");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
                    if(list.size() > 0){
                        int jml = 0;
                        for(int i = 0;i<list.size();i++){
                            jml = jml + list.get(i).getJumlah();
                        }
                        countCart.setText(""+jml);
                        wrapCount.setVisibility(View.VISIBLE);
                    }
                    else {
                        wrapCount.setVisibility(View.GONE);
                    }

                }


            }
        };




    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext(), Main.class);
        startActivity(i);
        finish();

    }

    private void DummyFeedback(){
        LIST_FEEDBACK = new ArrayList<ModelVendorFeedback>();
        /*
        ModelVendorFeedback modelVendorMenu0 = new ModelVendorFeedback("0","Cinta L***", "5", "23-10-2015", "Masakannya enak");
        LIST_FEEDBACK.add(modelVendorMenu0);
        ModelVendorFeedback modelVendorMenu1 = new ModelVendorFeedback("1","Ji***", "4", "23-10-2015", "Enak tapi porsinya kurang banyak :D");
        LIST_FEEDBACK.add(modelVendorMenu1);
        */


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
            bmImage.setImageBitmap(result);

            if (progress != null) {
                progress.setVisibility(View.GONE);
            }

        }
    }

    private void CheckCounter(String ID){
        if(ApplicationData.cart.size() > 0){
            btnAdd.setVisibility(View.GONE);
            layCounter.setVisibility(View.VISIBLE);
            if(ApplicationData.cart.containsKey(ID)){
                int jml = ApplicationData.cart.get(ID).getJumlah();
                txtCount.setText(""+jml);
            }
            else {
                btnAdd.setVisibility(View.VISIBLE);
                layCounter.setVisibility(View.GONE);
            }
        }
        else {
            btnAdd.setVisibility(View.VISIBLE);
            layCounter.setVisibility(View.GONE);
        }
    }

    private void AddCount(String ID,ModelCart c){
        if(ApplicationData.cart.size() > 0){
            if(ApplicationData.cart.containsKey(ID)){
                ModelCart cart = ApplicationData.cart.get(ID);
                int jumlah = cart.getJumlah()+1;
                cart.setJumlah(jumlah);
                ApplicationData.cart.get(ID).setJumlah(jumlah);
            }
            else {
                ApplicationData.cart.put(ID, c);
            }
        }
        else {
            ApplicationData.cart.put(ID, c);
        }
        CheckCounter(ID);
    }

    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(ActivityMenuDetailNew.this).registerReceiver(updateCart,
                new IntentFilter("updateCart"));

        if(ApplicationData.cart.size() > 0){
            List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
            int jml = 0;
            for(int i = 0;i<list.size();i++){
                jml = jml + list.get(i).getJumlah();
            }
            countCart.setText(""+jml);
            wrapCount.setVisibility(View.VISIBLE);
        }
        else {
            wrapCount.setVisibility(View.GONE);
        }

        CheckCounter(modelMenu.getId());






    }

}
