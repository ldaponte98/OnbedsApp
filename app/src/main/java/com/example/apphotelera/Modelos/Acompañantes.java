package com.example.apphotelera.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.Herramientas.Config;

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

    private Context context;

    public Acompañantes(){
    }

    public boolean Save(){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            ContentValues registro = new ContentValues();
            registro.put("id_tercero", id_tercero);
            registro.put("identificacion", identificacion);
            registro.put("nombre1", nombre1);
            registro.put("nombre2",nombre2);
            registro.put("apellido1",apellido1);
            registro.put("apellido2",apellido2);
            registro.put("telefono_movil", telefono_movil);
            registro.put("direccion",direccion);
            registro.put("email",email);
            registro.put("id_reserva",id_reserva);
            basededatos.insert("acompañantes", null, registro);
            basededatos.close();

            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public Acompañantes FindByPK(String id){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from acompañantes where id_tercero = "+id, null);

            if (cursor.moveToFirst()) {
                do {
                    Acompañantes acompañante = new Acompañantes();
                    acompañante.setId_acompañante(Integer.parseInt(cursor.getString(0)));
                    acompañante.setId_tercero(cursor.getString(1));
                    acompañante.setIdentificacion(cursor.getString(2));
                    acompañante.setNombre1(cursor.getString(3));
                    acompañante.setNombre2(cursor.getString(4));

                    acompañante.setApellido1(cursor.getString(5));
                    acompañante.setApellido2(cursor.getString(6));
                    acompañante.setTelefono_movil(cursor.getString(7));
                    acompañante.setDireccion(cursor.getString(8));
                    acompañante.setEmail(cursor.getString(9));
                    acompañante.setId_reserva(cursor.getString(10));

                    basededatos.close();
                    return acompañante;
                } while (cursor.moveToNext());

            }
            return null;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return null;
        }
    }

    public List<Acompañantes> FindBy(String columna, String value){
        List<Acompañantes> lista = new ArrayList<>();

        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from acompañantes where "+columna+" = '"+value+"'", null);

            if (cursor.moveToFirst()) {
                do {
                    Acompañantes acompañante = new Acompañantes();
                    acompañante.setId_acompañante(Integer.parseInt(cursor.getString(0)));
                    acompañante.setId_tercero(cursor.getString(1));
                    acompañante.setIdentificacion(cursor.getString(2));
                    acompañante.setNombre1(cursor.getString(3));
                    acompañante.setNombre2(cursor.getString(4));

                    acompañante.setApellido1(cursor.getString(5));
                    acompañante.setApellido2(cursor.getString(6));
                    acompañante.setTelefono_movil(cursor.getString(7));
                    acompañante.setDireccion(cursor.getString(8));
                    acompañante.setEmail(cursor.getString(9));
                    acompañante.setId_reserva(cursor.getString(10));
                    lista.add(acompañante);

                    basededatos.close();
                } while (cursor.moveToNext());
                    return lista;
            }
            return null;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return null;
        }
    }

    public List<Acompañantes> FindByReserva(String id_reserva){
        List<Acompañantes> lista = new ArrayList<>();

        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from acompañantes where id_reserva = '"+id_reserva+"'", null);

            if (cursor.moveToFirst()) {
                do {
                    Acompañantes acompañante = new Acompañantes();
                    acompañante.setId_acompañante(Integer.parseInt(cursor.getString(0)));
                    acompañante.setId_tercero(cursor.getString(1));
                    acompañante.setIdentificacion(cursor.getString(2));
                    acompañante.setNombre1(cursor.getString(3));
                    acompañante.setNombre2(cursor.getString(4));

                    acompañante.setApellido1(cursor.getString(5));
                    acompañante.setApellido2(cursor.getString(6));
                    acompañante.setTelefono_movil(cursor.getString(7));
                    acompañante.setDireccion(cursor.getString(8));
                    acompañante.setEmail(cursor.getString(9));
                    acompañante.setId_reserva(cursor.getString(10));
                    lista.add(acompañante);

                    basededatos.close();
                } while (cursor.moveToNext());
                return lista;
            }
            return null;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return null;
        }
    }


    public List<Acompañantes> All(){
        List<Acompañantes> lista = new ArrayList<>();

        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from acompañantes", null);

            if (cursor.moveToFirst()) {
                do {
                    Acompañantes acompañante = new Acompañantes();
                    acompañante.setId_acompañante(Integer.parseInt(cursor.getString(0)));
                    acompañante.setId_tercero(cursor.getString(1));
                    acompañante.setIdentificacion(cursor.getString(2));
                    acompañante.setNombre1(cursor.getString(3));
                    acompañante.setNombre2(cursor.getString(4));

                    acompañante.setApellido1(cursor.getString(5));
                    acompañante.setApellido2(cursor.getString(6));
                    acompañante.setTelefono_movil(cursor.getString(7));
                    acompañante.setDireccion(cursor.getString(8));
                    acompañante.setEmail(cursor.getString(9));
                    acompañante.setId_reserva(cursor.getString(10));
                    lista.add(acompañante);

                    basededatos.close();
                } while (cursor.moveToNext());
                return lista;
            }
            return null;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return null;
        }
    }



    public Acompañantes(Context context) {
        this.context = context;
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
}
