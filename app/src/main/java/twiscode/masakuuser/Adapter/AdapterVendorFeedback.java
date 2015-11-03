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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import twiscode.masakuuser.Model.ModelVendorFeedback;
import twiscode.masakuuser.Model.ModelVendorRating;
import twiscode.masakuuser.R;


public class AdapterVendorFeedback extends BaseAdapter {
    private Activity mAct;
    private List<ModelVendorFeedback> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterVendorFeedback(Activity activity, List<ModelVendorFeedback> d) {
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
            convertView = mInflater.inflate(R.layout.row_vendor_rating_feedback, null);
            holder = new ViewHolder();
            holder.txtNama = (TextView) convertView.findViewById(R.id.txtNama);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtFeedback = (TextView) convertView.findViewById(R.id.txtFeedback);
            holder.rateFeedback = (RatingBar) convertView.findViewById(R.id.rateFeedback);
            convertView.setTag(position);

            final ModelVendorFeedback modelVendorFeedback = mSourceData.get(position);
            final String ID = modelVendorFeedback.getId();
            final String NAMA = modelVendorFeedback.getNama();
            final String RATE = modelVendorFeedback.getRate();
            final String DATE = modelVendorFeedback.getDate();
            final String FEEDBACK = modelVendorFeedback.getFeedback();


            holder.txtNama.setText(NAMA);
            holder.txtDate.setText(DATE);
            holder.txtFeedback.setText(FEEDBACK);
            holder.rateFeedback.setRating(Float.parseFloat(RATE));
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtNama;
        public RatingBar rateFeedback;
        public TextView txtDate;
        public TextView txtFeedback;
    }



}
