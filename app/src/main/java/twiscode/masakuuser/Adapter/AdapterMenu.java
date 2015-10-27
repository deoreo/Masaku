package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;


public class AdapterMenu extends BaseAdapter {
    private Activity mAct;
    private List<ModelMenu> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterMenu(Activity activity, List<ModelMenu> d) {
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
            convertView = mInflater.inflate(R.layout.row_menu_item, null);
            holder = new ViewHolder();
            holder.namaVendor = (TextView) convertView.findViewById(R.id.namaVendor);
            holder.ratingVendor = (TextView) convertView.findViewById(R.id.txtRate);
            holder.jumlahOrder = (TextView) convertView.findViewById(R.id.jumlahPenjualan);
            holder.harga = (TextView) convertView.findViewById(R.id.minOrder);
            holder.imgVendor = (ImageView) convertView.findViewById(R.id.imgVendor);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rateVendor);
            convertView.setTag(position);

            final ModelMenu modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String VENDOR_NAMA = modelMenu.getNama();
            final String VENDOR_ORDER = modelMenu.getJumlahorder();
            final String VENDOR_RATING = modelMenu.getRating();
            final String VENDOR_HARGA = modelMenu.getHarga();
            final String VENDOR_IMAGE = modelMenu.getFoto();


            holder.namaVendor.setText(VENDOR_NAMA );
            holder.ratingVendor.setText(VENDOR_RATING + "/5");
            holder.jumlahOrder.setText(VENDOR_ORDER + " Order per Bulan");
            holder.harga.setText("Min Order Rp "+VENDOR_HARGA);
            holder.ratingBar.setRating(Float.parseFloat(VENDOR_RATING));
            Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgVendor);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    ApplicationData.modelDeliver = modelDeliver;
                    Intent i = new Intent(mAct, ActivityDeliveryDetail.class);
                    mAct.startActivity(i);
                    mAct.finish();
                    */
                }

            });
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView namaVendor;
        public TextView ratingVendor;
        public TextView jumlahOrder;
        public TextView harga;
        public ImageView imgVendor;
        public RatingBar ratingBar;
    }



}
