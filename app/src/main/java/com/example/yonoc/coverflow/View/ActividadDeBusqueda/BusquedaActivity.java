package com.example.yonoc.coverflow.View.ActividadDeBusqueda;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ActivityPeliculaDetalle;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

public class BusquedaActivity extends AppCompatActivity implements RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas, BusquedaFragment.Volvedor {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        BusquedaFragment busquedaFragment = new BusquedaFragment();
        busquedaFragment.setVolvedor(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedorDeFragmentBusqueda, busquedaFragment).commit();
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
    public void volver() {
        onBackPressed();
    }
}
