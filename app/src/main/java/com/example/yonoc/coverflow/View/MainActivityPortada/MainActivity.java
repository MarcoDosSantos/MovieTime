package com.example.yonoc.coverflow.View.MainActivityPortada;

import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yonoc.coverflow.Controller.PeliculasController;
import com.example.yonoc.coverflow.Controller.UserFacebookController;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorDeGenerosEstatico;
import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Genero;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.Model.POJO.UserFacebook;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.Utils.ResultsListener;
import com.example.yonoc.coverflow.View.ActividadCompartir.CompartirActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ContactoActivity;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ActivityPeliculaDetalle;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.PortadaListaFragment;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;
import com.example.yonoc.coverflow.View.PerfilDeUsuario.PerfilDeUserActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity implements RecyclerDeGrillaDePeliculasAdapter.PasadorDePeliculas, NavigationView.OnNavigationItemSelectedListener, PortadaListaFragment.Refrescador {

    /***Atributos***/

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    //private Button botonLogOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MenuItem menuItemLogin;
    private PortadaListaFragment portadaListaFragment;

    private String nameLoggedUser;
    private String imageLoggedUser;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();
        menuItemLogin = menuNav.findItem(R.id.logIn);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contenedorDeFragmentPortada, new EsperaFragment()).commitAllowingStateLoss();

        //voy a crear un Intent para llegar a Pantalla Login
        final Intent intentDeBotonLogin = new Intent(this, LoginActivity.class);

       //botonLogOut = findViewById(R.id.botonLogOut);

       mAuth = FirebaseAuth.getInstance();
       mAuthListener = new FirebaseAuth.AuthStateListener() {

          @Override
           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              /* if(firebaseAuth.getCurrentUser() == null){
                   startActivity(new Intent(MainActivity.this, LoginActivity.class));
               }*/
           }
       };

        portadaListaFragment = new PortadaListaFragment();
        portadaListaFragment.setRefrescador(this);
        portadaListaFragment.setDrawer(drawer);
        portadaListaFragment.setPasadorDePeliculas(this);

        chequearEstadoDeLogin();
        cargarPeliculas();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
        //super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //voy a hacer en esta parte lo del switch para que al hacer click en
        //cada uno de los items te lleve a su correspondiente actividad
        int id = item.getItemId();
        switch (id) {

            case R.id.logIn:
                if (mAuth.getCurrentUser() == null){
                    Intent u = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(u);
                } else {
                    mAuth.signOut();
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    chequearEstadoDeLogin();
                    Toast.makeText(this, "Usuario Deslogueado", Toast.LENGTH_SHORT).show();
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


    public void chequearEstadoDeLogin(){

        NavigationView navigation = findViewById(R.id.nav_view);
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

    public void cargarPeliculas(){
        final PeliculasController peliculasController = new PeliculasController();

        peliculasController.obtenerPeliculas(new ResultsListener<List<Pelicula>>() {
            @Override
            public void finish(List<Pelicula> resultado) {
                ContenedorPeliculasEstatico.peliculaList.clear();
                ContenedorPeliculasEstatico.peliculaList.addAll(resultado);
                fragmentManager.beginTransaction().replace(R.id.contenedorDeFragmentPortada,portadaListaFragment).commitAllowingStateLoss();
            }
        }, this);

        peliculasController.obtenerGeneros(new ResultsListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> resultado) {
                ContenedorDeGenerosEstatico.generoList.clear();
                ContenedorDeGenerosEstatico.generoList.addAll(resultado);
            }
        }, this);
    }

    public void cargarFacebookUserData(String actualToken, final ResultsListener<UserFacebook> escuchadorDeUserFB){
        UserFacebookController userFacebookController = new UserFacebookController();

        //LLamar al controlador
        userFacebookController.obtenerUserFacebookData(new ResultsListener<UserFacebook>() {
            @Override
            public void finish(UserFacebook resultado) {
               escuchadorDeUserFB.finish(resultado);
            }
        }, actualToken);
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
    public void refrescar() {
        cargarPeliculas();
        portadaListaFragment.refrescar();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}
