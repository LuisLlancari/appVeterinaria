package com.senati.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BuscarMascota extends AppCompatActivity {

    ConexionSQLiteHelper conexion;
    EditText etBNombreMascota, etBTipoMascota, etBRazaMascota, etBPesoMascota, etBColorMascota, etIDMascota;
    Button btBuscarMascota,btModificarMascota,btEliminarMascota,btReinicarMascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_mascota);

        LoadUI();
        conexion = new ConexionSQLiteHelper(getApplicationContext(),"bdmascota",null,1);


        btBuscarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarMascota();
            }
        });

        btModificarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ValidarCampos();

            }
        });

        btEliminarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarEliminar();
            }
        });

        btReinicarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarUI();
            }
        });
        
    }


    public void ValidarCampos(){
        String nombre, tipo, raza, color;
        int peso;
        nombre = etBNombreMascota.getText().toString();
        tipo = etBTipoMascota.getText().toString();
        raza = etBRazaMascota.getText().toString();
        color = etBColorMascota.getText().toString();

        peso =(etBPesoMascota.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etBPesoMascota.getText().toString());
        //peso = etPesoMascota.getText().toString();

        if(nombre.isEmpty() || tipo.isEmpty() || raza.isEmpty() || color.isEmpty() || peso == 0){
            notificar("completa formulario");
        }else {
            preguntarModificar();
        }

    }


    private void preguntarModificar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de modificar esta Mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActualizarMascota();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();

    }


    private void preguntarEliminar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de eliminar esta mascota?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EliminarMascota();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();

    }


    private void ActualizarMascota(){

        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDMascota.getText().toString()};
        ContentValues parametros = new ContentValues();

        parametros.put("nombre",etBNombreMascota.getText().toString());
        parametros.put("tipo",etBTipoMascota.getText().toString());
        parametros.put("raza",etBRazaMascota.getText().toString());
        parametros.put("peso",etBPesoMascota.getText().toString());
        parametros.put("color",etBColorMascota.getText().toString());


        int mascotaactualizada = db.update("mascota", parametros,"idmascota=?",campoCriterio);
        if(mascotaactualizada > 0){
            notificar("Datos Actualizados con éxito");
            reiniciarUI();
        }
        else{notificar("Error al Actualizar datos");}
    }


    private void EliminarMascota(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDMascota.getText().toString()};

        int mascotaeliminada = db.delete("mascota","idmascota=?",campoCriterio);
        if(mascotaeliminada > 0){
            notificar("Datos Eliminados con éxito");
            reiniciarUI();
        }
        else{notificar("Error al Eliminar datos");}
    }


    private void reiniciarUI(){
        etIDMascota.setText(null);
        etBNombreMascota.setText(null);
        etBTipoMascota.setText(null);
        etBRazaMascota.setText(null);
        etBPesoMascota.setText(null);
        etBColorMascota.setText(null);
    }


    private void buscarMascota(){
        //PASO 1: permiso
        SQLiteDatabase db = conexion.getReadableDatabase();

        //PASO 2: Arreglo con los datos a buscar
        String[] campoCriterio = {etIDMascota.getText().toString()};

        //PASO 3: Campos a obtener (retorno)
        String[] campos = {"nombre", "tipo", "raza", "peso", "color"};

        //PASO 4:Exepciones
        try{
            //PASO 5:Ejecutar la consulta
            Cursor cursor = db.query("mascota",campos,"idmascota=?",campoCriterio, null, null, null);
            cursor.moveToFirst();

            //PASO 6: Cursor envia informacion cajas
            etBNombreMascota.setText(cursor.getString(0));
            etBTipoMascota.setText(cursor.getString(1));
            etBRazaMascota.setText(cursor.getString(2));
            etBPesoMascota.setText(cursor.getString(3));
            etBColorMascota.setText(cursor.getString(4));

            // Terminar....

            //PASO 7: Cerrar el cursor
            cursor.close();

        }
        catch (Exception e){
            Toast.makeText(this,"Datos no encontrados", Toast.LENGTH_LONG).show();
        }

    }


    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }


    private void LoadUI() {

        etBNombreMascota = findViewById(R.id.etBuscarNombreMascota);
        etBTipoMascota = findViewById(R.id.etBuscarTipoMascota);
        etBRazaMascota = findViewById(R.id.etBuscarRazaMascota);
        etBPesoMascota = findViewById(R.id.etBuscarPesoMascota);
        etBColorMascota = findViewById(R.id.etBuscarColorMascota);
        etIDMascota = findViewById(R.id.etIdMascota);

        btBuscarMascota = findViewById(R.id.btnBusquedaMascota);
        btModificarMascota= findViewById(R.id.btnModificarRegistroMascota);
        btEliminarMascota = findViewById(R.id.btEliminarRegistroMascota);
        btReinicarMascota = findViewById(R.id.btnReiniciarMascota);

    }


}