package com.juanjiga.vescla;

// basado en Codigo Alonso, esta clase + AConstantes
// es equivalente a la clase DataBaseAdapter

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ADataBaseClaves {

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    //constructor
    public ADataBaseClaves(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    //metodos para abrir y cerrar la base de datos
    private void abrirpaleerDB() {
        db = dataBaseHelper.getReadableDatabase();
    }
    private void abrirpaescribirDB() {
        db = dataBaseHelper.getWritableDatabase();
    }
    private void cerrarDB() {
        if (db != null)
            db.close();
    }

    //metodo para no repetir Values  ---->>>>>>> valoresssss
    private ContentValues valores(Clave clave) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AConstantes.C_NOMBRE, clave.getNombre());
        contentValues.put(AConstantes.C_USUARIO, clave.getUsuario());
        contentValues.put(AConstantes.C_PASSWORD, clave.getPassword());
        return contentValues;
    }
    //metodos  "CRUD"

    //CREATE   insertar
    public long insertarClave(Clave clave) {
        this.abrirpaescribirDB();
        long rowID = db.insert(AConstantes.T_CLAVES, null, valores(clave));
        this.cerrarDB();
        return rowID;
    }

    //READ     buscar  leer
    public Clave buscarClave(String nombre) {
        Clave clave = new Clave();
        this.abrirpaleerDB();
        String where = AConstantes.C_NOMBRE + "= ?";
        String[] whereArgs = {nombre};
        Cursor cursor = db.query(AConstantes.T_CLAVES, null,
                where, whereArgs, null, null, null);

        if (cursor != null || cursor.getCount() <= 0) {
            cursor.moveToFirst();
            clave.setId(cursor.getInt(0));
            clave.setNombre(cursor.getString(1));
            clave.setUsuario(cursor.getString(2));
            clave.setPassword(cursor.getString(3));
            cursor.close();
        }
        this.cerrarDB();
        return clave;
    }

    //UPDATE   actualizar  modificar
    public void updateClave(Clave clave) {
        this.abrirpaescribirDB();
        String where = AConstantes.C_ID + "= ?";
        db.update(AConstantes.T_CLAVES, valores(clave),
                where, new String[]{String.valueOf(clave.getId())});
        db.close();
    }

    //DELETE   borrar
    public void deleteClave(int id) {
        this.abrirpaescribirDB();
        String where = AConstantes.C_ID + "= ?";
        db.delete(AConstantes.T_CLAVES, where, new String[]{String.valueOf(id)});
        this.cerrarDB();
    }

    public void borraTodo() {
        db.delete(AConstantes.T_CLAVES, null, null);
    }



    //clase interna Helper
    private static class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, AConstantes.DB_NAME, null, AConstantes.DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(AConstantes.DATABASE_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + AConstantes.T_CLAVES);
            onCreate(db);
        }
    }



}
