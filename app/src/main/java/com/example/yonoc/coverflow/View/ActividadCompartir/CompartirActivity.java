package com.example.yonoc.coverflow.View.ActividadCompartir;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmacionDeIntercambioFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ContactoActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas.BorrarEntradaFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas.MiListaDeEntradasPublicadasFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.PublicarEntrada.PublicarEntradasOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.PublicarEntrada.PublicacionConfirmadaOverlay;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.example.yonoc.coverflow.View.PerfilDeUsuario.PerfilDeUserActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
//import com.google.firebase.database.Transaction;

public class CompartirActivity extends AppCompatActivity implements FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir, NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentCompartirEntrada fragmentCompartirEntrada;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private MenuItem menuItemLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
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
        setContentView(R.layout.activity_compartir);

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              /* if(firebaseAuth.getCurrentUser() == null){
                   startActivity(new Intent(MainActivity.this, LoginActivity.class));
               }*/
            }
        };

        crearToolbarYMenu();
        chequearEstadoDeLogin();

        fragmentManager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();
        Boolean irAPublicadas = bundle.getBoolean("irAPublicadas");

        if (irAPublicadas){
            MiListaDeEntradasPublicadasFragment miListaDeEntradasPublicadasFragment = new MiListaDeEntradasPublicadasFragment();
            miListaDeEntradasPublicadasFragment.setManipuladorDeFragmentsCompartir(this);
            fragmentManager.beginTransaction().replace(R.id.contenededorDeFragmentCompartirEntrada, miListaDeEntradasPublicadasFragment).commit();
        } else {
            fragmentCompartirEntrada = new FragmentCompartirEntrada();
            fragmentCompartirEntrada.setArguments(bundle);
            fragmentCompartirEntrada.setManipuladorDeFragmentsCompartir(this);
            fragmentManager.beginTransaction().replace(R.id.contenededorDeFragmentCompartirEntrada, fragmentCompartirEntrada).commit();
        }
    }

    private void crearToolbarYMenu() {
        toolbar = findViewById(R.id.toolBarCompartir);
        navigationView = findViewById(R.id.nav_view_compartir);

        drawer = findViewById(R.id.drawerLayoutCompartir);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();
        menuItemLogin = menuNav.findItem(R.id.logIn);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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



    public void chequearEstadoDeLogin(){

        NavigationView navigation = findViewById(R.id.nav_view_compartir);
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
    public void irAFragmentPublicarEntrada(Bundle bundle) {
        PublicarEntradasOverlayFragment publicarEntradasOverlayFragment = new PublicarEntradasOverlayFragment();
        publicarEntradasOverlayFragment.setArguments(bundle);
        publicarEntradasOverlayFragment.setManipuladorDeFragmentsCompartir(this);
        fragmentManager.beginTransaction().replace(R.id.contenedorDeOverlay, publicarEntradasOverlayFragment).addToBackStack("1").commit();
    }

    @Override
    public void irAFragmentPublicacionConfirmada(Bundle bundle) {
        fragmentManager.popBackStack();
        PublicacionConfirmadaOverlay publicacionConfirmadaOverlay = new PublicacionConfirmadaOverlay();
        publicacionConfirmadaOverlay.setArguments(bundle);
        publicacionConfirmadaOverlay.setManipuladorDeFragmentsCompartir(this);
        fragmentManager.beginTransaction().replace(R.id.contenedorDeOverlay, publicacionConfirmadaOverlay).addToBackStack("1").commit();
    }

    @Override
    public void irAFragmentConfirmarCompartir(Bundle bundle, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter) {
        ConfirmarCompartirOverlayFragment confirmarCompartirOverlayFragment = new ConfirmarCompartirOverlayFragment();
        confirmarCompartirOverlayFragment.setArguments(bundle);
        confirmarCompartirOverlayFragment.setManipuladorDeFragmentsCompartir(this);
        confirmarCompartirOverlayFragment.setQuitadorDeElementosDelAdapter(adapter);
        fragmentManager.beginTransaction().replace(R.id.contenedorDeOverlay, confirmarCompartirOverlayFragment).addToBackStack("1").commit();
    }

    @Override
    public void irAConfirmacionDeIntercambio(Bundle bundle) {
        fragmentManager.popBackStack();
        ConfirmacionDeIntercambioFragment confirmacionDeIntercambioFragment = new ConfirmacionDeIntercambioFragment();
        confirmacionDeIntercambioFragment.setArguments(bundle);
        confirmacionDeIntercambioFragment.setManipuladorDeFragmentsCompartir(this);
        fragmentManager.beginTransaction().replace(R.id.contenedorDeOverlay, confirmacionDeIntercambioFragment).addToBackStack("1").commit();
    }

    @Override
    public void irAMiListaDeEntradas() {
        MiListaDeEntradasPublicadasFragment miListaDeEntradasPublicadasFragment = new MiListaDeEntradasPublicadasFragment();
        miListaDeEntradasPublicadasFragment.setManipuladorDeFragmentsCompartir(this);
        fragmentManager.beginTransaction().replace(R.id.contenededorDeFragmentCompartirEntrada, miListaDeEntradasPublicadasFragment).addToBackStack("1").commit();
    }

    @Override
    public void irAFragmentBorrarEntradaPropia(String key, String cine, String pelicula, int posicion, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("cine", cine);
        bundle.putString("pelicula", pelicula);
        bundle.putInt("posicion", posicion);

        BorrarEntradaFragment borrarEntradaFragment = new BorrarEntradaFragment();
        borrarEntradaFragment.setArguments(bundle);
        borrarEntradaFragment.setManipuladorDeFragmentsCompartir(this);
        borrarEntradaFragment.setQuitadorDeElementosDelAdapter(adapter);

        fragmentManager.beginTransaction().replace(R.id.contenedorDeOverlay, borrarEntradaFragment).addToBackStack("1").commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawerLayoutCompartir);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            fragmentManager.popBackStack();
        }
    }


}
