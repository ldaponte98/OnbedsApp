package com.example.apphotelera.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.Herramientas.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reserva {
    private String id_reserva;
    private String clase;
    private String estado;
    private String fecha_check_in;
    private String fecha_check_out;
    private String fecha_inicio;
    private String fecha_fin;
    private String huesped;
    private String huesped_identidad;
    private Hotel hotel;
    private String observaciones;
    private String tipo;
    private String numero_reserva;
    private String numero_habitacion;
    private Integer cant_adulto;
    private Integer cant_niño;
    private List<Acompañantes> Lista_acompañantes = new ArrayList<>();
    private Context context;
    public Reserva(){

    }
    public Reserva(Context context) {
        this.setContext(context);
    }

    public Reserva FindByPK(String id){

        try {
            BaseDeDatos bd = new BaseDeDatos(getContext(), new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from reservas where id_reserva =" + id, null);
            SimpleDateFormat formatearfecha = new SimpleDateFormat("yyyy-MM-dd");

            if (cursor.moveToFirst()) {
                do {
                    Reserva reserva = new Reserva();
                    reserva.setId_reserva(cursor.getString(0));
                    reserva.setClase(cursor.getString(1));
                    reserva.setEstado(cursor.getString(2));
                    String f = cursor.getString(3);

                    reserva.setFecha_check_in(cursor.getString(3));
                    reserva.setFecha_check_out(cursor.getString(4));
                    reserva.setFecha_inicio(cursor.getString(5));
                    reserva.setFecha_fin(cursor.getString(6));


                    reserva.setHuesped(cursor.getString(7));
                    reserva.setHuesped_identidad(cursor.getString(8));
                    reserva.setObservaciones(cursor.getString(9));
                    reserva.setTipo(cursor.getString(10));
                    reserva.setNumero_reserva(cursor.getString(11));
                    reserva.setNumero_habitacion(cursor.getString(12));

                    Hotel hotel = new Hotel();
                    hotel.setId(cursor.getString(13));
                    hotel.setNombre(cursor.getString(14));
                    hotel.setDireccion(cursor.getString(15));
                    hotel.setTelefono(cursor.getString(16));
                    hotel.setEmail(cursor.getString(17));
                    hotel.setPais(cursor.getString(18));
                    hotel.setCiudad(cursor.getString(19));
                    reserva.setHotel(hotel);

                    reserva.setCant_adulto(Integer.parseInt(cursor.getString(20)));
                    reserva.setCant_niño(Integer.parseInt(cursor.getString(21)));


                    reserva.setLista_acompañantes(new Acompañantes(context).FindByReserva(reserva.getId_reserva()));

                    basededatos.close();
                    return reserva;
                } while (cursor.moveToNext());

            }
            return null;
        }catch (Exception exc){
            System.out.println("Excepcion en buscar la reserva : "+ exc.getMessage());
            return null;
        }
    }

    public Reserva FindBy(String columna, String buscador){

        try {
            BaseDeDatos bd = new BaseDeDatos(getContext(), new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from reservas where " + columna + "='" + buscador +"'", null);
            SimpleDateFormat formatearfecha = new SimpleDateFormat("yyyy-MM-dd");

            if (cursor.moveToFirst()) {
                do {
                    Reserva reserva = new Reserva();
                    reserva.setId_reserva(cursor.getString(0));
                    reserva.setClase(cursor.getString(1));
                    reserva.setEstado(cursor.getString(2));

                    reserva.setFecha_check_in(cursor.getString(3));
                    reserva.setFecha_check_out(cursor.getString(4));
                    reserva.setFecha_inicio(cursor.getString(5));
                    reserva.setFecha_fin(cursor.getString(6));

                    reserva.setHuesped(cursor.getString(7));
                    reserva.setHuesped_identidad(cursor.getString(8));
                    reserva.setObservaciones(cursor.getString(9));
                    reserva.setTipo(cursor.getString(10));
                    reserva.setNumero_reserva(cursor.getString(11));
                    reserva.setNumero_habitacion(cursor.getString(12));

                    Hotel hotel = new Hotel();
                    hotel.setId(cursor.getString(13));
                    hotel.setNombre(cursor.getString(14));
                    hotel.setDireccion(cursor.getString(15));
                    hotel.setTelefono(cursor.getString(16));
                    hotel.setEmail(cursor.getString(17));
                    hotel.setPais(cursor.getString(18));
                    hotel.setCiudad(cursor.getString(19));

                    reserva.setHotel(hotel);

                    reserva.setCant_adulto(Integer.parseInt(cursor.getString(20)));
                    reserva.setCant_niño(Integer.parseInt(cursor.getString(21)));
                    reserva.setLista_acompañantes(new Acompañantes(context).FindByReserva(reserva.getId_reserva()));
                    basededatos.close();
                    return reserva;
                } while (cursor.moveToNext());

            }
            return null;
        }catch (Exception exc){
            System.out.println("Excepcion en buscar la reserva : "+ exc.getMessage());
            return null;
        }
    }

    public List<Reserva> FindAll(){
        List<Reserva> lista = new ArrayList<>();

        try {
            BaseDeDatos bd = new BaseDeDatos(getContext(), new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from reservas",null);
            if (cursor.moveToFirst()) {
                do {
                    Reserva reserva = new Reserva();
                    reserva.setId_reserva(cursor.getString(0));
                    reserva.setClase(cursor.getString(1));
                    reserva.setEstado(cursor.getString(2));
                    String f = cursor.getString(3);

                    reserva.setFecha_check_in(cursor.getString(3));
                    reserva.setFecha_check_out(cursor.getString(4));
                    reserva.setFecha_inicio(cursor.getString(5));
                    reserva.setFecha_fin(cursor.getString(6));


                    reserva.setHuesped(cursor.getString(7));
                    reserva.setHuesped_identidad(cursor.getString(8));
                    reserva.setObservaciones(cursor.getString(9));
                    reserva.setTipo(cursor.getString(10));
                    reserva.setNumero_reserva(cursor.getString(11));
                    reserva.setNumero_habitacion(cursor.getString(12));

                    Hotel hotel = new Hotel();
                    hotel.setId(cursor.getString(13));
                    hotel.setNombre(cursor.getString(14));
                    hotel.setDireccion(cursor.getString(15));
                    hotel.setTelefono(cursor.getString(16));
                    hotel.setEmail(cursor.getString(17));
                    hotel.setPais(cursor.getString(18));
                    hotel.setCiudad(cursor.getString(19));

                    reserva.setHotel(hotel);

                    reserva.setCant_adulto(Integer.parseInt(cursor.getString(20)));
                    reserva.setCant_niño(Integer.parseInt(cursor.getString(21)));
                    reserva.setLista_acompañantes(new Acompañantes(context).FindByReserva(reserva.getId_reserva()));
                    lista.add(reserva);
                } while (cursor.moveToNext());
                basededatos.close();
            }
            return lista;
        } catch (Exception excepcion) {
            return null;
        }
    }


    public boolean Save() {
        SimpleDateFormat formatearFecha = new SimpleDateFormat("yyyy-MM-dd");
        BaseDeDatos bd = new BaseDeDatos(getContext(), new Config().NameBD, null, 1);
        SQLiteDatabase basededatos = bd.getWritableDatabase();
        try {


            ContentValues registro = new ContentValues();
            registro.put("id_reserva", getId_reserva());
            registro.put("clase", getClase());
            registro.put("estado", getEstado());
            registro.put("fecha_check_in", getFecha_check_in());
            registro.put("fecha_check_out", getFecha_check_out());
            registro.put("fecha_inicio", getFecha_inicio());
            registro.put("fecha_fin", getFecha_fin());
            registro.put("huesped", getHuesped());
            registro.put("huesped_identidad", getHuesped_identidad());
            registro.put("observaciones", getObservaciones());
            registro.put("tipo", getTipo());
            registro.put("id_hotel", getHotel().getId());
            registro.put("hotel_nombre", getHotel().getNombre());
            registro.put("hotel_direccion", getHotel().getDireccion());
            registro.put("hotel_telefono", getHotel().getTelefono());
            registro.put("hotel_email", getHotel().getEmail());
            registro.put("hotel_pais", getHotel().getPais());
            registro.put("hotel_ciudad", getHotel().getCiudad());
            registro.put("numero_reserva", getNumero_reserva());
            registro.put("numero_habitacion", getNumero_habitacion());
            registro.put("cant_adulto", getCant_adulto());
            registro.put("cant_niño", getCant_niño());

            long n = basededatos.insert("reservas", null, registro);
            basededatos.close();
            SaveAcompañantes();
            return true;
        } catch (Exception excepcion) {
            return false;
        }
    }

    public boolean SaveAcompañantes() {
        try {
            for(Acompañantes acompañante: this.Lista_acompañantes){
                acompañante.Save();
            }
            return true;
        }catch (Exception e){
            return false;
        }
        
    }



    //convertiremos la fecha a oct 20 del 2019



    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_check_in() {
        return fecha_check_in;
    }

    public void setFecha_check_in(String fecha_check_in) {
        this.fecha_check_in = fecha_check_in;
    }

    public String getFecha_check_out() {
        return fecha_check_out;
    }

    public void setFecha_check_out(String fecha_check_out) {
        this.fecha_check_out = fecha_check_out;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getHuesped() {
        return huesped;
    }

    public void setHuesped(String huesped) {
        this.huesped = huesped;
    }

    public String getHuesped_identidad() {
        return huesped_identidad;
    }

    public void setHuesped_identidad(String huesped_identidad) {
        this.huesped_identidad = huesped_identidad;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero_reserva() {
        return numero_reserva;
    }

    public void setNumero_reserva(String numero_reserva) {
        this.numero_reserva = numero_reserva;
    }

    public String getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(String numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public List<Acompañantes> getLista_acompañantes() {
        return Lista_acompañantes;
    }

    public void setLista_acompañantes(List<Acompañantes> Lista_acompañantes) {
        this.Lista_acompañantes = Lista_acompañantes;
    }

    public Integer getCant_adulto() {
        return cant_adulto;
    }

    public void setCant_adulto(Integer cant_adulto) {
        this.cant_adulto = cant_adulto;
    }

    public Integer getCant_niño() {
        return cant_niño;
    }

    public void setCant_niño(Integer cant_niño) {
        this.cant_niño = cant_niño;
    }
}
