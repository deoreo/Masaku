package twiscode.masakuuser.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import twiscode.masakuuser.Activity.ActivityChangeLocation;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;

import static twiscode.masakuuser.Utilities.ApplicationData.jadwal;


/**
 * Created by Unity on 01/09/2015.
 */
public class FragmentCheckoutDelivery extends Fragment {
    private Activity act;
    private Button btnConfirm;
    private EditText txtNote;
    private TextView txtAlamat, txtTotal, txtTime;
    private LinearLayout btnAlamat;
    private TextView list_delivery;
    private ApplicationManager appManager;

    public static FragmentCheckoutDelivery newInstance() {
        FragmentCheckoutDelivery fragment = new FragmentCheckoutDelivery();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentCheckoutDelivery() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = null;
        try {
            v = inflater.inflate(R.layout.fragment_checkout_delivery, container, false);
            btnConfirm = (Button) v.findViewById(R.id.btnConfirm);
            btnAlamat = (LinearLayout) v.findViewById(R.id.btnAlamat);
            txtAlamat = (TextView) v.findViewById(R.id.alamatCheckout);
            txtNote = (EditText) v.findViewById(R.id.noteCheckout);
            txtTotal = (TextView) v.findViewById(R.id.txtTotal);
            list_delivery = (TextView) v.findViewById(R.id.list_delivery);
            appManager = new ApplicationManager(getActivity());
            String alamat = appManager.getAlamat();
            String total = ApplicationData.total;

            if (alamat != "") {
                txtAlamat.setText(alamat);
            }
            txtTotal.setText(total);
            String time = "";
            for(int i=0;i<ApplicationData.jadwal.size();i++){
                if(i==0){
                    list_delivery.append("- "+ApplicationData.jadwal.get(0) + "\n");
                }
                else if (i > 0 && !ApplicationData.jadwal.get(i).equalsIgnoreCase(ApplicationData.jadwal.get(i - 1))
                        && !list_delivery.getText().toString().contains(ApplicationData.jadwal.get(i)))
                {
                    list_delivery.append("- "+ApplicationData.jadwal.get(i) + "\n");
                }
            }

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendBroadcast("gotoPayment", "true");
                    ApplicationData.address = txtAlamat.getText().toString();
                    ApplicationData.note = txtNote.getText().toString();
                }
            });

            txtAlamat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplicationData.isFromCheckoutDelivery = true;
                    Intent i = new Intent(getActivity(), ActivityChangeLocation.class);
                    startActivity(i);
                    getActivity().finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }

    private void SendBroadcast(String typeBroadcast, String type) {
        Intent intent = new Intent(typeBroadcast);
        // add data
        intent.putExtra("message", type);
        LocalBroadcastManager.getInstance(act).sendBroadcast(intent);
    }
}
