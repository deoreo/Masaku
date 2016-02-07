package twiscode.masakuuser.Database;

/**
 * Created by ModelUser on 8/3/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;

import twiscode.masakuuser.Model.ModelAlamat;
import twiscode.masakuuser.Model.ModelPesanan;
import twiscode.masakuuser.Model.ModelPlace;
import twiscode.masakuuser.Model.ModelUser;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "MasakuDB";
    // ModelUser table name
    private static final String T_USER = "t_user";
    private static final String T_ALAMAT = "t_alamat";

    private static final String KEY_USER_ID = "id_user";
    private static final String KEY_USER_NAME = "name_user";
    private static final String KEY_TRUSTED = "trusted_user";
    private static final String KEY_USER_PHONE = "phone_user";
    private static final String KEY_USER_EMAIL = "email_user";

    private static final String KEY_ALAMAT_ID = "id_alamat";
    private static final String KEY_ALAMAT_NAME = "name_alamat";
    private static final String KEY_ALAMAT_DETAIL = "detail_alamat";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_USER = "CREATE TABLE " + T_USER + "("
                + KEY_USER_ID + " TEXT PRIMARY KEY,"
                + KEY_USER_NAME + " TEXT,"
                + KEY_USER_PHONE + " TEXT,"
                + KEY_USER_EMAIL + " TEXT,"
                + KEY_TRUSTED + " TEXT"
                + ")";

        String CREATE_TABLE_ALAMAT = "CREATE TABLE " + T_ALAMAT + "("
                + KEY_ALAMAT_ID + " TEXT PRIMARY KEY,"
                + KEY_ALAMAT_NAME + " TEXT,"
                + KEY_ALAMAT_DETAIL + " TEXT"
                + ")";

        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_ALAMAT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + T_USER);
        db.execSQL("DROP TABLE IF EXISTS " + T_ALAMAT);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void insertuser(ModelUser modeluser) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, modeluser.getId());
        values.put(KEY_USER_NAME, modeluser.getNama());
        values.put(KEY_USER_PHONE, modeluser.getPonsel());
        values.put(KEY_TRUSTED, modeluser.getTrusted());
        // Inserting Row
        db.insert(T_USER, null, values);
        db.close(); // Closing database connection
    }

    public int getuserCount() {
        int count = 0;
        String countQuery = "SELECT  * FROM " + T_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
            cursor.close();
        }

        // return count
        return count;
    }

    public ModelUser getuser() {
        String allData = "SELECT  * FROM " + T_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allData, null);
        cursor.close();

        ModelUser modeluser = new ModelUser(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4)
        );
        return modeluser;
    }


    public void logout() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + T_USER);

    }

    /*
    public void insertAlamat(ModelAlamat modelAlamat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALAMAT_ID, modelAlamat.getId());
        values.put(KEY_ALAMAT_NAME, modelAlamat.getNama());
        // Inserting Row
        db.insert(T_ALAMAT, null, values);
        db.close(); // Closing database connection
    }

    public ModelAlamat getAlamat(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(T_ALAMAT, new String[]{KEY_ALAMAT_ID,
                        KEY_ALAMAT_NAME},
                KEY_ALAMAT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ModelAlamat modelGroup = new ModelAlamat(cursor.getString(0),
                cursor.getString(1), cursor.getString(2)

        );
        return modelGroup;
    }

    public ArrayList<ModelAlamat> loadAlamat() {
        String allData = "SELECT  * FROM " + T_ALAMAT;
        ArrayList<ModelAlamat> alamatList = new ArrayList<ModelAlamat>();

        // Loads the event list data summary
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allData, null);
        if(cursor.moveToFirst()){
            do{
                alamatList.add(this.getAlamat(cursor.getString(0)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Collections.reverse(alamatList);
        return alamatList;
    }

    */
    public void insertPlace(ModelPlace modelAlamat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALAMAT_ID, modelAlamat.getPlaceId());
        values.put(KEY_ALAMAT_NAME, modelAlamat.getAddress());
        values.put(KEY_ALAMAT_DETAIL, modelAlamat.getAddressDetail());
        // Inserting Row
        db.insert(T_ALAMAT, null, values);
        db.close(); // Closing database connection
    }

    public ModelPlace getPlace(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(T_ALAMAT, new String[]{KEY_ALAMAT_ID,
                        KEY_ALAMAT_NAME, KEY_ALAMAT_DETAIL},
                KEY_ALAMAT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ModelPlace modelGroup = new ModelPlace(cursor.getString(0),
                cursor.getString(1), cursor.getString(2)

        );
        return modelGroup;
    }

    public ArrayList<ModelPlace> loadPlace() {
        String allData = "SELECT  * FROM " + T_ALAMAT;
        ArrayList<ModelPlace> alamatList = new ArrayList<ModelPlace>();

        // Loads the event list data summary
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(allData, null);
        if(cursor.moveToFirst()){
            do{
                alamatList.add(this.getPlace(cursor.getString(0)));
            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Collections.reverse(alamatList);
        return alamatList;
    }


}
