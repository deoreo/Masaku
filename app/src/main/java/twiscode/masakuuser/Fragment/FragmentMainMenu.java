package twiscode.masakuuser.Fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.R;

public class FragmentMainMenu extends Fragment {


	public static final String ARG_PAGE = "ARG_PAGE";




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
		ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		viewPager.setAdapter(new AdapterPagerMain(getFragmentManager()));

		// Give the PagerSlidingTabStrip the ViewPager
		PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
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


		return rootView;
	}

	private void DummyData(){


	}


}
