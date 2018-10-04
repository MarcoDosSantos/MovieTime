package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 5/30/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    //Instancio lista de 2 elementos que tendrán 1) Sinopsis y 2) Cines, Horarios
    private List<Fragment> listaParaDetalle = new ArrayList<>();
    private List<String> listaTitulos = new ArrayList<>();
    private String posterPelicula;

    //Armo el constructor de los fragmentos que componen el viewpager
    public ViewPagerAdapter(android.support.v4.app.FragmentManager fm, int posicion, String posterPelicula, FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
            super(fm);

            listaParaDetalle.add(new FragmentSinopsis().crearFragmentSinopsis(posicion));
            FragmentSalasHorarios fragmentSalasHorarios = (FragmentSalasHorarios) new FragmentSalasHorarios().crearFragmentSalasHorarios(posicion);
            fragmentSalasHorarios.setPosterPelicula(posterPelicula);
            fragmentSalasHorarios.setManipuladorDeFragmentsCompartir(manipuladorDeFragmentsCompartir);
            listaParaDetalle.add(fragmentSalasHorarios);
            listaTitulos.add("Sinopsis");
            listaTitulos.add("Funciones");
        }

    @Override
    public Fragment getItem(int position) {
        return listaParaDetalle.get(position);
    }

    @Override
    public int getCount() {
        return listaParaDetalle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listaTitulos.get(position);
    }
}

