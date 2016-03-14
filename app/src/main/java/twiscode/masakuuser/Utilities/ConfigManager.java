package twiscode.masakuuser.Utilities;

public class ConfigManager {

    public static final String FLURRY_API_KEY = "8K654QP3VC8NJF6SH35G";
    public static final String version ="~1";
    //public static final String SERVER = "https://masaku.id:2083/user"; // prod
    public static final String SERVER = "https://masaku.id:2053/user"; // dev
    //public static final String SERVER = "https://umkkf6ee2ac1.masaku.koding.io:2053/user"; // temp
    public static final String LOGIN = SERVER+"/authenticate";
    public static final String REGISTER = SERVER+"/register";
    public static final String INIT = SERVER+"/init";
    public static final String FORGOT_PASSWORD = SERVER+"/forgot";
    public static final String RESEND_RESET_PASSWORD = SERVER+"/forgot/resend-token";
    public static final String CHECK_RESET_PASSWORD = SERVER+"/forgot/check";
    public static final String RESET_PASSWORD = SERVER+"/forgot/reset";
    public static final String MENU_SPEED = SERVER+"/menu/speed/";
    public static final String MENU_PREORDER = SERVER+"/menu/preorder/";
    public static final String ALL_MENU = SERVER+"/menu/all/";
    public static final String CATEGORY = SERVER+"/menu/categories";
    public static final String LIKE = SERVER+"/menu/like";
    public static final String DISLIKE = SERVER+"/menu/dislike";
    public static final String MENU_NEXT = SERVER+"/menu/next/";
    public static final String WISHLIST = SERVER+"/like";
    public static final String CALCULATE_PRICE = SERVER+"/calculate-price";
    public static final String CHECKOUT = SERVER+"/checkout";
    public static final String LOGOUT_ALL = SERVER+"/logout-all";
    public static final String REFRESH_TOKEN = SERVER+"/refresh-token";
    public static final String VERIFY_PHONE_NUMBER = SERVER+"/verify";
    public static final String RESEND_VERIFY_CODE = SERVER+"/resend-code";
    public static final String UPDATE_PROFILE = SERVER+"/profile";
    public static final String TRANSACTIONS = SERVER+"/transactions/";
    public static final String TRANSACTION = SERVER+"/transaction/";
    public static final String NOTIFICATION = SERVER+"/notif/";
    public static final String DETAIL_TRANSACTION = SERVER+"/transaction/";
    public static final String POST_DEVICE_TOKEN = SERVER+"/device-token";
    public static final String DUKUHKUPANG = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlUb2tlbiI6Iic0T3M0VDlOMzg4J0NMMUs-MTlAZCwlT1pCJkBEOSIsImlhdCI6MTQ0NzIxMDY3MH0.xgocnzsQOLoSNZ3VoC3vlhVlzqakkNhr5hRayz008jo";
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String GEOCODE_API_BASE = "https://maps.googleapis.com/maps/api/geocode";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";
    public static final String API_KEY = "AIzaSyAJ6wF29SmwinrHoyJ-KjWwlZ3IFtVz0vY";
    public static final String URL_SUGGESTION = PLACES_API_BASE +"/autocomplete/json?components=country:id&key="+API_KEY+"&input=";

}
