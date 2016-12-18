package com.rabigol.wowmoney.events;

import com.android.volley.VolleyError;
import com.rabigol.wowmoney.base.FailEvent;

public class APILoginFailEvent extends FailEvent{
    public APILoginFailEvent() {
    }

    public APILoginFailEvent(VolleyError error) {
        super(error);
    }
}
