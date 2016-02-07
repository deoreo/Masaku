package twiscode.masakuuser.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import twiscode.masakuuser.Fragment.FragmentTutorial_1;
import twiscode.masakuuser.Fragment.FragmentTutorial_2;
import twiscode.masakuuser.Fragment.FragmentTutorial_3;
import twiscode.masakuuser.Fragment.FragmentTutorial_4;
import twiscode.masakuuser.Fragment.FragmentTutorial_4_2;

/**
 * Created by Unity on 01/09/2015.
 */
public class TutorialBantuanSliderAdapter extends FragmentPagerAdapter {
    private int pagerCount;


    public TutorialBantuanSliderAdapter(FragmentManager fm) {
        super(fm);
        pagerCount = 3;
    }

    @Override
    public Fragment getItem(int i) {
        String url = "";
        if(i==0){
            return FragmentTutorial_1.newInstance(url);
        }
        else if(i==1){
            return FragmentTutorial_2.newInstance(url);
        }
        else{
            return FragmentTutorial_3.newInstance(url);
        }

    }

    @Override
    public int getCount() {
        return pagerCount;
    }
}
