package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaginasFragment extends Fragment {

    private Toolbar toolbar;
    private ImageView arrowBack;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private WebView mWebView;
    private String urlToBuy;
    private ProgressBar progressBar;

    public PaginasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_paginas, container, false);

        progressBar = view.findViewById(R.id.progress_bar_paginas);
        progressBar.setIndeterminate(true);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                progressBar.setVisibility(View.GONE);
            }
        }, 2000);



        toolbar = view.findViewById(R.id.toolBarWebView);
        arrowBack = view.findViewById(R.id.arrowBack);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manipuladorDeFragmentsCompartir.cerrarFragment();
            }
        });

        mWebView = view.findViewById(R.id.webview);
        mWebView.loadUrl(urlToBuy);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return view;
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir, Bundle urlBundle) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
        this.urlToBuy = urlBundle.getString("url");
    }
}
