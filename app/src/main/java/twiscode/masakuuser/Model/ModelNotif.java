package twiscode.masakuuser.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TwisCode-02 on 12/10/2015.
 */
public class ModelNotif {
    private String id="",userID="",date="";
   private String menuId, message;
    public ModelNotif(){}
    public ModelNotif(String id, String userID, String menuId, String message, String date ){
        this.id = id;
        this.userID = userID;
        this.menuId = menuId;
        this.message = message;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
