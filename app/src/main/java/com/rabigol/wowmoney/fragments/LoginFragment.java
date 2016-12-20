package com.rabigol.wowmoney.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rabigol.wowmoney.App;
import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.activities.MainActivity;
import com.rabigol.wowmoney.activities.RegisterActivity;
import com.rabigol.wowmoney.utils.Helper;

import static android.R.attr.button;

public class LoginFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText username = (EditText) rootView.findViewById(R.id.login_username);
        final EditText pass = (EditText) rootView.findViewById(R.id.login_password);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = username.getText().toString();
                String password = pass.getText().toString();
                boolean cancel = false;
                View focusView = null;

                // Check for a valid login address.
                if (TextUtils.isEmpty(login)) {
                    Toast.makeText(App.getInstance(), R.string.login_toast_empty, Toast.LENGTH_SHORT).show();
                    focusView = username;
                    focusView.requestFocus();
                    return;
                } else if (!Helper.isLoginValid(login)) {
                    Toast.makeText(App.getInstance(), R.string.login_toast_incorrect_login, Toast.LENGTH_SHORT).show();
                    focusView = username;
                    focusView.requestFocus();
                    return;
                }

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(App.getInstance(), R.string.login_toast_empty_password, Toast.LENGTH_SHORT).show();
                    focusView = pass;
                    focusView.requestFocus();
                    return;
                } else if (!Helper.isPasswordValid(password)) {
                    Toast.makeText(App.getInstance(), R.string.login_toast_incorrect_password, Toast.LENGTH_SHORT).show();
                    focusView = pass;
                    focusView.requestFocus();
                    return;
                }
                if (mListener != null) {
                    // TODO: add edit texts
                    mListener.onLoginAttemptPerformed(login, password);
                }
            }
        });

        TextView addAccountTextView = (TextView) rootView.findViewById(R.id.login_add_account);
        addAccountTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                startActivity(new Intent(getContext(), RegisterActivity.class));
                //View rootView = inflater.inflate(R.layout.fragment_register, container, false);
                if (mListener != null) {
                    mListener.onRegistrationPageRequested();
                }
                return true;
            }
        });
        return rootView;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLoginAttemptPerformed(String name, String password);
        void onRegistrationPageRequested();
    }
}
