package com.example.apphotelera.Modelos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Acompañantes {
    private Integer id_acompañante;
    private String id_tercero;
    private String identificacion;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String telefono_movil;
    private String direccion;
    private String email;
    private String id_reserva;
    private String id_habitacion;
    private int posicion_in_lista;
    public static List<Acompañantes> BD_Acompañantes = new ArrayList<Acompañantes>();

    private Context context;

    public Acompañantes(){
    }

    public boolean Save(){
        try {
            setPosicion_in_lista(BD_Acompañantes.size());
            BD_Acompañantes.add(this);
            return true;
        }catch (Exception excepcion) {
            return false;
        }
    }

    public Acompañantes FindByPK(String id){
        for (Acompañantes acompañante: BD_Acompañantes) {
            if (acompañante.getId_acompañante().equals(id)) return acompañante;
        }
        return null;
    }


    public List<Acompañantes> FindByReserva(String id_reserva){
        List<Acompañantes> lista =new ArrayList<Acompañantes>();
        for (Acompañantes acompañante: BD_Acompañantes) {
            if (acompañante.getId_reserva().equals(id_reserva)) lista.add(acompañante);
        }
        return lista;
    }


    public List<Acompañantes> All(){
        return BD_Acompañantes;
    }



    public Acompañantes(Context context) {
        this.setContext(context);
    }



    public String getId_tercero() {
        return id_tercero;
    }

    public void setId_tercero(String id_tercero) {
        this.id_tercero = id_tercero;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getTelefono_movil() {
        return telefono_movil;
    }

    public void setTelefono_movil(String telefono_movil) {
        this.telefono_movil = telefono_movil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(String id_reserva) {
        this.id_reserva = id_reserva;
    }

    public Integer getId_acompañante() {
        return id_acompañante;
    }

    public void setId_acompañante(Integer id_acompañante) {
        this.id_acompañante = id_acompañante;
    }

    public String getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(String id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getPosicion_in_lista() {
        return posicion_in_lista;
    }

    public void setPosicion_in_lista(int posicion_in_lista) {
        this.posicion_in_lista = posicion_in_lista;
    }
}
