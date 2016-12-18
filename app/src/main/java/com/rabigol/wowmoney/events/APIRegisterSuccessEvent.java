package com.rabigol.wowmoney.events;

import org.json.JSONObject;

import com.rabigol.wowmoney.base.SuccessEvent;

public class APIRegisterSuccessEvent extends SuccessEvent {
    public APIRegisterSuccessEvent(JSONObject json) {
        super(json);
    }
}
