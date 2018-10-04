package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.DetallePeliculaFuncionesFragment;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragmentContenedor extends Fragment {
    private View viewBackgroundDelOverlayVerTrailer;
    private DetallePeliculaFuncionesFragment.ReproductorDePeliculas reproductorDePeliculas;
    private FragmentActivity myContext;
    private Bundle bundle;
    private String keyPelicula;
    private YouTubePlayer YPlayer;
    private static final String YoutubeDeveloperKey = "AIzaSyDMYKf1ool-DgmcTiou87r--AfWCo0lyFk";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    @Override
    public void onAttach(Activity activity) {

        if (activity instanceof FragmentActivity) {
            myContext = (FragmentActivity) activity;
        }

        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contenedor_youtube, container, false);

        viewBackgroundDelOverlayVerTrailer = rootView.findViewById(R.id.backgroundDelOverlayVerTrailer);


        viewBackgroundDelOverlayVerTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproductorDePeliculas.cerrarFragment();
            }
        });

        bundle = getArguments();
        keyPelicula = bundle.getString("keyPelicula");

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YoutubeDeveloperKey, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    //YPlayer.setFullscreen(true);
                    YPlayer.loadVideo(keyPelicula);
                    YPlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });


        return rootView;
    }

    public YoutubeFragmentContenedor() {
        // Required empty public constructor
    }

    public void setReproductorDePeliculas(DetallePeliculaFuncionesFragment.ReproductorDePeliculas reproductorDePeliculas){
        this.reproductorDePeliculas = reproductorDePeliculas;
    }


}
