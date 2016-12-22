package com.rabigol.wowmoney.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.api.RESTApi;
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.Helper;

import java.util.ArrayList;

import static com.rabigol.wowmoney.utils.OperationImageHelper.giveImage;

/**
 * Created by Artur.Ziaev on 01.11.2016.
 */

public class OperationItemsAdapter extends BaseAdapter {
    private ArrayList<OperationItem> operations = new ArrayList<>();
    private OnOperationItemInteractionListener listener;

    public OperationItemsAdapter(OnOperationItemInteractionListener listener) {
        this.listener = listener;
    }

    public void setOperations(ArrayList<OperationItem> operations) {
        this.operations = operations;
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<OperationItem> operations) {
        this.operations.addAll(operations);
        notifyDataSetChanged();
    }

    public void addItem(OperationItem operationItem){
        this.operations.add(operationItem);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return operations.size();
    }

    @Override
    public Object getItem(int i) {
        if (i < operations.size()) {
            return operations.get(i);
        } else {
            Log.i("getItem", "index out of bounds exception. Operation item not found");
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < operations.size()) {
            return operations.get(position).getId();
        } else {
            Log.i("getItemId", "index out of bounds exception. ItemID not found");
            return 0;
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.operation_item_layout, viewGroup, false);

//            holder.idTextView = (TextView) view.findViewById(R.id.idTextView);
            holder.dateTextView = (TextView) view.findViewById(R.id.timestampTextView);
            holder.operationTypeTextView = (TextView) view.findViewById(R.id.operationTypeTextView);
            holder.operationCategoryTextView = (TextView) view.findViewById(R.id.operationCategoryTextView);
            holder.accountTextView = (TextView) view.findViewById(R.id.accountTextView);
            holder.valueTextView = (TextView) view.findViewById(R.id.valueTextView);
            holder.currencyTextView = (TextView) view.findViewById(R.id.currencyTextView);
            holder.descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
            holder.operationPicTextView = (ImageView) view.findViewById(R.id.operationPicTextView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final OperationItem operation = operations.get(position);

        Long longValue = operation.getValue();
        Double valueDouble = longValue.doubleValue();
        Double value = valueDouble/100;

//        int result = App.getInstance().getBalance("RUB") + App.getInstance().getBalance("USD") * 62 + App.getInstance().getBalance("EUR") * 66;
//        Double totalBalance = ((double) result) / 100;

//        holder.idTextView.setText("Operation ID: " + Long.toString(operation.getId()));
//        holder.balance.setText(totalBalance + " RUB");
        holder.dateTextView.setText("Date: " + Helper.formatTimestampToDateShort(operation.getTimestamp()));
        holder.operationCategoryTextView.setText("Category: " + operation.getOperationCategory());
        holder.operationTypeTextView.setText("Type: " + operation.getOperationType());
        holder.accountTextView.setText("Account: " + operation.getAccount());
        holder.valueTextView.setText(String.format("%.2f", value));
        holder.currencyTextView.setText(operation.getCurrency());
        if (operation.getDescription() != null && !operation.getDescription().isEmpty()) {
            holder.descriptionTextView.setText("# " + operation.getDescription());
        }
        holder.operationPicTextView.setImageResource(giveImage(operation.getOperationCategory()));//TODO: make pic viewable

        holder.valueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onValueClicked(operation.getValue());
            }
        });

        return view;
    }

    public static class ViewHolder {
//        public TextView balance;
        public TextView idTextView;
        public TextView dateTextView;
        public TextView operationTypeTextView;
        public TextView operationCategoryTextView;
        public ImageView operationPicTextView;
        public TextView accountTextView;
        public TextView valueTextView;
        public TextView currencyTextView;
        public TextView descriptionTextView;
    }

    public interface OnOperationItemInteractionListener {
        public void onValueClicked(long value); //click operation value to edit or delete // TODO: Swipe for edit or delete
    }
}






















