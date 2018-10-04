package com.example.yonoc.coverflow.View.PerfilDeUsuario;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.CompartirActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ContactoActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas.BorrarEntradaFragment;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.example.yonoc.coverflow.View.MainActivityPortada.MainActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class PerfilDeUserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir{

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private MenuItem menuItemLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FragmentManager supportFragmentManager;
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
        setContentView(R.layout.activity_perfil_de_user);

        setTitle("Mi Perfil");

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
              /* if(firebaseAuth.getCurrentUser() == null){
                   startActivity(new Intent(MainActivity.this, LoginActivity.class));
               }*/
            }
        };


        drawer = findViewById(R.id.drawerLayoutPerfil);
        navigationView = findViewById(R.id.nav_view_perfil);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menuNav = navigationView.getMenu();
        menuItemLogin = menuNav.findItem(R.id.logIn);

        chequearEstadoDeLogin();

        PerfilDeUserFragment perfilDeUserFragment = new PerfilDeUserFragment();
        perfilDeUserFragment.setDrawer(drawer);
        perfilDeUserFragment.setManipuladorDeFragmentsCompartir(this);

        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().replace(R.id.contenedorDePerfilDeUsuario, perfilDeUserFragment).commitAllowingStateLoss();

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
                //startActivity(new Intent(this, PerfilDeUserActivity.class));
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawerLayoutPerfil);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    public void chequearEstadoDeLogin(){


        NavigationView navigation = findViewById(R.id.nav_view_perfil);
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

/*        if (mAuth.getCurrentUser() != null) {
            menuItemLogin.setTitle("Log Out");
        } else {
            menuItemLogin.setTitle("Log In");
        }*/
    }

    @Override
    public void cerrarFragment() {
        supportFragmentManager.popBackStack();
    }

    @Override
    public void irAFragmentPublicarEntrada(Bundle bundle) {

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
    public void irAFragmentBorrarEntradaPropia(String key, String sala, String titulo, int posicion, ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter adapter) {
        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        bundle.putString("cine", sala);
        bundle.putString("pelicula", titulo);
        bundle.putInt("posicion", posicion);

        BorrarEntradaFragment borrarEntradaFragment = new BorrarEntradaFragment();
        borrarEntradaFragment.setArguments(bundle);
        borrarEntradaFragment.setManipuladorDeFragmentsCompartir(this);
        borrarEntradaFragment.setQuitadorDeElementosDelAdapter(adapter);

        supportFragmentManager.beginTransaction().replace(R.id.contenedorDeOverlayEnPerfil, borrarEntradaFragment).addToBackStack("1").commit();
    }
}
