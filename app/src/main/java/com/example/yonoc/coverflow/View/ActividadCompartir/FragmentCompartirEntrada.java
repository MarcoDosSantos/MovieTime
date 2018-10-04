package com.example.yonoc.coverflow.View.ActividadCompartir;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ComponentesCompartir.CompartirEntradaAdapter;
import com.example.yonoc.coverflow.View.ActividadCompartir.ComponentesCompartir.EventoCompartir;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ContactoActivity;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCompartirEntrada extends Fragment {

    private CompartirEntradaAdapter adapter;
    private String userID, userName, userImage,otroUserId, nombreCine, nombrePeliculaDelBundle, posterPelicula, horario, fecha;
    private DatabaseReference dbBuscoEntrada;
    private ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private FirebaseUser user;

    public FragmentCompartirEntrada() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_compartir_entrada, container, false);

        Bundle bundle = getArguments();
        posterPelicula = bundle.getString("posterPelicula");
        horario = bundle.getString("horario");
        fecha = bundle.getString("fecha");

        mostrarDatosDeLaPeliculaSeleccionada(view, bundle);

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null){

            obtenerDatosDeUsuarioFirebase();
            crearRecyclerViewDeEntradasOfrecidas(view);
            //crearBotonCoincidencias(view);
            crearBotonPublicarEntrada(view);
            //crearBotonMisEntradasPublicadas(view);


        } else {
            siNoEstaLoggeadoIrAlLogin();
        }


        return view;
    }

/*    private void crearBotonMisEntradasPublicadas(View view) {
        Button misEntradasPublicadas = view.findViewById(R.id.botonEntradasPublicadas);

        misEntradasPublicadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manipuladorDeFragmentsCompartir.irAMiListaDeEntradas();
            }
        });
    }*/

    @Override
    public void onResume() {
        super.onResume();
        if (user != null){
            adapter.limpiarLista();
            encontrarEntradasOfrecidas();
        }
    }

    private void mostrarDatosDeLaPeliculaSeleccionada(View view, Bundle bundle) {
        nombreCine = bundle.getString("cineSeleccionado");
        nombrePeliculaDelBundle = bundle.getString("tituloPelicula");
        horario = bundle.getString("horario");
        fecha = bundle.getString("fecha");
        posterPelicula = bundle.getString("posterPelicula");

        TextView textViewPelicula = view.findViewById(R.id.textViewPelicula);
        TextView textViewCine = view.findViewById(R.id.textViewCine);
        ImageView imageView = view.findViewById(R.id.posterPeliculaEnCompartir);

        textViewCine.setText(nombreCine);
        textViewPelicula.setText(nombrePeliculaDelBundle);

        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w342" + posterPelicula).into(imageView);
    }

    private void obtenerDatosDeUsuarioFirebase() {
        dbBuscoEntrada = FirebaseDatabase.getInstance().getReference().child("usuarios").child("ofrece_entrada_para_compartir");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        userImage = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
    }

    private void crearRecyclerViewDeEntradasOfrecidas(View view) {
        List<EventoCompartir> listaDeEventosCompartir = new ArrayList<>();

        adapter = new CompartirEntradaAdapter(listaDeEventosCompartir, nombrePeliculaDelBundle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCompartirEntrada);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setManipuladorDeFragmentsCompartir(manipuladorDeFragmentsCompartir);
    }

    private void siNoEstaLoggeadoIrAlLogin() {
        Intent intent = new Intent(this.getContext(), LoginActivity.class);
        Bundle bundleALogin = new Bundle();
        bundleALogin.putBoolean("irACompartir", true);
        bundleALogin.putString("cineSeleccionado", nombreCine);
        bundleALogin.putString("tituloPelicula", nombrePeliculaDelBundle);
        intent.putExtras(bundleALogin);
        startActivity(intent);
    }

    private void crearBotonPublicarEntrada(View view) {
        Button botonTengoEntrada = view.findViewById(R.id.botonTengoUnaEntrada);

        botonTengoEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("nombre", userName);
                bundle.putString("pelicula", nombrePeliculaDelBundle);
                bundle.putString("cine", nombreCine);
                bundle.putString("imagen", userImage);
                bundle.putString("userId", userID);
                bundle.putString("posterPelicula", posterPelicula);
                bundle.putString("horario", horario);
                bundle.putString("fecha", fecha);

               manipuladorDeFragmentsCompartir.irAFragmentPublicarEntrada(bundle);


            }
        });
    }

/*    private void crearBotonCoincidencias(View view) {
        Button verCoincidencias = view.findViewById(R.id.botonVerCoincidencias);

        verCoincidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("botonConfirmar", false);
                startActivity(new Intent(getContext(), ContactoActivity.class));
            }
        });
    }*/





    public void encontrarEntradasOfrecidas(){

        final String  userIDLocal = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbBuscoEntrada.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    otroUserId = dataSnapshot.getKey();

                    if (!(otroUserId.equals(userIDLocal))){
                        DatabaseReference unaEntradaDB = dbBuscoEntrada.child(otroUserId).child("historial");
                        buscarEntradasEnRef(unaEntradaDB);
                    }
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

    private void buscarEntradasEnRef(DatabaseReference unaEntradaDB) {
        unaEntradaDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    String key = dataSnapshot.getKey();

                    String nombreUsuario = dataSnapshot.child("nombre").getValue(String.class);
                    String nombreCine = dataSnapshot.child("cine").getValue(String.class);
                    String nombrePelicula = dataSnapshot.child("pelicula").getValue(String.class);
                    String imagenUsuario = dataSnapshot.child("imagen").getValue(String.class);
                    String horario = dataSnapshot.child("horario").getValue(String.class);
                    String fecha = dataSnapshot.child("fecha").getValue(String.class);


                    adapter.sumarEventoALaLista(nombrePelicula, nombreUsuario, otroUserId, imagenUsuario, key, nombreCine, posterPelicula, horario, fecha);
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


    public void setManipuladorDeFragmentsCompartir(ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }



    public interface ManipuladorDeFragmentsCompartir {
        void cerrarFragment();

        void irAFragmentPublicarEntrada(Bundle bundle);

        void irAFragmentPublicacionConfirmada(Bundle bundle);

        void irAFragmentConfirmarCompartir(Bundle bundle, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter);

        void irAConfirmacionDeIntercambio(Bundle bundle);

        void irAMiListaDeEntradas();

        void irAFragmentBorrarEntradaPropia(String key, String sala, String titulo, int posicion, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter);
    }

}
