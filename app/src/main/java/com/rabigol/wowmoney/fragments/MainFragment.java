package com.rabigol.wowmoney.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.activities.MainActivity;
import com.rabigol.wowmoney.adapters.OperationItemsAdapter;
import com.rabigol.wowmoney.base.EventBusFragment;
import com.rabigol.wowmoney.events.APIOperationsLoadFailEvent;
import com.rabigol.wowmoney.events.APIOperationsLoadSuccessEvent;
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.FakeOperations;

import static com.rabigol.wowmoney.api.RESTApi.loadFeed;
import static com.rabigol.wowmoney.utils.FakeOperations.deleteOperationById;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationAccounts;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationCategories;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationCurrencies;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationTypes;

public class MainFragment extends EventBusFragment implements
        OperationItemsAdapter.OnOperationItemInteractionListener {

    private OperationItemsAdapter adapter = new OperationItemsAdapter(this);
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefresh;

    public MainFragment() {
        //Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //Long click for delete operation
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(R.string.alert_dialog_delete_ask);
                final View viewAndEditView = getActivity().getLayoutInflater().inflate(R.layout.operation_delete_dialog, null);
                final long idForDelete = id;
                builder.setView(viewAndEditView);

                builder.setPositiveButton(R.string.confirm_deleting_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOperationById(idForDelete);
                        Toast.makeText(getContext(), R.string.toast_item_deleted, Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        //View and edit operation by click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.alert_view_and_edit);

                final OperationItem operationItemEditAndView = FakeOperations.getOperationItemById(id);

                final View viewAndEditView = getActivity().getLayoutInflater().inflate(R.layout.operation_edit_dialog, null);

                // Types Spinner
                final Spinner operationTypesSpinner = (Spinner) viewAndEditView.findViewById(R.id.operation_edit_type_spinner);
                ArrayAdapter operationTypesSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getOperationTypes());
                operationTypesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationTypesSpinner.setAdapter(operationTypesSpinnerAdapter);
                // Spinner setDefaultValue
                operationTypesSpinner.setSelection(getOperationTypes().indexOf(operationItemEditAndView.getOperationType()));

                // Categories Spinner
                final Spinner operationCategoriesSpinner = (Spinner) viewAndEditView.findViewById(R.id.operation_edit_category_spinner);
                ArrayAdapter operationCategoriesSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getOperationCategories());
                operationCategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationCategoriesSpinner.setAdapter(operationCategoriesSpinnerAdapter);
                // Spinner setDefaultValue
                operationCategoriesSpinner.setSelection(getOperationCategories().indexOf(operationItemEditAndView.getOperationCategory()));

                // Accounts Spinner
                final Spinner operationAccountsSpinner = (Spinner) viewAndEditView.findViewById(R.id.operation_edit_account_spinner);
                ArrayAdapter operationAccountsSpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getOperationAccounts());
                operationCategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationAccountsSpinner.setAdapter(operationAccountsSpinnerAdapter);
                // Spinner setDefaultValue
                operationAccountsSpinner.setSelection(getOperationAccounts().indexOf(operationItemEditAndView.getAccount()));

                final EditText value = (EditText) viewAndEditView.findViewById(R.id.operation_get_value);
                Long operationValue = operationItemEditAndView.getValue();
                Double valueDouble = operationValue.doubleValue();
                Double valueToFormat = valueDouble/100;
                value.setText(String.format("%.2f", valueToFormat));

                // Currencies Spinner
                final Spinner operationCurrencySpinner = getSpinner(operationItemEditAndView, viewAndEditView);

                final EditText description = (EditText) viewAndEditView.findViewById(R.id.operation_get_description);
                description.setText(operationItemEditAndView.getDescription());

                builder.setView(viewAndEditView);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        operationItemEditAndView.setOperationType(operationTypesSpinner.getSelectedItem().toString());
                        operationItemEditAndView.setOperationCategory(operationCategoriesSpinner.getSelectedItem().toString());
                        operationItemEditAndView.setAccount(operationAccountsSpinner.getSelectedItem().toString());

                        //Conver inputed double to long
                        Double aDouble = Double.parseDouble(value.getText().toString());
                        aDouble = aDouble *100;
                        final Long operationValue = aDouble.longValue();
                        operationItemEditAndView.setValue(operationValue);
                        operationItemEditAndView.setCurrency(operationCurrencySpinner.getSelectedItem().toString());
                        operationItemEditAndView.setDescription(description.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        swipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });

        loadData();
        setHasOptionsMenu(true);
        return rootView;
    }

    @NonNull
    private Spinner getSpinner(OperationItem operationItemEditAndView, View viewAndEditView) {
        final Spinner operationCurrencySpinner = (Spinner) viewAndEditView.findViewById(R.id.operation_edit_currency_spinner);
        ArrayAdapter operationCurrencySpinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, getOperationCurrencies());
        operationCurrencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationCurrencySpinner.setAdapter(operationCurrencySpinnerAdapter);
        // Spinner setDefaultValue
        operationCurrencySpinner.setSelection(getOperationCurrencies().indexOf(operationItemEditAndView.getCurrency()));
        return operationCurrencySpinner;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.fragment_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void loadData() {
        if (mListener != null) {
            mListener.onOperationLoadRequested();
            swipeRefresh.setRefreshing(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIOperationsLoadSuccessEvent(APIOperationsLoadSuccessEvent event) {
        adapter.setOperations(event.getOperations());
        swipeRefresh.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIOperationLoadFailEvent(APIOperationsLoadFailEvent event){
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onValueClicked(long value) {
        Log.i("TAG", "Value clicked: " + value);
    }

    public interface OnFragmentInteractionListener {
        void onOperationLoadRequested();
    }
}
