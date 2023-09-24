package com.senati.veterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnAbrirMascota, btnAbrirCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadUI();
        btnAbrirMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegistrarMascota.class));
            }
        });

        btnAbrirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrarCliente.class));
            }
        });

    }

    private void loadUI(){
        btnAbrirMascota = findViewById(R.id.btAbriMascota);
        btnAbrirCliente = findViewById(R.id.btAbriCliente);
    }
}