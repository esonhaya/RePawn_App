package com.example.systemscoreinc.repawn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.systemscoreinc.repawn.Login_and_Before.LoginActivity;

import java.util.HashMap;

public class Session {

    //   private static final String KEY_USERNAME = "buyer_username";
    private static final String KEY_EMAIL = "RePawner_email";
    private static final String KEY_ID = "RePawner_ID";
    private static final String KEY_ACTIVATED = "0";
    private static final String KEY_IMAGE="RePawner_image";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor fornew;
    SharedPreferences fornewprefs;
    Context ctx;

    // constructor
    public Session(Context ctx) {
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("RePawn", Context.MODE_PRIVATE);
        fornewprefs = ctx.getSharedPreferences("RePawn", Context.MODE_PRIVATE);
        editor = prefs.edit();
        fornew = fornewprefs.edit();
    }

    public void setLoggedin(boolean loggedin, int activated) {
        editor.putBoolean("loggedInmode", loggedin);
        editor.putInt("activatedmode", activated);
        editor.commit();
    }


    public void justactivated() {
        editor.putInt(KEY_ACTIVATED, 1);
        editor.putInt("activatedmode", 1);
        editor.commit();
    }
    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);

    }
    public int activated() {
        return prefs.getInt(KEY_ACTIVATED, 0);
    }

    public void logoutUser() {
        // clearing all user data from shared preferences

        editor.clear();
        editor.commit();

        // after logout redirect user to login Activity
        Intent i = new Intent(ctx, LoginActivity.class);

        // Closing all the Acvtivities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // starting Login Activity
        ctx.startActivity(i);

    }

    public String getEmail() {

        return prefs.getString(KEY_EMAIL, null);
    }

   public String getKeyImage(){
        return prefs.getString(KEY_IMAGE,null);
   }

    public int getID() {
        return prefs.getInt(KEY_ID, 0);
    }

    public void setID(int id) {
        editor.putInt(KEY_ID, id);
        editor.commit();
    }
    public void setEmail(String email){
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    public void createUserLoginSession(int id, String email, int activated) {

        editor.putInt(KEY_ID, id);
        //    editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_ACTIVATED, activated);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {

        // use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_ID, String.valueOf(prefs.getInt(KEY_ID, 0)));
        //   user.put(KEY_USERNAME, prefs.getString(KEY_USERNAME, null));
        user.put(KEY_EMAIL, prefs.getString(KEY_EMAIL, null));


        return user;
    }
}
