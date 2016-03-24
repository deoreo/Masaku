package twiscode.masakuuser.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import twiscode.masakuuser.Adapter.AdapterFilterLokasi;
import twiscode.masakuuser.Model.ModelLokasi;
import twiscode.masakuuser.R;

public class ActivityFilterLokasi extends AppCompatActivity {
    private ListView mListLokasi;

    String[] data = new String[]{"Surabaya", "Malang", "Medan", "Makasar", "Semarang"};
    private AdapterFilterLokasi mFilterLokasi;
    private ArrayList<ModelLokasi> listLokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_lokasi);

        mListLokasi = (ListView) findViewById(R.id.listLokasi);
        listLokasi= new ArrayList<>();

        ModelLokasi lokasi = null;

        mFilterLokasi = new AdapterFilterLokasi(ActivityFilterLokasi.this, listLokasi);
        mListLokasi.setAdapter(mFilterLokasi);

        for (int i = 0; i < 5; i++){
            lokasi = new ModelLokasi();
            lokasi.setLokasi("adasdasd");
            Log.d("tes", data[i]);
            listLokasi.add(lokasi);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
