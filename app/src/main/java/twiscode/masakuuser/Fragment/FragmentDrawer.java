package twiscode.masakuuser.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityLogin;
import twiscode.masakuuser.Adapter.NavigationDrawerAdapter;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelNavDrawer;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.DataFragmentHelper;
import twiscode.masakuuser.Utilities.NetworkManager;
import twiscode.masakuuser.Utilities.PersistenceDataHelper;



public class FragmentDrawer extends android.support.v4.app.Fragment {
    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;
    private RoundedImageView profic;
    private ImageView toggle,gotoProfile;
    private TextView btnLogout,drawerName;
    private ApplicationManager appManager;
    private DatabaseHandler db;
    private DataFragmentHelper datafragmentHelper = PersistenceDataHelper.GetInstance().FragmentHelper;
    private Dialog dialog;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<ModelNavDrawer> getData() {
        List<ModelNavDrawer> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            ModelNavDrawer navItem = new ModelNavDrawer();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
        db = new DatabaseHandler(getActivity());
        JSONControl jsonControl = new JSONControl();
        try {
            jsonControl.postDeviceToken(ApplicationData.PARSE_DEVICE_TOKEN, ApplicationManager.getInstance(getActivity()).getUserToken());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.listMenu);
        drawerName = (TextView) layout.findViewById(R.id.drawerUsername);
        toggle = (ImageView) layout.findViewById(R.id.drawer_toggle_inside);
        gotoProfile = (ImageView) layout.findViewById(R.id.gotoProfile);
        btnLogout = (TextView) layout.findViewById(R.id.btnLogout);
        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
       toggle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mDrawerLayout.closeDrawer(containerView);
           }
       });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDrawerLayout.closeDrawer(containerView);
                android.support.v4.app.Fragment fragment = null;
                fragment = new FragmentProfile();
                FragmentManager fragmentManager = getFragmentManager();

                datafragmentHelper.SetDataFragmentHelper(fragment, fragmentManager);
                datafragmentHelper.ChangeFragment(fragment);
               /*
                fragmentManager.beginTransaction()
                        .replace(R.id.container_body, fragment)
                        .commit();
                */

                // set the toolbar title
                ApplicationData.titleBar.setText("Profile");
                mDrawerLayout.closeDrawer(containerView);
            }
        });
        SetupProfile();
        InitDialog();
        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        RoundedImageView bmImage;
        String url;

        public DownloadImageTask(RoundedImageView bmImage, String url) {
            this.bmImage = bmImage;
            this.url = url;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = url;
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    void SetupProfile(){
        if(ApplicationData.name != ""){
            drawerName.setText(ApplicationData.name);
        }

    }

    void InitDialog(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_logout);
        dialog.setTitle("Logout");

        // set the custom dialog components - text, image and button

        TextView logout = (TextView) dialog.findViewById(R.id.btnLogoutPop);
        TextView device = (TextView) dialog.findViewById(R.id.btnLogoutAllPop);
        TextView cancel = (TextView) dialog.findViewById(R.id.btnCancelPop);
        // if button is clicked, close the custom dialog
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationData.cart = new HashMap<String, ModelCart>();
                db.logout();
                Intent i = new Intent(getActivity(), ActivityLogin.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                getActivity().finish();
            }
        });

        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkManager.getInstance(getActivity()).isConnectedInternet()) {
                    //InitProgress();
                    //new DoLogoutAll(getActivity()).execute();
                    ApplicationData.cart = new HashMap<String, ModelCart>();
                    db.logout();
                    Intent i = new Intent(getActivity(), ActivityLogin.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        });

    }

    public void onResume() {
        super.onResume();

        SetupProfile();

    }

}
