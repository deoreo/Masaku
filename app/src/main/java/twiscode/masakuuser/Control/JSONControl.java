package twiscode.masakuuser.Control;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import twiscode.masakuuser.Model.ModelCart;
import twiscode.masakuuser.Utilities.ApplicationData;
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
        //Log.d("return login", jsonObj.toString());
        return jsonObj;

    }

    public JSONObject postRegister(String name, String phoneNumber, String email, String password, String gender, String tahun) {

        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phoneNumber", phoneNumber));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("yob", tahun));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.REGISTER, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postForgotPassword(String phone) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", phone));
            jsonObj = _JSONResponse.POSTResponseString(ConfigManager.FORGOT_PASSWORD, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postVerifyCode(String code, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("verificationCode", code));
            jsonObj = _JSONResponse.POSTResponseTokenString(ConfigManager.VERIFY_PHONE_NUMBER, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postDeviceToken(String devicetoken, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("deviceToken", devicetoken));
            jsonObj = _JSONResponse.POSTResponseTokenString(ConfigManager.POST_DEVICE_TOKEN, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postCheckCode(String phone, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", phone));
            params.add(new BasicNameValuePair("token", token));
            jsonObj = _JSONResponse.POSTResponseString(ConfigManager.CHECK_RESET_PASSWORD, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postResendCode(String phone) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", phone));
            jsonObj = _JSONResponse.POSTResponseString(ConfigManager.RESEND_RESET_PASSWORD, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postResendCodeVerify(String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.POSTResponseTokenString(ConfigManager.RESEND_VERIFY_CODE, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String postResetPassword(String phone, String token, String password) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("phoneNumber", phone));
            params.add(new BasicNameValuePair("token", token));
            params.add(new BasicNameValuePair("password", password));
            jsonObj = _JSONResponse.POSTResponseString(ConfigManager.RESET_PASSWORD, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject getMenuSpeed(int page) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponseToken(ConfigManager.MENU_SPEED + page, ConfigManager.DUKUHKUPANG);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject getPesanan(String token,int page) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.TRANSACTIONS + page, ConfigManager.DUKUHKUPANG, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("token",jsonObj.toString());
        return jsonObj;
    }

    public JSONObject getNotif(String token,int page) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.NOTIFICATION + page, ConfigManager.DUKUHKUPANG, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("token",jsonObj.toString());
        return jsonObj;
    }




    public JSONObject calculatePrice(String kode, String accessToken,List<ModelCart> cart) {

        JSONObject jsonObj = new JSONObject();

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            HashMap<String,Integer> ca = new HashMap<>();
            JSONArray jsArr = new JSONArray();
            /*
            for(int i=0;i<cart.size();i++){
                //ca.put(cart.get(i).getId(), cart.get(i).getJumlah());
                JSONObject a = new JSONObject();
                a.put(cart.get(i).getId(),cart.get(i).getJumlah());
                jsArr.put(a);
            }
            */
            Gson gson = new Gson();
            String json = gson.toJson(ca);
            params.add(new BasicNameValuePair("payment", "transfer"));
            params.add(new BasicNameValuePair("promoCode", kode));
            for(int i=0;i<cart.size();i++){
                //ca.put(cart.get(i).getId(), cart.get(i).getJumlah());
                params.add(new BasicNameValuePair("orders["+cart.get(i).getId()+"]", Integer.toString(cart.get(i).getJumlah())));
            }


            jsonObj = _JSONResponse.POSTResponseToken(ConfigManager.CALCULATE_PRICE, ConfigManager.DUKUHKUPANG, accessToken, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject checkOut(String kode, String address, String note, String tip, LatLng pos, String accessToken,List<ModelCart> cart, String conFee) {

        JSONObject jsonObj = new JSONObject();

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONArray loc = new JSONArray();
            try {
                loc.put(0, pos.latitude);
                loc.put(1, pos.longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            params.add(new BasicNameValuePair("payment", "transfer"));
            params.add(new BasicNameValuePair("tip", tip));
            params.add(new BasicNameValuePair("promoCode", kode));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("note", note));
            params.add(new BasicNameValuePair("addressGeo[]", ""+pos.longitude));
            params.add(new BasicNameValuePair("addressGeo[]", ""+pos.latitude));
            params.add(new BasicNameValuePair("conFee", conFee));
            for(int i=0;i<cart.size();i++){
                params.add(new BasicNameValuePair("orders["+cart.get(i).getId()+"]", Integer.toString(cart.get(i).getJumlah())));
            }



            jsonObj = _JSONResponse.POSTResponseToken(ConfigManager.CHECKOUT, ConfigManager.DUKUHKUPANG, accessToken, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject checkOutCOD(String kode, String address, String note, String tip, LatLng pos, String accessToken,List<ModelCart> cart) {

        JSONObject jsonObj = new JSONObject();

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONArray loc = new JSONArray();
            try {
                loc.put(0, pos.latitude);
                loc.put(1, pos.longitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            params.add(new BasicNameValuePair("payment", "cod"));
            params.add(new BasicNameValuePair("tip", tip));
            params.add(new BasicNameValuePair("promoCode", kode));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("note", note));
            params.add(new BasicNameValuePair("addressGeo[]", ""+pos.longitude));
            params.add(new BasicNameValuePair("addressGeo[]", ""+pos.latitude));
            for(int i=0;i<cart.size();i++){
                params.add(new BasicNameValuePair("orders["+cart.get(i).getId()+"]", Integer.toString(cart.get(i).getJumlah())));
            }


            jsonObj = _JSONResponse.POSTResponseToken(ConfigManager.CHECKOUT, ConfigManager.DUKUHKUPANG, accessToken, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }


    public String postLogoutAll(String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.POSTLogoutAll(ConfigManager.LOGOUT_ALL, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject postRefreshToken(String token) {
        JSONObject jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", token));
            jsonObj = _JSONResponse.POSTResponse(ConfigManager.REFRESH_TOKEN, ConfigManager.DUKUHKUPANG, params);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;

    }

    public String updateProfile(String data, String param,String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(param, data));
            jsonObj = _JSONResponse.PutResponseTokenString(ConfigManager.UPDATE_PROFILE, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }


    public String updateAllProfile(String name, String phone, String email, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phoneNumber", phone));
            params.add(new BasicNameValuePair("email", email));
            jsonObj = _JSONResponse.PutResponseTokenString(ConfigManager.UPDATE_PROFILE, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject detailTransaksi(String id,String token) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.DETAIL_TRANSACTION + id, ConfigManager.DUKUHKUPANG, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject getMenuSpeedNext(int page, String token) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.MENU_NEXT + page, ConfigManager.DUKUHKUPANG, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject getMenuPreOrder(int page, String token) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.MENU_PREORDER + page, ConfigManager.DUKUHKUPANG,token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject getAllMenus(int page, String token) {

        JSONObject jsonObj = new JSONObject();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponse(ConfigManager.ALL_MENU + page, ConfigManager.DUKUHKUPANG,token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONArray getWishlist(String token) {

        JSONArray jsonObj = new JSONArray();

        try {
            jsonObj = _JSONResponse.GETResponseArray(ConfigManager.WISHLIST, ConfigManager.DUKUHKUPANG, token);
            Log.d("url response wishlist", ConfigManager.WISHLIST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String LikeMenu(String data, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", data));
            jsonObj = _JSONResponse.POSTResponseTokenString(ConfigManager.LIKE, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public String DislikeMenu(String data, String token) {

        String jsonObj = null;

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", data));
            jsonObj = _JSONResponse.POSTResponseTokenString(ConfigManager.DISLIKE, token, ConfigManager.DUKUHKUPANG, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONArray getCategories(String token) {

        JSONArray jsonObj = new JSONArray();

        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            jsonObj = _JSONResponse.GETResponseArray(ConfigManager.CATEGORY, ConfigManager.DUKUHKUPANG, token);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    public JSONObject ConfirmPO(String kode, String accessToken) {

        JSONObject jsonObj = new JSONObject();

        try {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            jsonObj = _JSONResponse.POSTResponseToken(ConfigManager.TRANSACTION + kode + "/confirm", ConfigManager.DUKUHKUPANG, accessToken, params);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }


    public JSONObject getInit() {
        JSONObject jsonObj = null;

        try {
            jsonObj = _JSONResponse.GETResponseObject(ConfigManager.INIT, ConfigManager.DUKUHKUPANG);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;

    }








}
