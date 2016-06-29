package com.juanjiga.vescla;

// basado en Codigo Alonso, esta clase + Constantes
// es equivalente a la clase DataBaseAdapter

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseClaves {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    public DataBaseClaves(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }
    private void openReadableDB() {
        db = dataBaseHelper.getReadableDatabase();
    }
    private void openWriteableDB() {
        db = dataBaseHelper.getWritableDatabase();
    }
    private void closeDB() {
        if (db != null)
            db.close();
    }
    //metodo para no repetir Values
    private ContentValues clavesMapper(Clave clave) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constantes.USUARIO, clave.getUsuario());
        contentValues.put(Constantes.PASSWORD, clave.getPassword());
        return contentValues;
    }
    //metodos  "CRUD"

    //CREATE   insertar
    public long insertarClave(Clave clave) {
        this.openWriteableDB();
        long rowID = db.insert(Constantes.TABLA_CLAVES, null, clavesMapper(clave));
        this.closeDB();
        return rowID;
    }

    //READ      leer
    public ArrayList listaClaves() {
        ArrayList lista = new ArrayList<>();
        this.openReadableDB();
        String[] campos = new String[]{Constantes.ID, Constantes.NOMBRE,
                Constantes.USUARIO, Constantes.PASSWORD};
        Cursor cursor = db.query(Constantes.TABLA_CLAVES, campos, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                Clave clave = new Clave();
                clave.setId(cursor.getInt(0));
                clave.setNombre(cursor.getString(1));
                clave.setUsuario(cursor.getString(2));
                clave.setPassword(cursor.getString(3));
                lista.add(clave);
            }
        } finally {
            cursor.close();
        }
        this.closeDB();
        return lista;
    }
    // READ     buscar
    public Clave buscarClave(String nombre) {
        Clave clave = new Clave();
        this.openReadableDB();
        String where = Constantes.NOMBRE + "= ?";
        String[] whereArgs = {nombre};
        Cursor cursor = db.query(Constantes.TABLA_CLAVES, null,
                where, whereArgs, null, null, null);

        if (cursor != null || cursor.getCount() <= 0) {
            cursor.moveToFirst();
            clave.setId(cursor.getInt(0));
            clave.setNombre(cursor.getString(1));
            clave.setUsuario(cursor.getString(2));
            clave.setPassword(cursor.getString(3));
            cursor.close();
        }
        this.closeDB();
        return clave;
    }

    //UPDATE   actualizar  modificar
    public void updateClave(Clave clave) {
        this.openWriteableDB();
        String where = Constantes.ID+ "= ?";
        db.update(Constantes.TABLA_CLAVES, clavesMapper(clave),
                where, new String[]{String.valueOf(clave.getId())});
        db.close();
    }

    //DELETE   borrar
    public void deleteClave(int id) {
        this.openWriteableDB();
        String where = Constantes.ID + "= ?";
        db.delete(Constantes.TABLA_CLAVES, where, new String[]{String.valueOf(id)});
        this.closeDB();
    }

    //clase interna Helper
    private static class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Constantes.DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLA_CLAVES);
            onCreate(db);
        }
    }
}