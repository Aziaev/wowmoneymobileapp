package com.rabigol.wowmoney.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.rabigol.wowmoney.R;

/**
 * Created by Artur.Ziaev on 24.10.2016.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

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
}
