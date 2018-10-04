package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;


import android.annotation.TargetApi;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.CompartirActivity;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;

import java.util.ArrayList;
import java.util.List;

public class FragmentSalasHorarios extends Fragment implements AdapterView.OnItemSelectedListener /*, OnMapReadyCallBack*/ {
    String titulo = "Funciones";

    private Uri uri;
    private String url;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    String posterPelicula;
    String cine;

    MapFragment mMapFragment;

    private Button botonVerMapa, botonCompartir, botonComprar;

    private WebView mWebView;

    private Bundle bundleUrl = new Bundle();

    public FragmentSalasHorarios() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewFragment = inflater.inflate(R.layout.fragment_fragment_salas_horarios, container, false);

        //Traigo el titulo de la pelicula
        Bundle bundle = getArguments();
        final String tituloPelicula = bundle.getString("tituloPelicula");

        // Spinner element
        final Spinner spinnerCines = viewFragment.findViewById(R.id.spinnerCines);
        final Spinner spinnerTurnos = viewFragment.findViewById(R.id.spinnerTurnos);
        final Spinner spinnerFecha = viewFragment.findViewById(R.id.spinnerFecha);

        // Spinner click listener
        spinnerCines.setOnItemSelectedListener(this);
        spinnerTurnos.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> spinnerCinesList = new ArrayList<String>();
        spinnerCinesList.add("Hoyts Abasto");
        spinnerCinesList.add("Cinemark Caballito");
        spinnerCinesList.add("Village Caballito");
        spinnerCinesList.add("Hoyts Dot");
        spinnerCinesList.add("Cinemark Puerto Madero");
        spinnerCinesList.add("Village Recoleta");
        spinnerCinesList.add("Cinemark Palermo");

        List<String> spinnerTurnoList = new ArrayList<String>();
        spinnerTurnoList.add("14:00");
        spinnerTurnoList.add("15:10");
        spinnerTurnoList.add("16:50");
        spinnerTurnoList.add("18:00");
        spinnerTurnoList.add("19:35");
        spinnerTurnoList.add("20:55");
        spinnerTurnoList.add("22:40");
        spinnerTurnoList.add("01:00");

        List<String> spinnerFechaList = new ArrayList<>();
        spinnerFechaList.add("25-7");
        spinnerFechaList.add("26-7");
        spinnerFechaList.add("27-7");
        spinnerFechaList.add("28-7");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterSpinnerCines = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerCinesList);
        ArrayAdapter<String> dataAdapterSpinnerTurnos = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerTurnoList);
        ArrayAdapter<String> dataAdapterSpinnerFecha = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerFechaList);


        // Drop down layout style - list view with radio button
        dataAdapterSpinnerCines.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterSpinnerTurnos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterSpinnerFecha.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCines.setAdapter(dataAdapterSpinnerCines);
        spinnerTurnos.setAdapter(dataAdapterSpinnerTurnos);
        spinnerFecha.setAdapter(dataAdapterSpinnerFecha);

        mMapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        //fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();

        botonCompartir = viewFragment.findViewById(R.id.btnCompartir);

        botonCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cineSeleccionado = spinnerCines.getSelectedItem().toString();
             //   String cine = spinnerCines.getSelectedItem().toString();
                String horario = spinnerTurnos.getSelectedItem().toString();
                String fecha = spinnerFecha.getSelectedItem().toString();
                Intent intent = new Intent(getContext(), CompartirActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cineSeleccionado", cineSeleccionado);
                bundle.putString("tituloPelicula", tituloPelicula);
                bundle.putString("horario", horario);
                bundle.putString("posterPelicula", posterPelicula);
                bundle.putString("fecha", fecha);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        botonComprar = viewFragment.findViewById(R.id.btnComprar);

        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Spinner element

                String cineSeleccionado = spinnerCines.getSelectedItem().toString();

                if (cineSeleccionado.equals("Hoyts Abasto")){
                    uri = uri.parse("https://www.cinemarkhoyts.com.ar/home");
                    url = "https://www.cinemarkhoyts.com.ar/home";
                } else if (cineSeleccionado.equals("Cinemark Caballito")){
                    uri= uri.parse ("https://www.cinemarkhoyts.com.ar/home");
                    url = "https://www.cinemarkhoyts.com.ar/home";
                } else if (cineSeleccionado.equals("Village Caballito")){
                    uri= uri.parse ("https://www.villagecines.com/");
                    url = "https://www.villagecines.com/";
                } else if (cineSeleccionado.equals("Hoyts Dot")){
                    uri= uri.parse ("http://dotbairesshopping.com/tag/cine/");
                    url = "https://www.cinemarkhoyts.com.ar/";
                }  else if (cineSeleccionado.equals("Cinemark Puerto Madero")) {
                    uri = uri.parse("https://www.cinemarkhoyts.com.ar/home");
                    url = "https://www.cinemarkhoyts.com.ar/home";
                }    else if (cineSeleccionado.equals("Village Recoleta")) {
                    uri = uri.parse("https://www.villagecines.com/");
                    url = "https://www.villagecines.com/";
                }   else if (cineSeleccionado.equals("Cinemark Palermo")) {
                    uri = uri.parse("https://www.cinemarkhoyts.com.ar/home");
                    url = "https://www.cinemarkhoyts.com.ar/home";
                }

                if (url != null)
                {
                    bundleUrl.putString("url", url);

                    manipuladorDeFragmentsCompartir.irAFragmentPublicarEntrada(bundleUrl);
                }

//                Intent intent = new Intent (Intent.ACTION_VIEW,uri);
//                startActivity(intent);
            }
        });

        botonVerMapa = viewFragment.findViewById(R.id.botonComoLlegar);

        botonVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cineSeleccionado = spinnerCines.getSelectedItem().toString();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + cineSeleccionado));
                Intent chooser=Intent.createChooser(intent, "ver mapa");
                startActivity(chooser);

            }
        });



        return viewFragment;
    }

   /* public void mostrarMapa(View view){
        Intent intent= null, chooser=null;

        if(view.getId()==R.id.botonPerfil) {
            intent = new Intent(android.content.Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=restaurants"));
            chooser=Intent.createChooser(intent, "ver mapa");
            startActivity(chooser);
        }

    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Fragment crearFragmentSalasHorarios(int posicion) {

        FragmentSalasHorarios fragmentSalasHorarios = new FragmentSalasHorarios();
        Bundle bundle = new Bundle();
        bundle.putString("tituloPelicula", ContenedorPeliculasEstatico.peliculaList.get(posicion).getTitle());
        fragmentSalasHorarios.setArguments(bundle);

        return fragmentSalasHorarios;
    }

    public void setPosterPelicula(String posterPelicula) {
        this.posterPelicula = posterPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCine() {
        return cine;
    }

    public void setCine(String cine) {
        this.cine = cine;
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }
}
