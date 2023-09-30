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

public class ListarMascota extends AppCompatActivity {

    ConexionSQLiteHelper conexion;
    ListView lvRegistrosMascota;
    ArrayList<String> listarInformacion;
    ArrayList<Mascota> listarMascota;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mascota);
        loadUI();
        conexion = new ConexionSQLiteHelper(getApplicationContext(), "bdmascota",null,1);

        ConsultarListaMascota();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listarInformacion);

        lvRegistrosMascota.setAdapter(adaptador);

        lvRegistrosMascota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String mensaje = "";
                mensaje += "Peso  :" + listarMascota.get(position).getPeso()+ "\n";
                mensaje += "Color :" + listarMascota.get(position).getColor();
                Toast.makeText(ListarMascota.this,mensaje,Toast.LENGTH_LONG).show();
            }
        });

    }
    private void ConsultarListaMascota(){

        SQLiteDatabase db = conexion.getReadableDatabase();

        Mascota mascota = null;

        listarMascota = new ArrayList<Mascota>();

        Cursor cursor = db.rawQuery("SELECT * FROM mascota", null);

        while (cursor.moveToNext()){

            mascota = new Mascota();
            mascota.setIdmascota(cursor.getInt(0));
            mascota.setNombre(cursor.getString(1));
            mascota.setTipo(cursor.getString(2));
            mascota.setRaza(cursor.getString(3));
            mascota.setPeso(cursor.getInt(4));
            mascota.setColor(cursor.getString(5));

            listarMascota.add(mascota);
        }

        obtenerLista();

    }

    private void obtenerLista(){
        //Paso 1: Construimos nuestra lista con los  datos a mostrar
        listarInformacion = new ArrayList<String>();
        // Paso 2 : Recorremos la coleccion de personas
        for(int i = 0; i < listarMascota.size(); i++){
            //paso 3: Enviamos la informacion de la primera lista a la segunda
            listarInformacion.add(listarMascota.get(i).getNombre()+ "      " + listarMascota.get(i).getTipo() + "      " + listarMascota.get(i).getRaza());

        }
    }

    private void loadUI(){
        lvRegistrosMascota = findViewById(R.id.lvListarMascota);
    }
}