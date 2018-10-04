package com.example.yonoc.coverflow.View.ActividadCompartir.PublicarEntrada;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicacionConfirmadaOverlay extends Fragment {

    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    public PublicacionConfirmadaOverlay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publicacion_confirmada_overlay, container, false);

        Bundle bundle = getArguments();
        Integer cantidadDeEntradas = bundle.getInt("cantidad");
        final String strCantidadDeEntradas = cantidadDeEntradas.toString();
        final String strTitulo = bundle.getString("pelicula");
        final String sala = bundle.getString("cine");
        final String horario = bundle.getString("horario");
        final String fecha = bundle.getString("fecha");

        TextView nroEntradas = view.findViewById(R.id.numeroPublicado);
        TextView titulo = view.findViewById(R.id.tituloConfirmado);

        nroEntradas.setText(strCantidadDeEntradas);
        titulo.setText(bundle.getString("pelicula"));

        Button compartir = view.findViewById(R.id.compartir);

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent share = new Intent();
                share.setAction(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Manga de PUTOS!!!");
                share.putExtra(Intent.EXTRA_TEXT, "Me sobran " + strCantidadDeEntradas + " entradas para ir a ver " + strTitulo + " en el cine " + sala
                + " a las " + horario + " el " + fecha + ". Llevatela usando MovieTime!!!");
                startActivity(Intent.createChooser(share, "Compartir!"));
            }
        });


        Button continuar = view.findViewById(R.id.continuarConfirmado);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        View background = view.findViewById(R.id.backgroundDelOverlayConfirmar);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manipuladorDeFragmentsCompartir.cerrarFragment();
            }
        });

        return view;
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
