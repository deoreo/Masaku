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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.DialogManager;
import twiscode.masakuuser.Utilities.NetworkManager;

import static android.view.View.GONE;

/**
 * Created by User on 11/18/2015.
 */
public class ActivityChangeLocation extends FragmentActivity implements GoogleMap.OnMapClickListener {

    private GoogleMap googleMap;
    private String strDetail;
    private final String TAG = "ActivityChangeLocation";
    private Circle mCircle;
    private Marker markerCurrent,markerFrom;
    private CameraUpdate cameraUpdate;
    private EditText txtFrom;
    public static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_location);
        mActivity = this;
        txtFrom = (EditText) findViewById(R.id.txtFrom);
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        googleMap = fm.getMap();
        try {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.2577497, 112.7654645), 10f));
        } catch (Exception e) {

        }
        if (NetworkManager.getInstance(mActivity).isConnectedInternet()) {
            new GetMyLocation(mActivity, googleMap).execute();
        } else {
            DialogManager.showDialog(mActivity, "Mohon Maaf", "Anda tidak terhubung internet!");
        }
        googleMap.setOnMapClickListener(this);

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
        finish();
        super.onBackPressed();  // optional depending on your needs
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

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
                                        posFrom = new LatLng(latitude, longitude);
                                        ApplicationData.posFrom = posFrom;
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
                                        posFrom = new LatLng(latitude, longitude);
                                        ApplicationData.posFrom = posFrom;
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

}
