package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(){
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        return root;
    }

}
