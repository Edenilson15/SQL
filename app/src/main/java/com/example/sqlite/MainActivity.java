package com.example.sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnGuardar, btnConsultar1, btnConsultar2, btnBorrar, btnEditar;
    private EditText et1, et2, et3;


    boolean aviso1 = false;
    boolean aviso2 = false;
    boolean aviso3 = false;
    int resultadoInsert = 0;

    modal ventanas = new modal();
    AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Advertencia")
                    .setMessage("¿Realmente desea cerrar esta actividad?")
                    .setNegativeButton(android.R.string.cancel,null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                            finishAffinity();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_cancel));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setTitle("Registrar Empleados");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //     .setAction("Action", null).show();
                ventanas.Search(MainActivity.this);
            }
        });

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnConsultar1 = (Button) findViewById(R.id.btnConsultar1);
        btnConsultar2 = (Button) findViewById(R.id.btnConsultar2);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);


        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

      try{  Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            codigo = bundle.getString("codigo");
            senal = bundle.getString("senal");
            descripcion = bundle.getString("descripcion");
            precio = bundle.getString("precio");
            if(senal.equals("1")){
                et1.setText(codigo);
                et2.setText(descripcion);
                et3.setText(precio);
            }
        }
      }catch (Exception e){

      }
    }

    private void confirmacion(){
        String mensaje ="¿Realmente desea cerrar esta pantalla?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {

            }
        });
        dialogo.show();
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

        if (id == R.id.action_limpiar) {
            et1.setText(null);
            et2.setText(null);
            et3.setText(null);
            return true;
        }else if (id == R.id.action_ListaArticulos) {
            Intent spinnerActivity = new Intent(MainActivity.this,ConsultaSpinner.class);
            startActivity(spinnerActivity);
            return true;
        }else if(id == R.id.action_ListaArticulos1){
            Intent listViewActivity = new Intent(MainActivity.this,ListViewArticulos.class);
            startActivity(listViewActivity);
            return true;
        }else if (id == R.id.acercade) {
        Intent Acercade = new Intent(MainActivity.this, AcercaDe.class);
        startActivity(Acercade);
        return true;
    }else if (id == R.id.genereocupacion) {
            Intent Ocupacion = new Intent(MainActivity.this, Ocupacion.class);
            startActivity(Ocupacion);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void alta(View view) {
        if (et1.getText().toString().length() == 0) {

            et1.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;

        }
        if (et2.getText().toString().length() == 0) {

            et2.setError("NO PUEDE QUEDAR VACIO");
            aviso2 = false;
        } else {
            aviso2 = true;
        }
        if (et3.getText().toString().length() == 0) {

            et3.setError("NO PUEDE QUEDAR VACIO");
            aviso3 = false;
        } else {
            aviso3 = true;
        }
        if (aviso1 && aviso2 && aviso3) {
            // Toast.makeText(this, "Vamos bien", Toast.LENGTH_SHORT).show();
        /*    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = et1.getText().toString();
            String descri = et2.getText().toString();
            String pre = et3.getText().toString();

            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("descripcion", descri);
            registro.put("precio", pre);

            resultadoinsert = (int) bd.insert("articulos", null, registro);
            if (resultadoinsert > 0) {
                Toast.makeText(this, "Se cargaron los datos del artículo", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
            } else {
                //  bd.insert("articulos", null, registro);
                Toast.makeText(this, "Error, existe un artículo con ese código", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }
            bd.close();
            */
        try {
            datos.setCodigo(Integer.parseInt(et1.getText().toString()));
            datos.setDescripcion(et2.getText().toString());
            datos.setPrecio(Double.parseDouble(et3.getText().toString()));

            if (conexion.InserTradicional(datos)){
                Toast.makeText(this,"Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }else {
                Toast.makeText(getApplicationContext(),"Error. Ya existe un registro\n" + " Código: "+et1.getText().toString(),Toast.LENGTH_LONG).show();
                limpiarDatos();
            }
        }catch (Exception e){
            Toast.makeText(this,"ERROR. Ya existe.", Toast.LENGTH_SHORT).show();
        }
        }
    }

    public void mensaje(String mensaje){
        Toast.makeText(this,""+mensaje,Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos(){
        et1.setText(null);
        et2.setText(null);
        et3.setText(null);
        et1.requestFocus();
    }
    public void Consultar1(View view) {/*consultaporcodigo*/
        if (et1.getText().toString().length() == 0) {

            et1.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;

        }
        if (aviso1 == true) {
           /* AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = et1.getText().toString();
            Cursor fila = bd.rawQuery("Select descripcion, precio from articulos where codigo=" + cod, null);*/
           String codigo = et1.getText().toString();
           datos.setCodigo(Integer.parseInt(codigo));
            if (conexion.consultaCodigo(datos)==true) {
                et2.setText(datos.getDescripcion());
                et3.setText(""+datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un artículo con dicho código", Toast.LENGTH_SHORT).show();
                limpiarDatos();

                  }

        } else{
                Toast.makeText(this,"Ingrese el código del artículo a buscar.",Toast.LENGTH_SHORT).show();
        }
    }

    public void consultar2 /*consultapordescripcion*/(View view) {
        if (et2.getText().toString().length() == 0) {

            et2.setError("NO PUEDE QUEDAR VACIO");
            aviso2 = false;
        } else {
            aviso2 = true;
        }

        if (aviso2 == true) {
           /* AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String descri = et2.getText().toString();
            Cursor fila = bd.rawQuery("select codigo, precio from articulos where descripcion='" + descri + "'", null);*/
           String descripcion = et2.getText().toString();
           datos.setDescripcion(descripcion);
            if (conexion.consultarDescripcion(datos)== true) {
                et1.setText(""+datos.getCodigo());
                et2.setText(""+datos.getDescripcion());
                et3.setText(""+datos.getPrecio());
            } else {
                Toast.makeText(this, "No existe un artículo con dicha descripción", Toast.LENGTH_SHORT).show();
                limpiarDatos();

            }
        }else {
            Toast.makeText(this,"Ingrese la descripción del artículo a buscar",Toast.LENGTH_SHORT).show();
        }
    }

    public void Borrar /*eliminarporcodigo*/(View view) {

        if (et1.getText().toString().length() == 0) {

            et1.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;
        }
        if (aviso1 == true) {
            /*AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = et1.getText().toString();
            int cant = bd.delete("articulos", "codigo=" + cod, null);
            bd.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");*/
            String cod = et1.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this,datos)) {
              //  Toast.makeText(this, "Se borró el artículo con dicho código", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            } else {
                Toast.makeText(this, "NO EXISTE UN ARTÍCULO CON DICHO CÓDIGO", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }

    public void Editar/*modificacion*/(View view) {
        if (et1.getText().toString().length() == 0) {

            et1.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;

        }
        if (et2.getText().toString().length() == 0) {

            et2.setError("NO PUEDE QUEDAR VACIO");
            aviso2 = false;
        } else {
            aviso2 = true;
        }
        if (et3.getText().toString().length() == 0) {

            et3.setError("NO PUEDE QUEDAR VACIO");
            aviso3 = false;
        } else {
            aviso3 = true;
        }
        if (aviso1 && aviso2 && aviso3) {
            String cod = et1.getText().toString();
           // datos.setCodigo(Integer.parseInt(cod));
            String descripcion = et2.getText().toString();
            double precio = Double.parseDouble(et3.getText().toString());

          /*  AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
            SQLiteDatabase bd = admin.getWritableDatabase();
            String cod = et1.getText().toString();
            String descri = et2.getText().toString();
            String pre = et3.getText().toString();

            ContentValues registro = new ContentValues();
            registro.put("codigo", cod);
            registro.put("descripcion", descri);
            registro.put("precio", pre);
            int cant = bd.update("articulos", registro, "codigo=" + cod, null);
            bd.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");*/
          datos.setCodigo(Integer.parseInt(cod));
          datos.setDescripcion(descripcion);
          datos.setPrecio(precio);
            if (conexion.modificar(datos)) {

                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "No se han encontrado resultados para la búsqueda específicada", Toast.LENGTH_SHORT).show();

            }
        }
    }
}