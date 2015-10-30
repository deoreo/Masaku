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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import twiscode.masakuuser.Activity.ActivityMenuDetail;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterPesanan extends BaseAdapter {
    private Activity mAct;
    private List<ModelPesanan> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterPesanan(Activity activity, List<ModelPesanan> d) {
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
            convertView = mInflater.inflate(R.layout.row_delivery_empty, null);
        }
        else {
            ViewHolder holder;
            convertView = mInflater.inflate(R.layout.row_pesanan_item, null);
            holder = new ViewHolder();
            holder.namaVendor = (TextView) convertView.findViewById(R.id.namaVendor);
            holder.dateOrder = (TextView) convertView.findViewById(R.id.datePesanan);
            holder.statusOrder = (TextView) convertView.findViewById(R.id.statusPesanan);
            holder.harga = (TextView) convertView.findViewById(R.id.hargaPesanan);
            holder.imgVendor = (ImageView) convertView.findViewById(R.id.imgVendor);
            convertView.setTag(position);

            final ModelPesanan modelPesanan = mSourceData.get(position);
            final String ID = modelPesanan.getId();
            final String VENDOR_NAMA = modelPesanan.getNama();
            final String VENDOR_STATUS = modelPesanan.getStatus();
            final String VENDOR_DATE = modelPesanan.getTanggal();
            final String VENDOR_TIME = modelPesanan.getJam();
            final String VENDOR_HARGA = modelPesanan.getHarga();
            final String VENDOR_IMAGE = modelPesanan.getFoto();


            holder.namaVendor.setText(VENDOR_NAMA );
            holder.dateOrder.setText(VENDOR_DATE+" "+VENDOR_TIME);
            holder.statusOrder.setText(VENDOR_STATUS);
            holder.harga.setText("Rp "+VENDOR_HARGA);
            Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgVendor);

        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView namaVendor;
        public TextView dateOrder;
        public TextView statusOrder;
        public TextView harga;
        public ImageView imgVendor;
    }



}
