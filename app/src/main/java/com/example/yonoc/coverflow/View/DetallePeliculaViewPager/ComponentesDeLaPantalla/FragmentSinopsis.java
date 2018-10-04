package com.example.yonoc.coverflow.View.DetallePeliculaViewPager.ComponentesDeLaPantalla;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSinopsis extends Fragment {

    public FragmentSinopsis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_sinopsis, container, false);

        Bundle bundle = getArguments();
        String sinopsis = bundle.getString("sinopsis");

        TextView textViewSinopsis = view.findViewById(R.id.textViewSinopsis);

        if (sinopsis.isEmpty()){
            textViewSinopsis.setText("Sin descripción disponible");
        } else {
            textViewSinopsis.setText(sinopsis);

        }

        return view;
    }

    public Fragment crearFragmentSinopsis(int posicion) {
        FragmentSinopsis fragmentSinopsis = new FragmentSinopsis();

        Bundle bundle = new Bundle();
        bundle.putString("sinopsis", ContenedorPeliculasEstatico.peliculaList.get(posicion).getOverview());

        fragmentSinopsis.setArguments(bundle);

        return fragmentSinopsis;

    }
}
