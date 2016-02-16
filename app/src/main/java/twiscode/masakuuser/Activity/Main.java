package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonParser;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Fragment.FragmentAllMenus;
import twiscode.masakuuser.Fragment.FragmentBantuan;
import twiscode.masakuuser.Fragment.FragmentDrawer;
import twiscode.masakuuser.Fragment.FragmentMainMenu;
import twiscode.masakuuser.Fragment.FragmentNotif;
import twiscode.masakuuser.Fragment.FragmentPesanan;
import twiscode.masakuuser.Fragment.FragmentPromo;
import twiscode.masakuuser.Fragment.FragmentWishlist;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DataFragmentHelper;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.PersistenceDataHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class Main extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private ApplicationManager appManager;
    private ActionBar actionBar;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private DataFragmentHelper datafragmentHelper = PersistenceDataHelper.GetInstance().FragmentHelper;
    private TextView titleBar;
    private TextView countCart;
    private EditText txtEmail;
    private LinearLayout wrapCount;
    private ImageView btnCart;
    private RelativeLayout wrapCart, wishlistEmpty, wishlistFull, foodDatabase;
    private final int MENU = 0, HISTORI_PESANAN = 1, ALL_MENU = 2, NOTIFICATION = 3, PROMO = 4, BANTUAN = 5, CUSTOMER_SERVICE = 6, WISHLIST = 7;

    private BroadcastReceiver updateCart, doWishlistFull, gotoDiscover, emptyWishlist;
    private Dialog dialogEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appManager = new ApplicationManager(Main.this);
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
        wrapCart = (RelativeLayout) mCustomView.findViewById(R.id.wrapCart);
        wishlistEmpty = (RelativeLayout) mCustomView.findViewById(R.id.wishlistEmpty);
        wishlistFull = (RelativeLayout) mCustomView.findViewById(R.id.wishlistFull);
        foodDatabase = (RelativeLayout) mCustomView.findViewById(R.id.foodDatabase);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        ApplicationData.titleBar = titleBar;

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer); //getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationData.isFromMenu = false;
                if (ApplicationData.cart.size() > 0) {
                    Intent i = new Intent(Main.this, ActivityCheckout.class);
                    startActivity(i);
                    finish();
                } else {
                    DialogManager.showDialog(Main.this, "Mohon Maaf", "Anda belum memiliki pesanan");
                }
            }
        });
        foodDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(ALL_MENU);
            }
        });
        if (ApplicationData.cart.size() > 0) {
            List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
            int jml = 0;
            for (int i = 0; i < list.size(); i++) {
                jml = jml + list.get(i).getJumlah();
            }
            countCart.setText("" + jml);
            wrapCount.setVisibility(VISIBLE);
        } else {
            wrapCount.setVisibility(GONE);
        }

        updateCart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast updateCart");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    List<ModelCart> list = new ArrayList<ModelCart>(ApplicationData.cart.values());
                    if (list.size() > 0) {
                        int jml = 0;
                        for (int i = 0; i < list.size(); i++) {
                            jml = jml + list.get(i).getJumlah();
                        }
                        countCart.setText("" + jml);
                        wrapCount.setVisibility(VISIBLE);
                    } else {
                        wrapCount.setVisibility(GONE);
                    }

                }

                SendBroadcast("cekOrderNow", "true");


            }
        };

        doWishlistFull = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast wishlistFull");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    wishlistEmpty.setVisibility(GONE);
                    wishlistFull.setVisibility(VISIBLE);

                } else {
                    wishlistEmpty.setVisibility(VISIBLE);
                    wishlistFull.setVisibility(GONE);

                }

            }
        };

        gotoDiscover = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast gotoDiscover");
                String message = intent.getStringExtra("message");
                if (message.equals("true")) {
                    displayView(ALL_MENU);

                }

            }
        };

        wishlistEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(WISHLIST);
            }
        });
        wishlistFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayView(WISHLIST);
            }
        });

        new GetPromoHint(Main.this).execute();
        InitDialogEmail();
        if(!ApplicationData.isNotif) {
            displayView(0);
        }else{
            displayView(NOTIFICATION);
            ApplicationData.hasEmail = false;
        }


        if (!ApplicationData.hasEmail) {
            dialogEmail.show();
        }


    }

    @Override
    public void onBackPressed() {

        Log.d("counter stack", Integer.toString(getFragmentManager().getBackStackEntryCount()));
        //if(ApplicationData.titleBar.getText().toString().equalsIgnoreCase("Profile")){
        //    displayView(MENU);
        if (ApplicationData.isProfile) {
            datafragmentHelper.ReturnLastFragment();
            ApplicationData.isProfile = false;
        } else {
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
                wrapCart.setVisibility(VISIBLE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);
                break;
            case HISTORI_PESANAN:
                fragment = new FragmentPesanan();
                //title = getString(R.string.app_name);
                title = "History";
                wrapCart.setVisibility(VISIBLE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);
                break;

            case ALL_MENU:
                if (!ApplicationData.hasEmail) {
                    dialogEmail.show();
                }
                fragment = new FragmentAllMenus();
                //title = getString(R.string.app_name);
                title = "Discover";
                wrapCart.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);

                if (appManager.getWishlist() <= 0) {
                    wishlistEmpty.setVisibility(VISIBLE);
                    wishlistFull.setVisibility(GONE);
                } else if (appManager.getWishlist() > 0) {
                    wishlistEmpty.setVisibility(GONE);
                    wishlistFull.setVisibility(VISIBLE);
                }
                break;

            case WISHLIST:
                fragment = new FragmentWishlist();
                //title = getString(R.string.app_name);
                title = "Wishlist";
                wrapCart.setVisibility(GONE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(VISIBLE);
                break;

            case NOTIFICATION:
                fragment = new FragmentNotif();
                //title = getString(R.string.app_name);
                title = "Notification";
                wrapCart.setVisibility(VISIBLE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);
                break;

            case PROMO:
                fragment = new FragmentPromo();
                //title = getString(R.string.app_name);
                title = "Promotion";
                wrapCart.setVisibility(VISIBLE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);
                break;
            case BANTUAN:
                fragment = new FragmentBantuan();
                //title = getString(R.string.app_name);
                title = "Help";
                wrapCart.setVisibility(VISIBLE);
                wishlistEmpty.setVisibility(GONE);
                wishlistFull.setVisibility(GONE);
                foodDatabase.setVisibility(GONE);
                break;
            case CUSTOMER_SERVICE:
                if (!ApplicationData.hasEmail) {
                    dialogEmail.show();
                } else {
                    startActivity(new Intent(Main.this, ZopimChatActivity.class));
                    //fragment = new FragmentCustomerService();
                    //title = getString(R.string.app_name);
                    title = "Customer Service";
                    wrapCart.setVisibility(VISIBLE);
                    wishlistEmpty.setVisibility(GONE);
                    wishlistFull.setVisibility(GONE);
                    foodDatabase.setVisibility(GONE);
                }
                break;

            default:
                break;
        }


        if (fragment != null) {
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

    private void InitDialogEmail(){
        dialogEmail = new Dialog(Main.this);
        dialogEmail.setContentView(R.layout.popup_email);
        dialogEmail.setTitle("Update Email");

        // set the custom dialog components - text, image and button

        txtEmail = (EditText) dialogEmail.findViewById(R.id.txtEmail);
        Button btnDone = (Button) dialogEmail.findViewById(R.id.btnDone);
        Button btnNotNow = (Button) dialogEmail.findViewById(R.id.btnNotNow);

        // if button is clicked, close the custom dialog
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.email = txtEmail.getText().toString();
                String email = txtEmail.getText().toString();
                if (email.isEmpty()) {
                    DialogManager.showDialog(Main.this, "Mohon Maaf", "Silahkan mengisi email Anda!");
                }
                else if (!email.trim().contains("@") || !email.trim().contains(".")) {
                    DialogManager.showDialog(Main.this, "Mohon Maaf", "Format email Anda salah!");
                }else {
                    new UpdateEmail(Main.this).execute(txtEmail.getText().toString());
                }
            }
        });

        btnNotNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEmail.dismiss();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(Main.this).registerReceiver(updateCart,
                new IntentFilter("updateCart"));
        LocalBroadcastManager.getInstance(Main.this).registerReceiver(doWishlistFull,
                new IntentFilter("wishlistFull"));
        LocalBroadcastManager.getInstance(Main.this).registerReceiver(gotoDiscover,
                new IntentFilter("gotoDiscover"));


    }

    @Override
    public void onPause() {
        // Unregister since the activity is not visible
        super.onPause();
    }

    private void SendBroadcast(String typeBroadcast, String type) {
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    private class UpdateEmail extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private ProgressDialog progressDialog;
        private String msg = "Email sudah digunakan";
        private String response;
        private JSONObject jsonObj;
        //private String messageError,messageSuccess;

        public UpdateEmail(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Save your email. . .");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                String email = params[0];
                //String param = params[3];
                JSONControl jsControl = new JSONControl();
                response = jsControl.updateProfile(email,"email", appManager.getUserToken());
                Log.d("json response", response.toString());
                if (response.contains("true")) {
                    ModelUser modelUser = appManager.getUser();

                    modelUser.setEmail(email);
                    ApplicationData.email = email;

                    appManager.setUser(modelUser);

                    return "OK";
                }else{
                    try {
                        response.replace("\n", "");
                        response.replaceAll(".*\".*", "\\\"");
                        jsonObj = new JSONObject(response);
                        msg = jsonObj.getString("message");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();

            }

            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            switch (result) {
                case "FAIL":
                    DialogManager.showDialog(activity, "Mohon Maaf", msg);
                    break;
                case "OK":
                    DialogManager.showDialog(activity, "Info", "Berhasil update email");
                    ApplicationData.hasEmail = true;
                    VisitorInfo visitorData = new VisitorInfo.Builder()
                            .name(ApplicationManager.getInstance(activity).getUser().getNama())
                            .email(ApplicationManager.getInstance(activity).getUser().getEmail())
                            .phoneNumber(ApplicationManager.getInstance(activity).getUser().getPonsel())
                            .build();
                    ZopimChat.setVisitorInfo(visitorData);
                    dialogEmail.dismiss();
                    break;
            }
            progressDialog.dismiss();

        }


    }

    private class GetPromoHint extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;
        private String msg;

        public GetPromoHint(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                JSONControl jsControl = new JSONControl();
                JSONObject response = jsControl.getInit();
                Log.d("json response init", response.toString());
                String notice =  response.getString("notice");
                String couponHint = response.getString("couponHint");
                ApplicationData.notice = notice;
                ApplicationData.couponHint = couponHint;
            } catch (Exception e) {
                e.printStackTrace();
                return "FAIL";
            }
            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            switch (result) {
                case "FAIL":
                    break;
                case "OK":

                    break;
            }
            //progressDialog.dismiss();

        }


    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
