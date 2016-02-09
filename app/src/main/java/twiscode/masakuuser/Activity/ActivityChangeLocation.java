package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Adapter.AdapterAlamat;
import twiscode.masakuuser.Adapter.AdapterSuggestion;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelAlamat;
import twiscode.masakuuser.Model.ModelGeocode;
import twiscode.masakuuser.Model.ModelPlace;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.GoogleAPIManager;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by User on 11/18/2015.
 */
public class ActivityChangeLocation extends FragmentActivity
        //implements GoogleMap.OnMapClickListener
{

    //private GoogleMap googleMap;
    private String strDetail;
    private final String TAG = "ActivityChangeLocation";
     //private Circle mCircle;
    //public static Marker markerTemp;
    //public static Boolean mTouchMap = false;
    //private Marker markerCurrent,markerFrom;
    private CameraUpdate cameraUpdate;
    private TextView txtFrom;
    private ImageView btnCurrent, btnBack;
    private RelativeLayout btnGunakan;
    private static Activity mActivity;
    private RelativeLayout layoutSuggestion, layoutRecent;
    //private LatLng posFrom, posTemp, mapCenter;
    //public static LinearLayout layoutMarkerFrom;
    private Button btnLocationFrom;
    private TextView txtLocationFrom, upLocation;
    private ProgressBar progressMapFrom;
    public Boolean isSearchCurrent = false;
    private LinearLayout layoutfillForm;
    private ProgressBar progress;
    private FrameLayout itemCurrent;
    private ListView lvSuggestion, lvRecent;
    private List<ModelPlace> LIST_PLACE = null;
    private String description = "";
    private ModelPlace mPlace;
    Map<String, String> flurryParams = new HashMap<String, String>();
    private AdapterSuggestion mAdapter;
    private String strDetailFrom;
    private DatabaseHandler db;
    private AdapterAlamat adapterAlamat;
    private BroadcastReceiver changeAlamat;
    private boolean isRecent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);
        mActivity = this;
        //String[] maps = {"Android ", "java", "IOS", "SQL", "JDBC", "Web services"};
        db = new DatabaseHandler(mActivity);
        progress = (ProgressBar) findViewById(R.id.progressSuggestion);
        txtFrom = (TextView) findViewById(R.id.txtFrom);
        //btnCurrent = (ImageView) findViewById(R.id.btnCurrent);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnGunakan = (RelativeLayout) findViewById(R.id.btnGunakan);
        //layoutMarkerFrom = (LinearLayout) findViewById(R.id.layoutMarkerFrom);

        upLocation = (TextView) findViewById(R.id.txtLocation);
        layoutfillForm = (LinearLayout) findViewById(R.id.layoutfillForm);
        layoutSuggestion = (RelativeLayout) findViewById(R.id.layoutSuggestion);
        layoutRecent = (RelativeLayout) findViewById(R.id.layoutRecent);
        itemCurrent = (FrameLayout) findViewById(R.id.itemCurrent);
        lvSuggestion = (ListView) findViewById(R.id.lvSuggestion);
        lvRecent = (ListView) findViewById(R.id.lvRecent);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, maps);
        //txtFrom.setAdapter(adapter);
        //txtFrom.setThreshold(1);
        //SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        /*googleMap = fm.getMap();
        try {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.2577497, 112.7654645), 10f));
        } catch (Exception e) {

        }
        if (NetworkManager.getInstance(mActivity).isConnectedInternet()) {
                new GetMyLocation(mActivity, googleMap).execute();
        } else {
            DialogManager.showDialog(mActivity, "Mohon Maaf", "Anda tidak terhubung internet!");
        }
*/

        changeAlamat = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Extract data included in the Intent
                Log.d("", "broadcast changeAlamat");
                isRecent = true;
                String message = intent.getStringExtra("message");
                String address = ApplicationData.address;
                txtFrom.setText(message);
                ApplicationManager.getInstance(mActivity).setAlamat(message);
                new GetGeocode(mActivity, address).execute();
                if(!ApplicationData.isFromMenu) {
                    Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
                    startActivity(j);
                    finish();
                }else{
                    Intent j = new Intent(getBaseContext(), Main.class);
                    startActivity(j);
                    finish();
                }

            }
        };

        LIST_PLACE = db.loadPlace();
        adapterAlamat = new AdapterAlamat(mActivity, LIST_PLACE);
        lvRecent.setAdapter(adapterAlamat);
        upLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layoutfillForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtFrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isRecent = false;
                return false;
            }
        });

        txtFrom.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (s.length() >= 3 && !isRecent) {
                    new GetSuggestion(mActivity, s.toString()).execute();
                } else if (s.length() == 0) {
                    layoutSuggestion.setVisibility(GONE);
                    layoutRecent.setVisibility(VISIBLE);
                }


            }
        });


/*
        btnCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "btnCurrent click");
                isSearchCurrent = true;

                if (NetworkManager.getInstance(mActivity).isConnectedInternet()) {
                    new GetMyLocation(mActivity, googleMap).execute();
                } else {
                    DialogManager.showDialog(mActivity, "Mohon Maaf", "Anda tidak terhubung internet!");
                }
            }
        });

        btnGunakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txtFrom.getText().toString().isEmpty()) {


                    if (!ApplicationData.isFromMenu) {
                        ApplicationData.location = txtFrom.getText().toString();
                        Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
                        startActivity(j);
                        finish();
                    } else {
                        ApplicationData.location = txtFrom.getText().toString();
                        Intent j = new Intent(getBaseContext(), Main.class);
                        startActivity(j);
                        finish();
                    }
                }
                else{

                }
            }
        });
        */
        /*
        btnLocationFrom.setOnClickListener(new View.OnClickListener()

                                           {
                                               @Override
                                               public void onClick(View v) {
                                                   posFrom = posTemp;

                                                   if (markerFrom != null) {
                                                       markerFrom.remove();
                                                   }
                                                   if (markerTemp != null) {
                                                       markerTemp.remove();
                                                   }

                                                   layoutMarkerFrom.setVisibility(GONE);
                                                   String address = getAddress(mActivity, posFrom);
                                                   txtFrom.setText(address);
                                                   ApplicationData.posFrom = posFrom;

                                                   markerFrom = googleMap.addMarker(
                                                           new MarkerOptions()
                                                                   .position(posFrom)
                                                                   .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_from)));

                                               }
                                           }

        );
        googleMap.setOnMapClickListener(this);
        googleMap.setOnCameraChangeListener
                (new GoogleMap.OnCameraChangeListener() {
                     @Override
                     public void onCameraChange(CameraPosition cameraPosition) {
                         LatLngBounds bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
                         if (layoutMarkerFrom.getVisibility() == VISIBLE) {
                             try {
                                 if (NetworkManager.getInstance(mActivity).isConnectedInternet()) {
                                     mapCenter = googleMap.getCameraPosition().target;
                                     String address = getAddress(mActivity, mapCenter);
                                     txtLocationFrom.setText(address);
                                     progressMapFrom.setVisibility(View.GONE);
                                 } else {
                                     layoutMarkerFrom.setVisibility(GONE);
                                     DialogManager.showDialog(mActivity, "Mohon Maaf", "Anda tidak terhubung internet!");
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }

                     }
                 }

                );
                */

    }



    @Override
    public void onBackPressed() {
        if(!ApplicationData.isFromMenu) {
            Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
            startActivity(j);
            finish();
        }else{
            Intent j = new Intent(getBaseContext(), Main.class);
            startActivity(j);
            finish();
        }


        super.onBackPressed();  // optional depending on your needs
    }


    public static void hideKeyboard() {
        View view = mActivity.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            //layoutSuggestion.setVisibility(GONE);
            //handler.removeCallbacks(delayedAction);
        }

    }

    public class GetSuggestion extends AsyncTask<String, Void, JSONArray> {

        String address, tag;
        Activity activity;
        LatLng latLng;
        public GetSuggestion(Activity activity, String address) {
            this.address = address;
            this.tag = tag;
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
            progress.setVisibility(VISIBLE);
            layoutSuggestion.setVisibility(VISIBLE);
            layoutRecent.setVisibility(GONE);
            itemCurrent.setVisibility(GONE);
            lvSuggestion.setVisibility(GONE);
            lvSuggestion.setAdapter(null);
        }

        @Override
        protected JSONArray doInBackground(String... arg) {
            JSONArray json = null;
            LIST_PLACE = new ArrayList<ModelPlace>();
            JSONControl JSONControl = new JSONControl();
            try {
                json = JSONControl.listPlace(address);
            } catch (Exception e) {
            }
            if (json != null) {
                for (int i = 0; i < json.length(); i++) {
                    String id = "";
                    description = "";
                    address = "";
                    String detail = "";
                    boolean status = true;
                    try {
                        JSONObject jsonObject = json.getJSONObject(i);
                        id = jsonObject.getString("place_id");
                        description = jsonObject.getString("description");
                        String[] descSplit = description.split(",");
                        address = descSplit[0];
                        detail = descSplit[1] + "," + descSplit[2];
                        status = true;

                    } catch (JSONException e) {
                    } catch (Exception e) {

                    }

                    if (status) {
                        mPlace = new ModelPlace(id, address, detail);
                        LIST_PLACE.add(mPlace);
                    }
                }
                try {
                    mAdapter = new AdapterSuggestion(activity, LIST_PLACE);
                } catch (NullPointerException e) {
                }
            } else {
                LIST_PLACE = null;
            }

            return json;
        }

        @Override
        protected void onPostExecute(final JSONArray json) {
            // TODO Auto-generated method stub
            super.onPostExecute(json);
            progress.setVisibility(GONE);
            lvSuggestion.setAdapter(mAdapter);
            lvSuggestion.setVisibility(VISIBLE);
            lvSuggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ModelPlace selectedPlace = LIST_PLACE.get(position);

                        txtFrom.setText(selectedPlace.getAddress());
                        strDetailFrom = selectedPlace.getAddressDetail();
                        String alamat = selectedPlace.getAddress() + "," + selectedPlace.getAddressDetail();

                        new GetGeocode(activity,description).execute();

                        layoutSuggestion.setVisibility(GONE);
                        layoutRecent.setVisibility(VISIBLE);

                        ApplicationManager.getInstance(activity).setAlamat(selectedPlace.getAddress());

                        db.insertPlace(selectedPlace);

                        hideKeyboard();


                    } catch (Exception e) {
                        layoutSuggestion.setVisibility(GONE);
                        layoutRecent.setVisibility(VISIBLE);

                        hideKeyboard();
                    }
                    if(!ApplicationData.isFromMenu) {
                        Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
                        startActivity(j);
                        finish();
                    }else{
                        Intent j = new Intent(getBaseContext(), Main.class);
                        startActivity(j);
                        finish();
                    }
                }
            });

        }
    }

    public class GetGeocode extends AsyncTask<String, Void, ModelGeocode> {

        String address, tag;
        Activity activity;
        public GetGeocode(Activity activity, String address) {
            this.address = address;
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ModelGeocode doInBackground(String... arg) {
            ModelGeocode modelGeocode = GoogleAPIManager.geocode(address);

            return modelGeocode;
        }

        @Override
        protected void onPostExecute(final ModelGeocode modelGeocode) {
            // TODO Auto-generated method stub
            super.onPostExecute(modelGeocode);
            ApplicationManager.getInstance(activity).setGeocode(new LatLng(modelGeocode.getLat(), modelGeocode.getLon()));
            //ApplicationData.posFrom = new LatLng(modelGeocode.getLat(), modelGeocode.getLon());
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("CHECKOUT_LOCATION", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("CHECKOUT_LOCATION");
        FlurryAgent.onEndSession(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(ActivityChangeLocation.this).registerReceiver(changeAlamat,
                new IntentFilter("changeAlamat"));

    }

}
