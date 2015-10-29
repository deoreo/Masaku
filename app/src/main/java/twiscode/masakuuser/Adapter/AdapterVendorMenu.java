package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelVendorMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterVendorMenu extends BaseAdapter {
    private Activity mAct;
    private List<ModelVendorMenu> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterVendorMenu(Activity activity, List<ModelVendorMenu> d) {
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
            convertView = mInflater.inflate(R.layout.row_vendor_menu_item, null);
            holder = new ViewHolder();
            holder.menuNama = (TextView) convertView.findViewById(R.id.menuNama);
            holder.menuTotal = (TextView) convertView.findViewById(R.id.menuTotal);
            holder.menuMinimal = (TextView) convertView.findViewById(R.id.menuMinimal);
            holder.menuImage = (ImageView) convertView.findViewById(R.id.menuImage);
            convertView.setTag(position);

            final ModelVendorMenu modelVendorMenu = mSourceData.get(position);
            final String ID = modelVendorMenu.getId();
            final String VENDOR_MENU_NAMA = modelVendorMenu.getMenunama();
            final String VENDOR_MENU_TOTAL = modelVendorMenu.getMenutotalpenjualan();
            final String VENDOR_MENU_MINIMAL = modelVendorMenu.getMenuminimalorder();
            final String VENDOR_IMAGE = modelVendorMenu.getMenuimage();


            holder.menuNama.setText(VENDOR_MENU_NAMA );
            holder.menuTotal.setText(VENDOR_MENU_TOTAL + " order per bulan");
            holder.menuMinimal.setText("Min order Rp. "+VENDOR_MENU_MINIMAL);
            Picasso.with(mAct).load(VENDOR_IMAGE).error(R.drawable.icon).fit().into(holder.menuImage);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                    ApplicationData.modelVendorMenu = modelVendorMenu;
                    Intent i = new Intent(mAct, ActivityMenuDetail.class);
                    mAct.startActivity(i);
                    mAct.finish();
                    */
                }

            });
        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView menuNama;
        public TextView menuTotal;
        public TextView menuMinimal;
        public ImageView menuImage;
    }



}
