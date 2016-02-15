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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckoutKonfirmasi_2;
import twiscode.masakuuser.Activity.ActivityCheckoutVerify;
import twiscode.masakuuser.Activity.ActivityDetailTransaksi;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelNotif;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


public class AdapterNotif extends BaseAdapter {
    private Activity mAct;
    private List<ModelNotif> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;

    public AdapterNotif(Activity activity, List<ModelNotif> d) {
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
            convertView = mInflater.inflate(R.layout.row_notification_item, null);
            holder = new ViewHolder();
            holder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.btnDetailNotif = (LinearLayout) convertView.findViewById(R.id.layoutNotif);
            //holder.imgVendor = (ImageView) convertView.findViewById(R.id.imgVendor);
            convertView.setTag(position);

            final ModelNotif modelNotif = mSourceData.get(position);
            final String ID = modelNotif.getId();
            final String NOTIF_MESSAGE = modelNotif.getMessage();
            final String NOTIF_DATE = modelNotif.getDate();
            final String NOTIF_MENU_ID = modelNotif.getMenuId();
            String status = "";


            holder.txtMessage.setText(Html.fromHtml(NOTIF_MESSAGE) );
            holder.txtDate.setText(NOTIF_DATE);

            holder.btnDetailNotif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApplicationData.modelNotif = modelNotif;
                    if(NOTIF_MESSAGE.equalsIgnoreCase("")){

                    }
                    else if(NOTIF_MESSAGE.equalsIgnoreCase("")){

                    }
                    else{

                    }

                }
            });


        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtMessage;
        public TextView txtDate;
        public LinearLayout btnDetailNotif;
    }



}
