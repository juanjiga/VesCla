package com.juanjiga.vescla;

//ADataBaseClaves y AConstantes unidas en
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
    public static final String T_CLAVES = "claves";

    public static final String  _id = "_id";
    public static final String C_NOMBRE = "nombre";
    public static final String C_USUARIO = "usuario";
    public static final String C_PASSWORD = "password";

    //Setencia para crear la base de datos
    public static final String DATABASE_CREATE =
            "CREATE TABLE  " + T_CLAVES + "(" +
                    _id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    C_NOMBRE + " TEXT NOT NULL," +
                    C_USUARIO + " TEXT," +
                    C_PASSWORD + " TEXT);" ;

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    //Constructor
    public DataBaseControl(Context context) {
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
    //metodo para no repetir Values
    private ContentValues valores(Clave clave) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(C_NOMBRE, clave.getNombre());
            contentValues.put(C_USUARIO, clave.getUsuario());
            contentValues.put(C_PASSWORD, clave.getPassword());
            return contentValues;
    }

    //metodos  "CRUD"

    //CREATE   insertar
    public long insertarClave(Clave clave) {
            this.abrirpaescribirDB();
            long rowID = db.insert(T_CLAVES, null, valores(clave));
            this.cerrarDB();
            return rowID;
        }
    public void insertar(String nombre, String usuario, String password){
            this.abrirpaescribirDB();
        ContentValues values = new ContentValues();
        values.put(C_NOMBRE, nombre);
        values.put(C_USUARIO, usuario);
        values.put(C_PASSWORD, password);
        db.insert(T_CLAVES, null, values);
    }
    //READ      leer
    public ArrayList listaClaves() {
            ArrayList lista = new ArrayList<>();
            this.abrirpaleerDB();
            Cursor cursor = db.query(T_CLAVES, null, C_NOMBRE + "=?",
                    new String[]{_id, C_NOMBRE, C_USUARIO, C_PASSWORD}, null, null, null, null);
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
            this.cerrarDB();
            return lista;
        }
    // READ     buscar
    public Clave buscarClave(String nombre) {
            Clave clave = new Clave();
            this.abrirpaleerDB();
            Cursor cursor = db.query(T_CLAVES, null, "=?", new String[]{nombre},
                    null, null, null);

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
            db.update(T_CLAVES, valores(clave),
                    _id + "=?", new String[]{String.valueOf(clave.getId())});
            db.close();
        }

    //DELETE   borrar
        public void deleteClave(int id) {
            this.abrirpaescribirDB();
            db.delete(T_CLAVES, _id + "=?", new String[]{String.valueOf(id)});
            this.cerrarDB();
        }

    //Cursor
    public Cursor cargarCursorClaves(){
        this.abrirpaescribirDB();
        String[] columnas = new String[]{_id, C_NOMBRE, C_USUARIO, C_PASSWORD};
        return db.query(T_CLAVES, columnas, null, null, null, null, null);
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
