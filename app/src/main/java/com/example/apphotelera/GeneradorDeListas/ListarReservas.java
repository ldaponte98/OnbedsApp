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
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.R;

import java.util.ArrayList;
import java.util.List;

public class ListarReservas extends RecyclerView.Adapter<ListarReservas.ViewHolder>{

private View.OnClickListener listener;


public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView nomHotel, infoTiempo;
    private Button btnPreCheckin, btnChat;
    private CardView cardreserva;
    private Context context;
    private int posicion = 0;
    private List<Reserva> reservas = new ArrayList<>();

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        nomHotel = (TextView) itemView.findViewById(R.id.txtNomHotel);
        infoTiempo = (TextView) itemView.findViewById(R.id.txtinfotiempo);
        btnPreCheckin = (Button) itemView.findViewById(R.id.btnPreCheckin);
        btnChat = (Button) itemView.findViewById(R.id.btnChat);
        cardreserva = (CardView) itemView.findViewById(R.id.cardViewReserva);
    }

    void setOnClickListener(List<Reserva> lista, int position) {
        reservas = lista;
        posicion = position;
        btnPreCheckin.setOnClickListener(this);
        btnChat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.cardViewRuta:
                Intent act = new Intent(context, detalle_ruta.class);
                act.putExtra("id", rutas.get(posicion).getRouteId());
                context.startActivity(act);
                break;
                */
            case R.id.btnPreCheckin:

                Intent activity = new Intent(context, DetallesReserva.class);
                activity.putExtra("id_reserva", reservas.get(posicion).getId_reserva());
                context.startActivity(activity);


                break;


            case R.id.btnChat:
                Toast.makeText(context, "Señor " + reservas.get(posicion).getHuesped() + " preparese para chatear" , Toast.LENGTH_LONG).show();
                break;

        }
    }

}

    public List<Reserva> listaReservas;
    public Context contexto;
    public int posi;

    public ListarReservas(List<Reserva> listaReservas, Context contexto) {
        this.listaReservas = listaReservas;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_reservas, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        posi = position;
        holder.nomHotel.setText(listaReservas.get(position).getHotel().getNombre());
        String fechainicio = Tools.FormatoDeFecha1(listaReservas.get(position).getFecha_inicio());
        String fechafin = Tools.FormatoDeFecha1(listaReservas.get(position).getFecha_fin());
        holder.infoTiempo.setText(fechainicio +"   "+ fechafin);
        holder.setOnClickListener(listaReservas, position);
    }


    @Override
    public int getItemCount() {
        return listaReservas.size();
    }


}