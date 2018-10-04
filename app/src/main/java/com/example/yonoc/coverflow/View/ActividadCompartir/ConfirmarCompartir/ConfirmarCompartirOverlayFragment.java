package com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmarCompartirOverlayFragment extends Fragment {

    private TextView titulo, sala;
    private View background;
    private Bundle bundle;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;
    private QuitadorDeElementosDelAdapter quitadorDeElementosDelAdapter;

    public ConfirmarCompartirOverlayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_confirmar_compartir_overlay, container, false);

        titulo = view.findViewById(R.id.tituloPeliculaAIntercambiar);
        sala = view.findViewById(R.id.salaIntercambiada);
        background = view.findViewById(R.id.backgroundDelOverlayConfirmarIntercambio);

        bundle = getArguments();

        titulo.setText(bundle.getString("nombrePelicula"));
        sala.setText(bundle.getString("nombreCine"));



        Button confirmarEntrada = view.findViewById(R.id.quieroMiEntrada);

        confirmarEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarIntercambio();
            }
        });

        background = view.findViewById(R.id.backgroundDelOverlayConfirmarIntercambio);

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manipuladorDeFragmentsCompartir.cerrarFragment();
            }
        });


        return view;
    }

    private void confirmarIntercambio() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid().toString();
        String miNombreDeUsuario = user.getDisplayName();
        String miURLdeImagen = user.getPhotoUrl().toString();
        String otroUserId = bundle.getString("otroUserId");
        String keyTicket = bundle.getString("keyEntrada");
        String chatId = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();


        String matchKey = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(userId).child("match").push().getKey();

        Map<String, String> entradaConfirmadaVersionUser = new HashMap<>();
        entradaConfirmadaVersionUser.put("chat", chatId);
        entradaConfirmadaVersionUser.put("otro_user_id", otroUserId);
        entradaConfirmadaVersionUser.put("pelicula", bundle.getString("nombrePelicula"));
        entradaConfirmadaVersionUser.put("nombre", bundle.getString("nombreUsuario"));
        entradaConfirmadaVersionUser.put("nombre_cine", bundle.getString("nombreCine"));
        entradaConfirmadaVersionUser.put("url", bundle.getString("url"));
        entradaConfirmadaVersionUser.put("fecha", bundle.getString("fecha"));
        entradaConfirmadaVersionUser.put("horario", bundle.getString("horario"));
        entradaConfirmadaVersionUser.put("poster", bundle.getString("poster"));

        DatabaseReference userChat = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(userId).child("match").child(matchKey);
        userChat.setValue(entradaConfirmadaVersionUser);

        Map<String, String> entradaConfirmadaVersionOtroUser = new HashMap<>();
        entradaConfirmadaVersionOtroUser.put("chat", chatId);
        entradaConfirmadaVersionOtroUser.put("otro_user_id", userId);
        entradaConfirmadaVersionOtroUser.put("pelicula", bundle.getString("nombrePelicula"));
        entradaConfirmadaVersionOtroUser.put("nombre", miNombreDeUsuario);
        entradaConfirmadaVersionOtroUser.put("nombre_cine", bundle.getString("nombreCine"));
        entradaConfirmadaVersionOtroUser.put("url", miURLdeImagen);
        entradaConfirmadaVersionOtroUser.put("fecha", bundle.getString("fecha"));
        entradaConfirmadaVersionOtroUser.put("horario", bundle.getString("horario"));
        entradaConfirmadaVersionOtroUser.put("poster", bundle.getString("poster"));


        DatabaseReference otroUserChat = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(otroUserId).child("match").child(matchKey);
        otroUserChat.setValue(entradaConfirmadaVersionOtroUser);

        DatabaseReference matchRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("ofrece_entrada_para_compartir").child(otroUserId).child("historial").child(keyTicket);
        matchRef.setValue(null);

        bundle.putString("chatId", chatId);

        quitadorDeElementosDelAdapter.quitarElementosDelAdapterEnPosicion(bundle.getInt("adapterPosition"));
        manipuladorDeFragmentsCompartir.irAConfirmacionDeIntercambio(bundle);
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }

    public void setQuitadorDeElementosDelAdapter(QuitadorDeElementosDelAdapter quitadorDeElementosDelAdapter) {
        this.quitadorDeElementosDelAdapter = quitadorDeElementosDelAdapter;
    }

    public interface QuitadorDeElementosDelAdapter{
        void quitarElementosDelAdapterEnPosicion(int posicion);
    }
}
