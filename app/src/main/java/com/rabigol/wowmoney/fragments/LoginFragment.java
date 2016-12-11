package com.rabigol.wowmoney.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rabigol.wowmoney.R;
import com.rabigol.wowmoney.activities.MainActivity;
import com.rabigol.wowmoney.activities.RegisterActivity;

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

        EditText username = (EditText) rootView.findViewById(R.id.login_username);
        EditText password = (EditText) rootView.findViewById(R.id.login_password);
        Button button = (Button) rootView.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add login & password checking
                if (mListener != null) {
                    // TODO: add edit texts
                    mListener.onLoginAttemptPerformed("login", "password");
                }
            }
        });

        TextView addAccountTextView = (TextView) rootView.findViewById(R.id.login_add_account);
        addAccountTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                startActivity(new Intent(getContext(), RegisterActivity.class));
                View rootView = inflater.inflate(R.layout.fragment_register, container, false);
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
    }
}
