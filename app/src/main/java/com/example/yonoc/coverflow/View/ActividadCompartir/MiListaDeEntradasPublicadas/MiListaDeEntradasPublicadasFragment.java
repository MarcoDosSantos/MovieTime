package com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiListaDeEntradasPublicadasFragment extends Fragment {

    private MisEntradasPublicadasAdapter misEntradasPublicadasAdapter;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    public MiListaDeEntradasPublicadasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mi_lista_de_entradas_publicadas, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewMisEntradas);

        misEntradasPublicadasAdapter = new MisEntradasPublicadasAdapter();
        misEntradasPublicadasAdapter.setManipuladorDeFragmentsCompartir(manipuladorDeFragmentsCompartir);
        recyclerView.setAdapter(misEntradasPublicadasAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        buscarMisEntradas();

        return view;
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }

    private void buscarMisEntradas() {
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference misEntradasDB = FirebaseDatabase.getInstance().getReference()
                .child("usuarios")
                .child("ofrece_entrada_para_compartir")
                .child(userId).child("historial");

        misEntradasDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    String key = dataSnapshot.getKey();

                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String nombreCine = dataSnapshot.child("cine").getValue(String.class);
                    String nombrePelicula = dataSnapshot.child("pelicula").getValue(String.class);
                    String imagenUsuario = dataSnapshot.child("imagen").getValue(String.class);
                    String posterPelicula = dataSnapshot.child("poster").getValue(String.class);
                    String horario = dataSnapshot.child("horario").getValue(String.class);
                    String fecha = dataSnapshot.child("fecha").getValue(String.class);


                    misEntradasPublicadasAdapter.sumarEventoALaLista(nombrePelicula, nombreUsuario, userId, imagenUsuario, key, nombreCine, posterPelicula,horario, fecha);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
