package com.senati.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListarCliente extends AppCompatActivity {
    ConexionSQLiteHelper conexion;
    ListView lvRegistrosClientes;
    ArrayList<String> listarInformacion;
    ArrayList<Cliente> listarCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);

        loadUI();
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdcliente",null,1);

        ConsultarListaCliente();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listarInformacion);

        lvRegistrosClientes.setAdapter(adaptador);

        lvRegistrosClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Email  :" + listarCliente.get(position).getEmail()+ "\n";
                mensaje += "Fecha nacimiento :" + listarCliente.get(position).getFechanacimiento();
                Toast.makeText(ListarCliente.this,mensaje,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ConsultarListaCliente(){

        SQLiteDatabase db = conexion.getReadableDatabase();

        Cliente cliente = null;

        listarCliente = new ArrayList<Cliente>();

        Cursor cursor = db.rawQuery("SELECT * FROM cliente", null);

        while (cursor.moveToNext()){

            cliente = new Cliente();
            cliente.setIdcliente(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setApellido(cursor.getString(2));
            cliente.setTelefono(cursor.getInt(3));
            cliente.setEmail(cursor.getString(4));
            cliente.setDireccion(cursor.getString(5));
            cliente.setFechanacimiento(cursor.getString(6));

            listarCliente.add(cliente);
        }

        obtenerLista();

    }

    private void obtenerLista(){
        //Paso 1: Construimos nuestra lista con los  datos a mostrar
        listarInformacion = new ArrayList<String>();
        // Paso 2 : Recorremos la coleccion de clientes
        for(int i = 0; i < listarCliente.size(); i++){
            //paso 3: Enviamos la informacion de la primera lista a la segunda
            listarInformacion.add(listarCliente.get(i).getApellido()+ " " + listarCliente.get(i).getNombre() +"    " + listarCliente.get(i).getDireccion());

        }


    }

    private void loadUI(){

        lvRegistrosClientes = findViewById(R.id.lvListarCliente);
    }
}