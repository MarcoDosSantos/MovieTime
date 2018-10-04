package com.example.yonoc.coverflow.View.ActividadClasificarPorGenero;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorEstaticoDePeliculasClasificadasPorGenero;
import com.example.yonoc.coverflow.Model.POJO.Genero;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 7/1/2018.
 */

public class ClasificadorDePeliculasPorGeneroHelper {

    private List<Pelicula> listaLocalDePeliculas;
    private List<Genero> listaLocalDeGeneros;

    public ClasificadorDePeliculasPorGeneroHelper(List<Pelicula> listaLocalDePeliculas, List<Genero> listaLocalDeGeneros) {
        this.listaLocalDePeliculas = listaLocalDePeliculas;
        this.listaLocalDeGeneros = listaLocalDeGeneros;
    }

    public ClasificadorDePeliculasPorGeneroHelper(List<Pelicula> listaLocalDePeliculas) {
        this.listaLocalDePeliculas = listaLocalDePeliculas;
    }

    public void clasificarPorGenero(){

        Integer codigoDeGenero;


        for (Genero unGenero : listaLocalDeGeneros) {
            codigoDeGenero = unGenero.getId();

            List<Pelicula> listaDePelisDeUnGenero = new ArrayList<>();

            for (Pelicula unaPelicula : listaLocalDePeliculas) {
                for (Integer genero : unaPelicula.getGenre_ids()) {
                    if (genero.equals(codigoDeGenero)){
                        listaDePelisDeUnGenero.add(unaPelicula);
                    }
                }
            }
            ContenedorEstaticoDePeliculasClasificadasPorGenero.diccionarioEstaticoDePeliculasPorGenero.put(unGenero.getName(), listaDePelisDeUnGenero);
        }
    }

    public List<Pelicula> clasificarPorCodigoDeGenero(Integer unCodigo){

        List<Pelicula> listaDePelisDeUnGenero = new ArrayList<>();

        for (Pelicula unaPelicula : listaLocalDePeliculas) {
            for (Integer genero : unaPelicula.getGenre_ids()) {
                if (genero.equals(unCodigo)){
                    listaDePelisDeUnGenero.add(unaPelicula);
                    break;
                }
            }
        }
        return listaDePelisDeUnGenero;
    }
}
