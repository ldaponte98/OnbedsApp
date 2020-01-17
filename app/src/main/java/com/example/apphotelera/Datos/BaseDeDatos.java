package com.example.apphotelera.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDeDatos extends SQLiteOpenHelper {


    public BaseDeDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //TABLA USUARIO
        db.execSQL("create table usuario(" +
                "id_tercero text PRIMARY KEY," +
                "identificacion text," +
                "nombre1 text," +
                "nombre2 text," +
                "apellido1 text," +
                "apellido2 text," +
                "estado text," +
                "telefono_fijo text," +
                "telefono_movil text," +
                "direccion text," +
                "clave text," +
                "email text)");

        /* SE COMENTARON PARA MANEJAR LISTAS EN MEMORIA Y MEJORARAR EL RENDIMIENTO
        //TABLA RESERVAS
        db.execSQL("create table reservas(" +
                "id_reserva text PRIMARY KEY," +
                "clase text," +
                "estado text," +
                "fecha_check_in text," +
                "fecha_check_out text," +
                "fecha_inicio text," +
                "fecha_fin text," +
                "huesped text," +
                "huesped_identidad text," +
                "observaciones text," +
                "tipo text," +
                "numero_reserva text," +
                "numero_habitacion text," +
                "id_hotel text," +
                "hotel_nombre text," +
                "hotel_direccion text," +
                "hotel_telefono text," +
                "hotel_email text," +
                "hotel_pais text," +
                "hotel_ciudad text)");

        //TABLA ACOMPAÑANTES
        db.execSQL("create table acompañantes(" +
                "id_acompañante integer PRIMARY KEY AUTOINCREMENT,"+
                "id_tercero text," +
                "identificacion text," +
                "nombre1 text," +
                "nombre2 text," +
                "apellido1 text," +
                "apellido2 text," +
                "telefono_movil text," +
                "direccion text," +
                "email text," +
                "id_reserva text," +
                "id_habitacion text)");

        //TABLA HABITACIONES
        db.execSQL("create table acompañantes(" +
                "id integer PRIMARY KEY AUTOINCREMENT,"+
                "id_habitacion text,"+
                "numero text," +
                "cant_adultos text," +
                "cant_niños text," +
                "id_reserva text)");

         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists reservas");
        onCreate(db);
    }


}
