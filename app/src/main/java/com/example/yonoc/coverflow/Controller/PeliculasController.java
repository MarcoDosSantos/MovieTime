package com.example.yonoc.coverflow.Controller;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.widget.Toast;

import com.example.yonoc.coverflow.Model.DAO.DAOPeliculasArchivo;
import com.example.yonoc.coverflow.Model.DAO.DAOPeliculasTMDB;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Genero;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.Model.RoomDB.FavoritoPeliculas;
import com.example.yonoc.coverflow.Model.RoomDB.PeliculaRoom;
import com.example.yonoc.coverflow.Model.RoomDB.RoomDAO;
import com.example.yonoc.coverflow.Utils.HTTPConnectionManager;
import com.example.yonoc.coverflow.Utils.ResultsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 6/9/2018.
 */

public class PeliculasController {


    public void obtenerPeliculas(final ResultsListener<List<Pelicula>> resultsListenerDeLaVista, final Context context){


        ResultsListener<List<Pelicula>> listenerDelControlador = new ResultsListener<List<Pelicula>>() {
            @Override
            public void finish(List<Pelicula> resultado) {
                //en el camino de vuelta es que viene con el resultado (=listaPeliculasDAOInternet) como parametro

                if (resultado.isEmpty()){
                    List<Pelicula> listaDeDB = crearListaDesdeDB(context);
                    resultsListenerDeLaVista.finish(listaDeDB);
                } else {
                    resultsListenerDeLaVista.finish(resultado);
                    cargarPeliculasADB(resultado, context);
                }

            }
        };

/*        ResultsListener<Integer> listenerDelNumeroDePaginas = new ResultsListener<Integer>() {
            @Override
            public void finish(Integer resultado) {
                //dispara la iteracion del retrofit sobre cada página
                int numeroDePaginas = 5;

                if (resultado <= 5){
                    numeroDePaginas = resultado;
                }

                //iterarPedidoDeServicio(numeroDePaginas, resultsListenerDeLaVista);
            }
        };*/



        if(HTTPConnectionManager.isNetworkingOnline(context)){
            DAOPeliculasTMDB daoPeliculasTMDB = new DAOPeliculasTMDB();
            daoPeliculasTMDB.getNowPlaying(listenerDelControlador/*, listenerDelNumeroDePaginas*/);
        }else{
            List<Pelicula> listaDeDB = crearListaDesdeDB(context);
            resultsListenerDeLaVista.finish(listaDeDB);
        }
    }



    public void obtenerGeneros(final ResultsListener<List<Genero>> resultsListenerDeGenerosDeLaVista, final Context context){

        ResultsListener<List<Genero>> listenerDeGenerosDelControlador = new ResultsListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> resultado) {
                resultsListenerDeGenerosDeLaVista.finish(resultado);
                guardarGenerosEnDB(resultado, context);
            }
        };


        if (HTTPConnectionManager.isNetworkingOnline(context)){
            DAOPeliculasTMDB daoPeliculasTMDB = new DAOPeliculasTMDB();
            daoPeliculasTMDB.getGenres(listenerDeGenerosDelControlador);
        } else {

            List<Genero> generos = cargarGenerosDeDB(context);
            resultsListenerDeGenerosDeLaVista.finish(generos);
        }

    }

    private void cargarPeliculasADB(List<Pelicula> resultado, Context contextLocal) {
        RoomDAO roomDAO = new RoomDAO(contextLocal);

        for (Pelicula pelicula : resultado) {
            Integer genreId = pelicula.getGenre_ids().get(0);
            Integer vote_average = pelicula.getVote_average().intValue();

            PeliculaRoom peliculaRoom = new PeliculaRoom(pelicula.getTitle(), pelicula.getOverview(),
                    pelicula.getRelease_date(), pelicula.getUrl(), pelicula.getId(),
                    pelicula.getVote_count(), vote_average,
                    pelicula.getBackdrop_path(), genreId);

            roomDAO.insertarPelicula(peliculaRoom);
        }
    }

    public void obtenerTrailer(final ResultsListener<String> resultListenerDeVistaKeyPelicula, Integer idPelicula) {
        ResultsListener<String> resultListenerDelControladorKeyPelicula = new ResultsListener<String>() {
            @Override
            public void finish(String resultado) {
                resultListenerDeVistaKeyPelicula.finish(resultado);
            }
        };


        if (hayInternet()){
            DAOPeliculasTMDB daoPeliculasTMDB = new DAOPeliculasTMDB();
            daoPeliculasTMDB.getTrailer(resultListenerDelControladorKeyPelicula, idPelicula);
        } else {

        }

    }

    public Boolean hayInternet(){
        return true;
    }


    private List<Pelicula> crearListaDesdeDB(Context contextLocal) {
        RoomDAO roomDAO = new RoomDAO(contextLocal);
        List<Pelicula> listPeliculaDesdeDB = new ArrayList<>();

        List<PeliculaRoom> listaRoom = roomDAO.getAllPeliculas();

        for (PeliculaRoom peliculaRoom : listaRoom) {
            List<Integer> genre_ids = new ArrayList<>();
            genre_ids.add(peliculaRoom.genre_ids);
            Pelicula pelicula = new Pelicula(peliculaRoom.getTitle(),peliculaRoom.getOverview(),peliculaRoom.getRelease_date()
                    ,peliculaRoom.getUrl(),peliculaRoom.getId(), peliculaRoom.getVote_count(),peliculaRoom.getVote_average()
                    , peliculaRoom.getBackdrop_path(),genre_ids);

            listPeliculaDesdeDB.add(pelicula);
        }

        return listPeliculaDesdeDB;
    }


    private List<Genero> cargarGenerosDeDB(Context context) {
        List<Genero> list;
        RoomDAO roomDAO = new RoomDAO(context);
        list = roomDAO.getGenero();

        return list;
    }

    private void guardarGenerosEnDB(List<Genero> resultado, Context context) {
        RoomDAO roomDAO = new RoomDAO(context);
        roomDAO.insertarGeneros(resultado);
    }

    public void guardarEnFavoritos(Pelicula pelicula, Context context) {
        RoomDAO roomDAO = new RoomDAO(context);

        List<FavoritoPeliculas>favoritoPeliculasList= roomDAO.getFavoritos();

        Integer genreId = pelicula.getGenre_ids().get(0);
        Integer vote_average = pelicula.getVote_average().intValue();

        FavoritoPeliculas favoritoPeliculas = new FavoritoPeliculas(pelicula.getTitle(), pelicula.getOverview(),
                pelicula.getRelease_date(), pelicula.getBackdrop_path(), pelicula.getUrl(), pelicula.getId(),
                pelicula.getVote_count(), vote_average, genreId);

        roomDAO.insertarFavorito(favoritoPeliculas);
    }

    public FavoritoPeliculas getFavoritoPorId(Integer id, Context context) {
        RoomDAO roomDAO = new RoomDAO(context);
        FavoritoPeliculas favoritoPeliculas = roomDAO.getFavoritoConId(id);
        return favoritoPeliculas;
    }

    public void borrarDeFavoritos(FavoritoPeliculas favoritoABorrar, Context context) {
        RoomDAO roomDAO = new RoomDAO(context);
        roomDAO.borrarFavorito(favoritoABorrar);
    }

    public List<Pelicula> getFavoritos(Context context){
        RoomDAO roomDAO = new RoomDAO(context);
        List<Pelicula> listaDePeliculasFavoritas = new ArrayList<>();
        List<FavoritoPeliculas> listaParaConvertir = roomDAO.getFavoritos();

        for (FavoritoPeliculas favoritoPeliculas : listaParaConvertir) {
            List<Integer> genre_ids = new ArrayList<>();
            genre_ids.add(favoritoPeliculas.genre_ids);
            Pelicula pelicula = new Pelicula(favoritoPeliculas.getTitle(),favoritoPeliculas.getOverview(),favoritoPeliculas.getRelease_date()
                    ,favoritoPeliculas.getUrl(),favoritoPeliculas.getId(), favoritoPeliculas.getVote_count(),favoritoPeliculas.getVote_average()
                    , favoritoPeliculas.getBackdrop_path(),genre_ids);

            listaDePeliculasFavoritas.add(pelicula);
        }

        return listaDePeliculasFavoritas;
    }
}
