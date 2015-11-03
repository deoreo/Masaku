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

import java.util.List;

import twiscode.masakuuser.Activity.ActivityMenuDetail;
import twiscode.masakuuser.Activity.ActivityMenuDetailNew;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterMenuNew extends BaseAdapter {
    private Activity mAct;
    private List<ModelMenu> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterMenuNew(Activity activity, List<ModelMenu> d) {
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
            convertView = mInflater.inflate(R.layout.row_menu_item_new, null);
            holder = new ViewHolder();
            holder.nameMenu = (TextView) convertView.findViewById(R.id.nameMenu);
            holder.timeMenu = (TextView) convertView.findViewById(R.id.timeMenu);
            holder.priceMenu = (TextView) convertView.findViewById(R.id.priceMenu);
            holder.imgMenu = (ImageView) convertView.findViewById(R.id.imgMenu);
            convertView.setTag(position);

            final ModelMenu modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String VENDOR_NAMA = modelMenu.getNama();
            final String VENDOR_HARGA = modelMenu.getPrice();
            final String VENDOR_TIME = modelMenu.getTime();
            final String VENDOR_RATING = modelMenu.getRating();
            final String VENDOR_IMAGE = modelMenu.getFoto();



            holder.nameMenu.setText(VENDOR_NAMA );
            holder.priceMenu.setText("Rp. "+VENDOR_HARGA);
            holder.timeMenu.setText(VENDOR_TIME);
            Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.imgMenu);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelMenu = modelMenu;
                    Intent i = new Intent(mAct, ActivityMenuDetailNew.class);
                    mAct.startActivity(i);
                    mAct.finish();
                }

            });
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView nameMenu;
        public TextView timeMenu;
        public TextView priceMenu;
        public ImageView imgMenu;
    }



}
