package com.example.apphotelera.ServicesAsync;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class ServiceLogin extends AsyncTask<Void, Void, String> {
    
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi="";
        public String linkrequestAPI="";//link  para consumir el servicio rest
        public String email="";
        public String clave="";
        public String codeError="";


        public ServiceLogin(Context ctx, String linkAPI, String email, String clave ){
            this.httpContext=ctx;
            this.linkrequestAPI=linkAPI;
            this.email=email;
            this.clave=clave;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(httpContext, "Procesando Solicitud", "Validando credenciales...");
        }

        //login
        @Override
        protected String doInBackground(Void... params) {
            String wsURL = linkrequestAPI;
            URL url = null;
            try {

                url = new URL(wsURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                JSONObject parametrosPost= new JSONObject();
                parametrosPost.put("email",email);
                parametrosPost.put("clave",clave);

                urlConnection.setReadTimeout(15000);
                urlConnection.setConnectTimeout(15000 );
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(parametrosPost));
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
                        info = jo.optJSONObject("user");
                        Usuario usuario = new Usuario();
                        usuario.setId_tercero(info.getString("id_tercero"));
                        usuario.setIdentificacion(info.getString("identificacion"));
                        usuario.setNombre1(info.getString("nombre1"));
                        usuario.setNombre2(info.getString("nombre2"));
                        usuario.setApellido1(info.getString("apellido1"));
                        usuario.setApellido2(info.getString("apellido2"));
                        usuario.setEstado(info.getString("estado"));
                        usuario.setTelefono_fijo(info.getString("telefono_fijo"));
                        usuario.setTelefono_movil(info.getString("telefono_movil"));
                        usuario.setDireccion(info.getString("direccion"));
                        usuario.setEmail(info.getString("email"));
                        usuario.setClave(clave);
                        usuario.Save(httpContext);
                        return "Bienvenido "+info.getString("nombre1")+" "+info.getString("nombre2")+" "+info.getString("apellido1")+" "+info.getString("apellido2");
                    }
                    return mensaje;
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
            Toast.makeText(httpContext, resultadoapi, Toast.LENGTH_SHORT).show();
            if (codeError.equals("200")){
                ServiceReservas servicio = new ServiceReservas(httpContext);
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
    }
