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


/**
 * Created by Unity on 01/09/2015.
 */
public class FragmentTutorial_4_2 extends Fragment {
    private static final String ARG_COLOR = "color";
    ProgressBar progressBar;
    private String mColor;

    public static FragmentTutorial_4_2 newInstance(String param1) {
        FragmentTutorial_4_2 fragment = new FragmentTutorial_4_2();
        Bundle args = new Bundle();
        args.putString(ARG_COLOR, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTutorial_4_2() {

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
        View v = inflater.inflate(R.layout.tutorial_slider_fragment_4_2, container, false);
        Button btn = (Button)  v.findViewById(R.id.btnMenu);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }
}
