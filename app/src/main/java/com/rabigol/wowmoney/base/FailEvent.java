package com.rabigol.wowmoney.base;

import com.android.volley.VolleyError;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;

public class FailEvent {
    public static final int UNKNOWN_ERROR = 10;
    private VolleyError mError;

    public FailEvent() {
        mError = null;
    }

    public FailEvent(VolleyError error) {
        mError = error;
    }

    //TODO: ывставить обработчики для разных типов ошибок
    public int getError() {
        return UNKNOWN_ERROR;
    }

    public String getErrorMessage() {
        return App.getInstance().getString(R.string.api_error_unknown);
    }
}
