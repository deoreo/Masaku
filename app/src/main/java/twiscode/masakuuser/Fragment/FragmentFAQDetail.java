package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

public class FragmentFAQDetail extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";

	private int mPage;

	private RecyclerView recyclerView;
	private TextView txtQuestion, txtAnswer;



	public static FragmentFAQDetail newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentFAQDetail fragment = new FragmentFAQDetail();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_faq_detail, container, false);
		txtQuestion = (TextView) rootView.findViewById(R.id.txtQuestion);
		txtAnswer = (TextView) rootView.findViewById(R.id.txtAnswer);

		txtQuestion.setText(Html.fromHtml(ApplicationData.question));
		txtAnswer.setText(Html.fromHtml(ApplicationData.answer));



		return rootView;
	}


}
