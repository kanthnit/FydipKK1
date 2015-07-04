package com.example.kk.fydipkk1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 04-07-2015.
 */
public class MatchScorecard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.match_scorecard, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
