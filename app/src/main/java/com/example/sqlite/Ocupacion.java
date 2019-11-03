package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ocupacion extends AppCompatActivity {
    private EditText etocupacion,etid;
    private Button btnguardar;
    boolean aviso1 = false;
    AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this);
    Dto1 datos1 = new Dto1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocupacion);
        etocupacion = findViewById(R.id.etocupacion);
        etid = findViewById(R.id.etid);
        btnguardar = findViewById(R.id.btngenerar);


    }

    public void generar(View view) {
        if (etocupacion.getText().toString().length() == 0) {

            etocupacion.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;

        }
        if (aviso1) {

            try {
                datos1.setTipo(etocupacion.getText().toString());

                if (conexion.Guarda(datos1)) {
                    Toast.makeText(this, "Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                } else {
                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro\n" + " Código: " + etocupacion.getText().toString(), Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
            } catch (Exception e) {
                Toast.makeText(this, "ERROR. Ya existe.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void limpiarDatos(){
        etocupacion.setText(null);
        etocupacion.requestFocus();
    }

}
