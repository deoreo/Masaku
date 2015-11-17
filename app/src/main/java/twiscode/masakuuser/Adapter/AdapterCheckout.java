package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import twiscode.masakuuser.Activity.ActivityMenuDetail;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterCheckout extends BaseAdapter {
    private Activity mAct;
    private List<ModelCart> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private DecimalFormat decimalFormat;

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
            ViewHolder holder;
            convertView = mInflater.inflate(R.layout.row_checkout_item, null);
            holder = new ViewHolder();
            holder.namaItem = (TextView) convertView.findViewById(R.id.namaCheckout);
            holder.jumlahItem= (TextView) convertView.findViewById(R.id.jumlahCheckout);
            holder.hargaItem = (TextView) convertView.findViewById(R.id.hargaCheckout);
            convertView.setTag(position);

            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            otherSymbols.setDecimalSeparator(',');
            otherSymbols.setGroupingSeparator('.');
            decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalFormatSymbols(otherSymbols);

            final ModelCart modelMenu = mSourceData.get(position);
            final String VENDOR_NAMA = modelMenu.getNama();
            final int VENDOR_ORDER = modelMenu.getJumlah();
            final int VENDOR_HARGA = VENDOR_ORDER*modelMenu.getHarga();


            holder.namaItem.setText(VENDOR_NAMA);
            holder.jumlahItem.setText(""+VENDOR_ORDER);
            holder.hargaItem.setText("Rp " + decimalFormat.format(VENDOR_HARGA));

        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView namaItem;
        public TextView jumlahItem;
        public TextView hargaItem;
    }



}
