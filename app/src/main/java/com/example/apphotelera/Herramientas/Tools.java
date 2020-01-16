package com.example.apphotelera.Herramientas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    public static String FormatoDeFecha1(String fecha){
        SimpleDateFormat formatearfecha = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatearfecha.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formateadorMesDia = new SimpleDateFormat("MMM d");
        SimpleDateFormat formateadorAño = new SimpleDateFormat("yyyy");
        String MesDia = formateadorMesDia.format(date);
        MesDia = Character.toUpperCase(MesDia.charAt(0)) + MesDia.substring(1,MesDia.length());
        return MesDia + " del "+formateadorAño.format(date);
    }
    public String FechaFormato4(String fecha){//recibe yyyy-mm-dd
        if (fecha.equals("null")) return "No registrada";
        String[] f = fecha.split("-");
        String dia = f[2];
        String mes = f[1];
        String año = f[0];
        return dia + " de "+GetNameMes(mes)+ " de "+año;
    }

    public String GetNameMes(String numero) {
        switch (numero) {
            case "01":
                return "Enero";
            case "02":
                return "Febrero";
            case "03":
                return "Marzo";
            case "04":
                return "Abril";
            case "05":
                return "Mayo";
            case "06":
                return "Junio";
            case "07":
                return "Julio";
            case "08":
                return "Agosto";
            case "09":
                return "Septiembre";
            case "10":
                return "Octubre";
            case "11":
                return "Noviembre";
            case "12":
                return "Diciembre";
            default:
                return "Indefinido";
        }
    }

    public String FormatoOracion(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }
}
