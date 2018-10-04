package com.example.yonoc.coverflow.View.OnBoarding;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.R;


public class OnBoardingScreen3Fragment extends Fragment {
    public OnBoardingScreen3Fragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {
        View vista = inflater.inflate(R.layout.fragment_on_boarding_screen3,container,false);

        return vista;

    }

}
