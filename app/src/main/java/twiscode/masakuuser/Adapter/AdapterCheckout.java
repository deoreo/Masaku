package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.PicassoTrustAll;


public class AdapterCheckout extends BaseAdapter {
    private Activity mAct;
    private List<ModelCart> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private DecimalFormat decimalFormat;
    int noImage = R.drawable.icon;
    public AdapterCheckout(Activity activity, List<ModelCart> d) {
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
            convertView = mInflater.inflate(R.layout.row_checkout_item_new, null);
            holder = new ViewHolder();
            holder.namaItem = (TextView) convertView.findViewById(R.id.namaCheckout);
            holder.btnMinus = (TextView) convertView.findViewById(R.id.btnMinus);
            holder.btnPlus = (TextView) convertView.findViewById(R.id.btnPlus);
            holder.txtCount = (TextView)convertView.findViewById(R.id.txtCount);
            holder.hargaItem = (TextView) convertView.findViewById(R.id.hargaCheckout);
            holder.imgMenu = (ImageView) convertView.findViewById(R.id.imgMenu);
            convertView.setTag(position);

            final ViewHolder holder2 = holder;

            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            otherSymbols.setDecimalSeparator(',');
            otherSymbols.setGroupingSeparator('.');
            decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalFormatSymbols(otherSymbols);

            final ModelCart modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String VENDOR_NAMA = modelMenu.getNama();
            final int VENDOR_ORDER = modelMenu.getJumlah();
            final int VENDOR_HARGA = VENDOR_ORDER*modelMenu.getHarga();
            final String VENDOR_IMAGE = modelMenu.getImage();


            holder.namaItem.setText(VENDOR_NAMA);
            holder.txtCount.setText(""+VENDOR_ORDER);
            holder.hargaItem.setText("Rp " + decimalFormat.format(VENDOR_HARGA));

            PicassoTrustAll.getInstance(mAct)
                    .load(VENDOR_IMAGE)
                    .placeholder(noImage)
                    .into(holder.imgMenu);

            holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ModelCart cart = new ModelCart(ID, VENDOR_NAMA, 1, VENDOR_HARGA);
                    AddCount(holder2, ID, cart);
                    int jml = ApplicationData.cart.get(ID).getJumlah();
                    holder.txtCount.setText("" + jml);
                    int harga = jml*modelMenu.getHarga();
                    holder.hargaItem.setText("Rp " + decimalFormat.format(harga));
                    SendBroadcast("updateCart", "true");
                }

            });
            holder.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int jml = ApplicationData.cart.get(ID).getJumlah();
                    if (jml > 1) {
                        int last = jml - 1;
                        ApplicationData.cart.get(ID).setJumlah(last);
                        holder.txtCount.setText("" + last);
                        int harga = last*modelMenu.getHarga();
                        holder.hargaItem.setText("Rp " + decimalFormat.format(harga));
                        SendBroadcast("updateCart", "true");
                    } else {
                        ApplicationData.cart.remove(ID);
                        SendBroadcast("updateCart", "delete");
                    }

                }

            });

        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView namaItem;
        public TextView jumlahItem;
        public TextView hargaItem;
        public TextView btnMinus;
        public TextView btnPlus;
        public TextView txtCount;
        public ImageView imgMenu;
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
       // CheckCounter(holder,ID);
    }

    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(mAct).sendBroadcast(intent);
    }



}
