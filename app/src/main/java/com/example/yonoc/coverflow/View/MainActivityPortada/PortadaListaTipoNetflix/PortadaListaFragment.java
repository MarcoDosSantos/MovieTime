package com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorDeGenerosEstatico;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.Utils.HTTPConnectionManager;
import com.example.yonoc.coverflow.View.ActividadClasificarPorGenero.ClasificadorDePeliculasPorGeneroHelper;
import com.example.yonoc.coverflow.View.ActividadClasificarPorGenero.FiltroPorGeneroActivity;
import com.example.yonoc.coverflow.View.ActividadDeBusqueda.BusquedaActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortadaListaFragment extends Fragment {

    private AppBarLayout appBarLayout;
    private int parallaxHeight;
    private View tab;
    private RecyclerView recyclerView;
    private ImageView imageView, logo;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Refrescador refrescador;
    private RecyclerDeGrillaDePeliculasAdapter recyclerDeGrillaDePeliculasAdapter;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas pasadorDePeliculas;

    public PortadaListaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_portada_lista, container, false);

        generarCollapsingToolbar(view);
        ponerImagenTituloEnCollapsingToolbar(view);
        generarMenuDeOpciones(view);
        crearRecyclerDePeliculas(view);

        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);

        swipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(android.R.color.holo_blue_bright),
                getActivity().getResources().getColor(android.R.color.holo_green_light),
                getActivity().getResources().getColor(android.R.color.holo_orange_light),
                getActivity().getResources().getColor(android.R.color.holo_red_light));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (HTTPConnectionManager.isNetworkingOnline(getContext())){
                    refrescador.refrescar();

                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 5000);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });


        return view;
    }

    private void crearRecyclerDePeliculas(View view) {
        tab = view.findViewById(R.id.tab);
        recyclerView = view.findViewById(R.id.recyclerDeRecyclersPortada);

        recyclerDeGrillaDePeliculasAdapter = new RecyclerDeGrillaDePeliculasAdapter(ContenedorPeliculasEstatico.peliculaList, (RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas) getContext());
        recyclerView.setAdapter(recyclerDeGrillaDePeliculasAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void generarMenuDeOpciones(View view) {
        toolbar = view.findViewById(R.id.toolbarPortada);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(20);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void generarCollapsingToolbar(View view) {
        appBarLayout = view.findViewById(R.id.appbar);
        logo = view.findViewById(R.id.mtLogoEnAppbar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) >= 415) {
                    logo.animate().alpha(1.0f);
                } else {
                    logo.animate().alpha(0.0f);
                }
            }
        });
    }

    private void ponerImagenTituloEnCollapsingToolbar(View view) {
        Pelicula pelicula = obtenerPeliculaRandom();
        final int posicion = ContenedorPeliculasEstatico.peliculaList.indexOf(pelicula);
        String url = pelicula.getBackdrop_path();
        textView = view.findViewById(R.id.textViewTituloEnCollapsingBar);
        textView.setText(pelicula.getTitle());


        imageView = view.findViewById(R.id.imagenCollapsingBar);
        if (url.startsWith("http")) {
            Picasso.with(view.getContext()).load(url).into(imageView);
        } else {
            Picasso.with(view.getContext()).load("https://image.tmdb.org/t/p/w780" + pelicula.getBackdrop_path()).into(imageView);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasadorDePeliculas.pasarPelicula(posicion);
            }
        });

    }

    private Pelicula obtenerPeliculaRandom() {
        int result = 0;
        Random r = new Random();
        do {

           int Low = 0;
           int High = ContenedorPeliculasEstatico.peliculaList.size();
           result = r.nextInt(High - Low) + Low;
       } while (result < 0);

        return ContenedorPeliculasEstatico.peliculaList.get(result);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_collapsing_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuBuscar:
                startActivity(new Intent(getActivity(), BusquedaActivity.class));
                break;
            case R.id.menuOtroItem:
                startActivity(new Intent(getActivity(), FiltroPorGeneroActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public void refrescar(){
        recyclerDeGrillaDePeliculasAdapter.refrescar();
    }

    public void setPasadorDePeliculas(RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas pasadorDePeliculas) {
        this.pasadorDePeliculas = pasadorDePeliculas;
    }

    public void setRefrescador(Refrescador refrescador) {
        this.refrescador = refrescador;
    }

    public interface Refrescador{
        void refrescar();
    }


}
