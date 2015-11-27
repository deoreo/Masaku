package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import twiscode.masakuuser.Activity.ActivityMenuDetailNew;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.MySSLSocketFactoryManager;


public class AdapterMenuPODetail extends BaseAdapter {
    private Activity mAct;
    private List<ModelMenuSpeed> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private int height=0,width=0;
    private DecimalFormat decimalFormat;

    public AdapterMenuPODetail(Activity activity, List<ModelMenuSpeed> d) {
        mAct = activity;
        mSourceData = d;
        mInflater = (LayoutInflater) mAct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(d==null || d.isEmpty()){
            mKeyIsEmpty = true;
        }

    }

    @Override
    public int getCount() {
        if(mKeyIsEmpty)
            return 1;
        return mSourceData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(mKeyIsEmpty){
            convertView = mInflater.inflate(R.layout.row_delivery_empty2, null);
        }
        else {
            final ViewHolder holder;
            convertView = mInflater.inflate(R.layout.row_menu_item_new, null);
            holder = new ViewHolder();
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress);
            holder.nameMenu = (TextView) convertView.findViewById(R.id.nameMenu);
            holder.timeMenu = (TextView) convertView.findViewById(R.id.timeMenu);
            holder.priceMenu = (TextView) convertView.findViewById(R.id.priceMenu);
            holder.imgMenu = (ImageView) convertView.findViewById(R.id.imgMenu);
            holder.add = (Button) convertView.findViewById(R.id.btnAdd);
            holder.layCounter = (LinearLayout) convertView.findViewById(R.id.layCounter);
            holder.btnMinus = (TextView) convertView.findViewById(R.id.btnMinus);
            holder.btnPlus = (TextView) convertView.findViewById(R.id.btnPlus);
            holder.txtCount = (TextView)convertView.findViewById(R.id.txtCount);
            holder.layoutTime = (LinearLayout) convertView.findViewById(R.id.layoutTime);
            convertView.setTag(position);

            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            otherSymbols.setDecimalSeparator(',');
            otherSymbols.setGroupingSeparator('.');
            decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalFormatSymbols(otherSymbols);

            final ModelMenuSpeed modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String VENDOR_NAMA = modelMenu.getNama();
            final String VENDOR_HARGA = modelMenu.getPrice();
            final String VENDOR_TIME = modelMenu.getTime();
            final String VENDOR_IMAGE = modelMenu.getFoto();

            final ViewHolder holder2 = holder;



            holder.nameMenu.setText(VENDOR_NAMA );
            holder.priceMenu.setText("Rp. "+decimalFormat.format(Integer.parseInt(VENDOR_HARGA)));
            holder.timeMenu.setText(VENDOR_TIME);
            holder.layoutTime.setVisibility(View.GONE);
            Log.d("image : ", VENDOR_IMAGE);
            height = holder.imgMenu.getHeight();
            width = holder.imgMenu.getWidth();
            //Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgMenu);
            if(ApplicationData.temp_img.containsKey(VENDOR_IMAGE)){
                holder.imgMenu.setImageBitmap(ApplicationData.temp_img.get(VENDOR_IMAGE));
            }
            else {
                new DownloadImageTask(holder.imgMenu,holder.progress)
                        .execute(VENDOR_IMAGE);
            }


            holder.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelMenuSpeed = modelMenu;
                    Intent i = new Intent(mAct, ActivityMenuDetailNew.class);
                    mAct.startActivity(i);
                    mAct.finish();
                }

            });

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelCart cart = new ModelCart(ID,VENDOR_NAMA,1,Integer.parseInt(VENDOR_HARGA));
                    AddCount(holder2, ID, cart);
                    SendBroadcast("updateCart","true");
                }

            });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelCart cart = new ModelCart(ID,VENDOR_NAMA,1,Integer.parseInt(VENDOR_HARGA));
                    AddCount(holder2,ID,cart);
                    int jml = ApplicationData.cart.get(ID).getJumlah();
                    holder.txtCount.setText("" + jml);
                    SendBroadcast("updateCart", "true");
                }

            });
            holder.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int jml = ApplicationData.cart.get(ID).getJumlah();
                    if(jml > 1){
                        int last = jml-1;
                        ApplicationData.cart.get(ID).setJumlah(last);
                        holder.txtCount.setText(""+last);
                    }
                    else {
                        ApplicationData.cart.remove(ID);
                        holder.add.setVisibility(View.VISIBLE);
                        holder.layCounter.setVisibility(View.GONE);
                    }
                    SendBroadcast("updateCart","true");
                }

            });

            CheckCounter(holder, ID);


        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView nameMenu;
        public TextView timeMenu;
        public TextView priceMenu;
        public ImageView imgMenu;
        public Button add;
        public LinearLayout layCounter;
        public TextView btnMinus;
        public TextView btnPlus;
        public TextView txtCount;
        public ProgressBar progress;
        public LinearLayout layoutTime;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        String url;
        ImageView bmImage;
        ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage, ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.progressBar = progressBar;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            url = urldisplay;
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
            ApplicationData.temp_img.put(url, result);
            if(ApplicationData.temp_img.size() > 10){
                List<String> players = new ArrayList<>(ApplicationData.temp_img.keySet());
                String key = players.get(players.size()-1);
                ApplicationData.temp_img.remove(key);
            }

            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }

        }
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

    private void CheckCounter(ViewHolder holder, String ID){
        if(ApplicationData.cart.size() > 0){
            holder.add.setVisibility(View.GONE);
            holder.layCounter.setVisibility(View.VISIBLE);
            if(ApplicationData.cart.containsKey(ID)){
                int jml = ApplicationData.cart.get(ID).getJumlah();
                holder.txtCount.setText(""+jml);
            }
            else {
                holder.add.setVisibility(View.VISIBLE);
                holder.layCounter.setVisibility(View.GONE);
            }
        }
        else {
            holder.add.setVisibility(View.VISIBLE);
            holder.layCounter.setVisibility(View.GONE);
        }
    }

    private void AddCount(ViewHolder holder,String ID,ModelCart c){
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
        CheckCounter(holder,ID);
    }

    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(mAct).sendBroadcast(intent);
    }



}
