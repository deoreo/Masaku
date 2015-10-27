package twiscode.masakuuser.Control;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import twiscode.masakuuser.Utilities.ConfigManager;


public class JSONControl {
    private JSONResponse _JSONResponse;


    public JSONControl() {
        _JSONResponse = new JSONResponse();
    }

    public JSONArray listPlace(String addressInput) {
        JSONArray json = null;
        JSONObject jsonObj = null;
        try {
            String url = ConfigManager.URL_SUGGESTION +
                            URLEncoder.encode(addressInput, "utf8");
            Log.d("url", url);


            jsonObj = _JSONResponse.GETResponse(url);
            json = jsonObj.getJSONArray("predictions");
        } catch (ConnectException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (JSONException e) {
        }
        return json;

    }

    public JSONObject postLogin(String email, String password) {
        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", email));
            params.add(new BasicNameValuePair("password", password));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.LOGIN, params);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("url login", ConfigManager.LOGIN);
        Log.d("params login", email + "-" + password);
        Log.d("return login", jsonObj.toString());
        return jsonObj;

    }

    public JSONObject postRefreshToken(String token) {
        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", token));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.REFRESH_TOKEN, params);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("url token", ConfigManager.LOGIN);
        Log.d("params token", token);
        Log.d("return token", jsonObj.toString());
        return jsonObj;

    }

    public String postDeviceToken(String token, String deviceToken) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("deviceToken", deviceToken));
            jsonObj = _JSONResponse.POSTDeviceToken(ConfigManager.DEVICE_TOKEN, token, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postLogoutAll(String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.POSTLogoutAll(ConfigManager.LOGOUT_ALL, token, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject postPromo() {
        JSONObject jsonObj = null;

        try {
            jsonObj = _JSONResponse.GETResponse(ConfigManager.PROMO);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;

    }

    public JSONObject postPromoImage() {
        JSONObject jsonObj = null;

        try {
            jsonObj = _JSONResponse.GETResponse(ConfigManager.PROMO_IMAGE);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;

    }

    public JSONObject getHelp() {
        JSONObject jsonObj = null;

        try {
            jsonObj = _JSONResponse.GETResponse(ConfigManager.HELP);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;

    }






}
