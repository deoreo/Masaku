package twiscode.masakuuser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import twiscode.masakuuser.Activity.ActivityFilterLokasi;
import twiscode.masakuuser.Model.ModelLokasi;
import twiscode.masakuuser.R;

/**
 * Created by User on 3/23/2016.
 */
public class AdapterFilterLokasi extends BaseAdapter {
    ArrayList<ModelLokasi> listLokasi;
    Activity activity;

    public AdapterFilterLokasi(Activity activity, ArrayList<ModelLokasi> listLokasi){
        this.activity = activity;
        this.listLokasi = listLokasi;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_filter_lokasi, null);
            holder.lokasi = (TextView)view.findViewById(R.id.lokasi);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

//        MobilModel mobil = (MobilModel)getItem(position);
//        holder.txtTitle.setText(mobil.getTitle());
//        holder.txtHarga.setText(mobil.getHarga());
//        holder.txtLokasi.setText(mobil.getLokasi());
//
//        Picasso.with(activity).load(mobil.getImage()).into(holder.imgItem);

        return view;
    }

    static class ViewHolder{
        TextView lokasi;
    }
}
