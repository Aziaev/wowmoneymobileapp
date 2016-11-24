package com.rabigol.wowmoney.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.fragments.MainFragment;
import com.rabigol.wowmoney.models.OperationItem;
import com.rabigol.wowmoney.utils.FakeOperations;

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

    // TODO: check app state (стереть данные приложения)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
//

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
                        //TODO: fail after decimal numer entrance
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // TODO: make all subscribe options work
    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIFeedLoadSuccessEvent(APIFeedLoadSuccessEvent event) {
        // TODO : Feed success subscribe
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIFeedLoadFailEvent(APIFeedLoadFailEvent event) {
        // TODO : Feed fail subscribe
    }*/


    // TODO: RESTApi.loadFeed(WHAT IS HERE SHOULD BE?
    @Override
    public void onOperationLoadRequested() {
        loadFeed();
    }
}