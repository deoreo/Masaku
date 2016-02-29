package twiscode.masakuuser.Parse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import twiscode.masakuuser.Activity.Main;
import twiscode.masakuuser.Database.DatabaseHandler;
import twiscode.masakuuser.Utilities.ApplicationData;
import twiscode.masakuuser.Utilities.ApplicationManager;


public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private final String TAG = CustomPushReceiver.class.getSimpleName();
    private NotificationManager notificationManager;
    private Intent parseIntent;
    private DatabaseHandler db;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            Log.e(TAG, "Push received: " + json);
            parseIntent = intent;
            parsePushJsonCustom(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
        Log.e("Push", "Clicked");
        ApplicationData.isNotif = true;
        Intent i = new Intent(context, Main.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }



    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {
        Log.e("Push", "showNotificationMessage");
        notificationManager = new NotificationManager(context);
        intent.putExtras(parseIntent.getExtras());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationManager.showNotificationMessage(title, message, intent);
    }


    private void parsePushJsonCustom(Context context, JSONObject json) {
        try {
            db = new DatabaseHandler(context);
            String message = json.getString("alert");
            ApplicationData.isNotif = true;
            Intent resultIntent = new Intent(context, Main.class);
            showNotificationMessage(context, "Notifikasi Delihome", message, resultIntent);
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            String date = df.format(Calendar.getInstance().getTime());
            Message msg = new Message();
            msg.setId(message);
            msg.setMessage(message);
            msg.setTimestamp(date);
            db.insertMessage(msg);
            ApplicationManager.getInstance(context).setMessage(msg);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }
}
