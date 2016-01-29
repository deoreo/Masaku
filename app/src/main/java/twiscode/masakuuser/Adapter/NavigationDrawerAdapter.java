package twiscode.masakuuser.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import twiscode.masakuuser.Model.ModelNavDrawer;
import twiscode.masakuuser.R;

/**
 * Created by Unity on 18/05/2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>  {
    List<ModelNavDrawer> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Activity act;
    final int ic[] = {
            R.drawable.all_menu_icon,
            R.drawable.history_pesanan_icon,
            R.drawable.fooddatabase_icon,
            R.drawable.promo_icon,
            R.drawable.help_icon,
            R.drawable.chat_with_cs
    };

    public NavigationDrawerAdapter(Context context, List<ModelNavDrawer> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.act = (Activity) context;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelNavDrawer current = data.get(position);
        holder.title.setText(current.getTitle());
        if(position==data.size()-1){
            //holder.line.setVisibility(View.GONE);
        }
        holder.icon.setImageDrawable(act.getResources().getDrawable(ic[position]));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        View line;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            //line = (View) itemView.findViewById(R.id.line);
        }
    }
}
