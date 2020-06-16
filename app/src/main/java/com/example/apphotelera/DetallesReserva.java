package com.example.apphotelera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.apphotelera.GeneradorDeListas.ListarAcompañantes;
import com.example.apphotelera.GeneradorDeListas.ListarHabitaciones;
import com.example.apphotelera.GeneradorDeListas.ListarReservas;
import com.example.apphotelera.Herramientas.Tools;
import com.example.apphotelera.Modelos.Acompañantes;
import com.example.apphotelera.Modelos.Habitacion;
import com.example.apphotelera.Modelos.Hotel;
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.Modelos.Usuario;
import com.example.apphotelera.ServicesAsync.ServiceSavePreCheckin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DetallesReserva extends AppCompatActivity {

    public static Reserva reserva;
    public static RecyclerView Recycler_ListarAcompañantes;

    TextView txt_nombre1,txt_nombre2, txt_apellido1, txt_apellido2, txt_direccion, txt_telefono, txt_identificacion, txt_email;


    Spinner spinner_habitaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reserva);
        reserva = new Reserva(this);
        Recycler_ListarAcompañantes = (RecyclerView) findViewById(R.id.recyclerAcompañantes);
        Recycler_ListarAcompañantes.setLayoutManager(new LinearLayoutManager(this));
        BottomNavigationView navView =  findViewById(R.id.navegador_botton_nav);
        reserva = reserva.FindByPK(getIntent().getStringExtra("id_reserva"));
        getSupportActionBar().setTitle("Onbeds Xperience");
        List<Acompañantes> acomp = new Acompañantes(this).All();

        spinner_habitaciones = (Spinner) findViewById(R.id.spinner_habitaciones);

        final Activity my_activity = this;

        llenarSpinnerHabitaciones();

        spinner_habitaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ListarAcompañantes(getApplicationContext(), my_activity, reserva.getLista_habitaciones().get(position).getId_habitacion());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        final CardView card_info_reserva, card_info_titular, card_info_acompañantes;
        card_info_reserva = (CardView) findViewById(R.id.card_info_reserva);
        card_info_titular = (CardView) findViewById(R.id.card_info_personal);
        card_info_acompañantes = (CardView) findViewById(R.id.card_acompañantes);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_detalle :
                        card_info_acompañantes.setVisibility(View.GONE);
                        card_info_reserva.setVisibility(View.VISIBLE);
                        card_info_titular.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.navigation_acompañantes :
                        card_info_acompañantes.setVisibility(View.VISIBLE);
                        card_info_reserva.setVisibility(View.GONE);
                        card_info_titular.setVisibility(View.GONE);
                        return true;

                    default:
                        return false;

                }
            }
        });

        txt_nombre1 = (TextView) findViewById(R.id.txt_detalle_reserva_titular_nombre1);
        txt_nombre2 = (TextView) findViewById(R.id.txt_detalle_reserva_titular_nombre2);
        txt_apellido1 = (TextView) findViewById(R.id.txt_detalle_reserva_titular_apellido1);
        txt_apellido2 = (TextView) findViewById(R.id.txt_detalle_reserva_titular_apellido2);
        txt_direccion = (TextView) findViewById(R.id.txt_detalle_reserva_titular_direccion);
        txt_telefono = (TextView) findViewById(R.id.txt_detalle_reserva_titular_telefono);
        txt_identificacion = (TextView) findViewById(R.id.txt_detalle_reserva_titular_identificacion);
        txt_email = (TextView) findViewById(R.id.txt_detalle_reserva_titular_email);

        RedondearImagenHotel();
        EstablecerInformacionDeHotel();
        EstablecerDetallesDeReserva();
        EstablecerInfoPropietario();
        ListarAcompañantes(this, this, reserva.getLista_habitaciones().get(0).getId_habitacion());


    }

    public void RedondearImagenHotel(){
        Drawable originalDrawable = getResources().getDrawable(R.mipmap.hotel1);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        if (originalBitmap.getWidth() > originalBitmap.getHeight()) {
            originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getHeight(), originalBitmap.getHeight());
        } else if (originalBitmap.getWidth() < originalBitmap.getHeight()) {
            originalBitmap = Bitmap.createBitmap(originalBitmap, 0, 0, originalBitmap.getWidth(), originalBitmap.getWidth());
        }

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable =
                RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());


        ImageView imageView = (ImageView) findViewById(R.id.img_detalle_hotel);

        imageView.setImageDrawable(roundedDrawable);
    }

    public void EstablecerInformacionDeHotel(){
        TextView txt_nombre, txt_pais_ciudad, txt_direccion, txt_email, txt_telefono;

        txt_nombre = (TextView) findViewById(R.id.txt_detalles_reserva_hotel_nombre);
        txt_pais_ciudad = (TextView) findViewById(R.id.txt_detalles_hotel_pais_ciudad);
        txt_direccion = (TextView) findViewById(R.id.txt_detalles_hotel_direccion);
        txt_email = (TextView) findViewById(R.id.txt_detalles_hotel_email);
        txt_telefono = (TextView) findViewById(R.id.txt_detalles_hotel_telefono);

        Hotel hotel = reserva.getHotel();
        txt_nombre.setText(hotel.getNombre());
        txt_pais_ciudad.setText(hotel.getPais()+" - "+hotel.getCiudad());
        txt_direccion.setText(hotel.getDireccion());
        txt_email.setText(hotel.getEmail());
        txt_telefono.setText(hotel.getTelefono());
    }

    public void EstablecerDetallesDeReserva(){
        TextView txt_estado,txt_numero, txt_fecha_inicio, txt_fecha_fin, txt_clase, txt_num_habitacion, txt_tipo_habitacion ,txt_cant_adulto, txt_cant_niño;
        txt_estado = (TextView) findViewById(R.id.txt_detalle_reserva_estado);
        txt_numero = (TextView) findViewById(R.id.txt_detalle_reserva_numero);
        txt_fecha_inicio = (TextView) findViewById(R.id.txt_detalle_reserva_fecha_inicio);
        txt_fecha_fin = (TextView) findViewById(R.id.txt_detalle_reserva_fecha_fin);
        txt_num_habitacion = (TextView) findViewById(R.id.txt_detalle_reserva_num_habitacion);

        txt_estado.setText(reserva.getEstado());
        txt_numero.setText(reserva.getNumero_reserva());
        txt_fecha_inicio.setText(new Tools().FechaFormato4(reserva.getFecha_inicio()));
        txt_fecha_fin.setText(new Tools().FechaFormato4(reserva.getFecha_fin()));
        String habitaciones = "";
        int cont = 1;
        for (Habitacion habitacion:reserva.getLista_habitaciones()){
            if (cont == reserva.getLista_habitaciones().size()){
                habitaciones += habitacion.getNumero();
            }else{
                habitaciones += habitacion.getNumero()+" - ";
            }
            cont++;
        }
        txt_num_habitacion.setText(habitaciones);

        txt_num_habitacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarInformacionHabitacion();
            }
        });

    }

    public void EstablecerInfoPropietario(){
        Usuario titular = new Usuario().Find(this);
        txt_nombre1.setText(titular.getNombre1());
        txt_nombre2.setText(titular.getNombre2());
        txt_apellido1.setText(titular.getApellido1());
        txt_apellido2.setText(titular.getApellido2());
        txt_direccion.setText(titular.getDireccion());
        txt_telefono.setText(titular.getTelefono_movil());
        txt_identificacion.setText(titular.getIdentificacion());
        txt_email.setText(titular.getEmail());
    }

    public static void ListarAcompañantes(Context context, Activity activity, String id_habitacion){


        int total_en_lista;

        if (reserva.getLista_acompañantes() == null){
            reserva.setLista_acompañantes(new ArrayList<Acompañantes>());
            total_en_lista = 0;
        }else{
            total_en_lista = reserva.FindAcompañantesByHabitacion(id_habitacion).size();
        }

        Habitacion habitacion = new Habitacion(context).FindByPK(id_habitacion);
        int total_huesped =  habitacion.getCant_adultos() + habitacion.getCant_niños();
        if (total_huesped > total_en_lista){
            int faltantes =  total_huesped - total_en_lista;
            for (int i = 1; i<= faltantes; i++){
                Acompañantes acompañante = new Acompañantes(context);
                acompañante.setId_habitacion(id_habitacion);
                acompañante.setPosicion_in_lista(reserva.getLista_acompañantes().size());
                reserva.getLista_acompañantes().add(acompañante);
            }
        }
        ListarAcompañantes AdaptadorDeListas_ListarAcompañantes;
        ProgressDialog progressDialog;

        AdaptadorDeListas_ListarAcompañantes = new ListarAcompañantes(reserva.FindAcompañantesByHabitacion(id_habitacion), context, activity, id_habitacion, reserva.getId_reserva());
        Recycler_ListarAcompañantes.setAdapter(AdaptadorDeListas_ListarAcompañantes);

    }
    public void PreguntarActualizacionDeReserva(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Esta seguro que desea actualizar la informacion de esta reserva?")
                .setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ActualizarDatosReserva();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ActualizarDatosReserva(){
        Usuario usuario = new Usuario().Find(this);
        usuario.setIdentificacion(txt_identificacion.getText().toString());
        usuario.setEmail(txt_email.getText().toString());
        usuario.setNombre1(txt_nombre1.getText().toString());
        usuario.setNombre2(txt_nombre2.getText().toString());
        usuario.setApellido1(txt_apellido1.getText().toString());
        usuario.setApellido2(txt_apellido2.getText().toString());
        usuario.setTelefono_movil(txt_telefono.getText().toString());
        usuario.setDireccion(txt_direccion.getText().toString());
        ServiceSavePreCheckin servicio = new ServiceSavePreCheckin(this, reserva, usuario);
        servicio.execute();
    }

    public void llenarSpinnerHabitaciones(){
        List<String> habitaciones = new ArrayList<>();
        for (Habitacion habitacion : reserva.getLista_habitaciones()){
            habitaciones.add("Habitacion "+ habitacion.getNumero());
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this,R.layout.my_spinner_desing, habitaciones);
        spinner_habitaciones.setAdapter(adapter);
    }

    public void mostrarInformacionHabitacion(){
        Intent activity = new Intent(this, VerHabitaciones.class);
        activity.putExtra("id_reserva", reserva.getId_reserva());
        startActivity(activity);
    }


}
