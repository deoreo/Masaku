package twiscode.masakuuser.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import twiscode.masakuuser.Fragment.FragmentAntarCepat;
import twiscode.masakuuser.Fragment.FragmentMenu;
import twiscode.masakuuser.Fragment.FragmentPesanan;
import twiscode.masakuuser.Fragment.FragmentProfile;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by Unity on 27/07/2015.
 */
public class AdapterPagerMain extends FragmentPagerAdapter {
    int PAGE_COUNT = 0;
    //private String titles[] = {"PRE ORDER","SPEED DELIVERY"};
    private String titles[] = {"PRE ORDER"};
    public AdapterPagerMain(FragmentManager fm) {
        super(fm);
        PAGE_COUNT = titles.length;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //Fragment fragment = null;
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return FragmentMenu.newInstance(position + 1);
        }
        else        // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            return FragmentAntarCepat.newInstance(position + 1);
        }



        //return fragment;
        //return fragment.newInstance(position + 1);
    }
/*
    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }
    */

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Log.d("TAG", "getPage");
        return titles[position];
    }


}
