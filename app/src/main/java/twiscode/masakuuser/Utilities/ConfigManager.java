package twiscode.masakuuser.Utilities;

public class ConfigManager {
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String GEOCODE_API_BASE = "https://maps.googleapis.com/maps/api/geocode";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";
    public static final String API_KEY = "AIzaSyAJ6wF29SmwinrHoyJ-KjWwlZ3IFtVz0vY";
    public static final String URL_SUGGESTION = PLACES_API_BASE +"/autocomplete/json?components=country:id&key="+API_KEY+"&input=";

    //public static final String SERVER = "https://ladyjek.com:2053/ladyjek/driver-api"; // public
    public static final String SERVER = "https://ladyjek.com:2083/ladyjek/driver-api"; // develop

    public static final String LOGIN = SERVER+"/authenticate";
    public static final String REFRESH_TOKEN = SERVER+"/refresh-token";
    public static final String DEVICE_TOKEN = SERVER+"/device-token/create";
    public static final String LOGOUT_ALL = SERVER+"/logout-all";
    public static final String PROMO = SERVER+"/promo/init";
    public static final String PROMO_IMAGE = SERVER+"/promo/page";
    public static final String HELP = SERVER+"/help";

    //public static final String SERVER_SOCKET = "https://ladyjek.com:2096/"; //public
    public static final String SERVER_SOCKET = "https://ladyjek.com:2087/"; //develop
}
