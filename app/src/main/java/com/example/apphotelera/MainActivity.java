package com.example.apphotelera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.Herramientas.Config;
import com.example.apphotelera.Rutas.ApiRoutes;
import com.example.apphotelera.ServicesAsync.ServiceLogin;
import com.example.apphotelera.ServicesAsync.ServiceReservas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email;
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseDeDatos bd = new BaseDeDatos(this, new Config().NameBD, null, 1);
        SQLiteDatabase basededatos = bd.getWritableDatabase();
        Cursor fila = basededatos.rawQuery("select * from usuario", null);
        if (fila.moveToFirst()) {
            ServiceReservas servicio = new ServiceReservas(this);
            servicio.execute();
            basededatos.close();
        }else{
            setContentView(R.layout.activity_main);
            email = (EditText) findViewById(R.id.txtema);
            clave = (EditText) findViewById(R.id.txtcon);
        }
    }

    public void Login(View view){
        String email= this.email.getText().toString().trim();
        String clave= this.clave.getText().toString().trim();
        if (email.length()==0 || clave.length()==0){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_LONG).show();
        }else {
            ServiceLogin service = new ServiceLogin(this, new ApiRoutes().Auth,email, clave);
            service.execute();
            this.email.setText("");
            this.clave.setText("");
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.options) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    int contadoratras = 0;
    @Override
    public void onBackPressed() {
        if (contadoratras==0){
            Toast.makeText(this, "Presione nuevamente para salir", Toast.LENGTH_LONG).show();
            contadoratras++;
        }else{
            super.onBackPressed();
            moveTaskToBack(true);
            System.exit(0);
            this.finish();

        }
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                contadoratras = 0;
            }
        };
    }
}
