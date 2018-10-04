package com.example.yonoc.coverflow.View.ActividadClasificarPorGenero;

import android.content.ClipData;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ActivityPeliculaDetalle;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

public class FiltroPorGeneroActivity extends AppCompatActivity implements RecyclerFiltroPorGeneroAdapter.PasadorDeGenero, RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_por_genero);

        Toolbar toolbar = findViewById(R.id.flechaAtrasFiltroGeneros);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setElevation(20);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenededorDeFragmentFiltrarPorGenero, new FiltroPorGeneroFragment()).commit();
    }


    @Override
    public void pasarGenero(Integer codigoDeGenero) {
        Bundle bundle = new Bundle();
        bundle.putInt("codigo", codigoDeGenero);

        GenerosFiltradosFragment generosFiltradosFragment = new GenerosFiltradosFragment();
        generosFiltradosFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.contenededorDeFragmentFiltrarPorGenero, generosFiltradosFragment).addToBackStack("1").commit();
    }

    @Override
    public void pasarPelicula(int posicion) {
        Intent intent = new Intent(this, ActivityPeliculaDetalle.class);
        Bundle bundle = new Bundle();
        bundle.putInt("posicion", posicion);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void pasarPeliculaPorId(int id) {
        //vacio
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragmentManager.popBackStack();
    }
}
