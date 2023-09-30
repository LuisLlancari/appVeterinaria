package com.senati.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarMascota extends AppCompatActivity {
    EditText etNombreMascota, etTipoMascota, etRazaMascota, etPesoMascota, etColorMascota;
    Button   btRegistrarMascota, btAbrirBusqueda, btnAbrirListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        loadUI();
        btRegistrarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarCampos();
            }
        });

        btAbrirBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BuscarMascota.class));
            }
        });

        btnAbrirListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ListarMascota.class));
            }
        });

    }

    public void ValidarCampos(){
        String nombre, tipo, raza, color;
        int peso;
        nombre = etNombreMascota.getText().toString();
        tipo = etTipoMascota.getText().toString();
        raza = etRazaMascota.getText().toString();
        color = etColorMascota.getText().toString();

        peso =(etPesoMascota.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etPesoMascota.getText().toString());
        //peso = etPesoMascota.getText().toString();

        if(nombre.isEmpty() || tipo.isEmpty() || raza.isEmpty() || color.isEmpty() || peso == 0){
            notificar("completa formulario");
        }else {
            preguntar();
        }

    }
    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de registrar?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RegistrarPrestamo();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();

    }

    private void RegistrarPrestamo(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "bdmascota",null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();

        parametros.put("nombre",etNombreMascota.getText().toString());
        parametros.put("tipo",etTipoMascota.getText().toString());
        parametros.put("raza",etRazaMascota.getText().toString());
        parametros.put("peso",etPesoMascota.getText().toString());
        parametros.put("color",etColorMascota.getText().toString());

        long idobtenido = db.insert("mascota","idmascota",parametros);

        notificar("Datos guardados correctamente - " + String.valueOf(idobtenido));
        reiniciarUI();
        etNombreMascota.requestFocus();

    }

    private void reiniciarUI(){
        etNombreMascota.setText(null);
        etTipoMascota.setText(null);
        etRazaMascota.setText(null);
        etPesoMascota.setText(null);
        etColorMascota.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    public void loadUI(){
        etNombreMascota = findViewById(R.id.etNombreMascota);
        etTipoMascota = findViewById(R.id.etTipoMascota);
        etRazaMascota = findViewById(R.id.etRazaMascota);
        etPesoMascota = findViewById(R.id.etPesoMascota);
        etColorMascota = findViewById(R.id.etColorMascota);


        btRegistrarMascota = findViewById(R.id.btRegistrarMascota);
        btAbrirBusqueda = findViewById(R.id.btAbrirBusqueda);
        btnAbrirListar = findViewById(R.id.btAbrirListarMascota);


    }
}