package com.example.apphotelera;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.apphotelera.Datos.BaseDeDatos;
import com.example.apphotelera.GeneradorDeListas.ListarReservas;
import com.example.apphotelera.Herramientas.Config;
import com.example.apphotelera.Modelos.Reserva;
import com.example.apphotelera.Modelos.Usuario;
import com.example.apphotelera.ServicesAsync.ServiceReservas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.CountDownTimer;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
public class VistaInicial extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Reserva Reserva_Controller = new Reserva(this);
    public RecyclerView Recycler_ListarReserva;
    public ListarReservas AdaptadorDeListas_ListarReserva;
    public Toolbar toolbar;
    public TextView txt_nombre_user_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vista_inicial);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nada", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navegador_botton);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Object lista = Reserva_Controller.FindAll();
        Recycler_ListarReserva = (RecyclerView) findViewById(R.id.recyclerReservas);
        Recycler_ListarReserva.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        AdaptadorDeListas_ListarReserva = new ListarReservas(Reserva_Controller.FindAll(),this);
        Recycler_ListarReserva.setAdapter(AdaptadorDeListas_ListarReserva);
        toolbar.setTitle("Onbeds Xperience");
        //----------------------IMAGEN DEL USUARIO-----------------------


    }



    int contadoratras = 0;
    @Override
    public void onBackPressed() {
        if (contadoratras==0){
            Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_LONG).show();
            contadoratras++;
        }else{
            super.onBackPressed();
            moveTaskToBack(true);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vista_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.optiones_principal) {
            ServiceReservas servicio = new ServiceReservas(this);
            servicio.execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            // Handle the camera action
        } else if (id == R.id.nav_reserva) {

        } else if (id == R.id.nav_check_in) {

        } else if (id == R.id.nav_check_out) {

        } else if (id == R.id.nav_salir) {
                CerrarSesion();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void CerrarSesion(){
        BaseDeDatos bd = new BaseDeDatos(this, new Config().NameBD, null, 1);
        SQLiteDatabase basededatos = bd.getWritableDatabase();
        basededatos.execSQL("delete from usuario");
        basededatos.execSQL("delete from reservas");
        basededatos.execSQL("delete from acompañantes");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
