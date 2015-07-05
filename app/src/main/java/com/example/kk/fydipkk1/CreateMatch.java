package com.example.kk.fydipkk1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 05-07-2015.
 */
public class CreateMatch extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.create_match, container, false);

        return view;
        // return super.onCreateView(inflater, container, savedInstanceState);
    }

}
