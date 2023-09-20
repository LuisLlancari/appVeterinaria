package com.senati.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

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
    Button btBuscarMascota;

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
            Toast.makeText(this,"Error consulta", Toast.LENGTH_LONG).show();
        }

    }


    private void LoadUI() {

        etBNombreMascota = findViewById(R.id.etBuscarNombreMascota);
        etBTipoMascota = findViewById(R.id.etBuscarTipoMascota);
        etBRazaMascota = findViewById(R.id.etBuscarRazaMascota);
        etBPesoMascota = findViewById(R.id.etBuscarPesoMascota);
        etBColorMascota = findViewById(R.id.etBuscarColorMascota);
        etIDMascota = findViewById(R.id.etIdMascota);

        btBuscarMascota = findViewById(R.id.btnBusquedaMascota);



    }
}