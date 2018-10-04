package com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat.ChatActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmacionDeIntercambioFragment extends Fragment {


    private TextView pelicula, nombreUser;
    private Button comunicar;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private Bundle bundle;

    public ConfirmacionDeIntercambioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirmacion_de_intercambio, container, false);

        bundle = getArguments();

        pelicula = view.findViewById(R.id.tituloPeliculaIntercambiada);
        nombreUser = view.findViewById(R.id.usuarioIntercambiado);
        comunicar = view.findViewById(R.id.comunicateConElUsuario);

        pelicula.setText(bundle.getString("nombrePelicula"));
        nombreUser.setText(bundle.getString("nombreUsuario"));

        comunicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comunicarConElOtroUser();
            }
        });


        return view;
    }

    private void comunicarConElOtroUser() {
        String chatId = bundle.getString("chatId");
        Intent intent = new Intent(getContext(), ChatActivity.class);
        bundle.putBoolean("vengoDeConfirmarUnaEntrada", true);
        bundle.putString("chatId", chatId);
        intent.putExtras(bundle);
        manipuladorDeFragmentsCompartir.cerrarFragment();
        startActivity(intent);
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
