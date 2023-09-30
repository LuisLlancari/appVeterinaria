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

import java.util.function.Function;

public class BuscarCliente extends AppCompatActivity {
    ConexionSQLiteHelper conexion;
    EditText etNombre,etApellido, etTelefono, etDireccion, etEmail, etFechaNacimiento, etIDcliente;

    Button btModificarCliente, btEliminarCliente, btBuscarCliente, btReiniciarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cliente);

        conexion = new ConexionSQLiteHelper(getApplicationContext(),"bdcliente",null,1);
        loadUI();

        btBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCliente();
            }
        });

        btEliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarEliminar();
            }
        });

        btModificarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preguntarModificar();
            }
        });
        btReiniciarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarUI();
            }
        });

    }

    private void preguntarModificar(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Veterinaria");
        dialogo.setMessage("¿Esta seguro de modificar esta Cliente?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ModificarCliente();
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
        dialogo.setMessage("¿Esta seguro de Eliminar esta Cliente?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EliminarCliente();
            }
        });

        dialogo.setNegativeButton("canelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        dialogo.show();

    }

    private void buscarCliente(){
        //PASO 1: permiso
        SQLiteDatabase db = conexion.getReadableDatabase();

        //PASO 2: Arreglo con los datos a buscar
        String[] campoCriterio = {etIDcliente.getText().toString()};

        //PASO 3: Campos a obtener (retorno)
        String[] campos = {"nombre", "apellido", "telefono", "email", "direccion", "fechanacimiento"};

        //PASO 4:Exepciones
        try{
            //PASO 5:Ejecutar la consulta
            Cursor cursor = db.query("cliente",campos,"idcliente=?",campoCriterio, null, null, null);
            cursor.moveToFirst();

            //PASO 6: Cursor envia informacion cajas
            etNombre.setText(cursor.getString(0));
            etApellido.setText(cursor.getString(1));
            etTelefono.setText(cursor.getString(2));
            etEmail.setText(cursor.getString(3));
            etDireccion.setText(cursor.getString(4));
            etFechaNacimiento.setText(cursor.getString(5));

            // Terminar....

            //PASO 7: Cerrar el cursor
            cursor.close();

        }
        catch (Exception e){
            Toast.makeText(this,"Datos no encontrados", Toast.LENGTH_LONG).show();
        }

    }


    private void EliminarCliente(){
        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDcliente.getText().toString()};

        int clienteEliminado = db.delete("cliente","idcliente=?",campoCriterio);




        if(clienteEliminado > 0){
            notificar("Datos Eliminados con éxito");
            reiniciarUI();
        }
        else{notificar("Error al Eliminar datos");}
    }


    private void ModificarCliente(){

        SQLiteDatabase db = conexion.getWritableDatabase();
        String[] campoCriterio = {etIDcliente.getText().toString()};
        ContentValues parametros = new ContentValues();

        parametros.put("nombre",etNombre.getText().toString());
        parametros.put("apellido",etApellido.getText().toString());
        parametros.put("telefono",etTelefono.getText().toString());
        parametros.put("email",etEmail.getText().toString());
        parametros.put("direccion",etDireccion.getText().toString());
        parametros.put("fechanacimiento",etFechaNacimiento.getText().toString());


        int clientemodificado = db.update("cliente", parametros,"dclientei=?",campoCriterio);


        if(clientemodificado > 0){
            notificar("Datos Actualizados con éxito");
            reiniciarUI();
        }
        else{notificar("Error al actualizar datos");}

    }

    private void notificar(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    private void reiniciarUI(){
        etIDcliente.setText(null);
        etNombre.setText(null);
        etApellido.setText(null);
        etTelefono.setText(null);
        etEmail.setText(null);
        etDireccion.setText(null);
        etFechaNacimiento.setText(null);
    }

    private void loadUI(){
        etNombre     = findViewById(R.id.etBuscarNombreCliente);
        etApellido   = findViewById(R.id.etBuscarApellidoCliente);
        etTelefono   = findViewById(R.id.etBuscarTelefonoCliente);
        etEmail      = findViewById(R.id.etBuscarEmailCliente);
        etDireccion  = findViewById(R.id.etBuscarDireccionCliente);
        etFechaNacimiento = findViewById(R.id.etBuscarFechaNacimientoCliente);
        etIDcliente       = findViewById(R.id.etBuscaridCliente);

        btModificarCliente = findViewById(R.id.btEditarRegistroCliente);
        btEliminarCliente = findViewById(R.id.btEliminarRegistroCliente);
        btBuscarCliente = findViewById(R.id.btnBuscarCliente);
        btReiniciarCliente = findViewById(R.id.btnReiniciarCliente);

    }
}