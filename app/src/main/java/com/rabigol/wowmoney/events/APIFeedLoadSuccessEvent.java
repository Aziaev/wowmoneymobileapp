package com.rabigol.wowmoney.events;

import com.rabigol.wowmoney.models.FeedItem;

import java.util.ArrayList;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class APIFeedLoadSuccessEvent {
    private ArrayList<FeedItem> items;

    public APIFeedLoadSuccessEvent(ArrayList<FeedItem> items){
        this.items = items;
    }

    public ArrayList<FeedItem> getItems(){
        return items;
    }
}
