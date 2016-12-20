package com.rabigol.wowmoney.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.api.RESTApi;
import com.rabigol.wowmoney.base.EventBusActivity;
import com.rabigol.wowmoney.events.APILoginFailEvent;
import com.rabigol.wowmoney.events.APILoginSuccessEvent;
import com.rabigol.wowmoney.events.APIRestoreFailEvent;
import com.rabigol.wowmoney.events.APIRestoreSuccessEvent;
import com.rabigol.wowmoney.fragments.LoginFragment;
import com.rabigol.wowmoney.fragments.RegisterFragment;
import com.rabigol.wowmoney.fragments.RestoreFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AuthActivity extends EventBusActivity implements
        LoginFragment.OnFragmentInteractionListener,
        RestoreFragment.OnFragmentInteractionListener,
        RegisterFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (App.getInstance().getAppState() == App.APP_STATE_LOGGED) {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // TODO: think about this
            return;
        } else

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, LoginFragment.newInstance())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void onLoginAttemptPerformed(String login, String password) {
        RESTApi.getInstance().login(login, password);
        showProgressDialog();
    }

    @Override
    public void onRestoreAttemptPerformed(String login) {
        //TODO: make REST call
        showProgressDialog();
        RESTApi.restore(login);
    }

    @Override
    public void onRegistrationPageRequested() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new RegisterFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("register")
                .commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPILoginFailEvent(APILoginFailEvent event) {
        dismissProgressDialog();
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_occured)
                .setMessage(R.string.error_try_again)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
        //TODO: handle event
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPILoginSuccessEvent(APILoginSuccessEvent event) {
        //TODO: handle event
        dismissProgressDialog();
        App.getInstance().setAppState(App.APP_STATE_LOGGED);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPIRestoreSuccessEvent(APIRestoreSuccessEvent event) {
        //TODO: handle event
        dismissProgressDialog();
        new AlertDialog.Builder(this)
                .setTitle(R.string.restore_success_title)
                .setMessage(R.string.restore_message_sent_email)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        getSupportFragmentManager().popBackStack();
                    }
                })
                .create()
                .show();
    }

    public void onAPIRestoreFailEvent(APIRestoreFailEvent event) {
        dismissProgressDialog();
        new AlertDialog.Builder(this)
                .setTitle(R.string.restore_fail_message)
                .setMessage(R.string.restore_try_again_message)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
        //TODO: handle event
    }
}
