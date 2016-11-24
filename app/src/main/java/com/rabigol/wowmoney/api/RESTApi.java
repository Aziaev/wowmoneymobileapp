package com.rabigol.wowmoney.api;

import android.os.Handler;

import com.rabigol.wowmoney.events.APILoginSuccessEvent;
import com.rabigol.wowmoney.events.APIOperationsLoadSuccessEvent;
import com.rabigol.wowmoney.utils.FakeOperations;

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
        }, 1000);
    }

    public static void restore(String login) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //TODO: Something
            }
        }, 1000);
    }

    public static void loadFeed() {
        // TODO:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new APIOperationsLoadSuccessEvent(FakeOperations.getInstance().getOperationItems()));
            }
        }, 1000);
    }
}
