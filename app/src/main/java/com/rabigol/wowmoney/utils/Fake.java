package com.rabigol.wowmoney.utils;

import java.util.ArrayList;

import com.rabigol.wowmoney.models.FeedItem;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class Fake {
    private ArrayList<FeedItem> feedItems = new ArrayList<>();
    private static Fake instance;

    public Fake() {
        for (int i = 0; i < 50; i++) {
            feedItems.add(new FeedItem(i, i, "My Friend " + i, 1477389789, "Hello world " + i, null));
        }
    }

    public ArrayList<FeedItem> getFeedItems() {
        return feedItems;
    }

    public void addFeedItemById(int ownerId, String ownerName, String message) {
        int id = feedItems.size() > 0 ? feedItems.get(feedItems.size() - 1).getId() + 1 : 1;
        feedItems.add(new FeedItem(id, ownerId, ownerName, (int) ((System.currentTimeMillis() / 1000)), message, null));
    }

    public static Fake getInstance() {
        if (instance == null) {
            instance = new Fake();
        }

        return instance;
    }
}