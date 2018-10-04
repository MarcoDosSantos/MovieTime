package com.example.yonoc.coverflow.View.ActividadCompartir.ActividadChat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 7/9/2018.
 */

public class ChatAdapter extends Adapter {

    private List<ObjetoChat> chatList = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ObjetoChat objetoChat = chatList.get(position);
        ChatViewHolder chatViewHolder = (ChatViewHolder) holder;
        chatViewHolder.llenarCelda(objetoChat);

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public void sumarMensajeALista(ObjetoChat objetoChat){
        chatList.add(0, objetoChat);
    }


    /***********************VIEWHOLDER************************/

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        private TextView textoChat, publicadoPor;
        private LinearLayout linearLayout;

        public ChatViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.layoutChatItem);
            textoChat = itemView.findViewById(R.id.textViewChat);
            publicadoPor = itemView.findViewById(R.id.textViewNombreChat);
        }

        public void llenarCelda(ObjetoChat objetoChat){
            textoChat.setText(objetoChat.getMensaje());
            publicadoPor.setText(objetoChat.getNombre());
            //textoChat.setBackgroundColor(itemView.getResources().getColor(R.color.fondoMovieTime));
            //textoChat.setTextColor(Color.WHITE);

            String userId = objetoChat.getUserId();
            String otroUserId = objetoChat.getOtroUserId();
        }
    }
}
