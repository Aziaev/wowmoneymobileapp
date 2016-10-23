package com.rabigol.mysocial.api;

import android.os.Handler;

import com.rabigol.mysocial.events.APILoginFailEvent;
import com.rabigol.mysocial.events.APILoginSuccessEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class RESTApi {
    public static void login(String login, String password) {
        //TODO: make api call

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: make some result
//                EventBus.getDefault().post(new APILoginFailEvent());
                EventBus.getDefault().post(new APILoginSuccessEvent());
            }
        }, 1500);
    }

    public static void restore(String login) {
        //TODO: make api call
    }
}
