package twiscode.masakuuser.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import twiscode.masakuuser.R;

public class ActivityMain extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();


        TabHost.TabSpec menu = tabHost.newTabSpec("Menu");
        menu.setIndicator("Menu", getResources().getDrawable(R.drawable.selector_menu_tab));
        Intent menuIntent = new Intent(this, ActivityMenu.class);
        menu.setContent(menuIntent);


        TabHost.TabSpec pesanan = tabHost.newTabSpec("Pesanan");
        pesanan.setIndicator("Pesanan", getResources().getDrawable(R.drawable.selector_pesanan_tab));
        Intent pesananIntent = new Intent(this, ActivityPesanan.class);
        pesanan.setContent(pesananIntent);


        TabHost.TabSpec profile = tabHost.newTabSpec("Profile");
        profile.setIndicator("Profile", getResources().getDrawable(R.drawable.selector_profile_tab));
        Intent profileIntent = new Intent(this, ActivityProfile.class);
        profile.setContent(profileIntent);


        tabHost.addTab(menu); // Adding photos tab
        tabHost.addTab(pesanan); // Adding songs tab
        tabHost.addTab(profile); // Adding videos tab

        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
        }
    }
    
}
