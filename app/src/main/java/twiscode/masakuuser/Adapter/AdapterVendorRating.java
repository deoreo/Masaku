package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import twiscode.masakuuser.Model.ModelVendorMenu;
import twiscode.masakuuser.Model.ModelVendorRating;
import twiscode.masakuuser.R;


public class AdapterVendorRating extends BaseAdapter {
    private Activity mAct;
    private List<ModelVendorRating> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterVendorRating(Activity activity, List<ModelVendorRating> d) {
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
            convertView = mInflater.inflate(R.layout.row_vendor_rating_chart, null);
            holder = new ViewHolder();
            holder.txtStar = (TextView) convertView.findViewById(R.id.txtStar);
            holder.chartGrafik = (RelativeLayout) convertView.findViewById(R.id.chartGrafik);
            holder.jumlahRating = (TextView) convertView.findViewById(R.id.jumlahRating);
            convertView.setTag(position);

            final ModelVendorRating modelVendorRating = mSourceData.get(position);
            final String ID = modelVendorRating.getId();
            final String RATING = modelVendorRating.getRating();
            final String JUMLAH_RATE = modelVendorRating.getJumlahRate();


            holder.txtStar.setText(ID );
            holder.jumlahRating.setText(JUMLAH_RATE);
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtStar;
        public RelativeLayout chartGrafik;
        public TextView jumlahRating;
    }



}
