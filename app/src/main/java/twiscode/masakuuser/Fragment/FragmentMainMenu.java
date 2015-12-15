package twiscode.masakuuser.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.baoyz.widget.PullRefreshLayout;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import twiscode.masakuuser.Activity.ActivityCheckout;
import twiscode.masakuuser.Adapter.AdapterMenu;
import twiscode.masakuuser.Adapter.AdapterPagerMain;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;
import twiscode.masakuuser.Utilities.ApplicationData;

public class FragmentMainMenu extends Fragment {


	public static final String ARG_PAGE = "ARG_PAGE";
	ViewPager viewPager;
	PagerSlidingTabStrip tabsStrip;
	private BroadcastReceiver gotoPO;
	Button btnPesan;
	private BroadcastReceiver updateCart;



	public static FragmentMainMenu newInstance(int page) {
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, page);
		FragmentMainMenu fragment = new FragmentMainMenu();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DummyData();
		View rootView = inflater.inflate(R.layout.activity_mainmenu, container, false);
		btnPesan = (Button) rootView.findViewById(R.id.btnPesanSkrg);
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		viewPager.setAdapter(new AdapterPagerMain(getChildFragmentManager()));

		// Give the PagerSlidingTabStrip the ViewPager
		tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		// Attach the view pager to the tab strip
		tabsStrip.setViewPager(viewPager);
		tabsStrip.setTextColor(Color.WHITE);

		tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			// This method will be invoked when a new page becomes selected.
			@Override
			public void onPageSelected(int position) {

			}

			// This method will be invoked when the current page is scrolled
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// Code goes here
			}

			// Called when the scroll state changes:
			// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
			@Override
			public void onPageScrollStateChanged(int state) {
				// Code goes here
			}
		});

		gotoPO = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Extract data included in the Intent
				Log.d("", "broadcast gotoPO");
				String message = intent.getStringExtra("message");
				if (message.equals("true")) {
					viewPager.setCurrentItem(1);
				}


			}
		};

		updateCart = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// Extract data included in the Intent
				Log.d("", "broadcast updateCart");
				String message = intent.getStringExtra("message");
				if(ApplicationData.cart.size() > 0){
					btnPesan.setVisibility(View.VISIBLE);
				}
				else {
					btnPesan.setVisibility(View.GONE);
				}


			}
		};

		btnPesan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ActivityCheckout.class);
				startActivity(i);
			}
		});


		return rootView;
	}

	private void DummyData(){


	}


	@Override
	public void onResume() {
		super.onResume();
		// Register mMessageReceiver to receive messages.
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(gotoPO,
				new IntentFilter("gotoPO"));
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(updateCart,
				new IntentFilter("cekOrderNow"));

	}




}
