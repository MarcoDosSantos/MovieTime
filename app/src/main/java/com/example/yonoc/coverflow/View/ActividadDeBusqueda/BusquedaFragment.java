package com.example.yonoc.coverflow.View.ActividadDeBusqueda;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusquedaFragment extends Fragment {

    private EditText editTextBusqueda;
    private RecyclerView recyclerView;
    private List<Pelicula> listaLocalDePeliculas;
    private RecyclerDeGrillaDePeliculasAdapter recyclerDeGrillaDePeliculasAdapter;
    private Volvedor volvedor;


    public BusquedaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbarBusqueda);
        ((BusquedaActivity) getActivity()).setSupportActionBar(toolbar);
        ((BusquedaActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((BusquedaActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((BusquedaActivity) getActivity()).getSupportActionBar().setElevation(20);

        editTextBusqueda = view.findViewById(R.id.editTextBusqueda);
        recyclerView = view.findViewById(R.id.recyclerBusqueda);
        listaLocalDePeliculas = ContenedorPeliculasEstatico.peliculaList;

        setHasOptionsMenu(true);

        editTextBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filtrarLista(editable.toString());
            }
        });


        recyclerDeGrillaDePeliculasAdapter = new RecyclerDeGrillaDePeliculasAdapter(new ArrayList<Pelicula>(), (RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas) getContext());
        recyclerView.setAdapter(recyclerDeGrillaDePeliculasAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }

    private void filtrarLista(String textoDeBusqueda) {
        List<Pelicula> listaFiltradaDePeliculas = new ArrayList<>();

        for (Pelicula unaPelicula : listaLocalDePeliculas) {
            if (unaPelicula.getTitle().toLowerCase().contains(textoDeBusqueda.toLowerCase())){
                listaFiltradaDePeliculas.add(unaPelicula);
            }
        }

        recyclerDeGrillaDePeliculasAdapter.actualizarLista(listaFiltradaDePeliculas);

        if (textoDeBusqueda.isEmpty()){
            recyclerDeGrillaDePeliculasAdapter.limpiarLista();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                volvedor.volver();
        }


        return super.onOptionsItemSelected(item);
    }

    public void setVolvedor(Volvedor volvedor) {
        this.volvedor = volvedor;
    }

    public interface Volvedor{
        void volver();
    }
}
