package com.rabigol.wowmoney.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rabigol.wowmoney.R;

/**
 * Created by Artur.Ziaev on 29.11.2016.
 */

public class ViewPagerFragment extends Fragment {

    private static final String ARG_PARAM_TITLE = "title";
    private static final String ARG_PARAM_DESCRIPTION = "description";
    private static final String ARG_PARAM_TUTORIAL_PIC = "tutorialPic";

    private String mTitle;
    private String mDescription;
    private int mTutorialPic;

    private OnFragmentInteractionListener mListener;

    public ViewPagerFragment() {
    }

    public static ViewPagerFragment newInstance(String title, String description, int tutorialPic) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TITLE, title);
        args.putString(ARG_PARAM_DESCRIPTION, description);
        args.putInt(ARG_PARAM_TUTORIAL_PIC, tutorialPic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM_TITLE);
            mDescription = getArguments().getString(ARG_PARAM_DESCRIPTION);
            mTutorialPic = getArguments().getInt(ARG_PARAM_TUTORIAL_PIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);
        TextView screenTitle = (TextView) rootView.findViewById(R.id.screenTitle);
        TextView screenDescription = (TextView) rootView.findViewById(R.id.screenDescription);
        ImageView tutorialPic = (ImageView) rootView.findViewById(R.id.tutorialPic);

        screenTitle.setText(mTitle);
        screenDescription.setText(mDescription);
        tutorialPic.setBackgroundResource(mTutorialPic);

        return rootView;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
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

    }
}
