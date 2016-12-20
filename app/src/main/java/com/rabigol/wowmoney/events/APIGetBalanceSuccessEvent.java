package com.rabigol.wowmoney.events;

import android.util.Log;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.base.SuccessEvent;

import org.json.JSONException;
import org.json.JSONObject;

public class APIGetBalanceSuccessEvent extends SuccessEvent {
    public APIGetBalanceSuccessEvent(JSONObject json) {
        super(json);
        try {
            int userId = json.getInt("id");
            String userEmail = json.getString("email");
            App.getInstance().setAppLoggedUser(userId, userEmail);
            Log.i("APILoginSuc! userId = ", ""+App.getInstance().getAppLoggedUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
