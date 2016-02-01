package twiscode.masakuuser.Adapter;

/**
 * Created by deoreo06 on 22/10/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import twiscode.masakuuser.Model.ModelAlamat;
import twiscode.masakuuser.R;


public class AdapterAlamat extends BaseAdapter {
    private Activity mAct;
    private ArrayList<ModelAlamat> mSourceData, mFilterData;
    private LayoutInflater mInflater =null;
    private boolean mKeyIsEmpty = false;
    private int height=0,width=0;
    private DecimalFormat decimalFormat;
    private OkHttpClient okHttpClient;
    int noImage = R.drawable.masaku_dummy_480x360;

    public AdapterAlamat(Activity activity, ArrayList<ModelAlamat> d) {
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
            final ViewHolder holder;
            convertView = mInflater.inflate(R.layout.row_alamat, null);
            holder = new ViewHolder();
            holder.txtAlamat = (TextView) convertView.findViewById(R.id.txtAlamat);
            convertView.setTag(position);

            final ModelAlamat modelMenu = mSourceData.get(position);
            final String ID = modelMenu.getId();
            final String ALAMAT = modelMenu.getNama();

            holder.txtAlamat.setText(ALAMAT);

            holder.txtAlamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendBroadcast("changeAlamat", ALAMAT);
                }
            });


        }
        return convertView;
    }

    private static class ViewHolder {
        public TextView txtAlamat;
    }

    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(mAct).sendBroadcast(intent);
    }




}
