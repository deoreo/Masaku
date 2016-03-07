package twiscode.masakuuser.Utilities;


import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import twiscode.masakuuser.Model.ModelAllMenus;
import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelDetailTransaksi;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelNotif;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelUser;
import twiscode.masakuuser.Model.ModelVendor;
import twiscode.masakuuser.Model.ModelVendorMenu;
import twiscode.masakuuser.Model.ModelWishlist;

/**
 * Created by User on 10/21/2015.
 */
public class ApplicationData {
    public static String login_id = "";
    public static String name = "";
    public static ModelMenu modelMenu = null;
    public static ModelVendorMenu modelVendorMenu = null;
    public static String phoneNumber = "";
    public static String phoneNumberLogin = "";
    public static String email = "";
    public static String tokenPass = "";
    public static ModelMenuSpeed modelMenuSpeed = null;
    public static ModelAllMenus modelAllMenus = null;
    public static ModelWishlist modelWishlist = null;
    public static HashMap<String,ModelCart> cart = new HashMap<>();
    public static int def_delivery = 10000;
    public static String temp_hp = "";
    public static String temp_nama = "";
    public static String temp_password = "";
    public static int isVerify = 0;
    public static String temp_token = "";
    public static ModelUser temp_user= null;
    public static ModelPesanan pesanan = null;

    public static final String PARSE_CHANNEL = "masaku_user";
    public static final String PARSE_APPLICATION_ID = "ZXWmpJgJjgEBRDPI4HjVhlFkfpg7SvYJeh1B4NlQ";
    public static final String PARSE_CLIENT_KEY = "Dqad1vSUuLyf1gnfOTj929Nmb1LlC9zupQyrxUQz";
    public static final int NOTIFICATION_ID = 100;
    public static String PARSE_DEVICE_TOKEN = "";
    public static final String ZOPIM_KEY = "3gUyhELEL6tPo7Zo862pupn4yYgNgqy9";


    //public static String location = "";
    //public static LatLng posFrom;
    public static Stack<Fragment> fragments = new Stack<>();
    public static HashMap<String,Bitmap> temp_img = new HashMap<>();
    public static String smsCode;
    public static TextView titleBar;
    public static boolean isFirstSpeed = true;
    public static boolean isNotif = false;
    public static String question, answer;
    public static HashMap<String,ModelAllMenus> allmenus = new HashMap<>();
    public static HashMap<String,ModelWishlist> wishlist = new HashMap<>();
    public static String idLike = "";
    public static String historyIdLike = "";
    public static String type="";
    public static String idLastTransaction="";
    public static ModelDetailTransaksi detailTransaksi;
    public static ModelNotif modelNotif;
    public static long timer = 0;

    public static List<ModelMenuSpeed> tempPO = null;
    public static List<ModelMenuSpeed> tempSpeed = null;

    public static boolean isNullCart = false;
    public static boolean isFirstLogin = false;
    public static boolean isFromMenu = true;
    public static boolean isProfile = false;
    public static boolean isHelp = false;
    public static boolean isHistory = false;
    //public static boolean hasEmail = false;
    public static String address="";
    public static String promocode="";
    //public static int CountWishlist = 0;
    public static String notice = "";
    public static String couponHint = "";

}
