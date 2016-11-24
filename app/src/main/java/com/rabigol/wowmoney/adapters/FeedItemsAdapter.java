package com.rabigol.wowmoney.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.models.FeedItem;
import com.rabigol.wowmoney.utils.Helper;

import java.util.ArrayList;

/**
 * Created by Artur.Ziaev on 23.10.2016.
 */

public class FeedItemsAdapter extends BaseAdapter {
    private ArrayList<FeedItem> items = new ArrayList<>();
    private OnFeedItemInteractionListener listener;

    public FeedItemsAdapter(OnFeedItemInteractionListener listener) {
        this.listener = listener;
    }

    public void setItems(ArrayList<FeedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<FeedItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i); }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.feed_item_layout, viewGroup, false);

            holder.ownerTextView = (TextView) view.findViewById(R.id.ownerNameTextView);
            holder.dateTextView = (TextView) view.findViewById(R.id.dateTextView);
            holder.messageTextView = (TextView) view.findViewById(R.id.messageTextView);
            holder.photoImageView = (ImageView) view.findViewById(R.id.photoTextView);
            view.setTag(holder);
            Log.i("TAG", "Inflate");
        } else {
            holder = (ViewHolder) view.getTag();
            Log.i("TAG", "Inflate");
        }

        final FeedItem item = items.get(i);

        holder.ownerTextView.setText(item.getOwnerName());
        holder.dateTextView.setText(Helper.formatTimestampToDateTime(item.getTimestamp()));
        if (item.getMessage() != null) {
            holder.messageTextView.setText(item.getMessage());
        }
        if (item.getPhoto() != null && !item.getPhoto().isEmpty()) {
            //TODO: set image view
        }
        holder.ownerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOwnerNameClicked(item.getOwnerId());
            }
        });
        return view;
    }

    private static class ViewHolder {
        public TextView ownerTextView;
        public TextView dateTextView;
        public ImageView photoImageView;
        public TextView messageTextView;
    }

    public interface OnFeedItemInteractionListener {
        public void onOwnerNameClicked(long ownerId);
    }
}
