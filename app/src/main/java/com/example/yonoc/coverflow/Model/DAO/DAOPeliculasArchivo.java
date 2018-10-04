package com.example.yonoc.coverflow.Model.DAO;

import com.example.yonoc.coverflow.Model.POJO.Pelicula;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 6/9/2018.
 */

public class DAOPeliculasArchivo {

    private List<Integer> genre_id = new ArrayList<>();

    public List<Pelicula> iniciarLista(){

        List<Pelicula> listaPeliculasDAOArchivo = new ArrayList<>();

        Pelicula avengers = new Pelicula("Avengers", "Cuando el hermano de Thor, Loki (Tom Hiddleston), logra acceder al poder ilimitado del Cubo Cósmico, Nick Fury (Samuel L. Jackson), director de la agencia para mantener la paz internacional, inicia el reclutamiento de unos superhéroes para vencer una amenaza sin precedente contra la Tierra. Al equipo de ensueño de Fury se unen Iron Man (Robert Downey Jr.), Capitán América (Chris Evans), Hulk (Mark Ruffalo), Thor (Chris Hemsworth), Viuda Negra (Scarlett Johansson) y Ojo de Halcón (Jeremy Renner).", "-5442822-5221-235", "https://hipertextual.com/files/2017/11/avengers_infinity_war_poster_by_themadbutcher-db8b8tc-1-670x410.jpg", 1, 45, 5.0,"https://hipertextual.com/files/2017/11/avengers_infinity_war_poster_by_themadbutcher-db8b8tc-1-670x410.jpg", genre_id);
        listaPeliculasDAOArchivo.add(avengers);

        avengers = new Pelicula("Y una mas de Avengers", "Cuando el hermano de Thor, Loki (Tom Hiddleston), logra acceder al poder ilimitado del Cubo Cósmico, Nick Fury (Samuel L. Jackson), director de la agencia para mantener la paz internacional, inicia el reclutamiento de unos superhéroes para vencer una amenaza sin precedente contra la Tierra. Al equipo de ensueño de Fury se unen Iron Man (Robert Downey Jr.), Capitán América (Chris Evans), Hulk (Mark Ruffalo), Thor (Chris Hemsworth), Viuda Negra (Scarlett Johansson) y Ojo de Halcón (Jeremy Renner).","5464-55466-4516","https://prod.media.larepublica.pe/720x405/larepublica/imagen/2018/04/13/noticia-avengers-infinity-war.jpg" ,2, 45, 5.0,"https://prod.media.larepublica.pe/720x405/larepublica/imagen/2018/04/13/noticia-avengers-infinity-war.jpg", genre_id);
        listaPeliculasDAOArchivo.add(avengers);

        avengers = new Pelicula("Sólo saben hacer películas de Avengers", "Cuando el hermano de Thor, Loki (Tom Hiddleston), logra acceder al poder ilimitado del Cubo Cósmico, Nick Fury (Samuel L. Jackson), director de la agencia para mantener la paz internacional, inicia el reclutamiento de unos superhéroes para vencer una amenaza sin precedente contra la Tierra. Al equipo de ensueño de Fury se unen Iron Man (Robert Downey Jr.), Capitán América (Chris Evans), Hulk (Mark Ruffalo), Thor (Chris Hemsworth), Viuda Negra (Scarlett Johansson) y Ojo de Halcón (Jeremy Renner).","65-87-6524","http://digitalspyuk.cdnds.net/a18/a12/980x490/landscape-1521646648-avengers-assemble-series.jpg",2, 45, 5.0,"http://digitalspyuk.cdnds.net/a18/a12/980x490/landscape-1521646648-avengers-assemble-series.jpg", genre_id);
        listaPeliculasDAOArchivo.add(avengers);

        avengers = new Pelicula("Y daaaale con Avengers", "Cuando el hermano de Thor, Loki (Tom Hiddleston), logra acceder al poder ilimitado del Cubo Cósmico, Nick Fury (Samuel L. Jackson), director de la agencia para mantener la paz internacional, inicia el reclutamiento de unos superhéroes para vencer una amenaza sin precedente contra la Tierra. Al equipo de ensueño de Fury se unen Iron Man (Robert Downey Jr.), Capitán América (Chris Evans), Hulk (Mark Ruffalo), Thor (Chris Hemsworth), Viuda Negra (Scarlett Johansson) y Ojo de Halcón (Jeremy Renner).","22-98-78","https://cdn.movieweb.com/img.news.tops/NEItUOIm7DNlMN_1_b/Avengers-Infinity-War-Theater-Display-International-Posters.jpg",2, 45, 5.0,"https://cdn.movieweb.com/img.news.tops/NEItUOIm7DNlMN_1_b/Avengers-Infinity-War-Theater-Display-International-Posters.jpg", genre_id);
        listaPeliculasDAOArchivo.add(avengers);

        avengers = new Pelicula("Y adivinà què? Otra de Avengers!!!", "Cuando el hermano de Thor, Loki (Tom Hiddleston), logra acceder al poder ilimitado del Cubo Cósmico, Nick Fury (Samuel L. Jackson), director de la agencia para mantener la paz internacional, inicia el reclutamiento de unos superhéroes para vencer una amenaza sin precedente contra la Tierra. Al equipo de ensueño de Fury se unen Iron Man (Robert Downey Jr.), Capitán América (Chris Evans), Hulk (Mark Ruffalo), Thor (Chris Hemsworth), Viuda Negra (Scarlett Johansson) y Ojo de Halcón (Jeremy Renner).","05-06-a99","http://cdn2.dailytrend.mx/media/bi/styles/gallerie/public/images/2018/04/avengers-infinity-war-no-esparaninos.jpg",2, 45, 5.0,"http://cdn2.dailytrend.mx/media/bi/styles/gallerie/public/images/2018/04/avengers-infinity-war-no-esparaninos.jpg", genre_id);
        listaPeliculasDAOArchivo.add(avengers);

        return listaPeliculasDAOArchivo;
    }
}
