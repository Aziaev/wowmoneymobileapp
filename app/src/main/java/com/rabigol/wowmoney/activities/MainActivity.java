package com.rabigol.wowmoney.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.api.RESTApi;
import com.rabigol.wowmoney.events.APIFeedLoadFailEvent;
import com.rabigol.wowmoney.events.APIFeedLoadSuccessEvent;
import com.rabigol.wowmoney.fragments.MainFragment;
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.FakeOperations;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.rabigol.wowmoney.api.RESTApi.loadFeed;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationAccounts;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationCategories;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationCurrencies;
import static com.rabigol.wowmoney.utils.FakeOperations.getOperationTypes;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        MainFragment.OnFragmentInteractionListener {

    private static String CURRENT_FRAGMENT = "currentFragment";
    private static int FRAGMENT_MAIN = 10;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //FAB button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.fab_add_new_operation);
                final View fabDialogView = getLayoutInflater().inflate(R.layout.operation_add_dialog, null);

                final Spinner operationTypesSpinner = (Spinner) fabDialogView.findViewById(R.id.operation_add_type_spinner);
                ArrayAdapter operationTypesSpinnerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getOperationTypes());
                operationTypesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationTypesSpinner.setAdapter(operationTypesSpinnerAdapter);

                final Spinner operationCategoriesSpinner = (Spinner) fabDialogView.findViewById(R.id.operation_add_category_spinner);
                ArrayAdapter operationCategoriesSpinnerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getOperationCategories());
                operationCategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationCategoriesSpinner.setAdapter(operationCategoriesSpinnerAdapter);

                final Spinner operationAccountsSpinner = (Spinner) fabDialogView.findViewById(R.id.operation_add_account_spinner);
                ArrayAdapter operationAccountsSpinnerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getOperationAccounts());
                operationCategoriesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationAccountsSpinner.setAdapter(operationAccountsSpinnerAdapter);

                final EditText value = (EditText) fabDialogView.findViewById(R.id.operation_add_value);

                final Spinner operationCurrencySpinner = (Spinner) fabDialogView.findViewById(R.id.operation_add_currency_spinner);
                ArrayAdapter operationCurrencySpinnerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, getOperationCurrencies());
                operationCurrencySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                operationCurrencySpinner.setAdapter(operationCurrencySpinnerAdapter);

                final EditText description = (EditText) fabDialogView.findViewById(R.id.operation_add_description);

                builder.setView(fabDialogView);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Double aDouble = Double.parseDouble(value.getText().toString());
                        aDouble = aDouble *100;
                        final Long operationValue = aDouble.longValue();

                        OperationItem operationItem = new OperationItem(
                                operationTypesSpinner.getSelectedItem().toString()
                                , operationCategoriesSpinner.getSelectedItem().toString()
                                , operationAccountsSpinner.getSelectedItem().toString()
                                , operationValue
                                , operationCurrencySpinner.getSelectedItem().toString()
                                , description.getText().toString());
                        FakeOperations.addOperation(operationItem);
//                        com.rabigol.wowmoney.adapters.OperationItemsAdapter.addItem(operationItem);
                        //TODO: There is bug here. Sometimes app crushes with notifyexception
                        loadFeed();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //MainFragment - things from main page
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, MainFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("main")
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_accounts) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.menu_dialog_title_accounts);
            final View menuDialogView = getLayoutInflater().inflate(R.layout.menu_dialog_accounts, null);

            final TextView textView1 = (TextView) menuDialogView.findViewById(R.id.menu_accounts_dialog_textview1);
            textView1.setText(getOperationAccounts().get(0));

            final TextView textView2 = (TextView) menuDialogView.findViewById(R.id.menu_accounts_dialog_textview2);
            textView2.setText(getOperationAccounts().get(1));

            final TextView textView3 = (TextView) menuDialogView.findViewById(R.id.menu_accounts_dialog_textview3);
            textView3.setText(getOperationAccounts().get(2));


            builder.setView(menuDialogView);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.menu_categories) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(R.string.menu_dialog_title_categories);
            final View menuDialogView = getLayoutInflater().inflate(R.layout.menu_dialog_categories, null);

            final TextView textView1 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview1);
            textView1.setText(getOperationCategories().get(0));

            final TextView textView2 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview2);
            textView2.setText(getOperationCategories().get(1));

            final TextView textView3 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview3);
            textView3.setText(getOperationCategories().get(2));

            final TextView textView4 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview4);
            textView4.setText(getOperationCategories().get(3));

            final TextView textView5 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview5);
            textView5.setText(getOperationCategories().get(4));

            final TextView textView6 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview6);
            textView6.setText(getOperationCategories().get(5));

            final TextView textView7 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview7);
            textView7.setText(getOperationCategories().get(6));

            final TextView textView8 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview8);
            textView8.setText(getOperationCategories().get(7));

            final TextView textView9 = (TextView) menuDialogView.findViewById(R.id.menu_categories_dialog_textview9);
            textView9.setText(getOperationCategories().get(8));


            builder.setView(menuDialogView);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.menu_currencies) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("All currencies");
            final View menuDialogView = getLayoutInflater().inflate(R.layout.menu_dialog_currencies, null);

            final TextView textView1 = (TextView) menuDialogView.findViewById(R.id.menu_currencies_dialog_textview1);
            textView1.setText(getOperationCurrencies().get(0));

            final TextView textView2 = (TextView) menuDialogView.findViewById(R.id.menu_currencies_dialog_textview2);
            textView2.setText(getOperationCurrencies().get(1));

            final TextView textView3 = (TextView) menuDialogView.findViewById(R.id.menu_currencies_dialog_textview3);
            textView3.setText(getOperationCurrencies().get(2));


            builder.setView(menuDialogView);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.menu_statistics) {

        } else if (id == R.id.menu_settings) {
            RESTApi.getInstance().loadItems(App.getInstance().getAppLoggedUserId());
            showProgressDialog();

        } else if (id == R.id.menu_logout) {
            showProgressDialog();
            App.getInstance().setAppState(App.APP_STATE_NOTLOGGED);
            Log.i("TAG", "appstate is " + App.APP_STATE_NOTLOGGED);
            startActivity(new Intent(this, StartActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
        }
        if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    protected void dismissProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    // TODO: make all subscribe options work
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIFeedLoadSuccessEvent(APIFeedLoadSuccessEvent event) {
        // TODO : Feed success subscribe
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIFeedLoadFailEvent(APIFeedLoadFailEvent event) {
        // TODO : Feed fail subscribe
    }


    // TODO: RESTApi.loadFeed(WHAT IS HERE SHOULD BE?
    @Override
    public void onOperationLoadRequested() {
        loadFeed();
    }

    //TODO: Used?
    @Override
    protected void onStop() {
        super.onStop();
    }
}
