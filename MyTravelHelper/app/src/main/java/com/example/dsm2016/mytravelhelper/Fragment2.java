package com.example.dsm2016.mytravelhelper;

import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dsm2016 on 2018-05-10.
 */

public class Fragment2 extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment2, container, false);

        return view;
    }

}
