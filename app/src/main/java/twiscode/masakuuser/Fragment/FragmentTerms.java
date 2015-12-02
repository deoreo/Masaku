package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

public class FragmentTerms extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private TextView txtCopyright,txtGuarantee,txtTerms,txtPrivacy,txtDisclaimer;

	public static FragmentTerms newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentTerms fragment = new FragmentTerms();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_terms, container, false);

		txtCopyright = (TextView) rootView.findViewById(R.id.txtCopyright);
		txtTerms = (TextView) rootView.findViewById(R.id.txtTerms);
		txtGuarantee = (TextView) rootView.findViewById(R.id.txtGuarantee);
		txtPrivacy = (TextView) rootView.findViewById(R.id.txtPrivacy);
		txtDisclaimer = (TextView) rootView.findViewById(R.id.txtDisclaimer);

		txtCopyright.setText(Html.fromHtml(getResources().getString(R.string.contentCopyright)));
		txtTerms.setText(Html.fromHtml(getResources().getString(R.string.contentTerms)));
		txtGuarantee.setText(Html.fromHtml(getResources().getString(R.string.contentGuarantee)));
		txtPrivacy.setText(Html.fromHtml(getResources().getString(R.string.contentPrivacy)));
		txtDisclaimer.setText(Html.fromHtml(getResources().getString(R.string.contentDisclaimer)));

		ApplicationData.titleBar.setText("Terms and Conditions");

		return rootView;
	}


}
