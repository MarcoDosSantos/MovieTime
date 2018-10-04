package com.example.yonoc.coverflow.View.ActividadCompartir.MiListaDeEntradasPublicadas;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ComponentesCompartir.EventoCompartir;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 7/19/2018.
 */

public class MisEntradasPublicadasAdapter extends RecyclerView.Adapter implements ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter {

    private List<EventoCompartir> listaLocalDeEntradasPublicadas = new ArrayList<>();
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.celda_evento, parent, false);
        MisEntradasViewHolder misEntradasViewHolder = new MisEntradasViewHolder(view);
        return misEntradasViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventoCompartir eventoCompartir = listaLocalDeEntradasPublicadas.get(position);
        MisEntradasViewHolder misEntradasViewHolder = (MisEntradasViewHolder) holder;
        misEntradasViewHolder.llenarCelda(eventoCompartir);
    }

    @Override
    public int getItemCount() {
        return listaLocalDeEntradasPublicadas.size();
    }

    public void sumarEventoALaLista(String nombrePelicula, String nombreUsuario, String otroUserId, String imagenUsuario, String keyEntrada, String nombreCine, String posterPelicula, String horario, String fecha){
        if (nombrePelicula != null){
            listaLocalDeEntradasPublicadas.add(new EventoCompartir(nombrePelicula, nombreUsuario, otroUserId, imagenUsuario, keyEntrada, nombreCine, posterPelicula, horario, fecha));
            notifyDataSetChanged();
        }
    }

    public void limpiarLista(){
        listaLocalDeEntradasPublicadas.clear();
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }

    @Override
    public void quitarElementosDelAdapterEnPosicion(int posicion) {
        listaLocalDeEntradasPublicadas.remove(posicion);
        notifyItemRemoved(posicion);
    }



    /**************************VIEWHOLDER************************/

    private class MisEntradasViewHolder extends RecyclerView.ViewHolder {

        private TextView nombrePelicula, nombreUsuario, sala, fecha, horario;
        private ImageView imagenUsuario;
        private CardView cardView;
        private EventoCompartir eventoCompartirLocal;
        private int posicion;

        public MisEntradasViewHolder(View itemView) {
            super(itemView);
            nombrePelicula = itemView.findViewById(R.id.textViewNombrePelicula);
            nombreUsuario = itemView.findViewById(R.id.textViewNombreUsuario);
            cardView = itemView.findViewById(R.id.cardViewEventoTicket);
            imagenUsuario = itemView.findViewById(R.id.imageViewUsuario);
            sala = itemView.findViewById(R.id.textViewSala);
            fecha = itemView.findViewById(R.id.textViewFecha);
            horario = itemView.findViewById(R.id.textViewHorario);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarEntrada();
                }
            });
        }

        private void borrarEntrada() {
            posicion = getAdapterPosition();
            manipuladorDeFragmentsCompartir.irAFragmentBorrarEntradaPropia(eventoCompartirLocal.getKeyTicket(), eventoCompartirLocal.getNombreCine(), eventoCompartirLocal.getNombrePelicula(), posicion, MisEntradasPublicadasAdapter.this);
        }


        public void llenarCelda(EventoCompartir eventoCompartir){
            eventoCompartirLocal = eventoCompartir;
            nombrePelicula.setText(eventoCompartir.getNombrePelicula());
            nombreUsuario.setText(eventoCompartir.getNombreCine());
            sala.setText(eventoCompartir.getFecha());
            fecha.setText(eventoCompartir.getHorario());
            horario.setText(null);

            Picasso.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w780" + eventoCompartir.getPoster()).into(imagenUsuario);
        }
    }


}
