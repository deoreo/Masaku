package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import twiscode.masakuuser.Model.ModelPromo;
import twiscode.masakuuser.R;


public class AdapterPromo extends BaseAdapter {
    private Activity mAct;
    private List<ModelPromo> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterPromo(Activity activity, List<ModelPromo> d) {
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
            convertView = mInflater.inflate(R.layout.row_promo_item, null);
            holder = new ViewHolder();
            holder.promoDate = (TextView) convertView.findViewById(R.id.promoDate);
            holder.promoDeskripsi = (TextView) convertView.findViewById(R.id.promoDeskripsi);
            holder.promoSyarat = (TextView) convertView.findViewById(R.id.promoSyarat);
            holder.promoPhoto = (ImageView) convertView.findViewById(R.id.promoPhoto);
            convertView.setTag(position);

            final ModelPromo modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String VENDOR_DATE = modelMenu.getDateStart()+" - "+modelMenu.getDateEnd();
            final String VENDOR_DESC = modelMenu.getDeskripsi();
            final String VENDOR_SYARAT = modelMenu.getSyarat();
            final int VENDOR_IMAGE = modelMenu.getPhotos();


            holder.promoDeskripsi.setText(VENDOR_DESC );
            holder.promoSyarat.setText(Html.fromHtml(VENDOR_SYARAT));
            holder.promoDate.setText(VENDOR_DATE);
            Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).into(holder.promoPhoto);
            /*
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelMenu = modelMenu;
                    Intent i = new Intent(mAct, ActivityMenuDetail.class);
                    mAct.startActivity(i);
                    mAct.finish();
                }

            });
            */
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView promoDate;
        public TextView promoDeskripsi;
        public TextView promoSyarat;
        public ImageView promoPhoto;
    }



}
