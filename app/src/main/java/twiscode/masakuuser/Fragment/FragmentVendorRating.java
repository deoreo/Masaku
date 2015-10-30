package twiscode.masakuuser.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Adapter.AdapterVendorFeedback;
import twiscode.masakuuser.Adapter.AdapterVendorRating;
import twiscode.masakuuser.Model.ModelVendorFeedback;
import twiscode.masakuuser.Model.ModelVendorRating;
import twiscode.masakuuser.R;

public class FragmentVendorRating extends Fragment {

	public static final String ARG_PAGE = "ARG_PAGE";
	private List<ModelVendorRating> LIST_MENU = new ArrayList<>();
	private List<ModelVendorFeedback> LIST_FEEDBACK = new ArrayList<>();
	private ListView mListView, mListFeedback;
	private AdapterVendorRating mAdapter;
	private AdapterVendorFeedback adapterFeedback;
	private int mPage;
	private RecyclerView recyclerView;



	public static FragmentVendorRating newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentVendorRating fragment = new FragmentVendorRating();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyRating();
		DummyFeedback();
		View rootView = inflater.inflate(R.layout.activity_vendor_rating, container, false);
		mListView = (ListView) rootView.findViewById(R.id.chartList);
		mListFeedback = (ListView) rootView.findViewById(R.id.feedbackList);
		mAdapter = new AdapterVendorRating(getActivity(), LIST_MENU);
		adapterFeedback = new AdapterVendorFeedback(getActivity(), LIST_FEEDBACK);

		mListView.setAdapter(mAdapter);
		mListView.setScrollingCacheEnabled(false);

		mListFeedback.setAdapter(adapterFeedback);
		mListFeedback.setScrollingCacheEnabled(false);

		mListFeedback.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				view.getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			}
		});

		mListView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				view.getParent().requestDisallowInterceptTouchEvent(false);
				return false;
			}
		});

		return rootView;
	}

	private void DummyRating(){
		LIST_MENU = new ArrayList<ModelVendorRating>();
		ModelVendorRating modelVendorMenu0 = new ModelVendorRating("5", "100", "60");
		LIST_MENU.add(modelVendorMenu0);
		ModelVendorRating modelVendorMenu1 = new ModelVendorRating("4", "100", "60");
		LIST_MENU.add(modelVendorMenu1);
		ModelVendorRating modelVendorMenu2 = new ModelVendorRating("3", "100", "60");
		LIST_MENU.add(modelVendorMenu2);
		ModelVendorRating modelVendorMenu3 = new ModelVendorRating("2", "100", "60");
		LIST_MENU.add(modelVendorMenu3);
		ModelVendorRating modelVendorMenu4 = new ModelVendorRating("1", "100", "60");
		LIST_MENU.add(modelVendorMenu4);

	}
	private void DummyFeedback(){
		LIST_FEEDBACK = new ArrayList<ModelVendorFeedback>();
		ModelVendorFeedback modelVendorMenu0 = new ModelVendorFeedback("0","Cinta L", "5", "23-10-2015", "Masakannya enak");
		LIST_FEEDBACK.add(modelVendorMenu0);
		ModelVendorFeedback modelVendorMenu1 = new ModelVendorFeedback("1","Jimmy", "4", "23-10-2015", "Enak tapi porsinya kurang banyak :D");
		LIST_FEEDBACK.add(modelVendorMenu1);


	}


}
