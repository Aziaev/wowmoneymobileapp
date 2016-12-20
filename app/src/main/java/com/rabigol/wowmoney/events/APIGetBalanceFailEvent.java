package com.rabigol.wowmoney.events;

import com.android.volley.VolleyError;
import com.rabigol.wowmoney.base.FailEvent;

/**
 * Created by Artur.Ziaev on 13.11.2016.
 */

public class APIGetBalanceFailEvent extends FailEvent {
    public APIGetBalanceFailEvent() {
    }

    public APIGetBalanceFailEvent(VolleyError error) {
        super(error);
    }
}
