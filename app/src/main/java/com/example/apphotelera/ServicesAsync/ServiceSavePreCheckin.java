package com.example.apphotelera.ServicesAsync;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.apphotelera.DetallesReserva;
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
import java.util.Iterator;

public class ServiceSavePreCheckin extends AsyncTask<Void, Void, String> {

    private Context httpContext;//contexto
    ProgressDialog progressDialog;//dialogo cargando
    public String resultadoapi="";
    public Reserva reserva;
    public String clave="";
    public String codeError="";
    public Usuario usuario;


    public ServiceSavePreCheckin(Context ctx, Reserva reserva, Usuario usuario){
        this.httpContext=ctx;
        this.reserva=reserva;
        this.usuario = usuario;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Actualizando informacion", "Por favor espere...");
    }

    //login
    @Override
    protected String doInBackground(Void... params) {
        String wsURL = new ApiRoutes().SavePreCheckin;
        URL url = null;
        try {

            url = new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            JSONObject parametrosPost= new JSONObject();
            parametrosPost.put("reserva",this.ArmarJSON(reserva));

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000 );
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String result = parametrosPost.toString();
            writer.write(result);
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
                    usuario.Save(httpContext);
                    return "ok";
                }
                return "error";

            }
            else{
                Toast.makeText(httpContext,"Ocurrio un error al procesar la peticion",Toast.LENGTH_LONG).show();
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
        progressDialog.dismiss();
        resultadoapi=s;
        if (resultadoapi.equals("ok")){
            ServiceReservas servicio = new ServiceReservas(httpContext);
            servicio.redireccionar = false;
            servicio.execute();

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

    public JSONObject ArmarJSON(Reserva reserva) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        JSONObject jsonInfoTitular = new JSONObject();



        Usuario titular = usuario;
        jsonInfoTitular.put("id_tercero",titular.getId_tercero());
        jsonInfoTitular.put("identificacion",titular.getIdentificacion());
        jsonInfoTitular.put("email",titular.getEmail());
        jsonInfoTitular.put("nombre1",titular.getNombre1());
        jsonInfoTitular.put("nombre2",titular.getNombre2());
        jsonInfoTitular.put("apellido1",titular.getApellido1());
        jsonInfoTitular.put("apellido2",titular.getApellido2());
        jsonInfoTitular.put("direccion",titular.getDireccion());
        jsonInfoTitular.put("telefono_movil",titular.getTelefono_movil());



        JSONArray json_acompañantes = new JSONArray();
        for (Acompañantes acompañante: reserva.getLista_acompañantes()) {
            if (acompañante.getIdentificacion() != null && acompañante.getIdentificacion().length()>1) {
                JSONObject jsonacompañante = new JSONObject();
                jsonacompañante.put("id_tercero", acompañante.getId_tercero());
                jsonacompañante.put("identificacion", acompañante.getIdentificacion());
                jsonacompañante.put("email", acompañante.getEmail());
                jsonacompañante.put("nombre1", acompañante.getNombre1());
                jsonacompañante.put("nombre2", acompañante.getNombre2());
                jsonacompañante.put("apellido1", acompañante.getApellido1());
                jsonacompañante.put("apellido2", acompañante.getApellido2());
                jsonacompañante.put("direccion", acompañante.getDireccion());
                jsonacompañante.put("telefono_movil", acompañante.getTelefono_movil());
                jsonacompañante.put("id_habitacion", acompañante.getId_habitacion());

                json_acompañantes.put(jsonacompañante);
            }
        }

        jsonObject.put("id_reserva", reserva.getId_reserva());
        jsonObject.put("titular", jsonInfoTitular);
        jsonObject.put("acompañantes", json_acompañantes);


        return  jsonObject;
    }
}
