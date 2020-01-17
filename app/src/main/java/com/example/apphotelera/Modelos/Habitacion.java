package com.example.apphotelera.Modelos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Habitacion {
    private String id_habitacion;
    private String numero;
    private int cant_adultos;
    private int cant_niños;
    private Context context;

    public static List<Habitacion> BD_Habitaciones = new ArrayList<Habitacion>();

    public Habitacion() {

    }

    public Habitacion(Context context) {
        this.setContext(context);
    }


    public boolean Save(){
        try {
            if (FindByPK(this.id_habitacion)==null) BD_Habitaciones.add(this);
            return true;
        }catch (Exception excepcion) {
            return false;
        }
    }

    public Habitacion FindByPK(String id){
        for (Habitacion habitacion: BD_Habitaciones) {
            if (habitacion.getId_habitacion().equals(id)) return habitacion;
        }
        return null;
    }

    public static List<Habitacion> getBD_Habitaciones() {
        return BD_Habitaciones;
    }

    public static void setBD_Habitaciones(List<Habitacion> BD_Habitaciones) {
        Habitacion.BD_Habitaciones = BD_Habitaciones;
    }


    public List<Habitacion> All(){
        return BD_Habitaciones;
    }


    public String getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(String id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    public int getCant_adultos() {
        return cant_adultos;
    }

    public void setCant_adultos(int cant_adultos) {
        this.cant_adultos = cant_adultos;
    }

    public int getCant_niños() {
        return cant_niños;
    }

    public void setCant_niños(int cant_niños) {
        this.cant_niños = cant_niños;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
