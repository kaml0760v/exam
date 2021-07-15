package com.example.mybusinessbook.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME =  "android_api";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_UID = "UID";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobilenumber";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DOB = "dateofbirth";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CATID = "categoryID";
    private static final String KEY_CATNAME = "categoryname";
    private static final String KEY_CATTYPE = "categorytype";
    private static final String TABLE_USER = "userdetails";
    private static final String TABLE_CAT = "category";



    public SQLiteHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE="CREATE TABLE " + TABLE_USER + "("
                + KEY_UID + " INTEGER PRIMARY KEY,"+ KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_MOBILE + " TEXT,"
                + KEY_GENDER + " TEXT," + KEY_DOB + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";
        String CREATE_CATEGORY_TABLE= "CREATE TABLE " + TABLE_CAT + "("
                + KEY_CATID + " INTEGER PRIMARY KEY,"
                + KEY_CATNAME + " TEXT,"
                + KEY_CATTYPE + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAT);
        onCreate(db);
    }
    public void adduser(String name,String email,String mobile,String gender,String dob,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_EMAIL,email);
        values.put(KEY_MOBILE,mobile);
        values.put(KEY_GENDER,gender);
        values.put(KEY_DOB,dob);
        values.put(KEY_PASSWORD,password);
        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void addcategory(String name,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATNAME,name);
        values.put(KEY_CATTYPE,type);
        db.insert(TABLE_CAT,null,values);
        db.close();
    }
        public ArrayList<String> getAllIncomeCategory(){
            ArrayList<String> category = new ArrayList<>();
            String query = "SELECT  * FROM " + TABLE_CAT + " where "+ KEY_CATTYPE + " = " + "'Income'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            cursor.moveToFirst();
            if(cursor.getCount() >0){
                do{
                    category.add(cursor.getString(1));
                }while(cursor.moveToNext());
                //category.put("NAME",cursor.getString(1));

            }
            cursor.close();
            db.close();
            return category;
        }
    public ArrayList<String> getAllExpenseCategory(){
        ArrayList<String> category = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_CAT + " where "+ KEY_CATTYPE + "=" + " 'Expense'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        if(cursor.getCount() >0){
            do{
                category.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return category;
    }
    public void deleteCategory(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAT,KEY_CATNAME + " = ?",new String[]{s});
        db.close();
    }
    public void UpdateCategory(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATNAME, s);
        db.update(TABLE_CAT,values,KEY_CATNAME +" = ?", new String[]{s});
        db.close();
    }

}
