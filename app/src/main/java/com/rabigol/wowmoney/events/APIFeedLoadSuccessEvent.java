package com.rabigol.wowmoney.events;

import android.util.Log;

import com.rabigol.wowmoney.models.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class APIFeedLoadSuccessEvent {
    private ArrayList<FeedItem> items;

    public APIFeedLoadSuccessEvent(JSONArray jsonArray){
        System.out.println(jsonArray);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = null;
            //TODO: parse to array
            try {
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("jsonObject1", jsonObject1.toString());
        }
    }

    public ArrayList<FeedItem> getItems(){
        return items;
    }
}
