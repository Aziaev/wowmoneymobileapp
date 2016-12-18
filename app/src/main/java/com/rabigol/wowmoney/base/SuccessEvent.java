package com.rabigol.wowmoney.base;

import org.json.JSONObject;

public class SuccessEvent {
    private JSONObject mJson;

    public SuccessEvent(JSONObject json) {
        mJson = json;
    }

    public JSONObject getJson() {
        return mJson;
    }
}
