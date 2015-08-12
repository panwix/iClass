package com.panwix.iclass.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panwix.iclass.R;

/**
 * Created by Panwix on 15/8/12.
 */
public class Fragment_Bottom_Actiionbar extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_actionbar, container, false);
    }
}
