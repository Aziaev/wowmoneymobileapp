package com.rabigol.mysocial.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rabigol.mysocial.R;
import com.rabigol.mysocial.api.RESTApi;
import com.rabigol.mysocial.events.APILoginFailEvent;
import com.rabigol.mysocial.fragments.LoginFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

// Step 1. Create activity

public class AuthActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, LoginFragment.newInstance())
                .addToBackStack("login")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Subscribe
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unsubscribe from events
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoginAttemptPerformed(String login, String password) {
        RESTApi.login(login, password);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAPILoginFailEvent(APILoginFailEvent event){
        dismissProgressDialog();

        // TODO: show reason
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_occured)
                .setMessage(R.string.error_try_again)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getString(R.string.loading_progress)
            );
        }
        if (!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    private void dismissProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
