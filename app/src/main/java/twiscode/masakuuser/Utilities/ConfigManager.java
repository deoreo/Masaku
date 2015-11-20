package twiscode.masakuuser.Utilities;

public class ConfigManager {

    public static final String version ="~1";
    public static final String SERVER = "https://masaku.id:2083/user"; // develop
    public static final String LOGIN = SERVER+"/authenticate";
    public static final String REGISTER = SERVER+"/register";
    public static final String FORGOT_PASSWORD = SERVER+"/forgot";
    public static final String RESEND_RESET_PASSWORD = SERVER+"/forgot/resend-token";
    public static final String CHECK_RESET_PASSWORD = SERVER+"/forgot/check";
    public static final String RESET_PASSWORD = SERVER+"/forgot/reset";
    public static final String MENU_SPEED = SERVER+"/menu/speed/";
    public static final String CALCULATE_PRICE = SERVER+"/calculate-price";
    public static final String LOGOUT_ALL = SERVER+"/logout-all";
    public static final String REFRESH_TOKEN = SERVER+"/refresh-token";
    public static final String VERIFY_PHONE_NUMBER = SERVER+"/verify";
    public static final String RESEND_VERIFY_CODE = SERVER+"/resend-code";
    public static final String UPDATE_PROFILE = SERVER+"/profile";
    public static final String TRANSACTIONS = SERVER+"/transactions";
    public static final String DUKUHKUPANG = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGlUb2tlbiI6Iic0T3M0VDlOMzg4J0NMMUs-MTlAZCwlT1pCJkBEOSIsImlhdCI6MTQ0NzIxMDY3MH0.xgocnzsQOLoSNZ3VoC3vlhVlzqakkNhr5hRayz008jo";

}
