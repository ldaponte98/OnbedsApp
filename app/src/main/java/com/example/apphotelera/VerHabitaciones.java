package com.example.apphotelera;

import android.os.Bundle;

import com.example.apphotelera.GeneradorDeListas.ListarHabitaciones;
import com.example.apphotelera.Modelos.Reserva;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class VerHabitaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_habitaciones);


        Reserva reserva = new Reserva(this);
        reserva = reserva.FindByPK(getIntent().getStringExtra("id_reserva"));
        getSupportActionBar().setTitle("Onbeds Xperience");

        RecyclerView Recycler_ListarHabitaciones = (RecyclerView) findViewById(R.id.recyclerVerHabitaciones);
        Recycler_ListarHabitaciones.setLayoutManager(new LinearLayoutManager(this));
        ListarHabitaciones AdaptadorDeListas = new ListarHabitaciones(reserva.getLista_habitaciones(),this);
        Recycler_ListarHabitaciones.setAdapter(AdaptadorDeListas);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
