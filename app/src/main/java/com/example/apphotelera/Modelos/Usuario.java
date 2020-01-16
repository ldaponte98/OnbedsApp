package com.example.apphotelera.Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.Herramientas.Config;

import java.text.SimpleDateFormat;

public class Usuario {
    private String id_tercero;
    private String identificacion;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String estado;
    private String telefono_fijo;
    private String telefono_movil;
    private String direccion;
    private String email;
    private String clave;

    public Usuario() {
    }

    public boolean Save(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            basededatos.execSQL("delete from usuario");
            ContentValues registro = new ContentValues();
            registro.put("id_tercero", id_tercero);
            registro.put("identificacion", identificacion);
            registro.put("nombre1", nombre1);
            registro.put("nombre2",nombre2);
            registro.put("apellido1",apellido1);
            registro.put("apellido2",apellido2);
            registro.put("estado",estado);
            registro.put("telefono_fijo", telefono_fijo);
            registro.put("telefono_movil", telefono_movil);
            registro.put("direccion",direccion);
            registro.put("email",email);
            registro.put("clave", clave);
            basededatos.insert("usuario", null, registro);
            basededatos.close();

            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public boolean Update(Context context){
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
            registro.put("estado",estado);
            registro.put("telefono_fijo", telefono_fijo);
            registro.put("telefono_movil", telefono_movil);
            registro.put("direccion",direccion);
            registro.put("email",email);
            registro.put("clave", clave);
            basededatos.insert("usuario", null, registro);
            basededatos.close();

            return true;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return false;
        }
    }

    public Usuario Find(Context context){
        try {
            BaseDeDatos bd = new BaseDeDatos(context, new Config().NameBD, null, 1);
            SQLiteDatabase basededatos = bd.getWritableDatabase();
            Cursor cursor = basededatos.rawQuery("select * from usuario", null);

            if (cursor.moveToFirst()) {
                do {
                    Usuario usuario = new Usuario();
                    usuario.setId_tercero(cursor.getString(0));
                    usuario.setIdentificacion(cursor.getString(1));
                    usuario.setNombre1(cursor.getString(2));
                    usuario.setNombre2(cursor.getString(3));

                    usuario.setApellido1(cursor.getString(4));
                    usuario.setApellido2(cursor.getString(5));
                    usuario.setEstado(cursor.getString(6));
                    usuario.setTelefono_fijo(cursor.getString(7));

                    usuario.setTelefono_movil(cursor.getString(8));
                    usuario.setDireccion(cursor.getString(9));
                    usuario.setClave(cursor.getString(10));
                    usuario.setEmail(cursor.getString(11));


                    basededatos.close();
                    return usuario;
                } while (cursor.moveToNext());

            }
            return null;
        }catch (Exception excepcion) {
            String mensaje = excepcion.getMessage();
            return null;
        }
    }


    public String getNombreCompleto(){
        return nombre1 + " " +nombre2 + " " +apellido1 + " " +apellido2;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono_fijo() {
        return telefono_fijo;
    }

    public void setTelefono_fijo(String telefono_fijo) {
        this.telefono_fijo = telefono_fijo;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
