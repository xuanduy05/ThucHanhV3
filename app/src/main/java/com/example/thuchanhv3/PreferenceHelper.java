package com.example.thuchanhv3;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private final String ID = "id";
    private final String INTRO = "intro";
    private final String NAME = "name";
    private final String HOBBY = "hobby";
    private final String CLASS = "class";
    private final String CONTACT = "contact";
    private final String ADDRESS = "address";
    private final String ADMIN_ID = "admin_id";
    private final String USERNAME = "user_name";
    private final String PASSWORD = "password";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context) {
        app_prefs = context.getSharedPreferences("shared",
                Context.MODE_PRIVATE);
        this.context = context;
    }
    public void putID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ID,loginorout);
        edit.commit();
    }
    public String getId() {
        return app_prefs.getString(ID, "");
    }

    public void putIsLogin(boolean loginorout){
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginorout);
        edit.commit();
    }
    public boolean getIsLogin() {
        return app_prefs.getBoolean(INTRO, false);
    }

    public void putName(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(NAME, loginorout);
        edit.commit();
    }
    public String getName() {
        return app_prefs.getString(NAME, "");
    }

    public void putHobby(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(HOBBY, loginorout);
        edit.commit();
    }
    public String getHobby() {
        return app_prefs.getString(HOBBY, "");
    }

    public void putClass(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CLASS, loginorout);
        edit.commit();
    }
    public String getClass1() {
        return app_prefs.getString(CLASS, "");
    }

    public void putAddress(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ADDRESS, loginorout);
        edit.commit();
    }
    public String getAddress() {
        return app_prefs.getString(ADDRESS, "");
    }

    public void putContact(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(CONTACT, loginorout);
        edit.commit();
    }
    public String getContact() {
        return app_prefs.getString(CONTACT, "");
    }

    public void putAdmin_ID(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(ADMIN_ID, loginorout);
        edit.commit();
    }
    public String getAdmin_ID() {
        return app_prefs.getString(ADMIN_ID, "");
    }

    public void putUsername(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(USERNAME, loginorout);
        edit.commit();
    }
    public String getUsername() {
        return app_prefs.getString(USERNAME, "");
    }

    public void putPassword(String loginorout) {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(PASSWORD, loginorout);
        edit.commit();
    }
    public String getPassword() {
        return app_prefs.getString(PASSWORD, "");
    }

}
