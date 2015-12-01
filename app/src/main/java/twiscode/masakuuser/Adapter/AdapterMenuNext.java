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
import java.util.List;
import java.util.Locale;


import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.MySSLSocketFactoryManager;


public class AdapterMenuNext extends BaseAdapter {
    private Activity mAct;
    private List<ModelMenuSpeed> mSourceData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private DecimalFormat decimalFormat;
    int noImage = R.drawable.masaku_dummy_480x360;

    public AdapterMenuNext(Activity activity, List<ModelMenuSpeed> d) {
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
            convertView = mInflater.inflate(R.layout.row_menu_item_next, null);
            holder = new ViewHolder();
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress);
            holder.nameMenu = (TextView) convertView.findViewById(R.id.nameMenu);
            holder.timeMenu = (TextView) convertView.findViewById(R.id.timeMenu);
            holder.priceMenu = (TextView) convertView.findViewById(R.id.priceMenu);
            holder.imgMenu = (ImageView) convertView.findViewById(R.id.imgMenu);
            holder.layTime = (LinearLayout) convertView.findViewById(R.id.layoutTime);
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
            Log.d("image : ", VENDOR_IMAGE);
            //Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgMenu);
            if(VENDOR_IMAGE.length()==0 || VENDOR_IMAGE==""){
                holder.imgMenu.setImageResource(noImage);
                holder.progress.setVisibility(View.GONE);
            }
            else {
                new DownloadImageTask(holder.imgMenu,holder.progress)
                        .execute(VENDOR_IMAGE);

            }


            /*
            holder.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelMenuSpeed = modelMenu;
                    Intent i = new Intent(mAct, ActivityMenuSpeedDetail.class);
                    mAct.startActivity(i);
                    //mAct.finish();
                }

            });
            */




        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView nameMenu;
        public TextView timeMenu;
        public TextView priceMenu;
        public ImageView imgMenu;
        public Button add;
        public LinearLayout layTime;
        public ProgressBar progress;
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
                //bmImage.setImageResource(noImage);

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





}
