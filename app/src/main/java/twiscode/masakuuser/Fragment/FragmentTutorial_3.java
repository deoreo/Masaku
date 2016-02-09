package twiscode.masakuuser.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import twiscode.masakuuser.Activity.Main;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;


/**
 * Created by Unity on 01/09/2015.
 */
public class FragmentTutorial_3 extends Fragment {
    private static final String ARG_COLOR = "color";
    ProgressBar progressBar;
    private String mColor;

    public static FragmentTutorial_3 newInstance(String param1) {
        FragmentTutorial_3 fragment = new FragmentTutorial_3();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTutorial_3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColor = getArguments().getString(ARG_COLOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tutorial_slider_fragment_3, container, false);
        Button btn = (Button) v.findViewById(R.id.btnMenu);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ApplicationData.isHelp) {
                    getActivity().finish();
                }else if(!ApplicationData.isHelp){
                    Intent i = new Intent(getActivity(), Main.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    getActivity().finish();
                }
                ApplicationData.isHelp = false;
            }
        });

        return v;
    }
}
