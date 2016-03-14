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
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.MySSLSocketFactoryManager;
import twiscode.masakuuser.Utilities.PicassoTrustAll;


public class AdapterMenuPO extends BaseAdapter {
    private Activity mAct;
    private List<ModelMenuSpeed> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private int height=0,width=0;
    private DecimalFormat decimalFormat;
    int noImage = R.drawable.delhome_dummy_image;
    int tipe = 1;

    public AdapterMenuPO(Activity activity, List<ModelMenuSpeed> d) {
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
            Log.d("tipe PO",""+tipe);
                convertView = mInflater.inflate(R.layout.row_empty_po, null);
        }
        else {
            final ViewHolder holder;
            convertView = mInflater.inflate(R.layout.row_menu_item_new, null);
            holder = new ViewHolder();
            holder.progress = (ProgressBar) convertView.findViewById(R.id.progress);
            holder.nameMenu = (TextView) convertView.findViewById(R.id.nameMenu);

            //holder.deliveryMenu = (TextView) convertView.findViewById(R.id.deliveryMenu);
            holder.hari = (TextView) convertView.findViewById(R.id.hariMenu);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalMenu);
            holder.priceMenu = (TextView) convertView.findViewById(R.id.priceMenu);
            holder.imgMenu = (ImageView) convertView.findViewById(R.id.imgMenu);
            holder.add = (Button) convertView.findViewById(R.id.btnAdd);
            holder.layCounter = (LinearLayout) convertView.findViewById(R.id.layCounter);
            holder.btnMinus = (TextView) convertView.findViewById(R.id.btnMinus);
            holder.btnPlus = (TextView) convertView.findViewById(R.id.btnPlus);
            holder.txtCount = (TextView)convertView.findViewById(R.id.txtCount);
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
            final String VENDOR_DELIVERY = modelMenu.getDelivery();

            final ViewHolder holder2 = holder;


            //holder.deliveryMenu.setText(VENDOR_DELIVERY );

            String[] dev = VENDOR_DELIVERY.split(", ");
            Log.d("dev",dev.length+"");
            for(int k=0;k<dev.length;k++){
                Log.d("dev "+k,dev[k]);
            }
            String tgl = getDate(dev[1]);
            String hr = getDay(dev[0]);

            holder.tanggal.setText(tgl);
            holder.hari.setText(hr);

            holder.nameMenu.setText(VENDOR_NAMA );
            holder.priceMenu.setText("Rp. "+decimalFormat.format(Integer.parseInt(VENDOR_HARGA)));
            Log.d("image : ", VENDOR_IMAGE);
            height = holder.imgMenu.getHeight();
            width = holder.imgMenu.getWidth();
            //Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgMenu);
            if(VENDOR_IMAGE.length()==0 || VENDOR_IMAGE==""){
                holder.imgMenu.setImageResource(noImage);
                holder.progress.setVisibility(View.GONE);
            }
            else {
                //new DownloadImageTask(holder.imgMenu,holder.progress).execute(VENDOR_IMAGE);
                PicassoTrustAll.getInstance(mAct)
                        .load(VENDOR_IMAGE)
                        .placeholder(noImage)
                        .into(holder.imgMenu);
                holder.progress.setVisibility(View.GONE);

            }



            holder.imgMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelMenuSpeed = modelMenu;
                    ApplicationData.type = "po";
                    Intent i = new Intent(mAct, ActivityMenuDetailNew.class);
                    mAct.startActivity(i);
                    //mAct.finish();
                }

            });

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelCart cart = new ModelCart(ID,VENDOR_NAMA,1,Integer.parseInt(VENDOR_HARGA),"po",modelMenu.getIsEvent());
                    AddCount(holder2, ID, cart);
                    SendBroadcast("updateCart","true");
                }

            });

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelCart cart = new ModelCart(ID,VENDOR_NAMA,1,Integer.parseInt(VENDOR_HARGA),"po",modelMenu.getIsEvent());
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
        public TextView nameMenu,hari,tanggal;
        public TextView priceMenu;
        public ImageView imgMenu;
        public Button add;
        public LinearLayout layCounter;
        public TextView btnMinus;
        public TextView btnPlus;
        public TextView txtCount;
        public ProgressBar progress;
    }




    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(mAct).sendBroadcast(intent);
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
        CheckCounter(holder, ID);
    }

    private String getDay(String date){
        String day = "";
        if(date.equalsIgnoreCase("monday")){
            day = "Senin";
        }
        else if(date.equalsIgnoreCase("tuesday")){
            day = "Selasa";
        }
        else if(date.equalsIgnoreCase("wednesday")){
            day = "Rabu";
        }
        else if(date.equalsIgnoreCase("thursday")){
            day = "Kamis";
        }
        else if(date.equalsIgnoreCase("friday")){
            day = "Jumat";
        }
        else if(date.equalsIgnoreCase("saturday")){
            day = "Sabtu";
        }
        else {
            day = "Minggu";
        }
        return  day;
    }

    private String getDate(String date){
        String dt = "";
        String [] d = date.split(" ");
        dt = d[0]+" "+d[1]+ " " +d[2];
        return  dt;
    }



}
