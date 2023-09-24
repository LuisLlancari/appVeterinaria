package com.senati.veterinaria;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper  extends SQLiteOpenHelper {

    final String MASCOTA =  "" +
            "CREATE TABLE 'mascota'("+
            "'idmascota'    INTEGER  NOT NULL,"+
            "'nombre'       TEXT     NOT NULL,"+
            "'tipo'         TEXT     NOT NULL,"+
            "'raza'         TEXT     NOT NULL,"+
            "'peso'         INTEGER  NOT NULL,"+
            "'color'        TEXT     NOT NULL,"+
            "PRIMARY KEY ('idmascota' AUTOINCREMENT)"+
            ")";

    final String CLIENTE =  "" +
            "CREATE TABLE 'cliente'("+
            "'idcliente'    INTEGER   NOT NULL,"+
            "'nombre'       TEXT      NOT NULL,"+
            "'apellido'     TEXT      NOT NULL,"+
            "'telefono'     INTEGER   NOT NULL,"+
            "'email'        TEXT      NOT NULL,"+
            "'direccion'    TEXT      NOT NULL,"+
            "'fechanacimiento'    TEXT     NOT NULL,"+
            "PRIMARY KEY ('idcliente' AUTOINCREMENT)"+
            ")";



    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(MASCOTA);
        sqLiteDatabase.execSQL(CLIENTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS mascota");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS cliente");
        onCreate(sqLiteDatabase);
    }
}
