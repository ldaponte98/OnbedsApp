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
    private List<Acompañantes> lista_acompañantes = new ArrayList<Acompañantes>();
    private List<Habitacion> lista_habitaciones = new ArrayList<Habitacion>();
    private Context context;
    public static List<Reserva> BD_Reservas = new ArrayList<Reserva>();
    public Reserva(){

    }
    public Reserva(Context context) {
        this.setContext(context);
    }

    public Reserva FindByPK(String id){
            for (Reserva reserva: BD_Reservas) {
                if (reserva.getId_reserva().equals(id)) return reserva;
            }
            return null;
    }



    public List<Reserva> FindAll(){
        try{
            return BD_Reservas;
        } catch (Exception excepcion) {
            return null;
        }
    }


    public boolean Save() {
        try
        {
            BD_Reservas.add(this);
            SaveAcompañantes();
            SaveHabitaciones();
            return true;
        } catch (Exception excepcion) {
            return false;
        }
    }

    public boolean SaveAcompañantes() {
        try {
            for(Acompañantes acompañante: this.lista_acompañantes){
                acompañante.Save();
            }
            return true;
        }catch (Exception e){
            return false;
        }
        
    }

    public boolean SaveHabitaciones() {
        try {
            for(Habitacion habitacion: this.lista_habitaciones){
                habitacion.Save();
            }
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public Habitacion FindHabitacion(String id_habitacion){
        try {
            int total = 0;
            for(Habitacion habitacion: this.lista_habitaciones){
               if (habitacion.getId_habitacion().equals(id_habitacion)){
                   return habitacion;
               }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    public List<Acompañantes> FindAcompañantesByHabitacion(String id_habitacion){
        try {
            List<Acompañantes> lista = new ArrayList<Acompañantes>();
            for(Acompañantes acompañante: this.lista_acompañantes){
                if (acompañante.getId_habitacion().equals(id_habitacion)){
                    lista.add(acompañante);
                }
            }
            return lista;
        }catch (Exception e){
            return null;
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
        return lista_acompañantes;
    }

    public void setLista_acompañantes(List<Acompañantes> Lista_acompañantes) {
        this.lista_acompañantes = Lista_acompañantes;
    }


    public List<Habitacion> getLista_habitaciones() {
        return lista_habitaciones;
    }

    public void setLista_habitaciones(List<Habitacion> lista_habitaciones) {
        this.lista_habitaciones = lista_habitaciones;
    }


}
