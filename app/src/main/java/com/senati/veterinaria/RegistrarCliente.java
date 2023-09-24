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

public class RegistrarCliente extends AppCompatActivity {

    EditText etNombre, etApellido, etTelefono, etEmail, etDireccion, etFechaNacimiento;
    Button btAbrirBusqueda,btRegistrarCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        loadUI();
        btAbrirBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BuscarCliente.class));
            }
        });

        btRegistrarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarCampos();
            }
        });
    }

    public void ValidarCampos(){
        String nombre, apellido, email, direccion, fechanacimiento;
        int telefono;
        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        email = etEmail.getText().toString();
        direccion = etDireccion.getText().toString();
        fechanacimiento = etFechaNacimiento.getText().toString();

        telefono =(etTelefono.getText().toString().trim().isEmpty()) ? 0 : Integer.parseInt(etTelefono.getText().toString());


        if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || direccion.isEmpty() || fechanacimiento.isEmpty() || telefono == 0){
            notificar("completa formulario");
        }else {
            preguntar();
        }
    }

    private void preguntar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de registrar al cliente?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                registrarCliente();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();

    }

    private void registrarCliente(){
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "bdcliente",null,1);

        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues parametros = new ContentValues();

        parametros.put("nombre",etNombre.getText().toString());
        parametros.put("apellido",etApellido.getText().toString());
        parametros.put("telefono",etTelefono.getText().toString());
        parametros.put("email",etEmail.getText().toString());
        parametros.put("direccion",etDireccion.getText().toString());
        parametros.put("fechanacimiento",etFechaNacimiento.getText().toString());

        long idobtenido = db.insert("cliente","idcliente",parametros);

        notificar("Datos guardados correctamente - " + String.valueOf(idobtenido));
        reiniciarUI();
        etNombre.requestFocus();

    }

    private void reiniciarUI(){
        etNombre.setText(null);
        etApellido.setText(null);
        etTelefono.setText(null);
        etEmail.setText(null);
        etDireccion.setText(null);
        etFechaNacimiento.setText(null);
    }

    private void notificar(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }


    private void loadUI(){
        etNombre     = findViewById(R.id.etNombreCliente);
        etApellido   = findViewById(R.id.etApellidoCliente);
        etTelefono   = findViewById(R.id.etTelefonoCliente);
        etEmail      = findViewById(R.id.etEmailCliente);
        etDireccion  = findViewById(R.id.etDireccionCliente);
        etFechaNacimiento = findViewById(R.id.etFechaNacimientoCliente  );

        btAbrirBusqueda = findViewById(R.id.btAbrirBusquedaCli);
        btRegistrarCliente = findViewById(R.id.btRegistrarCliente);

    }


}