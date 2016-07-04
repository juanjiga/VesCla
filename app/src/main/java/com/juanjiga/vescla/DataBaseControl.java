package com.juanjiga.vescla;

//DataBaseClaves y Constantes unidas en 
//una sola Clase, equivalente a DataBaseAdapter

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseControl {

    //Nombre de la base de datos y versión
    public static final String DB_NAME = "vescladb.db";
    public static final int DB_VERSION = 1;

    //Nombre de la tabla "claves" y nombres de los campos (columnas)
    public static final String TABLA_CLAVES = "claves";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";

    //Setencia para crear la base de datos
    public static final String DATABASE_CREATE =
            "CREATE TABLE  " + TABLA_CLAVES + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NOMBRE + "TEXT NOT NULL," +
                    USUARIO + " TEXT," +
                    PASSWORD   + " TEXT);" ;

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    //Constructor
    public DataBaseControl(Context context) {
            dataBaseHelper = new DataBaseHelper(context);
        }
    //metodos para abrir y cerrar la base de datos
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
    private ContentValues valores(Clave clave) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(USUARIO, clave.getUsuario());
            contentValues.put(PASSWORD, clave.getPassword());
            return contentValues;
    }

    //metodos  "CRUD"

    //CREATE   insertar
    public long insertarClave(Clave clave) {
            this.openWriteableDB();
            long rowID = db.insert(TABLA_CLAVES, null, valores(clave));
            this.closeDB();
            return rowID;
        }

    //READ      leer
    public ArrayList listaClaves() {
            ArrayList lista = new ArrayList<>();
            this.openReadableDB();
            Cursor cursor = db.query(TABLA_CLAVES, null, NOMBRE + "=?",
                    new String[]{ID, NOMBRE, USUARIO, PASSWORD}, null, null, null, null);
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
            Cursor cursor = db.query(TABLA_CLAVES, null, "=?", new String[]{nombre},
                    null, null, null);

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
            db.update(TABLA_CLAVES, valores(clave),
                    ID + "=?", new String[]{String.valueOf(clave.getId())});
            db.close();
        }

    //DELETE   borrar
        public void deleteClave(int id) {
            this.openWriteableDB();
            db.delete(TABLA_CLAVES, ID + "=?", new String[]{String.valueOf(id)});
            this.closeDB();
        }

    //clase interna Helper
    private static class DataBaseHelper extends SQLiteOpenHelper {
            DataBaseHelper(Context context) {
                super(context, DB_NAME, null, DB_VERSION);
            }
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DATABASE_CREATE);
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLAVES);
                onCreate(db);
            }
        }

    }
