package com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ListaDeContactos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yonoc.coverflow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaDeContactosFragment extends Fragment {

    private RecyclerView recyclerView;
    private String userId, chatId, otroUserId, bundleOtroUserId, matchKey, peliculaMatch, nombreOtroUserMatch, nombreCineMatch, urlMatch;
    private ListaDeContactosAdapter adapter;


    public ListaDeContactosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_de_contactos, container, false);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        //bundleOtroUserId = getArguments().getString("otroUserId");

        recyclerView = view.findViewById(R.id.recyclerDeContactosConfirmados);
        adapter = new ListaDeContactosAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        cargarChatId();

        return view;
    }

    private void cargarChatId() {
        DatabaseReference chatContactosDB = FirebaseDatabase.getInstance().getReference().child("usuariosActivos").child(userId).child("match");

        chatContactosDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()){
                    matchKey = dataSnapshot.getKey();
                    chatId = dataSnapshot.child("chat").getValue(String.class);
                    otroUserId = dataSnapshot.child("otro_user_id").getValue(String.class);
                    peliculaMatch = dataSnapshot.child("pelicula").getValue(String.class);
                    nombreOtroUserMatch = dataSnapshot.child("nombre").getValue(String.class);
                    nombreCineMatch = dataSnapshot.child("nombre_cine").getValue(String.class);
                    urlMatch = dataSnapshot.child("url").getValue(String.class);

                    ContactoConfirmado contactoConfirmado = new ContactoConfirmado(userId,otroUserId,urlMatch,nombreOtroUserMatch,peliculaMatch, nombreCineMatch, chatId);
                    adapter.sumarAListaLocal(contactoConfirmado);
                    adapter.notifyDataSetChanged();
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

}
