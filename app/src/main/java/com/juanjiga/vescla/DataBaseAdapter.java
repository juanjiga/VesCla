package com.juanjiga.vescla;

// basado en Jesús Conde, equivalente
// a las clases DataBaseClaves + Constantes

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAdapter {

    //nombre y versión de la base de datos
    public static final String DB_NAME = "vescladb.db";
    public static final int DB_VERSION = 1;

    //nombre de la tabla
    public static final String TABLA_CLAVES = "claves";

    //nombres de los campos (columnas)
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String COL_USUARIO = "usuario";
    public static final String COL_PASSWORD = "password";

    //indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_NOMBRE = INDEX_ID +1;
    public static final int INDEX_USUARIO = INDEX_ID + 2;
    public static final int INDEX_PASSWORD = INDEX_ID + 3;

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    private final Context context;

    //sentencia SQL para crear la base de datos
    private static final String DATABASE_CREATE = "CREATE TABLE if not exist " +
            " ( " + ID + " INTEGER PRIMARY KEY autoincrement, " +
            COL_USUARIO + " TEXT, " +
            COL_PASSWORD + " TEXT );";

    //constructor
    public DataBaseAdapter(Context context) {
        this.context = context;
    }

    //metodo para abrir la base de datos
    public void abrir() throws SQLException {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    //metodo para cerrar la base de datos
    public void cerrar() {
        if (dataBaseHelper != null)
            dataBaseHelper.close();

    }

    //metodos "CRUD"
    //CREATE
    public void crearClave(String nombre, String usuario, String password) {
        ContentValues values = new ContentValues();
        values.put(NOMBRE, nombre);
        values.put(COL_USUARIO, usuario);
        values.put(COL_PASSWORD, password);
        db.insert(TABLA_CLAVES, null, values);
    }
    //sobrecarga del metodo
    public long crearClave(Clave clave) {
        ContentValues values = new ContentValues();
        values.put(NOMBRE, clave.getNombre());
        values.put(COL_USUARIO, clave.getUsuario());
        values.put(COL_PASSWORD, clave.getPassword());
        return db.insert(TABLA_CLAVES, null, values);
    }

    //READ
    public Clave leerClave(int id) {
        Cursor cursor = db.query(TABLA_CLAVES, new String[]{ID, COL_USUARIO, COL_PASSWORD},
                ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new Clave(cursor.getInt(INDEX_ID), cursor.getString(INDEX_NOMBRE),
                cursor.getString(INDEX_USUARIO), cursor.getString(INDEX_PASSWORD));
    }

    public Cursor leertodoClave() {
        Cursor cursor = db.query(TABLA_CLAVES, new String[]{ID,
                COL_USUARIO, COL_PASSWORD}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    //UPDATE
    public void updateClave(Clave clave) {
        ContentValues values = new ContentValues();
        values.put(NOMBRE, clave.getNombre());
        values.put(COL_USUARIO, clave.getUsuario());
        values.put(COL_PASSWORD, clave.getPassword());
        db.update(TABLA_CLAVES, values, ID + "=?", new String[]{String.valueOf(clave.getId())});
    }

    //DELETE
    public void deleteClaveById(int id) {
        db.delete(TABLA_CLAVES, ID + "=?", new String[]{String.valueOf(id)});
    }

    public void borraTodo() {
        db.delete(TABLA_CLAVES, null, null);
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