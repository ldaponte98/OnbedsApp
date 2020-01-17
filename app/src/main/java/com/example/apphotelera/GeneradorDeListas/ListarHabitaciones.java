package com.example.apphotelera.GeneradorDeListas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotelera.DetallesReserva;
import com.example.apphotelera.Herramientas.Tools;
import com.example.apphotelera.Modelos.Habitacion;
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.R;

import java.util.ArrayList;
import java.util.List;

public class ListarHabitaciones extends RecyclerView.Adapter<ListarHabitaciones.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView num_habitacion, cant_adultos, cant_niños, tipo, clase;

        private Context context;
        private int posicion = 0;
        private List<Habitacion> habitaciones = new ArrayList<Habitacion>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            num_habitacion = (TextView) itemView.findViewById(R.id.txt_view_habitacion_numero);
            cant_adultos = (TextView) itemView.findViewById(R.id.txt_view_habitacion_cant_adultos);
            cant_niños = (TextView) itemView.findViewById(R.id.txt_view_habitacion_cant_nino);
            tipo = (TextView) itemView.findViewById(R.id.txt_view_habitacion_tipo);
            clase = (TextView) itemView.findViewById(R.id.txt_view_habitacion_clase);

        }

        void setOnClickListener(List<Habitacion> lista, int position) {
            habitaciones = lista;
            posicion = position;

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {


            }
        }

    }

    public List<Habitacion> listaHabitaciones;
    public Context contexto;
    public int posi;

    public ListarHabitaciones(List<Habitacion> listaHabitaciones, Context contexto) {
        this.listaHabitaciones = listaHabitaciones;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_habitaciones, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        posi = position;
        holder.num_habitacion.setText("Habitacion " + listaHabitaciones.get(position).getNumero());
        holder.cant_adultos.setText(String.valueOf(listaHabitaciones.get(position).getCant_adultos()));
        holder.cant_niños.setText(String.valueOf(listaHabitaciones.get(position).getCant_niños()));
        holder.tipo.setText(listaHabitaciones.get(position).getTipo());
        holder.clase.setText(listaHabitaciones.get(position).getClase());

    }


    @Override
    public int getItemCount() {
        return listaHabitaciones.size();
    }


}