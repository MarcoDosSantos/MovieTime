package com.example.yonoc.coverflow.View.ActividadCompartir.ComponentesCompartir;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.ActividadCompartir.ConfirmarCompartir.ConfirmarCompartirOverlayFragment;
import com.example.yonoc.coverflow.View.ActividadCompartir.FragmentCompartirEntrada;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class CompartirEntradaAdapter extends RecyclerView.Adapter implements ConfirmarCompartirOverlayFragment.QuitadorDeElementosDelAdapter {
    private List<EventoCompartir> listaDeEventosCompartir;
    private String nombrePeliculaAFiltrar;
    private FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir;

    public CompartirEntradaAdapter(List<EventoCompartir> listaDeEventosCompartir, String nombrePeliculaAFiltrar) {
        this.listaDeEventosCompartir = listaDeEventosCompartir;
        this.nombrePeliculaAFiltrar = nombrePeliculaAFiltrar;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View celda = layoutInflater.inflate(R.layout.celda_evento, parent, false);
        EventoViewHolder eventoViewHolder = new EventoViewHolder(celda);

        return eventoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        EventoCompartir eventoCompartir = listaDeEventosCompartir.get(position);
        EventoViewHolder eventoViewHolder = (EventoViewHolder) holder;
        eventoViewHolder.asignarEvento(eventoCompartir);
    }

    @Override
    public int getItemCount() {
        return listaDeEventosCompartir.size();
    }


    public void sumarEventoALaLista(String nombrePelicula, String nombreUsuario, String otroUserId, String imagenUsuario, String keyEntrada, String nombreCine, String posterPelicula, String horario, String fecha){
        if (nombrePelicula != null && nombrePelicula.equals(nombrePeliculaAFiltrar)){
            listaDeEventosCompartir.add(new EventoCompartir(nombrePelicula, nombreUsuario, otroUserId, imagenUsuario, keyEntrada, nombreCine, posterPelicula, horario, fecha));
            notifyDataSetChanged();
        }
    }

    public void limpiarLista(){
        listaDeEventosCompartir.clear();
    }

    public void setManipuladorDeFragmentsCompartir(FragmentCompartirEntrada.ManipuladorDeFragmentsCompartir manipuladorDeFragmentsCompartir) {
        this.manipuladorDeFragmentsCompartir = manipuladorDeFragmentsCompartir;
    }

    @Override
    public void quitarElementosDelAdapterEnPosicion(int posicion) {
        listaDeEventosCompartir.remove(posicion);
        notifyItemRemoved(posicion);
    }

    /**********************VIEWHOLDER****************************/

    private class EventoViewHolder extends RecyclerView.ViewHolder {
        private TextView nombrePelicula, nombreUsuario, sala, fecha, horario;
        private ImageView imagenUsuario;
        private CardView cardView;
        private String otroUserId, url;
        private EventoCompartir eventoCompartirLocal;
        private int adapterPosition;

        public EventoViewHolder(View itemView) {
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
                    solicitarEntrada();
                }
            });
        }

        private void solicitarEntrada() {
            adapterPosition = getAdapterPosition();
            Bundle bundle = new Bundle();
            bundle.putString("otroUserId", eventoCompartirLocal.getOtroUserID());
            bundle.putString("nombrePelicula", eventoCompartirLocal.getNombrePelicula());
            bundle.putString("nombreUsuario", eventoCompartirLocal.getNombreUsuario());
            bundle.putString("keyEntrada", eventoCompartirLocal.getKeyTicket());
            bundle.putString("nombreCine", eventoCompartirLocal.getNombreCine());
            bundle.putString("url", url);
            bundle.putString("fecha", eventoCompartirLocal.getFecha());
            bundle.putString("horario", eventoCompartirLocal.getHorario());
            bundle.putString("poster", eventoCompartirLocal.getPoster());
            bundle.putInt("adapterPosition", adapterPosition);

            manipuladorDeFragmentsCompartir.irAFragmentConfirmarCompartir(bundle, CompartirEntradaAdapter.this);
        }

        public void asignarEvento(EventoCompartir eventoCompartir){
            eventoCompartirLocal = eventoCompartir;
            otroUserId = eventoCompartir.getOtroUserID();
            url = eventoCompartir.getUrlImagenPerfil();
            nombrePelicula.setText(eventoCompartir.getNombrePelicula());
            nombreUsuario.setText(eventoCompartir.getNombreUsuario());
            sala.setText(eventoCompartir.getNombreCine());
            fecha.setText(eventoCompartir.getFecha());
            horario.setText(eventoCompartir.getHorario());

            Picasso.with(itemView.getContext()).load(eventoCompartir.getUrlImagenPerfil()).transform(new CropCircleTransformation()).into(imagenUsuario);
        }
    }
}


