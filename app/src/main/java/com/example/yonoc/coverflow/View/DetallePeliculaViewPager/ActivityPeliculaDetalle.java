package com.example.yonoc.coverflow.View.DetallePeliculaViewPager;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yonoc.coverflow.Controller.PeliculasController;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.Utils.ResultsListener;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.PaginasFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.CompartirActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ContactoActivity;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.YoutubeFragmentContenedor;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.PortadaListaFragment;
import com.example.yonoc.coverflow.View.PerfilDeUsuario.PerfilDeUserActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ActivityPeliculaDetalle extends AppCompatActivity implements DetallePeliculaFuncionesFragment.FinalizadorDeActividades, DetallePeliculaFuncionesFragment.ReproductorDePeliculas, FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir, NavigationView.OnNavigationItemSelectedListener{
    Integer idPelicula;
    private FragmentManager fragmentManager;
    private YoutubeFragmentContenedor youtubeFragmentContenedor;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MenuItem menuItemLogin;
    private FirebaseAuth.AuthStateListener authStateListener;
    private PortadaListaFragment portadaListaFragment;
    private String nameLoggedUser;
    private String imageLoggedUser;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula_detalle);
        setTitle("");

        drawer = findViewById(R.id.drawerDetalle);

        navigationView = findViewById(R.id.nav_view_detalle);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();

        menuItemLogin = menuNav.findItem(R.id.logIn);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              /* if(firebaseAuth.getCurrentUser() == null){
                   startActivity(new Intent(MainActivity.this, LoginActivity.class));
               }*/
            }
        };

        chequearEstadoDeLogin();

        fragmentManager = getSupportFragmentManager();
        DetallePeliculaFuncionesFragment detallePeliculaFuncionesFragment = new DetallePeliculaFuncionesFragment();
        detallePeliculaFuncionesFragment.setArguments(getIntent().getExtras());
        detallePeliculaFuncionesFragment.setFinalizadorDeActividades(this);
        detallePeliculaFuncionesFragment.setReproductorDePeliculas(this);
        detallePeliculaFuncionesFragment.setDrawerLayout(drawer);
        detallePeliculaFuncionesFragment.setManipuladorDeFragmentsCompartir(this);
        fragmentManager.beginTransaction().replace(R.id.contenedorDeFragmentDetalle, detallePeliculaFuncionesFragment).commit();
    }

    @Override
    public void terminarActividad() {
        finish();
    }

    @Override
    public void reproducirTrailer(int posicion) {
        PeliculasController peliculasController = new PeliculasController();
        youtubeFragmentContenedor = new YoutubeFragmentContenedor();
        youtubeFragmentContenedor.setReproductorDePeliculas(this);
        idPelicula = ContenedorPeliculasEstatico.peliculaList.get(posicion).getId();
        peliculasController.obtenerTrailer(new ResultsListener<String>() {
            @Override
            public void finish(String resultado) {
                Bundle bundle = new Bundle();
                bundle.putString("keyPelicula", resultado);

                youtubeFragmentContenedor.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.contenedorDeTrailer, youtubeFragmentContenedor).addToBackStack("1").commit();
            }
        }, idPelicula);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerDetalle);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void chequearEstadoDeLogin(){


        NavigationView navigation = findViewById(R.id.nav_view_detalle);
        View navigationHeader = navigation.getHeaderView(0);
        ImageView imagenUsuario = navigationHeader.findViewById(R.id.imagenUsuarioHeader);
        TextView nombreUsuario = navigationHeader.findViewById(R.id.nombreUsuarioHeader);

        if (mAuth.getCurrentUser() != null) {
            menuItemLogin.setTitle("Log Out");

            imageLoggedUser = mAuth.getCurrentUser().getPhotoUrl().toString();
            nameLoggedUser = mAuth.getCurrentUser().getDisplayName();

            Picasso.with(this).load(imageLoggedUser).transform(new CropCircleTransformation()).into(imagenUsuario);
            nombreUsuario.setText(nameLoggedUser);
        } else {
            imagenUsuario.setImageResource(R.drawable.imagen_invitado);
            nombreUsuario.setText("Invitado");
            menuItemLogin.setTitle(" Log In");
        }

    }

    @Override
    public void cerrarFragment() {
        fragmentManager.popBackStack();
    }

    @Override
    public void irAFragmentPublicarEntrada(Bundle bundleURL) {
        PaginasFragment paginasFragment = new PaginasFragment();
        paginasFragment.setManipuladorDeFragmentsCompartir(this, bundleURL);
        fragmentManager.beginTransaction().replace(R.id.contenedorDePaginas, paginasFragment).addToBackStack("1").commit();
    }

    @Override
    public void irAFragmentPublicacionConfirmada(Bundle bundle) {

    }

    @Override
    public void irAFragmentConfirmarCompartir(Bundle bundle, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter) {

    }

    @Override
    public void irAConfirmacionDeIntercambio(Bundle bundle) {

    }

    @Override
    public void irAMiListaDeEntradas() {

    }

    @Override
    public void irAFragmentBorrarEntradaPropia(String url, String sala, String titulo, int posicion, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //voy a hacer en esta parte lo del switch para que al hacer click en
        //cada uno de los items te lleve a su correspondiente actividad
        int id = item.getItemId();
        switch (id) {

            case R.id.logIn:
                if (mAuth.getCurrentUser() == null){
                    Intent u = new Intent(this, LoginActivity.class);
                    startActivity(u);
                } else {
                    mAuth.signOut();
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    chequearEstadoDeLogin();
                    Toast.makeText(this, "Usuario Deslogueado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            case R.id.ubicacion:
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0"));
                Intent chooser=Intent.createChooser(intent, "ver mapa");
                startActivity(chooser);
                break;

            case R.id.miPerfil:
                startActivity(new Intent(this, PerfilDeUserActivity.class));
                break;

            case R.id.menuHome:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.botonEntradasPublicadasMenuDrawer:
                Intent intent1 = new Intent(this, CompartirActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("irAPublicadas", true);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;

            case R.id.botonMisContactosMenuDrawer:
                startActivity(new Intent(this, ContactoActivity.class));
                break;

        }
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
