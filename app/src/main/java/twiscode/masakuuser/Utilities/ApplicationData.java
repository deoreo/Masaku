package twiscode.masakuuser.Utilities;


import java.util.HashMap;

import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Model.ModelMenu;
import twiscode.masakuuser.Model.ModelMenuSpeed;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelVendor;
import twiscode.masakuuser.Model.ModelVendorMenu;

/**
 * Created by User on 10/21/2015.
 */
public class ApplicationData {
    public static String login_id = "";
    public static ModelMenu modelMenu = null;
    public static ModelVendorMenu modelVendorMenu = null;
    public static String phoneNumber = "";
    public static String tokenPass = "";
    public static ModelMenuSpeed modelMenuSpeed = null;
    public static HashMap<String,ModelCart> cart = new HashMap<>();

    public static final String PARSE_CHANNEL = "masaku_user";
    public static final String PARSE_APPLICATION_ID = "n84R2LqU4dXQFqdVj36ArIvKYxgu2TRe6oYqrb5u";
    public static final String PARSE_CLIENT_KEY = "JeOIlqbtDaTkO8Cqospcc2YsGDm6uVnaaEW61aVH";
    public static final int NOTIFICATION_ID = 100;
    public static String PARSE_DEVICE_TOKEN = "";



}
