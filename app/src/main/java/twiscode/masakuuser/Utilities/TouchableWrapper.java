package twiscode.masakuuser.Utilities;

/**
 * Created by User on 8/18/2015.
 */

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import twiscode.masakuuser.Activity.ActivityChangeLocation;


public class TouchableWrapper extends FrameLayout {

    private final String TAG = "TouchableWrapper";
    private Context context;

    public TouchableWrapper(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(NetworkManager.getInstance(context).isConnectedInternet()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ActivityChangeLocation.hideKeyboard();
                    //ActivityChangeLocation.layoutMarkerFrom.setVisibility(GONE);
                    //ActivityChangeLocation.mTouchMap = true;
                    //if (ActivityChangeLocation.markerTemp != null) {
                    //    ActivityChangeLocation.markerTemp.remove();
                    //}
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }

        return super.dispatchTouchEvent(event);
    }

}
