package com.rabigol.wowmoney.events;

import com.rabigol.wowmoney.base.SuccessEvent;

import org.json.JSONObject;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class APILoginSuccessEvent extends SuccessEvent {
    public APILoginSuccessEvent(JSONObject json) {
        super(json);
    }
}
