package com.rabigol.wowmoney;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class App extends Application {
    private static App instance;
    public static final int APP_STATE_LOGGED = 10;
    public static final int APP_STATE_NOTLOGGED = 11;
    private int state;
    private int appStateLoggedUserId;
    private String appStateLoggedUserEmail;
    private SharedPreferences preferences;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(getString(R.string.app_preferences), Context.MODE_PRIVATE);
        state = preferences.getInt(getString(R.string.app_state), APP_STATE_NOTLOGGED);
        appStateLoggedUserId = preferences.getInt(getString(R.string.app_logged_userid), 0);
        appStateLoggedUserEmail = preferences.getString(getString(R.string.app_logged_user_email), null);
        if (state == APP_STATE_LOGGED) {
            // TODO: retieve user data
        }
        // TODO: handle global app state
    }

    public int getAppState() {
        return state;
    }

    public void setAppState(int state){
        this.state = state;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.app_state), this.state);
        editor.apply();
    }

    public void setAppLoggedUser(int userId, String email) {
        this.appStateLoggedUserId = userId;
        this.appStateLoggedUserEmail = email;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.app_logged_userid), userId);
        editor.putString(getString(R.string.app_logged_user_email), email);
        editor.apply();
    }

    public int getAppLoggedUserId() {
            return App.getInstance().appStateLoggedUserId;
    }

    public String getAppLoggedUserEmail() {
//        if (state == APP_STATE_LOGGED) {
//            Log.i("getAppLoggedUserEmail()", this.appStateLoggedUserEmail);
//            Log.i("App.getEmailmeth", App.getInstance().appStateLoggedUserEmail);
            return App.getInstance().appStateLoggedUserEmail;
//        } else return "Not logged. Check";
    }

    public void setBalance(String currency, int sum){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(currency, sum);
        Log.i("Setted " + currency, " " + sum);
        editor.apply();
    }

    public int getBalance(String currency) {
        return preferences.getInt(currency, 0);
    }
}
