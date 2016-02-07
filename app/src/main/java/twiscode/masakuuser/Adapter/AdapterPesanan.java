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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_1;
import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_2;
import twiscode.masakuuser.Activity.ActivityCheckoutVerify;
import twiscode.masakuuser.Activity.ActivityDetailTransaksi;
import twiscode.masakuuser.Activity.ActivityRegister;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterPesanan extends BaseAdapter {
    private Activity mAct;
    private List<ModelDetailTransaksi> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterPesanan(Activity activity, List<ModelDetailTransaksi> d) {
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
            convertView = mInflater.inflate(R.layout.row_pesanan_item, null);
            holder = new ViewHolder();
            holder.namaVendor = (TextView) convertView.findViewById(R.id.namaVendor);
            holder.dateOrder = (TextView) convertView.findViewById(R.id.datePesanan);
            holder.statusOrder = (TextView) convertView.findViewById(R.id.statusPesanan);
            holder.harga = (TextView) convertView.findViewById(R.id.hargaPesanan);
            holder.btnPesan = (TextView) convertView.findViewById(R.id.btnPesan);
            holder.btnDetailPesanan = (LinearLayout) convertView.findViewById(R.id.layoutPesanan);
            //holder.imgVendor = (ImageView) convertView.findViewById(R.id.imgVendor);
            convertView.setTag(position);

            final ModelDetailTransaksi modelPesanan = mSourceData.get(position);
            final String ID = modelPesanan.getId();
            final String DETAIL_ID = modelPesanan.getDetailID();
            final String VENDOR_NAMA = modelPesanan.getNama();
            final String VENDOR_STATUS = modelPesanan.getStatus();
            final String VENDOR_DATE = modelPesanan.getDate();
            final String VENDOR_TIME = modelPesanan.getTime();
            final String VENDOR_HARGA = modelPesanan.getTotal();
            //final String VENDOR_IMAGE = modelPesanan.getFoto();
            String status = "";

            holder.namaVendor.setText(DETAIL_ID );
            holder.dateOrder.setText(VENDOR_DATE+" "+VENDOR_TIME);
            if(VENDOR_STATUS.equalsIgnoreCase("waitingPayment")){
                status = "waiting payment";
            }
            else if(VENDOR_STATUS.equalsIgnoreCase("verifyingPayment")){
                status = "verifying payment";
            }
            else {
                status = VENDOR_STATUS;
            }

            holder.statusOrder.setText(status);
            holder.harga.setText("Rp " + VENDOR_HARGA);
            //Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgVendor);
            /*
            holder.btnPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.pesanan = modelPesanan;
                    Intent j = new Intent(mAct, ActivityDetailTransaksi.class);
                    mAct.startActivity(j);
                }
            });
            */

            holder.btnDetailPesanan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.detailTransaksi = modelPesanan;
                    if(VENDOR_STATUS.equalsIgnoreCase("waitingPayment")){
                        Intent i = new Intent(mAct, ActivityCheckoutKonfirmasi_2.class);
                        mAct.startActivity(i);
                       // mAct.finish();
                    }
                    else if(VENDOR_STATUS.equalsIgnoreCase("verifyingPayment")){
                        Intent i = new Intent(mAct, ActivityCheckoutVerify.class);
                        mAct.startActivity(i);
                        //mAct.finish();
                    }
                    else{
                        Intent i = new Intent(mAct, ActivityDetailTransaksi.class);
                        mAct.startActivity(i);
                        //mAct.finish();
                    }

                }
            });


        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView namaVendor;
        public TextView dateOrder;
        public TextView statusOrder;
        public TextView harga;
        public TextView btnPesan;
        public LinearLayout btnDetailPesanan;
        //public ImageView imgVendor;
    }



}
