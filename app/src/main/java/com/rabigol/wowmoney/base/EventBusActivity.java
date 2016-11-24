package com.rabigol.wowmoney.base;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class EventBusActivity extends BaseActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
