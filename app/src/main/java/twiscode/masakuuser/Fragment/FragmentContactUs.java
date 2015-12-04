package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import twiscode.masakuuser.R;

public class FragmentContactUs extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

	private RecyclerView recyclerView;
	private RelativeLayout btnCallUs;
	private Button btnKirim;
	private EditText txtNama, txtFeedback;



	public static FragmentContactUs newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentContactUs fragment = new FragmentContactUs();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_contact, container, false);
		btnCallUs = (RelativeLayout) rootView.findViewById(R.id.btnCallUs);
		btnKirim = (Button) rootView.findViewById(R.id.btnKirim);
		txtFeedback = (EditText) rootView.findViewById(R.id.txtFeedback);
		txtNama = (EditText) rootView.findViewById(R.id.txtNama);
		btnCallUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent callIntent = new Intent(Intent.ACTION_DIAL);
				callIntent.setData(Uri.parse("tel:081234534488"));
				startActivity(callIntent);
			}
		});
		btnKirim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"sales@masaku.id"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
				i.putExtra(Intent.EXTRA_TEXT   , txtFeedback.getText().toString()+"\nBest regards\n"+txtNama.getText().toString());
				try {
					startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(getActivity(), "Tidak ada email application terinstal", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return rootView;
	}


}
