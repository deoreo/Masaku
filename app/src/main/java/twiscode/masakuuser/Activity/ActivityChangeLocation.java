package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import twiscode.masakuuser.Adapter.AdapterSuggestion;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Model.ModelGeocode;
import twiscode.masakuuser.Model.ModelPlace;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ConfigManager;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.GoogleAPIManager;
import twiscode.masakuuser.Utilities.NetworkManager;
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
    private DatabaseHandler db;
    //private Circle mCircle;
    //public static Marker markerTemp;
    //public static Boolean mTouchMap = false;
    //private Marker markerCurrent,markerFrom;
    private CameraUpdate cameraUpdate;
    private AutoCompleteTextView txtFrom;
    private ImageView btnCurrent, btnBack;
    private RelativeLayout btnGunakan;
    private static Activity mActivity;
    private RelativeLayout layoutSuggestion;
    //private LatLng posFrom, posTemp, mapCenter;
    //public static LinearLayout layoutMarkerFrom;
    private Button btnLocationFrom;
    private TextView txtLocationFrom, upLocation;
    private ProgressBar progressMapFrom;
    public Boolean isSearchCurrent = false;
    private LinearLayout layoutfillForm;
    private ProgressBar progress;
    private FrameLayout itemCurrent;
    private ListView mListView;
    private List<ModelPlace> LIST_PLACE = null;
    private String description = "";
    private ModelPlace mPlace;
    Map<String, String> flurryParams = new HashMap<String, String>();
    private AdapterSuggestion mAdapter;
    private String strDetailFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);
        mActivity = this;
        //String[] maps = {"Android ", "java", "IOS", "SQL", "JDBC", "Web services"};
        db = new DatabaseHandler(mActivity);
        progress = (ProgressBar) findViewById(R.id.progress);
        txtFrom = (AutoCompleteTextView) findViewById(R.id.txtFrom);
        //btnCurrent = (ImageView) findViewById(R.id.btnCurrent);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnGunakan = (RelativeLayout) findViewById(R.id.btnGunakan);
        //layoutMarkerFrom = (LinearLayout) findViewById(R.id.layoutMarkerFrom);

        upLocation = (TextView) findViewById(R.id.txtLocation);
        layoutfillForm = (LinearLayout) findViewById(R.id.layoutfillForm);
        layoutSuggestion = (RelativeLayout) findViewById(R.id.layoutSuggestion);
        itemCurrent = (FrameLayout) findViewById(R.id.itemCurrent);
        mListView = (ListView) findViewById(R.id.lvSuggestion);
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

                    if (s.length() >= 3) {
                        new GetSuggestion(mActivity, s.toString()).execute();
                    } else if (s.length() == 0) {
                        layoutSuggestion.setVisibility(GONE);
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
        */
        btnGunakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ApplicationData.isFromMenu) {
                    ApplicationData.location = txtFrom.getText().toString();
                    Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
                    startActivity(j);
                    finish();
                }else{
                    ApplicationData.location = txtFrom.getText().toString();
                    Intent j = new Intent(getBaseContext(), Main.class);
                    startActivity(j);
                    finish();
                }
            }
        });
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


    public String getAddress(Activity activity, LatLng latlng) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        double lat = latlng.latitude;
        double lng = latlng.longitude;
        String addressLine = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            addressLine = obj.getAddressLine(0);
            strDetail = obj.getAddressLine(1) + " , " + obj.getAddressLine(2);
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return addressLine;
    }

    @Override
    public void onBackPressed() {
        if(!ApplicationData.isFromMenu) {
            ApplicationData.location = txtFrom.getText().toString();
            Intent j = new Intent(getBaseContext(), ActivityCheckout.class);
            startActivity(j);
            finish();
        }else{
            ApplicationData.location = txtFrom.getText().toString();
            Intent j = new Intent(getBaseContext(), Main.class);
            startActivity(j);
            finish();
        }


        super.onBackPressed();  // optional depending on your needs
    }

    /*
        @Override
        public void onMapClick(LatLng latLng) {
            float zoom = googleMap.getCameraPosition().zoom;
            if (zoom <= 15) {
                zoom = 15;
            }
                posTemp = latLng;
                markerTemp = googleMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_from)));
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
                googleMap.animateCamera(cameraUpdate, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        layoutMarkerFrom.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
        }
    */
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
            layoutSuggestion.setVisibility(VISIBLE);
            itemCurrent.setVisibility(GONE);
            mListView.setVisibility(GONE);
            mListView.setAdapter(null);
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
            mListView.setAdapter(mAdapter);
            mListView.setVisibility(VISIBLE);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        ModelPlace selectedPlace = LIST_PLACE.get(position);

                        txtFrom.setText(selectedPlace.getAddress());
                        strDetailFrom = selectedPlace.getAddressDetail();
                        String alamat = selectedPlace.getAddress() + "," + selectedPlace.getAddressDetail();

                        ModelGeocode geocode = GoogleAPIManager.geocode(description);
                        ApplicationData.posFrom = new LatLng(geocode.getLat(), geocode.getLon());


                        layoutSuggestion.setVisibility(GONE);
                        hideKeyboard();
                    } catch (Exception e) {
                        e.printStackTrace();
                        layoutSuggestion.setVisibility(GONE);
                        hideKeyboard();
                    }
                }
            });

        }
    }


    /*
     private class GetMyLocation extends AsyncTask<String, Void, String> implements LocationListener {
         private Activity activity;
         private Context context;
         private Resources resources;
         private double latitude, longitude;
         private GoogleMap googleMap;
         private LocationManager locationManager;
         private CircleOptions circleOptions;
         private Location location;
         private LatLng posFrom;


         public GetMyLocation(Activity activity, GoogleMap googleMap) {
             super();
             this.activity = activity;
             this.context = activity.getApplicationContext();
             this.resources = activity.getResources();
             this.googleMap = googleMap;
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             DialogManager.ShowLoading(activity, "Memuat lokasi anda. . .");

         }

         @Override
         protected String doInBackground(String... params) {
             Log.v(TAG, "GetMyLocation doInBackground");


                 try {
                     try {
                         int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
                         if (status != ConnectionResult.SUCCESS) {
                             int requestCode = 10;
                         } else {
                             locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                             boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                             boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                             Criteria criteria = new Criteria();
                             String provider = locationManager.getBestProvider(criteria, true);
                             location = locationManager.getLastKnownLocation(provider);
                             if (!isGPSEnabled && !isNetworkEnabled) {
                                 return "FAIL";
                             } else {
                                 if (isNetworkEnabled) {
                                     locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this, Looper.getMainLooper());
                                     Log.v("locationManager", "Network");
                                     if (locationManager != null) {
                                         location = locationManager
                                                 .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                         if (location != null) {
                                             latitude = location.getLatitude();
                                             longitude = location.getLongitude();

                                             if(ApplicationData.posFrom!=null && !isSearchCurrent){
                                                 posFrom = ApplicationData.posFrom;
                                             }
                                             else {
                                                 posFrom = new LatLng(latitude, longitude);
                                                 ApplicationData.posFrom = posFrom;
                                             }
                                         }
                                     }
                                 }
                                 if (isGPSEnabled) {
                                     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this, Looper.getMainLooper());
                                     Log.v("locationManager", "Network");
                                     if (locationManager != null) {
                                         location = locationManager
                                                 .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                         if (location != null) {
                                             latitude = location.getLatitude();
                                             longitude = location.getLongitude();
                                             if(ApplicationData.posFrom!=null && !isSearchCurrent){
                                                 posFrom = ApplicationData.posFrom;
                                             }
                                             else {
                                                 posFrom = new LatLng(latitude, longitude);
                                                 ApplicationData.posFrom = posFrom;
                                             }
                                         }
                                     }
                                 }

                             }
                         }

                     } catch (Exception e) {
                     }
                     return "OK";
                 } catch (Exception e) {
                     e.printStackTrace();
                 }

             return "FAIL";

         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
             Log.v("posisi gps", "onPost");

             switch (result) {
                 case "FAIL":
                     DialogManager.showDialog(activity, "Mohon Maaf", "Tidak dapat menemukan lokasi Anda!");
                     break;
                 case "OK":
                     try {
                         LatLng pFrom = ApplicationData.posFrom;
                         float zoom = googleMap.getCameraPosition().zoom;
                         if (zoom <= 15) {
                             zoom = 15;
                         }
                         double radiusInMeters = 5 * 100.0;
                         int strokeColor = 0xffffffff;
                         int shadeColor = 0x3366ffff;

                         if (mCircle != null) {
                             mCircle.remove();
                         }

                         if (markerCurrent != null) {
                             markerCurrent.remove();
                         }

                         if (markerFrom != null) {
                             markerFrom.remove();
                         }

                         circleOptions = new CircleOptions()
                                 .center(pFrom)
                                 .radius(radiusInMeters)
                                 .fillColor(shadeColor)
                                 .strokeColor(strokeColor)
                                 .strokeWidth(10);
                         mCircle = googleMap.addCircle(circleOptions);
                         cameraUpdate = CameraUpdateFactory.newLatLngZoom(pFrom, zoom);
                         //if (!isSearchCurrent) {
                         markerFrom = googleMap.addMarker(
                                 new MarkerOptions()
                                         .position(pFrom)
                                         .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_from)));
                         txtFrom.setText(getAddress(activity, posFrom));
                         googleMap.animateCamera(cameraUpdate);


                         Log.v("posisi gps", pFrom.toString());
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     break;

             }
             DialogManager.DismissLoading(activity);
         }

         @Override
         public void onLocationChanged(Location location) {

         }

         @Override
         public void onStatusChanged(String s, int i, Bundle bundle) {

         }

         @Override
         public void onProviderEnabled(String s) {

         }

         @Override
         public void onProviderDisabled(String s) {

         }
     }
 */
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

}
