package com.example.yonoc.coverflow.View.PerfilDeUsuario;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yonoc.coverflow.Controller.PeliculasController;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ActivityPeliculaDetalle;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritosPerfilFragment extends Fragment implements RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas{


    public FavoritosPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos_perfil, container, false);

        PeliculasController controller = new PeliculasController();
        List<Pelicula> listaDeFavoritos = controller.getFavoritos(getContext());


        RecyclerView recyclerView = view.findViewById(R.id.recyclerFavoritosEnPerfil);
        RecyclerDeGrillaDePeliculasAdapter recyclerDeGrillaDePeliculasAdapter = new RecyclerDeGrillaDePeliculasAdapter(listaDeFavoritos, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setAdapter(recyclerDeGrillaDePeliculasAdapter);
        recyclerView.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void pasarPelicula(int posicion) {
    }

    @Override
    public void pasarPeliculaPorId(int id) {

        Pelicula pelicula = null;

        for (Pelicula peliculaDeLista : ContenedorPeliculasEstatico.peliculaList) {
            if (peliculaDeLista.getId().equals(id)){
                pelicula = peliculaDeLista;
                break;
            }
        }

        if (pelicula != null){
            int posicion = ContenedorPeliculasEstatico.peliculaList.indexOf(pelicula);
            Bundle bundle = new Bundle();
            bundle.putInt("posicion", posicion);
            Intent intent = new Intent(getActivity(), ActivityPeliculaDetalle.class);
            intent.putExtras(bundle);

            startActivity(intent);

        }
    }


}
