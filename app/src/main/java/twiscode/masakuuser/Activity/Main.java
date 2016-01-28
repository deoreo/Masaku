package twiscode.masakuuser.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Fragment.FragmentAllMenus;
import twiscode.masakuuser.Fragment.FragmentAntarCepat;
import twiscode.masakuuser.Fragment.FragmentBantuan;
import twiscode.masakuuser.Fragment.FragmentDrawer;
import twiscode.masakuuser.Fragment.FragmentMainMenu;
import twiscode.masakuuser.Fragment.FragmentPesanan;
import twiscode.masakuuser.Fragment.FragmentProfile;
import twiscode.masakuuser.Fragment.FragmentPromo;
import twiscode.masakuuser.Fragment.FragmentWishlist;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DataFragmentHelper;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.PersistenceDataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Main extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private ActionBar actionBar;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    DataFragmentHelper datafragmentHelper = PersistenceDataHelper.GetInstance().FragmentHelper;
    TextView titleBar;
    private TextView countCart;
    private LinearLayout wrapCount;
    private ImageView btnCart;
    private final int MENU = 0, WISHLIST = 1, HISTORI_PESANAN = 2, ALL_MENU = 3, PROMO = 4, BANTUAN = 5, CUSTOMER_SERVICE = 6;

    private BroadcastReceiver updateCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.drawer_toggle);
        mToolbar.setLogo(R.drawable.drawer_toggle);
        setSupportActionBar(mToolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        actionBar = getSupportActionBar();

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.drawer_toggle);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        titleBar = (TextView) mCustomView.findViewById(R.id.title_text);
        wrapCount = (LinearLayout) mCustomView.findViewById(R.id.wrapCount);
        countCart = (TextView) mCustomView.findViewById(R.id.countCart);
        btnCart = (ImageView) mCustomView.findViewById(R.id.btnCart);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        ApplicationData.titleBar = titleBar;

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer); //getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ApplicationData.cart.size()>0) {
                    Intent i = new Intent(Main.this, ActivityCheckout.class);
                    startActivity(i);
                }else{
                    DialogManager.showDialog(Main.this,"Mohon Maaf", "Anda belum memiliki pesanan");
                }
            }
        });
        if(ApplicationData.cart.size() > 0){
            List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
            int jml = 0;
            for(int i = 0;i<list.size();i++){
                jml = jml + list.get(i).getJumlah();
            }
            countCart.setText(""+jml);
            wrapCount.setVisibility(View.VISIBLE);
        }
        else {
            wrapCount.setVisibility(View.GONE);
        }

        updateCart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateCart");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
                    if(list.size() > 0){
                        int jml = 0;
                        for(int i = 0;i<list.size();i++){
                            jml = jml + list.get(i).getJumlah();
                        }
                        countCart.setText(""+jml);
                        wrapCount.setVisibility(View.VISIBLE);
                    }
                    else {
                        wrapCount.setVisibility(View.GONE);
                    }

                }

                SendBroadcast("cekOrderNow","true");


            }
        };





        displayView(0);


    }

    @Override
    public void onBackPressed() {
        /*
        Log.d("counter stack", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        if(ApplicationData.titleBar.getText().toString().equalsIgnoreCase("Contact Us")
                || ApplicationData.titleBar.getText().toString().equalsIgnoreCase("FAQ")
                || ApplicationData.titleBar.getText().toString().equalsIgnoreCase("Terms and Conditions")
                ){
            datafragmentHelper.ReturnLastFragment();
        }
        */

            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("no", null).show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */
        /*
        if(id == R.id.action_write){
            Toast.makeText(getApplicationContext(), "Write post action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }
        */


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case MENU:
                fragment = new FragmentMainMenu();
                //title = getString(R.string.app_name);
                title = "delihome";
                break;
            case HISTORI_PESANAN:
                fragment = new FragmentPesanan();
                //title = getString(R.string.app_name);
                title = "Histori Pesanan";
                break;

            case ALL_MENU:
                fragment = new FragmentAllMenus();
                //title = getString(R.string.app_name);
                title = "All Menu";
                break;

            case WISHLIST:
                fragment = new FragmentWishlist();
                //title = getString(R.string.app_name);
                title = "Wishlist";
                break;
            case PROMO:
                fragment = new FragmentPromo();
                //title = getString(R.string.app_name);
                title = "Promo";
                break;
            case BANTUAN:
                fragment = new FragmentBantuan();
                //title = getString(R.string.app_name);
                title = "Bantuan";
                break;
            case CUSTOMER_SERVICE:
                fragment = new FragmentBantuan();
                //title = getString(R.string.app_name);
                title = "Bantuan";
                break;

            default:
                break;
        }


        if (fragment != null ) {
            /*
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            */

            FragmentManager fragmentManager = getSupportFragmentManager();

            datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
            datafragmentHelper.ChangeFragment(fragment);

            /*
            fragmentManager.beginTransaction()
                    .replace(R.id.container_body, fragment)
                    .commit();
            */
            // set the toolbar title
            titleBar.setText(title);
        }


    }





    @Override
    public void onResume() {
        super.onResume();
        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(Main.this).registerReceiver(updateCart,
                new IntentFilter("updateCart"));

    }

    @Override
    public void onPause() {
        // Unregister since the activity is not visible
        super.onPause();
    }

    private void SendBroadcast(String typeBroadcast,String type){
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }






}
