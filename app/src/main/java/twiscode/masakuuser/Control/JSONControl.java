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



    public JSONObject postLogin(String phone, String password) {
        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", phone));
            params.add(new BasicNameValuePair("password", password));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.LOGIN, ConfigManager.DUKUHKUPANG, params);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("url login", ConfigManager.LOGIN);
        Log.d("params login", phone + "-" + password);
        Log.d("return login", jsonObj.toString());
        return jsonObj;

    }

    public JSONObject postRegister(String name, String email, String password) {

        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phoneNumber", email));
            params.add(new BasicNameValuePair("password", password));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.REGISTER, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }






}
