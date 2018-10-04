package com.example.yonoc.coverflow.View.MainActivityPortada;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.yonoc.coverflow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EsperaFragment extends Fragment {

    private ProgressBar progressBar;

    public EsperaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_espera, container, false);

        progressBar = view.findViewById(R.id.progress_bar_espera);
        progressBar.setIndeterminate(true);



        return view;
    }

}
