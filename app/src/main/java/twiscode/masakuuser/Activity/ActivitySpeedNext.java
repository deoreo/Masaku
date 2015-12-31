package twiscode.masakuuser.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.flurry.android.FlurryAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twiscode.masakuuser.Adapter.AdapterMenuNew;
import twiscode.masakuuser.Adapter.AdapterMenuNext;
import twiscode.masakuuser.Control.JSONControl;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;
import twiscode.masakuuser.Utilities.ConfigManager;

/**
 * Created by TwisCode-02 on 10/26/2015.
 */
public class ActivitySpeedNext extends AppCompatActivity {

    private TextView countCart;
    private LinearLayout wrapCount;
    private ImageView btnBack,btnCart;
    private PullRefreshLayout mSwipeRefreshLayout,mSwipeRefreshLayoutNoData;
    private ListView mListView;
    private List<ModelMenuSpeed> LIST_MENU = new ArrayList<>();
    int page =1;
    boolean isNodata = false;
    private AdapterMenuNext mAdapter;
    private HashMap<String,ModelMenuSpeed> speedmenu = new HashMap<>();
    private ProgressBar progress;
    ApplicationManager applicationManager;

    Map<String, String> flurryParams = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antar_cepat_next);

        applicationManager = new ApplicationManager(ActivitySpeedNext.this);
        progress = (ProgressBar) findViewById(R.id.progress);
        wrapCount = (LinearLayout) findViewById(R.id.wrapCount);
        countCart = (TextView) findViewById(R.id.countCart);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        mListView = (ListView) findViewById(R.id.list_delivery);
        mSwipeRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
        //mSwipeRefreshLayoutNoData = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayoutNoData);
        //mSwipeRefreshLayoutNoData.setRefreshStyle(PullRefreshLayout.STYLE_RING);
        mListView.setScrollingCacheEnabled(false);
        mSwipeRefreshLayout.setRefreshing(false);
        //mSwipeRefreshLayoutNoData.setRefreshing(false);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //isNodata = false;
                DummyData();
            }
        });
/*
        mSwipeRefreshLayoutNoData.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isNodata = true;
                DummyData(isNodata);
            }
        });


/*









        DummyData(isNodata);
    */
        DummyData();





    }

    private void DummyData(){
        //isNodata = nodata;
        LIST_MENU = new ArrayList<ModelMenuSpeed>();
        String p = Integer.toString(page);
		/*
		ModelMenu modelDeliver0 = new ModelMenu("0", "Pecel Mak Yem", "8.000", "https://upload.wikimedia.org/wikipedia/commons/a/a1/Pecel_Solo.JPG", "11");
		LIST_MENU.add(modelDeliver0);
		ModelMenu modelDeliver1 = new ModelMenu("0", "Soto Spesial Bu Winda", "14.000", "http://blog.travelio.com/wp-content/uploads/2015/03/Soto-Lamongan-Jawa-Timur-Indonesia.jpg", "15");
		LIST_MENU.add(modelDeliver1);
		*/

        new GetMenuSpeed(ActivitySpeedNext.this).execute(
                p
        );


    }

    private class GetMenuSpeed extends AsyncTask<String, Void, String> {
        private Activity activity;
        private Context context;
        private Resources resources;


        public GetMenuSpeed(Activity activity) {
            super();
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.resources = activity.getResources();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            if(isNodata){
                mSwipeRefreshLayoutNoData.setRefreshing(true);
            }
            else {
                mSwipeRefreshLayout.setRefreshing(true);
            }
            */
            mSwipeRefreshLayout.setRefreshing(true);

            //
			/*
			progressDialog = new ProgressDialog(activity);
			progressDialog.setMessage("Loading. . .");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
			*/
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                int page = Integer.parseInt(params[0]);
                JSONControl jsControl = new JSONControl();
                JSONObject response = jsControl.getMenuSpeedNext(page,applicationManager.getUserToken());
                Log.d("json response", response.toString());
                JSONArray menus = response.getJSONArray("menus");
                if(menus.length() > 0){
                    for(int i=0;i<menus.length();i++){
                        String id = menus.getJSONObject(i).getString("_id");
                        String nama = menus.getJSONObject(i).getString("name");
                        String foto = menus.getJSONObject(i).getJSONArray("imageUrls").getString(0);
                        String price = menus.getJSONObject(i).getString("price");
                        String desc = menus.getJSONObject(i).getString("description");
                        String time = menus.getJSONObject(i).getJSONObject("speed").getString("waitingTime");
                        JSONArray feedback = new JSONArray();//menus.getJSONObject(i).getJSONArray("feedbacks");
                        try{
                            feedback = menus.getJSONObject(i).getJSONArray("feedbacks");
                        }
                        catch (Exception ex){
                            feedback = new JSONArray();
                        }
                        JSONArray category = new JSONArray();
                        try{
                            category = menus.getJSONObject(i).getJSONArray("category");
                        }
                        catch (Exception ex){
                            category = new JSONArray();
                        }
                        String hashtag = "";
                        if(category.length() > 0){
                            for(int j=0;j<category.length();j++){
                                if(j==0){
                                    hashtag = "#"+category.getString(j);
                                }
                                else {
                                    hashtag = hashtag+" #"+category.getString(j);
                                }
                            }
                            Log.d("hashtag",hashtag);
                        }
                        ModelMenuSpeed menu = new ModelMenuSpeed(id,nama,price,foto,time,desc,feedback,hashtag);
                        //LIST_MENU.add(menu);
                        if(speedmenu.size() > 0){
                            if(!speedmenu.containsKey(id)){
                                speedmenu.put(id,menu);
                            }
                        }
                        else {
                            speedmenu.put(id,menu);
                        }
                        LIST_MENU = new ArrayList<>(speedmenu.values());
                    }

                    return "OK";
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("json response id 0", "FAIL");
            return "FAIL";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            //progressDialog.dismiss();
            progress.setVisibility(View.GONE);
            switch (result) {
                case "FAIL":
                    //DialogManager.showDialog(activity, "Mohon maaf", "Nomor ponsel Anda belum terdaftar!");

                    break;
                case "OK":
                    //Intent i = new Intent(getBaseContext(), ActivityHome.class);
                    //startActivity(i);
                    //finish();
                    Log.d("jumlah menu : ",""+LIST_MENU.size());
                    mAdapter = new AdapterMenuNext(ActivitySpeedNext.this, LIST_MENU);
                    mListView.setAdapter(mAdapter);

                    break;

            }
            mAdapter = new AdapterMenuNext(ActivitySpeedNext.this, LIST_MENU);
            mListView.setAdapter(mAdapter);
            mSwipeRefreshLayout.setRefreshing(false);

            //mSwipeRefreshLayoutNoData.setRefreshing(false);

        }
    }

    public void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, ConfigManager.FLURRY_API_KEY);
        FlurryAgent.logEvent("MENU_SPEED_NEXT", flurryParams, true);
    }

    public void onStop() {
        super.onStop();
        FlurryAgent.endTimedEvent("MENU_SPEED_NEXT");
        FlurryAgent.onEndSession(this);
    }

}
