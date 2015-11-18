package twiscode.masakuuser.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by User on 10/21/2015.
 */
public class ApplicationManager {
    private static ApplicationManager sInstance = null;

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "LadyjekApplication";

    private static final String KEY_REFRESH_TOKEN = "refreshToken";
    private static final String KEY_EXPIRED_ON = "expiredOn";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_ARRIVE = "isArrive";
    private static final String KEY_ACTIVITY = "activity";
    private static final String KEY_FROM = "userFrom";
    private static final String KEY_DESTINATION = "userDestination";
    private static final String KEY_DRIVER = "driverlocation";
    private static final String TAG = "ApplicationManager";
    private static final String KEY_ORDER = "order";
    private static final String KEY_TRIP = "trip";
    private static final String KEY_USER = "user";
    private static final String KEY_POS_DRIVER = "trip";
    private static final String KEY_MESSAGE = "messageinbox";
    private Context mContext;


    public ApplicationManager(Context context){
        mContext = context;
        mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    public static ApplicationManager getInstance(Context ctx) {
        if (sInstance == null)
            sInstance = new ApplicationManager(ctx);
        return sInstance;
    }

    public void setUserToken(String userToken) {
        try {
            mEditor.putString(KEY_TOKEN, userToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        mEditor.commit();
    }
    public String getUserToken() {
        return mPref.getString(KEY_TOKEN, null);
    }

    public void setUserMail(String userMail) {
        try {
            mEditor.putString(KEY_USER_EMAIL, userMail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        mEditor.commit();
    }

    public String getUserMail() {
        return mPref.getString(KEY_USER_EMAIL, null);
    }

    public void logoutUser() {
        mEditor.clear();
        mEditor.commit();
    }
}
