package com.example.apphotelera.ServicesAsync;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.DetallesReserva;
import com.example.apphotelera.Herramientas.Config;
import com.example.apphotelera.Modelos.Acompañantes;
import com.example.apphotelera.Modelos.Hotel;
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.Modelos.Usuario;
import com.example.apphotelera.Rutas.ApiRoutes;
import com.example.apphotelera.VistaInicial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class ServiceReservas extends AsyncTask<Void, Void, String> {

    private Context httpContext;//contexto
    ProgressDialog progressDialog;//dialogo cargando
    public String resultadoapi="";
    public String linkrequestAPI="";//link  para consumir el servicio rest
    public String codeError="";
    public boolean redireccionar = true;


    public ServiceReservas(Context ctx){
        this.httpContext=ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (redireccionar) {
            progressDialog = ProgressDialog.show(httpContext, "Procesando Solicitud", "Consultando mis reservas...");
        }else{
            progressDialog = ProgressDialog.show(httpContext, "Actualizando informacion de reservas", "Actualizando mis reservas...");
        }
    }

    //login
    @Override
    protected String doInBackground(Void... params) {

        URL url = null;
        try {
            String id_tercero ="";


            id_tercero = new Usuario().Find(httpContext).getId_tercero();

            String ruta = new ApiRoutes().MisReservas+id_tercero;
            url = new URL(ruta);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(15000 );
            urlConnection.setConnectTimeout(15000 );
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=urlConnection.getResponseCode();// conexion OK?
            if(responseCode== HttpURLConnection.HTTP_OK){
                BufferedReader in= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb= new StringBuffer("");
                String linea="";
                while ((linea=in.readLine())!= null){
                    sb.append(linea);
                    break;
                }
                in.close();
                String json = "";
                json = sb.toString();
                JSONObject jo = null;
                jo = new JSONObject(json);

                JSONArray JSONObject = null;

                String mensaje = jo.optString("statusText");
                codeError = jo.optString("statusCode");
                String errors = jo.optString("errors");
                JSONArray reservas = new JSONArray();


                if (codeError.equals("200")) {
                    BaseDeDatos bd = new BaseDeDatos(httpContext, new Config().NameBD, null, 1);
                    SQLiteDatabase basededatos = bd.getWritableDatabase();
                    basededatos.execSQL("delete from reservas");
                    basededatos.execSQL("delete from acompañantes");
                    basededatos.close();
                    reservas = jo.optJSONArray("reservas");

                    SimpleDateFormat formatearfecha = new SimpleDateFormat("yyyy-MM-dd");
                    for (int i=0; i<reservas.length();i++){
                        JSONObject r = reservas.getJSONObject(i);

                        Reserva reserva = new Reserva(httpContext);
                        reserva.setId_reserva(r.getString("id_reserva"));
                        reserva.setClase(r.getString("clase"));
                        reserva.setEstado(r.getString("estado"));
                        reserva.setFecha_check_in(r.getString("fecha_check_in"));
                        reserva.setFecha_check_out(r.getString("fecha_check_out"));
                        reserva.setFecha_inicio(r.getString("fecha_inicio"));
                        reserva.setFecha_fin(r.getString("fecha_fin"));
                        reserva.setHuesped(r.getString("huesped"));
                        reserva.setHuesped_identidad(r.getString("identificacion"));
                        reserva.setObservaciones(r.getString("observaciones"));
                        reserva.setTipo(r.getString("tipo"));
                        reserva.setNumero_reserva(r.getString("numero_reserva"));
                        reserva.setNumero_habitacion(r.getString("numero_habitacion"));
                        reserva.setCant_adulto(Integer.parseInt(r.getString("cant_adulto")));
                        reserva.setCant_niño(Integer.parseInt(r.getString("cant_niño")));

                        Hotel hotel = new Hotel();
                        hotel.setId(r.getString("id_hotel"));
                        hotel.setNombre(r.getString("hotel"));
                        hotel.setDireccion(r.getString("hotel_direccion"));
                        hotel.setTelefono(r.getString("hotel_telefono"));
                        hotel.setEmail(r.getString("hotel_email"));
                        hotel.setPais(r.getString("hotel_pais"));
                        hotel.setCiudad(r.getString("hotel_ciudad"));
                        reserva.setHotel(hotel);

                        JSONArray acompañantes = r.getJSONArray("acompañantes");
                        for (int j=0; j<acompañantes.length();j++){
                            JSONObject a = acompañantes.getJSONObject(j);

                            Acompañantes acompañante = new Acompañantes(httpContext);
                            acompañante.setId_reserva(a.getString("id_reserva"));
                            acompañante.setId_tercero(a.getString("id_tercero"));
                            acompañante.setIdentificacion(a.getString("identificacion"));
                            acompañante.setNombre1(a.getString("nombre1"));
                            acompañante.setNombre2(a.getString("nombre2"));
                            acompañante.setApellido1(a.getString("apellido1"));
                            acompañante.setApellido2(a.getString("apellido2"));
                            acompañante.setTelefono_movil(a.getString("telefono_movil"));
                            acompañante.setDireccion(a.getString("direccion"));
                            acompañante.setEmail(a.getString("email"));

                            reserva.getLista_acompañantes().add(acompañante);
                        }


                        reserva.Save();
                    }
                }

                return "Reservas actualizadas";
            }
            else{
                Toast.makeText(httpContext,"Ocurrio un error al procesar la peticion",Toast.LENGTH_LONG).show();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "No se pudo actulizar las reservas, revise su conexion";
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return  "";

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        resultadoapi=s;
        Toast.makeText(httpContext, resultadoapi, Toast.LENGTH_LONG).show();
        if (redireccionar == true) {
            Intent actividad = new Intent(httpContext, VistaInicial.class);
            httpContext.startActivity(actividad);
        }
        else{
            Intent activity = new Intent(httpContext, DetallesReserva.class);
            activity.putExtra("id_reserva", DetallesReserva.reserva.getId_reserva());
            httpContext.startActivity(activity);
            Toast.makeText(httpContext, "Informacion actualizada correctamente", Toast.LENGTH_LONG).show();
        }
    }


    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
