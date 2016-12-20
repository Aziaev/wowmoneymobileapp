package com.rabigol.wowmoney.events;

import android.util.Log;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.models.FeedItem;
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.FakeOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class APIFeedLoadSuccessEvent {
    private ArrayList<OperationItem> operations;

    public APIFeedLoadSuccessEvent(ArrayList<OperationItem> operations){
        this.operations = operations;
    }

    public ArrayList<OperationItem> getOperations() {
        return operations;
    }
}
