package com.rabigol.wowmoney.events;

import com.android.volley.VolleyError;
import com.rabigol.wowmoney.base.FailEvent;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class APIFeedLoadFailEvent extends FailEvent {
    public APIFeedLoadFailEvent() {
    }

    public APIFeedLoadFailEvent(VolleyError error) {
        super(error);
    }

}
