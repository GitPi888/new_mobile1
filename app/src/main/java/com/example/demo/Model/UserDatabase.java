package com.example.demo.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDatabase extends SQLiteOpenHelper {
    private final Context context;
    public static String TB_USER = "USER";
    private static final String USER_SESSION_PREF = "user_session";
    // This object is used to read stored values
    private static final String KEY_USER_EMAIL = "user_email"; // Adjust the key as needed
    private static final String KEY_USER_ID = "user_id"; // Adjust the key as needed

    //User information
    public static String TB_USER_ID_USER = "ID_USER";
    public static String TB_USER_NAME = "NAME";
    public static String TB_USER_EMAIL = "EMAIL";
    public static String TB_USER_PASSWORD = "PASSWORD";
    private SQLiteDatabase db;
    public UserDatabase(@Nullable Context context) {
        super(context,"QuizzApp", null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String tbUser = " CREATE TABLE " + TB_USER + " ( " + TB_USER_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TB_USER_NAME + " TEXT, " + TB_USER_EMAIL + " TEXT, " + TB_USER_PASSWORD + " TEXT) ";
        db.execSQL(tbUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS  "+TB_USER);
        onCreate(db);
    }
    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
    public SQLiteDatabase getData(){
        return this.getReadableDatabase();
    }
    public void register(String user_Name , String email,String password) {
        ContentValues cv = new ContentValues();
        cv.put(TB_USER_NAME, user_Name);
        cv.put(TB_USER_EMAIL, email);
        cv.put(TB_USER_PASSWORD, password);
        SQLiteDatabase sq = getWritableDatabase();
        sq.insert(TB_USER, null, cv);
        sq.close();
    }
    public int login(String email,String password){
           int rs=0;
          String str[] = new String[2];
          str[0] = email;
          str[1] = password;
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.rawQuery("SELECT*FROM USER where EMAIL=? and PASSWORD=?", str);
          if (c.moveToFirst()) {
              rs = 1;
      }
        return rs;
      }
    public Boolean CheckEmailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT 1 FROM " + TB_USER + " WHERE " + TB_USER_EMAIL + " = ?", new String[]{email});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
    public boolean isValid(String pass){
        int f1=0,f2=0,f3=0;
        if(pass.length()<0){
            return false;
        }
        else{
            for (int i =0;i<pass.length();i++){
                if(Character.isLetter(pass.charAt(i)))
                    f1=1;
            }
            for (int h =0;h<pass.length();h++){
                if(Character.isDigit(pass.charAt(h)));
                f2=1;
            }
            for (int k=0;k<pass.length();k++)
            {
                char c = pass.charAt(k);
                if(c>=33&&c<=46||c==64){
                    f3=1;
                }
            }
            if(f1==1&&f2==1&&f3==1)
                return true;
            return false;
        }
    }
    public  boolean UpdatePassword(String email,String new_password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("PASSWORD",new_password);
       int check = db.update("USER",cv,"EMAIL=?",new String[]{email});
       if(check<=0){
           return false;
       }
       return true;
    }
    public  boolean UpdateUserName(String email,String user_Name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NAME",user_Name);
        int check = db.update("USER",cv,"EMAIL=?",new String[]{email});
        if(check<=0){
            return false;
        }
        return true;
    }
    public boolean DeleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select*from USER where EMAIL=?",new String[]{email});
        if(c.getCount()>0)
        {
            long rs = db.delete("USER","EMAIL=?",new String[]{email});
            if(rs==-1)
                return false;
            else
                return true;
        }
        else {
            return false;
        }
    }
}
