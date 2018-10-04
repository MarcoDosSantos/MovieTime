package com.example.yonoc.coverflow.View.ActividadClasificarPorGenero;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorDeGenerosEstatico;
import com.example.yonoc.coverflow.Model.POJO.Genero;
import com.example.yonoc.coverflow.R;
import com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix.RecyclerDeGrillaDePeliculasAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 7/2/2018.
 */

public class RecyclerFiltroPorGeneroAdapter extends RecyclerView.Adapter {

    private PasadorDeGenero pasadorDeGenero;

    public RecyclerFiltroPorGeneroAdapter(PasadorDeGenero pasadorDeGenero) {
        this.pasadorDeGenero = pasadorDeGenero;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.celda_de_genero_e_icono, parent, false);
        RecyclerGenerosViewHolder recyclerGenerosViewHolder = new RecyclerGenerosViewHolder(view);
        return recyclerGenerosViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Genero genero = ContenedorDeGenerosEstatico.generoList.get(position);
        RecyclerGenerosViewHolder recyclerGenerosViewHolder = (RecyclerGenerosViewHolder) holder;
        recyclerGenerosViewHolder.llenarCelda(genero);
    }

    @Override
    public int getItemCount() {
        return ContenedorDeGenerosEstatico.generoList.size();
    }

    public interface PasadorDeGenero {
        void pasarGenero(Integer codigoDeGenero);
    }


    /*****************************VIEWHOLDER******************************/

    public class RecyclerGenerosViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconoDeGenero;
        private TextView nombreDeGenero;
        private Integer codigoDeGenero;

        public RecyclerGenerosViewHolder(final View itemView) {
            super(itemView);
            iconoDeGenero = itemView.findViewById(R.id.imageViewIconoGenero);
            nombreDeGenero = itemView.findViewById(R.id.textViewNombreDeGenero);

            iconoDeGenero.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(itemView.getContext(), codigoDeGenero.toString(), Toast.LENGTH_SHORT).show();
                    pasadorDeGenero.pasarGenero(codigoDeGenero);
                }
            });
        }


        public void llenarCelda(Genero genero){
            nombreDeGenero.setText(genero.getName());
            codigoDeGenero = genero.getId();

            ponerIconoDeGenero();


        }

        private void ponerIconoDeGenero() {
            switch (codigoDeGenero){
                case 28:
                    iconoDeGenero.setImageResource(R.drawable.accion);
                    break;
                case 12:
                    iconoDeGenero.setImageResource(R.drawable.aventura);
                    break;
                case 16:
                    iconoDeGenero.setImageResource(R.drawable.animacion);
                    break;
                case 35:
                    iconoDeGenero.setImageResource(R.drawable.comedia);
                    break;
                case 80:
                    iconoDeGenero.setImageResource(R.drawable.crimen);
                    break;
                case 99:
                    iconoDeGenero.setImageResource(R.drawable.documental);
                    break;
                case 18:
                    iconoDeGenero.setImageResource(R.drawable.drama);
                    break;
                case 10751:
                    iconoDeGenero.setImageResource(R.drawable.familiar);
                    break;
                case 14:
                    iconoDeGenero.setImageResource(R.drawable.fantasia);
                    break;
                case 36:
                    iconoDeGenero.setImageResource(R.drawable.historica);
                    break;
                case 27:
                    iconoDeGenero.setImageResource(R.drawable.terror);
                    break;
                case 10402:
                    iconoDeGenero.setImageResource(R.drawable.musical);
                    break;
                case 9648:
                    iconoDeGenero.setImageResource(R.drawable.misterio);
                    break;
                case 10749:
                    iconoDeGenero.setImageResource(R.drawable.romance);
                    break;
                case 878:
                    iconoDeGenero.setImageResource(R.drawable.ciencia_ficcion);
                    break;
                case 10770:
                    iconoDeGenero.setImageResource(R.drawable.pelicula_para_tv);
                    break;
                case 53:
                    iconoDeGenero.setImageResource(R.drawable.suspense);
                    break;
                case 10752:
                    iconoDeGenero.setImageResource(R.drawable.belica);
                    break;
                case 37:
                    iconoDeGenero.setImageResource(R.drawable.western);
                    break;

            }
        }

    }
}
