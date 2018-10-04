package com.example.yonoc.coverflow.View.PerfilDeUsuario;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ListaDeContactos.ListaDeContactosFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas.MiListaDeEntradasPublicadasFragment;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.FragmentSalasHorarios;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.FragmentSinopsis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 7/22/2018.
 */

public class ViewPagerPerfilAdapter extends FragmentPagerAdapter {
    //Instancio lista de 2 elementos que tendrán 1) Sinopsis y 2) Cines, Horarios
    private List<Fragment> listaParaDetalle = new ArrayList<>();
    private List<String> listaTitulos = new ArrayList<>();
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    //Armo el constructor de los fragmentos que componen el viewpager
    public ViewPagerPerfilAdapter(android.support.v4.app.FragmentManager fm, FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        super(fm);
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;

        FavoritosPerfilFragment favoritosPerfilFragment = new FavoritosPerfilFragment();
        ListaDeContactosFragment listaDeContactosFragment = new ListaDeContactosFragment();

        MiListaDeEntradasPublicadasFragment miListaDeEntradasPublicadasFragment = new MiListaDeEntradasPublicadasFragment();
        miListaDeEntradasPublicadasFragment.setManipuladorDeFragmentsCompartir(manipuladorDeFragmentsCompartir);

        listaParaDetalle.add(favoritosPerfilFragment);
        listaParaDetalle.add(miListaDeEntradasPublicadasFragment);
        listaParaDetalle.add(listaDeContactosFragment);

        listaTitulos.add("Favoritos");
        listaTitulos.add("Publicadas");
        listaTitulos.add("Solicitadas");
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
