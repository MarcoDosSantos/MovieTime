package com.example.yonoc.coverflow.View.ActividadClasificarPorGenero;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenerosFiltradosFragment extends Fragment {


    public GenerosFiltradosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generos_filtrados, container, false);


        Bundle bundle = getArguments();
        Integer codigoGenero = bundle.getInt("codigo");

        ClasificadorDePeliculasPorGeneroHelper clasificador = new ClasificadorDePeliculasPorGeneroHelper(ContenedorPeliculasEstatico.peliculaList);

        List<Pelicula> listaDePeliculasFiltradas = clasificador.clasificarPorCodigoDeGenero(codigoGenero);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerGenerosFiltrados);
        RecyclerDeGrillaDePeliculasAdapter recyclerDeGrillaDePeliculasAdapter = new RecyclerDeGrillaDePeliculasAdapter(listaDePeliculasFiltradas, (RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas)getContext());
        recyclerView.setAdapter(recyclerDeGrillaDePeliculasAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
