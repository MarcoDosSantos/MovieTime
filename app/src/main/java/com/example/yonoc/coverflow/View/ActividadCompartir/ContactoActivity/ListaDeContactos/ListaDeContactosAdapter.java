package com.example.yonoc.coverflow.View.ActividadCompartir.ContactoActivity.ListaDeContactos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat.ChatActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by yonoc on 7/a14/2018.
 */

public class ListaDeContactosAdapter extends RecyclerView.Adapter {

    List<ContactoConfirmado> listaDeContactosLocal = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.celda_contacto_confirmado, parent, false);
        ContactoConfirmadoViewHolder viewHolder = new ContactoConfirmadoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactoConfirmado contactoConfirmado = listaDeContactosLocal.get(position);
        ContactoConfirmadoViewHolder contactoConfirmadoViewHolder = (ContactoConfirmadoViewHolder) holder;
        contactoConfirmadoViewHolder.llenarCelda(contactoConfirmado);
    }

    @Override
    public int getItemCount() {
        return listaDeContactosLocal.size();
    }

    public void sumarAListaLocal (ContactoConfirmado contactoConfirmado){
        listaDeContactosLocal.add(0, contactoConfirmado);
    }

    public int getListSize(){
        return listaDeContactosLocal.size();
    }


    /***********************VIEWHOLDER********************/

    private class ContactoConfirmadoViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewContacto;
        private TextView textViewNombreContacto, textViewPeliculaContacto, textViewCineContaco;
        private CardView cardView;
        private String chatId;
        private String nombre;
        private String url;


        public ContactoConfirmadoViewHolder(final View itemView) {
            super(itemView);
            imageViewContacto = itemView.findViewById(R.id.contactoImagen);
            textViewNombreContacto = itemView.findViewById(R.id.contactoNombre);
            textViewCineContaco = itemView.findViewById(R.id.contactoCine);
            textViewPeliculaContacto = itemView.findViewById(R.id.contactoPelicula);
            cardView = itemView.findViewById(R.id.cardviewListaDeContactos);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("chatId", chatId);
                    bundle.putBoolean("vengoDeConfirmarUnaEntrada", false);
                    bundle.putString("nombreUsuario", nombre);
                    bundle.putString("url", url);
                    Intent intent = new Intent(itemView.getContext(), ChatActivity.class);
                    intent.putExtras(bundle);
                    itemView.getContext().startActivity(intent);
                }
            });


        }

        public void llenarCelda (ContactoConfirmado contactoConfirmado){
            textViewNombreContacto.setText(contactoConfirmado.getNombre());
            textViewCineContaco.setText(contactoConfirmado.getCine());
            textViewPeliculaContacto.setText(contactoConfirmado.getPelicula());
            chatId = contactoConfirmado.getChatId();
            nombre = contactoConfirmado.getNombre();
            url = contactoConfirmado.getUrlImagen();

            Picasso.with(itemView.getContext()).load(contactoConfirmado.getUrlImagen()).transform(new CropCircleTransformation()).into(imageViewContacto);
        }

    }


}
