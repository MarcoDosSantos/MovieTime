package com.example.yonoc.coverflow.View.MainActivityPortada.PortadaListaTipoNetflix;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yonoc.coverflow.Model.POJO.ContenedoresEstaticos.ContenedorPeliculasEstatico;
import com.example.yonoc.coverflow.Model.POJO.Pelicula;
import com.example.yonoc.coverflow.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonoc on 6/26/2018.
 */

public class RecyclerDeGrillaDePeliculasAdapter extends RecyclerView.Adapter{

    private List<Pelicula> listaDePeliculas;
    private PasadorDePeliculas pasadorDePeliculas;

    public RecyclerDeGrillaDePeliculasAdapter(List<Pelicula> lista, PasadorDePeliculas pasadorDePeliculas){
        this.listaDePeliculas = lista;
        this.pasadorDePeliculas = pasadorDePeliculas;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.celda_recycler_de_peliculas_en_portada_y_filtro, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Pelicula pelicula = listaDePeliculas.get(position);
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.llenarCelda(pelicula);
    }

    @Override
    public int getItemCount() {
        return listaDePeliculas.size();
    }



    public void actualizarLista (List<Pelicula> list){
        listaDePeliculas = new ArrayList<>();
        listaDePeliculas.addAll(list);
        notifyDataSetChanged();

    }

    public void limpiarLista() {
        listaDePeliculas.clear();
    }

    public void refrescar(){
        listaDePeliculas.clear();
        notifyDataSetChanged();

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                listaDePeliculas = ContenedorPeliculasEstatico.peliculaList;
                notifyDataSetChanged();
            }
        }, 5000);
    }


    /*****************Interfaz de paso de informacion **************************/

    public interface PasadorDePeliculas {
        void pasarPelicula(int posicion);
        void pasarPeliculaPorId (int id);
    }


    /**********************************VIEWHOLDER******************************/

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView, star1, star2, star3, star4, star5;
        private TextView textView;
        private int posicionEnListaOriginal;
        private int id;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPrueba);
            star1 = itemView.findViewById(R.id.star1);
            star2 = itemView.findViewById(R.id.star2);
            star3 = itemView.findViewById(R.id.star3);
            star4 = itemView.findViewById(R.id.star4);
            star5 = itemView.findViewById(R.id.star5);
            textView = itemView.findViewById(R.id.textViewTituloPeliculaEnCardViewInicial);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pasadorDePeliculas.pasarPelicula(posicionEnListaOriginal);
                    pasadorDePeliculas.pasarPeliculaPorId(id);
                }
            });

        }

        public void llenarCelda (Pelicula pelicula){
            String titulo = pelicula.getTitle();
            String url = pelicula.getUrl();
            Integer promedioVotos = pelicula.getVote_average().intValue();
            posicionEnListaOriginal = ContenedorPeliculasEstatico.peliculaList.indexOf(pelicula);
            id = pelicula.getId();

            textView.setText(titulo);

            calificarConEstrellas(promedioVotos);

            if (url.startsWith("http")){
                Picasso.with(itemView.getContext()).load(url).into(imageView);
            } else {
                Picasso.with(itemView.getContext()).load("https://image.tmdb.org/t/p/w342" + pelicula.getUrl()).into(imageView);
            }
        }

        private void calificarConEstrellas(Integer promedioVotos) {
            switch (promedioVotos){
                case 0:
                    break;
                case 1:
                    star1.setImageResource(R.drawable.star_icon);
                    break;
                case 2:
                    star1.setImageResource(R.drawable.star_icon);
                    break;
                case 3:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    break;
                case 4:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    break;
                case 5:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    break;
                case 6:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    break;
                case 7:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    star4.setImageResource(R.drawable.star_icon);
                    break;
                case 8:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    star4.setImageResource(R.drawable.star_icon);
                    break;
                case 9:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    star4.setImageResource(R.drawable.star_icon);
                    star5.setImageResource(R.drawable.star_icon);
                    break;
                case 10:
                    star1.setImageResource(R.drawable.star_icon);
                    star2.setImageResource(R.drawable.star_icon);
                    star3.setImageResource(R.drawable.star_icon);
                    star4.setImageResource(R.drawable.star_icon);
                    star5.setImageResource(R.drawable.star_icon);
                    break;
            }
        }
    }
}
