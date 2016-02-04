package twiscode.masakuuser.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import twiscode.masakuuser.Model.ModelUser;

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
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_WISHLIST = "wishlist";
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

    public void setUser(ModelUser user) {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            mEditor.putString(KEY_USER, json);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        mEditor.commit();
    }
    public ModelUser getUser() {
        Gson gson = new Gson();
        String json = mPref.getString(KEY_USER, null);
        ModelUser user = gson.fromJson(json,ModelUser.class);
        return user;
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

    public void setAlamat(String alamat) {
        try {
            mEditor.putString(KEY_ALAMAT, alamat);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        mEditor.commit();
    }
    public String getAlamat() {
        return mPref.getString(KEY_ALAMAT, null);
    }

    public void setWishlist(int wishlist) {
        try {
            mEditor.putInt(KEY_WISHLIST,wishlist);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
        mEditor.commit();
    }
    public int getWishlist() {
        return mPref.getInt(KEY_WISHLIST, 0);
    }
}
