package com.example.yonoc.coverflow.View.ActividadCompartir.PublicarEntrada;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicarEntradasOverlayFragment extends Fragment {

    private TextView editTextCantidadDeEntradas,textViewTitulo,textViewSala, textViewhorario, textViewfecha;
    private Button btnMas;
    private Button btnMenos,confirmar;
    private View viewBackgroundOverlay;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private Integer cantidadDeEntradas;
    private Bundle bundle;

    public PublicarEntradasOverlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_publicar_entradas_overlay, container, false);

        textViewTitulo = view.findViewById(R.id.tituloFragmentoConfirmarOverlay);
        textViewSala = view.findViewById(R.id.nombreSalaFragmentConfirmarOverlay);
        textViewhorario = view.findViewById(R.id.horarioEnOverlayConfirmar);
        textViewfecha = view.findViewById(R.id.fechaEnOverlayConfirmar);
        editTextCantidadDeEntradas = view.findViewById(R.id.cantidadEntradasFragmentConfirmarOvelay);
        confirmar = view.findViewById(R.id.confirmarCompartirOverlay);
        viewBackgroundOverlay = view.findViewById(R.id.backgroundDelOverlayConfirmar);
        btnMas = view.findViewById(R.id.botonMas);
        btnMenos = view.findViewById(R.id.botonMenos);

        bundle = getArguments();

        textViewTitulo.setText(bundle.getString("pelicula"));
        textViewSala.setText(bundle.getString("cine"));
        textViewhorario.setText(bundle.getString("horario"));
        textViewfecha.setText(bundle.getString("fecha"));

        cantidadDeEntradas = 1;
        editTextCantidadDeEntradas.setText(cantidadDeEntradas.toString());

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringCantidadDeEntradasAPublicar = editTextCantidadDeEntradas.getText().toString();
                Integer intCantidadDeEntradasAPublicar = Integer.parseInt(stringCantidadDeEntradasAPublicar);

                escribirADB(intCantidadDeEntradasAPublicar);

            }
        });

        viewBackgroundOverlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manipuladorDeFragmentsCompartir.cerrarFragment();
            }
        });




        crearContadorDeEntradas();

        return view;
    }

    private void escribirADB(Integer cantidadDeEntradas) {

        DatabaseReference current_user_historial_de_entradas = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child("ofrece_entrada_para_compartir").child(bundle.getString("userId")).child("historial");

        for (int i = 0; i < cantidadDeEntradas; i++) {
            String ticketRef = current_user_historial_de_entradas.push().getKey();
            DatabaseReference ticketHistorialDBporUser = current_user_historial_de_entradas.child(ticketRef);

            Map<String, String> entradaPublicada = new HashMap<>();
            entradaPublicada.put("nombre", bundle.getString("nombre"));
            entradaPublicada.put("pelicula", bundle.getString("pelicula"));
            entradaPublicada.put("cine", bundle.getString("cine"));
            entradaPublicada.put("imagen", bundle.getString("imagen"));
            entradaPublicada.put("poster", bundle.getString("posterPelicula"));
            entradaPublicada.put("horario", bundle.getString("horario"));
            entradaPublicada.put("fecha", bundle.getString("fecha"));

            ticketHistorialDBporUser.setValue(entradaPublicada);
        }
        bundle.putInt("cantidad", cantidadDeEntradas);
        manipuladorDeFragmentsCompartir.irAFragmentPublicacionConfirmada(bundle);

    }

    private void crearContadorDeEntradas() {


        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cantidadDeEntradas++;
                editTextCantidadDeEntradas.setText(cantidadDeEntradas.toString());
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cantidadDeEntradas>1){
                    cantidadDeEntradas--;
                    editTextCantidadDeEntradas.setText(cantidadDeEntradas.toString());
                }
            }

        });


    }


    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
