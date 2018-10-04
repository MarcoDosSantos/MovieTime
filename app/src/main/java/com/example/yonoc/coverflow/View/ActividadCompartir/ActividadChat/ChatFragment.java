package com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yonoc.coverflow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private RecyclerView recyclerDeChat;
    private String userId, otroUserId, chatId, userName;
    private EditText entradaDeMensaje;
    private Button botonEnviar;
    private DatabaseReference chatDBRef;
    private Boolean vengoDePedirUnaEntrada;
    private ChatAdapter chatAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatId = getArguments().getString("chatId");
        chatDBRef = FirebaseDatabase.getInstance().getReference().child("chat").child(chatId);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        //otroUserId = getArguments().getString("otroUserId");
        //vengoDePedirUnaEntrada = getArguments().getBoolean("botonConfirmar");


        recyclerDeChat = view.findViewById(R.id.recyclerDeChat);
        entradaDeMensaje = view.findViewById(R.id.editTextMensaje);
        botonEnviar = view.findViewById(R.id.botonEnviarMensaje);

        chatAdapter = new ChatAdapter();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,true);
        recyclerDeChat.setAdapter(chatAdapter);
        recyclerDeChat.setLayoutManager(layoutManager);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publicarMensajeEnDB();

            }
        });

        buscarChats(chatId);

        return view;
    }


    private void publicarMensajeEnDB() {
        String mensaje = entradaDeMensaje.getText().toString();

        if (!mensaje.isEmpty()){
            DatabaseReference nuevoMensajeDB = chatDBRef.push();

            Map nuevoMensaje = new HashMap();
            nuevoMensaje.put("creado_por", userId);
            nuevoMensaje.put("mensaje", mensaje);
            nuevoMensaje.put("nombre", userName);
            nuevoMensajeDB.setValue(nuevoMensaje);
        }

        entradaDeMensaje.setText("");
    }


    private void buscarChats(String chatID2) {
        DatabaseReference dbBuscoEntrada = FirebaseDatabase.getInstance().getReference().child("chat").child(chatID2);
        dbBuscoEntrada.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    String key = dataSnapshot.getKey();
                    buscarMensajeYUsuario(key);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void buscarMensajeYUsuario(String key){
        DatabaseReference mensajeDB = FirebaseDatabase.getInstance().getReference().child("chat").child(chatId).child(key);
        mensajeDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String mensajeRecuperado = dataSnapshot.child("mensaje").getValue(String.class);
                    otroUserId = dataSnapshot.child("creado_por").getValue(String.class);
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    ObjetoChat objetoChat = new ObjetoChat(mensajeRecuperado,userId, otroUserId, nombre);
                    chatAdapter.sumarMensajeALista(objetoChat);
                    chatAdapter.notifyDataSetChanged();
                    scrollRecycler();
                }
                scrollRecycler();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void scrollRecycler() {
        int listSize = chatAdapter.getItemCount();
        recyclerDeChat.scrollToPosition(listSize);
    }


}
