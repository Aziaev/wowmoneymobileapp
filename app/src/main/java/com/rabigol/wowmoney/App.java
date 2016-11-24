package com.rabigol.wowmoney;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class App extends Application {
    private static App instance;
    public static final int APP_STATE_LOGGED = 10;
    public static final int APP_STATE_NOTLOGGED = 11;
    private int state;
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
}
