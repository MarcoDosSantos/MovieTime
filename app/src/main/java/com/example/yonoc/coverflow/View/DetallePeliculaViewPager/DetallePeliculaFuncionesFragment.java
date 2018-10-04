package com.example.yonoc.coverflow.View.DetallePeliculaViewPager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yonoc.coverflow.Controller.PeliculasController;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.Model.RoomDB.FavoritoPeliculas;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadClasificarPorGenero.FiltroPorGeneroActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.ActividadDeBusqueda.BusquedaActivity;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.ViewPagerAdapter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePeliculaFuncionesFragment extends Fragment {

    private KenBurnsView kenBurnsViewImagen;
    private TextView textViewTitulo;
    private AppBarLayout appBarLayout;
    private int parallaxHeight;
    private View tab;
    private int posicion;
    private int posicionPagerView;
    private FinalizadorDeActividades finalizadorDeActividades;
    private LinearLayout linearLayoutLeft;
    private LinearLayout linearLayoutRight;
    private ImageView arrowLeft, arrowRight;
    private Pelicula pelicula;
    private MenuItem menuItemFavoritos;
    private Toolbar toolbar;
    private ReproductorDePeliculas reproductorDePeliculas;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private DrawerLayout drawerLayout;


    public DetallePeliculaFuncionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_pelicula_funciones, container, false);

        // Genero el appbar
        generarMenuDeOpciones(view);

        //Busco las vistas a usar del layout
        kenBurnsViewImagen = view.findViewById(R.id.imagenPelicula);
        textViewTitulo = view.findViewById(R.id.textViewTitulo);
        linearLayoutLeft = view.findViewById(R.id.layoutIrIzquierda);
        linearLayoutRight = view.findViewById(R.id.layoutIrDerecha);
        arrowLeft = view.findViewById(R.id.arrowLeft);
        arrowRight = view.findViewById(R.id.arrowRight);

        //Coloco el escuchador en la imagen para reproducir trailer

        kenBurnsViewImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproductorDePeliculas.reproducirTrailer(posicion);
            }
        });

        //Busco los valores enviados desde el Main
        Bundle bundle = getArguments();
        posicion = bundle.getInt("posicion");
        posicionPagerView = bundle.getInt("posicionPagerView");

        //Busco la información para la vista en el contenedor de peliculas
        pelicula = ContenedorPeliculasEstatico.peliculaList.get(posicion);
        String image = ContenedorPeliculasEstatico.peliculaList.get(posicion).getBackdrop_path();
        String poster = ContenedorPeliculasEstatico.peliculaList.get(posicion).getUrl();
        String titulacion = ContenedorPeliculasEstatico.peliculaList.get(posicion).getTitle();

        //Actualizo imagen y titulo del layout
        Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w780" + image).into(kenBurnsViewImagen);
        textViewTitulo.setText(titulacion);

        //Busco el viewpager
        final ViewPager viewPager = view.findViewById(R.id.viewPagerDetallePeliculas);

        //Le seteo el adapter al viewpager (lo unico que puedo pasar por ahora es la sinopsis)
        FragmentManager fragmentManager = getChildFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(fragmentManager,posicion, poster, manipuladorDeFragmentsCompartir);
        viewPager.setAdapter(adapter);
        if (bundle.getInt("idBoton") != 0){
            viewPager.setCurrentItem(1);
        }

        //Busco el tablayout y se lo seteo con el viewpager
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutDetalle);
        tabLayout.setupWithViewPager(viewPager);

        crearFlechasLateralesDeNavegacion(viewPager);

        return view;
    }

    private Boolean chequearEstadoDeFavoritos(Integer id) {

        PeliculasController controller = new PeliculasController();
        FavoritoPeliculas favoritoPeliculas = controller.getFavoritoPorId(pelicula.getId(), getContext());

        Boolean noExisteRegistro = favoritoPeliculas == null;

        if (noExisteRegistro){
            menuItemFavoritos.setIcon(R.drawable.como);
        } else {
            menuItemFavoritos.setIcon(R.drawable.como_lleno);
        }

        return noExisteRegistro;
    }

    private void crearFlechasLateralesDeNavegacion(final ViewPager viewPager) {
        int tamañoDeLaLista = ContenedorPeliculasEstatico.peliculaList.size() - 1;

        if (posicion == tamañoDeLaLista){
            arrowRight.setImageResource(R.drawable.arrow_right_grey);
        }

        if (posicion == 0){
            arrowLeft.setImageResource(R.drawable.arrow_left_grey);
        }

        arrowLeft.setOnClickListener(new View.OnClickListener() {
            int nuevaPosicion;
            int poscionPagerView;
            @Override
            public void onClick(View view) {
                if (posicion > 0){
                    nuevaPosicion = posicion -1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("posicion", nuevaPosicion);
                    poscionPagerView = viewPager.getCurrentItem();
                    bundle.putInt("posicionPagerView", posicionPagerView);
                    Intent intent = new Intent(getContext(), ActivityPeliculaDetalle.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finalizadorDeActividades.terminarActividad();
                }
            }
        });


        arrowRight.setOnClickListener(new View.OnClickListener() {
            int nuevaPosicion;
            int poscionPagerView;
            @Override
            public void onClick(View view) {
                if (posicion < ContenedorPeliculasEstatico.peliculaList.size()-1){
                    nuevaPosicion = posicion+1;
                    Bundle bundle = new Bundle();
                    bundle.putInt("posicion", nuevaPosicion);
                    poscionPagerView = viewPager.getCurrentItem();
                    bundle.putInt("posicionPagerView", posicionPagerView);
                    Intent intent = new Intent(getContext(), ActivityPeliculaDetalle.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finalizadorDeActividades.terminarActividad();
                }
            }
        });
    }

    private void generarMenuDeOpciones(View view) {
        toolbar = view.findViewById(R.id.toolbarDetalle);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_detalle, menu);
        super.onCreateOptionsMenu(menu, inflater);
        Menu menuFav = toolbar.getMenu();
        menuItemFavoritos = menuFav.findItem(R.id.menuFavoritos);
        chequearEstadoDeFavoritos(pelicula.getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuFavoritos:
                if (chequearEstadoDeFavoritos(pelicula.getId())){
                    guardarEnFavoritos(pelicula);
                } else {
                    borrarDeFavoritos(pelicula.getId());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void borrarDeFavoritos(Integer id) {
        Context context = getContext();
        PeliculasController peliculasController = new PeliculasController();
        FavoritoPeliculas favoritoABorrar = peliculasController.getFavoritoPorId(id, context);
        peliculasController.borrarDeFavoritos(favoritoABorrar, context);
        chequearEstadoDeFavoritos(pelicula.getId());
    }

    private void guardarEnFavoritos(Pelicula pelicula) {
        PeliculasController peliculasController = new PeliculasController();
        peliculasController.guardarEnFavoritos(pelicula, getContext());
        chequearEstadoDeFavoritos(pelicula.getId());
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public void setFinalizadorDeActividades(FinalizadorDeActividades finalizadorDeActividades) {
        this.finalizadorDeActividades = finalizadorDeActividades;
    }

    public void setReproductorDePeliculas(ReproductorDePeliculas reproductorDePeliculas) {
        this.reproductorDePeliculas = reproductorDePeliculas;
    }

    public interface FinalizadorDeActividades{
        void terminarActividad();
    }

    public interface ReproductorDePeliculas{
        void reproducirTrailer(int posicion);
        void cerrarFragment();
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
