package com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class BorrarEntradaFragment extends Fragment {

    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter quitadorDeElementosDelAdapter;
    private TextView titulo, cine;
    private View viewEliminar;
    private String key;
    private int posicion;


    public BorrarEntradaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_borrar_entrada, container, false);

        titulo = view.findViewById(R.id.tituloEliminar);
        cine = view.findViewById(R.id.nombreSalaEliminar);

        Bundle bundle = getArguments();
        key = bundle.getString("key");
        posicion = bundle.getInt("posicion");

        titulo.setText(bundle.getString("pelicula"));
        cine.setText(bundle.getString("cine"));

        viewEliminar = view.findViewById(R.id.backgrounEliminar);

        viewEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manipuladorDeFragmentsCompartir.cerrarFragment();
            }
        });

        Button eliminarEntrada = view.findViewById(R.id.eliminarPublicacion);

        eliminarEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPublicacion();
            }
        });


        return view;
    }

    private void eliminarPublicacion() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ticketDB = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child("ofrece_entrada_para_compartir").child(userId)
                .child("historial").child(key);

        ticketDB.setValue(null);


        quitadorDeElementosDelAdapter.quitarElementosDelAdapterEnPosicion(posicion);
        manipuladorDeFragmentsCompartir.cerrarFragment();

    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }

    public void setQuitadorDeElementosDelAdapter(ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter quitadorDeElementosDelAdapter) {
        this.quitadorDeElementosDelAdapter = quitadorDeElementosDelAdapter;
    }
}
