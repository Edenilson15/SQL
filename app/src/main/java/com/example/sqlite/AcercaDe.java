package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AcercaDe extends AppCompatActivity {

    TextView tvNombre, tvRedSocial, tvCel, tvDireccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_personales);

        tvNombre = findViewById(R.id.tvnombre);
        tvRedSocial = findViewById(R.id.tvred);
        tvCel = findViewById(R.id.tvcel);
        tvDireccion = findViewById(R.id.tvdireccion);

        informacion();
    }

    public void informacion (){

        tvNombre.setText("Nombre del Tecnico: Ricardo Edenilson Gutierres Mauricio");
        tvRedSocial.setText("Carrera: Tecnico En Sitemas Informaticos");
        tvCel.setText("Telefono: 75742609");
        tvDireccion.setText("Direccion : San Vicente, Tecoluca.");

    }
}