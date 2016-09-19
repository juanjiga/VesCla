package com.juanjiga.vescla.conde;

// basado en Jesús Conde, equivalente
// a las clases ADataBaseClaves + AConstantes

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.juanjiga.vescla.Clave;

public class DataBaseAdapter {

    //nombre y versión de la base de datos
    public static final String DB_NAME = "vescladb.db";
    public static final int DB_VERSION = 1;

    //nombre de la tabla
    public static final String T_CLAVES = "claves";

    //nombres de los campos (columnas)
    public static final String C_ID = "_id";
    public static final String C_NOMBRE = "nombre";
    public static final String C_USUARIO = "usuario";
    public static final String C_PASSWORD = "password";

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
            " ( " + C_ID + " INTEGER PRIMARY KEY autoincrement, " + C_NOMBRE + " TEXT NOT NULL, " +
            C_USUARIO + " TEXT, " +
            C_PASSWORD + " TEXT );";

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
        values.put(C_NOMBRE, nombre);
        values.put(C_USUARIO, usuario);
        values.put(C_PASSWORD, password);
        db.insert(T_CLAVES, null, values);
    }
    //sobrecarga del metodo
    public long crearClave(Clave clave) {
        ContentValues values = new ContentValues();
        values.put(C_NOMBRE, clave.getNombre());
        values.put(C_USUARIO, clave.getUsuario());
        values.put(C_PASSWORD, clave.getPassword());
        return db.insert(T_CLAVES, null, values);
    }

    //READ
    public Clave leerClave(int id) {
        Cursor cursor = db.query(T_CLAVES, new String[]{C_ID, C_USUARIO, C_PASSWORD},
                C_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new Clave(cursor.getInt(INDEX_ID), cursor.getString(INDEX_NOMBRE),
                cursor.getString(INDEX_USUARIO), cursor.getString(INDEX_PASSWORD));
    }

    public Cursor leertodoClave() {
        Cursor cursor = db.query(T_CLAVES, new String[]{C_ID,
                C_USUARIO, C_PASSWORD}, null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    //UPDATE
    public void updateClave(Clave clave) {
        ContentValues values = new ContentValues();
        values.put(C_NOMBRE, clave.getNombre());
        values.put(C_USUARIO, clave.getUsuario());
        values.put(C_PASSWORD, clave.getPassword());
        db.update(T_CLAVES, values, C_ID + "=?", new String[]{String.valueOf(clave.getId())});
    }

    //DELETE
    public void deleteClaveById(int id) {
        db.delete(T_CLAVES, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void borraTodo() {
        db.delete(T_CLAVES, null, null);
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
            db.execSQL("DROP TABLE IF EXISTS " + T_CLAVES);
            onCreate(db);
        }
    }


}