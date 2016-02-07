package twiscode.masakuuser.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import twiscode.masakuuser.Fragment.FragmentMenu;
import twiscode.masakuuser.Fragment.FragmentPesanan;
import twiscode.masakuuser.Fragment.FragmentVendorMenu;
import twiscode.masakuuser.Fragment.FragmentVendorRating;


/**
 * Created by Unity on 27/07/2015.
 */
public class AdapterPagerMenuDetail extends FragmentPagerAdapter {
    int PAGE_COUNT = 0;
    private String titles[] = {"Menu", "Rating"};

    public AdapterPagerMenuDetail(FragmentManager fm) {
        super(fm);
        PAGE_COUNT = titles.length;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return FragmentVendorMenu.newInstance(position + 1);
        }
        else
        {
            return FragmentVendorRating.newInstance(position + 1);
        }

    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Log.d("TAG", "getPage");
        return titles[position];
    }
}
