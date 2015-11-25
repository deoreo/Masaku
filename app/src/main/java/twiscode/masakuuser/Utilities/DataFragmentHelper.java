package twiscode.masakuuser.Utilities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import twiscode.masakuuser.R;

/**
 * Created by User on 13/04/2015.
 */
public class DataFragmentHelper {
    private Fragment FragmentHelper;
    private FragmentManager FragmentManagerHelper;

    public void SetDataFragmentHelper(Fragment fragment, FragmentManager fragmentManager){
        this.FragmentHelper = fragment;
        this.FragmentManagerHelper = fragmentManager;
        //this.FragmentTransactionHelper = fragmentTransaction;
    }


    public void ReturnLastFragment(){
        ApplicationData.fragments.pop();
        Fragment fr = ApplicationData.fragments.peek();
        FragmentManager fragmentManager = this.FragmentManagerHelper;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fr);
        fragmentTransaction.commit();
    }

    public void ChangeFragment(Fragment fr){
        ApplicationData.fragments.push(fr);
        FragmentManager fragmentManager = this.FragmentManagerHelper;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fr);
        fragmentTransaction.commit();
    }


}
