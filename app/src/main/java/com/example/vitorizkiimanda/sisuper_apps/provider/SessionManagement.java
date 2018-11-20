package com.example.vitorizkiimanda.sisuper_apps.provider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.vitorizkiimanda.sisuper_apps.activity.BusinessListActivity;
import com.example.vitorizkiimanda.sisuper_apps.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)

    // Email address (make variable public to access from outside)
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_TOKEN = "token";



    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String token, JSONObject result) throws JSONException {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing token in pref
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_ID, result.getString("_id"));
        editor.putString(KEY_USERNAME, result.getString("username"));
        editor.putString(KEY_EMAIL, result.getString("email"));
        editor.putString(KEY_PHONE, result.getString("phone"));
        editor.putString(KEY_ADDRESS, result.getString("address"));
        editor.putString(KEY_IMAGE, result.getString("image"));

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, BusinessListActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
