package com.example.apphotelera.ServicesAsync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.apphotelera.DetallesReserva;
import com.example.apphotelera.GeneradorDeListas.ListarAcompañantes;
import com.example.apphotelera.Modelos.Acompañantes;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.apphotelera.GeneradorDeListas.ListarAcompañantes.ViewHolder.txt_nombre1_search;


public class ServiceSearchTercero extends AsyncTask<Void, Void, String> {

    private Context httpContext;//contexto
    ProgressDialog progressDialog;//dialogo cargando
    public String resultadoapi="";
    public String identificacion_tercero="";
    public String clave="";
    public String codeError="";
    public Acompañantes acompañante_search = new Acompañantes();
    public Activity activity;
    public int posicion;
    public String id_habitacion;
    public String id_reserva;
    List<Acompañantes> lista  = new ArrayList<Acompañantes>();


    public ServiceSearchTercero(Context ctx, Activity activity, String identificacion_tercero, int posicion, String id_habitacion){
        this.httpContext=ctx;
        this.identificacion_tercero=identificacion_tercero;
        this.activity = activity;
        this.posicion = posicion;
        this.id_habitacion = id_habitacion;
        this.id_reserva = DetallesReserva.reserva.getId_reserva();
        this.lista = DetallesReserva.reserva.getLista_acompañantes();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog = ProgressDialog.show(httpContext, "Procesando Solicitud", "Validando credenciales...");
    }

    //login
    @Override
    protected String doInBackground(Void... params) {
        String wsURL = new ApiRoutes().SearchTercero + identificacion_tercero;
        URL url = null;
        try {

            url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            JSONObject parametrosPost= new JSONObject();

            urlConnection.setReadTimeout(15000);
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

                JSONArray jsonArray = null;

                String mensaje = jo.optString("statusText");
                codeError = jo.optString("statusCode");
                String errors = jo.optString("errors");
                JSONObject info = new JSONObject();


                if (codeError.equals("200")) {
                    info = jo.optJSONObject("tercero");
                    if (info!=null){

                    acompañante_search.setId_tercero(info.getString("id_tercero"));
                    acompañante_search.setIdentificacion(info.getString("identificacion"));
                    acompañante_search.setNombre1(info.getString("nombre1"));
                    acompañante_search.setNombre2(info.getString("nombre2"));
                    acompañante_search.setApellido1(info.getString("apellido1"));
                    acompañante_search.setApellido2(info.getString("apellido2"));
                    acompañante_search.setTelefono_movil(info.getString("telefono_movil"));
                    acompañante_search.setDireccion(info.getString("direccion"));
                    acompañante_search.setEmail(info.getString("email"));
                    return "ok";
                    }
                    return "no exite";

                }
                return mensaje;
            }
            else{
                //Toast.makeText(httpContext,"Ocurrio un error al procesar la peticion",Toast.LENGTH_LONG).show();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error de red, revise su conexion";
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
        //progressDialog.dismiss();
        resultadoapi=s;

        boolean yaExiste = false;

        for (Acompañantes acom : lista){
            if (acom.getIdentificacion() != null) {
                if (acom.getIdentificacion().equals(acompañante_search.getIdentificacion()) && acom.getPosicion_in_lista() != posicion) {
                    yaExiste = true;
                }
            }
        }

        if (yaExiste == true) {
            Toast.makeText(httpContext, "USTED ESTA INTENTANDO REGISTRAR UN HUESPED QUE YA PERTENECE A ESTA RESERVA", Toast.LENGTH_SHORT).show();
            acompañante_search.setIdentificacion("");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    DetallesReserva.reserva.getLista_acompañantes().get(posicion).setIdentificacion(acompañante_search.getIdentificacion());
                    DetallesReserva.ListarAcompañantes(httpContext, activity, id_habitacion);

                }
            });
        }
        else {
        if (codeError.equals("200")){
            if (resultadoapi.equals("ok")) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DetallesReserva.reserva.getLista_acompañantes().get(posicion).setIdentificacion(acompañante_search.getIdentificacion());
                        DetallesReserva.reserva.getLista_acompañantes().get(posicion).setEmail(acompañante_search.getEmail());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setNombre1(acompañante_search.getNombre1());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setNombre2(acompañante_search.getNombre2());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setApellido1(acompañante_search.getApellido1());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setApellido2(acompañante_search.getApellido2());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setDireccion(acompañante_search.getDireccion());
                            DetallesReserva.reserva.getLista_acompañantes().get(posicion).setTelefono_movil(acompañante_search.getTelefono_movil());
                            DetallesReserva.ListarAcompañantes(httpContext, activity, id_habitacion);

                    }
                });

            }
        }
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
