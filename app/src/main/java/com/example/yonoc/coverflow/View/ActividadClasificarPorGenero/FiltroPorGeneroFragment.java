package com.example.yonoc.coverflow.View.ActividadClasificarPorGenero;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorDeGenerosEstatico;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiltroPorGeneroFragment extends Fragment {

    private RecyclerView recyclerView;

    public FiltroPorGeneroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filtro_por_genero, container, false);

        recyclerView = view.findViewById(R.id.recyclerDeIconosDeGeneros);

        RecyclerFiltroPorGeneroAdapter recyclerFiltroPorGeneroAdapter = new RecyclerFiltroPorGeneroAdapter((RecyclerFiltroPorGeneroAdapter.PasadorDeGenero) getContext());
        recyclerView.setAdapter(recyclerFiltroPorGeneroAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
