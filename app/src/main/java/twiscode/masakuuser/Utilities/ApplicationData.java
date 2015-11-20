package twiscode.masakuuser.Utilities;


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.Model.ModelVendor;
import twiscode.masakuuser.Model.ModelVendorMenu;

/**
 * Created by User on 10/21/2015.
 */
public class ApplicationData {
    public static String login_id = "";
    public static String name = "";
    public static ModelMenu modelMenu = null;
    public static ModelVendorMenu modelVendorMenu = null;
    public static String phoneNumber = "";
    public static String tokenPass = "";
    public static ModelMenuSpeed modelMenuSpeed = null;
    public static HashMap<String,ModelCart> cart = new HashMap<>();
    public static int def_delivery = 10000;
    public static String temp_hp = "";
    public static String temp_nama = "";
    public static String temp_password = "";
    public static int isVerify = 0;
    public static String temp_token = "";
    public static ModelUser temp_user= null;

    public static final String PARSE_CHANNEL = "masaku_user";
    public static final String PARSE_APPLICATION_ID = "ZXWmpJgJjgEBRDPI4HjVhlFkfpg7SvYJeh1B4NlQ";
    public static final String PARSE_CLIENT_KEY = "Dqad1vSUuLyf1gnfOTj929Nmb1LlC9zupQyrxUQz";
    public static final int NOTIFICATION_ID = 100;
    public static String PARSE_DEVICE_TOKEN = "";
    public static String location = "";
    public static LatLng posFrom;



}
