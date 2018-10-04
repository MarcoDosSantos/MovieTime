package com.example.yonoc.coverflow.View.PerfilDeUsuario;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla.ViewPagerAdapter;
import com.example.yonoc.coverflow.View.LoginRegisterActivities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilDeUserFragment extends Fragment {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ImageView imageViewFotoPerfil;
    private TextView textViewNombrePerfil;
    private FirebaseUser firebaseUser;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;





    public PerfilDeUserFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_de_user, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null){
            String nombreUser = firebaseUser.getDisplayName();
            String urlFotoUser = firebaseUser.getPhotoUrl().toString();

            viewPager = view.findViewById(R.id.viewPagerPerfil);
            imageViewFotoPerfil = view.findViewById(R.id.imageViewFotoPerfil);
            textViewNombrePerfil = view.findViewById(R.id.nombreDelUsuarioEnPerfil);

            textViewNombrePerfil.setText(nombreUser);

            Picasso.with(getContext()).load(urlFotoUser).transform(new CropCircleTransformation()).into(imageViewFotoPerfil);

            FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir m = manipuladorDeFragmentsCompartir;


            FragmentManager fragmentManager = getChildFragmentManager();
            ViewPagerPerfilAdapter adapter = new ViewPagerPerfilAdapter(fragmentManager, m);
            viewPager.setAdapter(adapter);

            TabLayout tabLayout = view.findViewById(R.id.tabLayoutPerfil);
            //tabLayout.setBackgroundColor(R.color.backgroundCardview);
            //tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
            tabLayout.setupWithViewPager(viewPager);


            crearToolbar(view);
        } else {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }



        return view;
    }

    private void crearToolbar(View view) {
        toolbar = view.findViewById(R.id.toolbarPerfil);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
