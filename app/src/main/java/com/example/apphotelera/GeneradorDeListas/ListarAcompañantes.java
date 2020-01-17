package com.example.apphotelera.GeneradorDeListas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.apphotelera.Modelos.Acompañantes;
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.R;
import com.example.apphotelera.ServicesAsync.ServiceSearchTercero;

import java.util.ArrayList;
import java.util.List;

public class ListarAcompañantes extends RecyclerView.Adapter<ListarAcompañantes.ViewHolder>{


    private View.OnClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_nombre1,txt_nombre2, txt_apellido1, txt_apellido2, txt_direccion, txt_telefono, txt_identificacion, txt_email ,txt_numero_huesped;
        public static TextView txt_nombre1_search,txt_nombre2_search, txt_apellido1_search, txt_apellido2_search, txt_direccion_search, txt_telefono_search, txt_identificacion_search, txt_email_search;


        private Context context;
        private int posicion = 0;
        private List<Acompañantes> acompañantes = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            txt_nombre1 = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_nombre1);
            txt_nombre2 = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_nombre2);
            txt_apellido1 = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_apellido1);
            txt_apellido2 = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_apellido2);
            txt_direccion = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_direccion);
            txt_telefono = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_telefono);
            txt_identificacion = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_identificacion);
            txt_email = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_email);
            txt_numero_huesped = (TextView) itemView.findViewById(R.id.txt_detalle_reserva_acompañante_numero);
            txt_nombre1_search = txt_nombre1;
        }

        void setOnClickListener(List<Acompañantes> lista, final int position) {
            acompañantes = lista;
            posicion = position;
            txt_nombre1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setNombre1(s.toString());
                }
            });
            txt_nombre2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setNombre2(s.toString());
                }
            });
            txt_apellido1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setApellido1(s.toString());
                }
            });
            txt_apellido2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setApellido2(s.toString());
                }
            });
            txt_direccion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setDireccion(s.toString());
                }
            });
            txt_telefono.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setTelefono_movil(s.toString());
                }
            });
            txt_identificacion.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    boolean yaExiste = false;
                    if (s.toString().length() >= 7){
                            ServiceSearchTercero servicio = new ServiceSearchTercero(context, activity, s.toString(), acompañantes.get(position).getPosicion_in_lista(), id_habitacion);
                            servicio.execute();
                    }
                    acompañantes.get(position).setIdentificacion(s.toString());
                }
            });
            txt_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    acompañantes.get(position).setEmail(s.toString());
                }
            });
        }




        @Override
        public void onClick(View v) {
            switch (v.getId()) {

            }
        }

    }

    public List<Acompañantes> lista_acompañantes;
    public Context contexto;
    public int posi;

    public static String id_habitacion;
    public static String id_reserva;
    public static Activity activity = new Activity();


    public ListarAcompañantes(List<Acompañantes> listaacompañantes, Context contexto, Activity activity, String id_habitacion, String id_reserva) {
        this.lista_acompañantes = listaacompañantes;
        this.contexto = contexto;
        this.activity = activity;
        this.id_habitacion = id_habitacion;
        this.id_reserva = id_reserva;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_acompanantes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    List<String> list = new ArrayList<>();
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        posi = position;
        Acompañantes acompañante = lista_acompañantes.get(position);
        holder.txt_numero_huesped.setText("Huested "+(posi+1));
        holder.txt_identificacion.setText(acompañante.getIdentificacion());
        holder.txt_email.setText(acompañante.getEmail());
        holder.txt_nombre1.setText(acompañante.getNombre1());
        holder.txt_nombre2.setText(acompañante.getNombre2());
        holder.txt_apellido1.setText(acompañante.getApellido1());
        holder.txt_apellido2.setText(acompañante.getApellido2());
        holder.txt_direccion.setText(acompañante.getDireccion());
        holder.txt_telefono.setText(acompañante.getTelefono_movil());
        holder.setOnClickListener(lista_acompañantes, position);
        viewHolder = holder;
    }


    @Override
    public int getItemCount() {
        return lista_acompañantes.size();
    }

    public ListarAcompañantes.ViewHolder viewHolder;

    public List<String> get(){
        return list;
    }


}