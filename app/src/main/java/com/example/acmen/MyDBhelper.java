package com.example.acmen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DetailsDB";
    private static final int DATABASE_VERSION = 9;
    private static final String TABLE_DETAIL = "details";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_GST_NO = "gst_no";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUMBER = "phoneNumber";

    private static final String TABLE_MATERIAL = "materials";
    private static final String KEY_MATERIAL_ID = "material_id";
    private static final String KEY_MATERIAL_NAME = "material_name";
    private static final String KEY_MATERIAL_HSN = "material_hsn";
    private static final String KEY_MATERIAL_TAX = "material_tax";

    private static final String TABLE_USER_DETAIL = "userDetails";

    private static final String KEY_USER_DETAIL_ID = "userDetailId";
    private static final String KEY_USER_NAME = "userDetailName";
    private static final String KEY_USER_GST_NO = "userGstNo";
    private static final String KEY_USER_ADDRESS = "userAddress";
    private static final String KEY_USER_NUMBER = "userNumber";
    private static final String KEY_USER_DESCRIPTION = "userDescription";

    private static final String TABLE_BILLBOOK = "billBook";

    private static final String BILL_QUANTITY = "billQuantity";
    private static final String BILL_RATE = "billRate";
    private static final String BILL_ID = "billId";
    private static final String BILL_DATE = "billDate";


    public MyDBhelper(Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_MATERIAL+"("+KEY_MATERIAL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_MATERIAL_NAME+" TEXT NOT NULL, "+ KEY_MATERIAL_HSN+ " TEXT, "+ KEY_MATERIAL_TAX+" INTEGER "+")");

        db.execSQL("CREATE TABLE "+TABLE_DETAIL + "("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + KEY_NAME+" TEXT NOT NULL , " + KEY_GST_NO+ " TEXT , "+ KEY_ADDRESS+" TEXT , "+KEY_PHONE_NUMBER+" TEXT " + ")" );

        db.execSQL("CREATE TABLE "+TABLE_USER_DETAIL+"("+KEY_USER_DETAIL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_USER_NAME+" TEXT NOT NULL, "+ KEY_USER_GST_NO+" TEXT, "+KEY_USER_NUMBER+" TEXT, "+KEY_USER_DESCRIPTION+" TEXT, "+KEY_USER_ADDRESS+" TEXT )");

        db.execSQL("CREATE TABLE "+ TABLE_BILLBOOK+"("+BILL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_MATERIAL_ID+
                " INTEGER , "+KEY_ID+" INTEGER , "+ BILL_QUANTITY+" INTEGER, "+BILL_RATE+" INTEGER, "+BILL_DATE+" TEXT "+")");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


            db.execSQL("ALTER TABLE " + TABLE_DETAIL + " ADD COLUMN " + KEY_PHONE_NUMBER + " TEXT DEFAULT''");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MATERIAL + "(" + KEY_MATERIAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_MATERIAL_NAME + " TEXT NOT NULL, " + KEY_MATERIAL_HSN + " TEXT, " + KEY_MATERIAL_TAX + " INTEGER " + ")");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER_DETAIL + "(" + KEY_USER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USER_NAME + " TEXT NOT NULL, " + KEY_USER_GST_NO + " TEXT, " + KEY_USER_NUMBER + " TEXT, " + KEY_USER_DESCRIPTION + " TEXT, " + KEY_USER_ADDRESS + " TEXT )");

            db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_BILLBOOK+"("+BILL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ KEY_MATERIAL_ID+
                " INTEGER , "+KEY_ID+" INTEGER , "+ BILL_QUANTITY+" INTEGER, "+BILL_RATE+" INTEGER, "+BILL_DATE+" TEXT )");


    }

    public void addDetail(String name,String gst_no,String address,String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,name);
        values.put(KEY_GST_NO,gst_no);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_PHONE_NUMBER,phoneNumber);
        db.insert(TABLE_DETAIL,null,values);
    }
        public ArrayList<DetailModel> fetchDetail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_DETAIL,null);

        ArrayList<DetailModel> arrDetails = new ArrayList<>();

        while (cursor.moveToNext()){

            DetailModel model = new DetailModel();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.gst_no = cursor.getString(2);
            model.address = cursor.getString(3);
            model.phoneNo = cursor.getString(4);

            arrDetails.add(model);
        }
            Collections.sort(arrDetails, new Comparator<DetailModel>() {
                @Override
                public int compare(DetailModel detailModel, DetailModel t1) {
                    String name1 = detailModel.name;
                    String name2 = t1.name;
                    return name1.compareTo(name2);
                }
            });
        db.close();
        return arrDetails;
    }

    public void editDetail(int id,String name,String gst_no,String address){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_ADDRESS,address);
        cv.put(KEY_NAME,name);
        cv.put(KEY_GST_NO,gst_no);

        database.update(TABLE_DETAIL,cv,KEY_ID+" = "+id,null);
        database.close();
    }
    public void deleteDetail(int id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_DETAIL,KEY_ID +" = ? ",new String[]{String.valueOf(id)});
        database.close();
    }

    public void addMaterial(String EnteredMaterial,String EnteredHsn, int EnteredTax){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MATERIAL_NAME,EnteredMaterial);
        values.put(KEY_MATERIAL_HSN,EnteredHsn);
        values.put(KEY_MATERIAL_TAX,EnteredTax);
        database.insert(TABLE_MATERIAL,null,values);
    }

    public ArrayList<material> fetchMaterial(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cr = database.rawQuery("SELECT * FROM "+TABLE_MATERIAL,null);

        ArrayList<material> arrMaterial = new ArrayList<>();

        while(cr.moveToNext()){
            material m = new material();
            m.id = cr.getInt(0);
            m.material = cr.getString(1);
            m.hsn = cr.getString(2);
            m.tax = cr.getInt(3);

            arrMaterial.add(m);
        }
        database.close();
        return arrMaterial;
    }

    public void addUserDetail(String name, String gst, String address, String number, String Description){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME,name);
        values.put(KEY_USER_GST_NO,gst);
        values.put(KEY_USER_NUMBER,number);
        values.put(KEY_USER_DESCRIPTION,Description);
        values.put(KEY_USER_ADDRESS,address);

        database.insert(TABLE_USER_DETAIL,null,values);
        database.close();
    }

    public ArrayList<userDetail> getUserDetail(){
        SQLiteDatabase database = getReadableDatabase();

        Cursor c = database.rawQuery("SELECT * FROM "+TABLE_USER_DETAIL,null);

        ArrayList<userDetail> userDetails = new ArrayList<>();
        while (c.moveToNext()){
            userDetail user = new userDetail();
            user.id = c.getInt(0);
            user.name = c.getString(1);
            user.gst = c.getString(2);
            user.number = c.getString(3);
            user.description = c.getString(4);
            user.address = c.getString(5);

            userDetails.add(user);
        }

        database.close();
        return userDetails;
    }
    public void createBill(int customerId,int materialId,String date,int quantity, int rate){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MATERIAL_ID,materialId);
        values.put(KEY_ID,customerId);
        values.put(BILL_QUANTITY,quantity);
        values.put(BILL_RATE,rate);
        values.put(BILL_DATE,date);

        database.insert(TABLE_BILLBOOK,null,values);
        database.close();
    }

    public ArrayList<billDetail> getBillBook(){
        SQLiteDatabase database = getReadableDatabase();

        ArrayList<billDetail> arrBill =  new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM "+TABLE_BILLBOOK,null);

        while(c.moveToNext()){
            billDetail b = new billDetail();
            b.bill_no = c.getInt(0);
            b.material_id = c.getInt(1);
            b.customer_id = c.getInt(2);
            b.quantity= c.getInt(3);
            b.rate = c.getInt(4);
            b.date = c.getString(5);

            arrBill.add(b);
        }
        database.close();
        return arrBill;
    }
}